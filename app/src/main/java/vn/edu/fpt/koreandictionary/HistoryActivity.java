package vn.edu.fpt.koreandictionary;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.google.android.material.textfield.TextInputEditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import vn.edu.fpt.koreandictionary.adapter.SearchHistoryAdapter;
import vn.edu.fpt.koreandictionary.data.entity.SearchHistory;
import vn.edu.fpt.koreandictionary.viewmodel.DictionaryViewModel;

public class HistoryActivity extends AppCompatActivity implements SearchHistoryAdapter.OnHistoryItemClickListener {
    
    private RecyclerView recyclerViewHistory;
    private TextView textViewEmpty;
    private SearchHistoryAdapter adapter;
    private DictionaryViewModel viewModel;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        
        // Setup toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Search History");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        
        // Initialize views
        recyclerViewHistory = findViewById(R.id.recyclerViewHistory);
        textViewEmpty = findViewById(R.id.textViewEmpty);
        FloatingActionButton fabClear = findViewById(R.id.fabClear);
        
        // Setup RecyclerView
        recyclerViewHistory.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SearchHistoryAdapter(this, this);
        recyclerViewHistory.setAdapter(adapter);
        
        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(DictionaryViewModel.class);
        viewModel.setContext(this);
        
        // Load history
        viewModel.loadSearchHistory();
        
        // Observe history data
        viewModel.getSearchHistory().observe(this, this::updateHistory);
        
        // Setup FAB
        fabClear.setOnClickListener(v -> showClearHistoryDialog());
    }
    
    private void updateHistory(List<SearchHistory> history) {
        if (history == null || history.isEmpty()) {
            recyclerViewHistory.setVisibility(View.GONE);
            textViewEmpty.setVisibility(View.VISIBLE);
            textViewEmpty.setText("No search history yet.\nStart searching for Korean words!");
        } else {
            recyclerViewHistory.setVisibility(View.VISIBLE);
            textViewEmpty.setVisibility(View.GONE);
            adapter.updateHistory(history);
        }
    }
    
    private void showClearHistoryDialog() {
        new AlertDialog.Builder(this)
            .setTitle("Clear History")
            .setMessage("Are you sure you want to clear all search history?")
            .setPositiveButton("Clear", (dialog, which) -> {
                viewModel.clearSearchHistory();
            })
            .setNegativeButton("Cancel", null)
            .show();
    }
    
    @Override
    public void onHistoryItemClick(SearchHistory history) {
        // Navigate back to main activity with the word
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("search_word", history.getWord());
        startActivity(intent);
        finish();
    }
    
    @Override
    public void onHistoryItemLongClick(SearchHistory history) {
        // Show options dialog
        new AlertDialog.Builder(this)
            .setTitle("History Item")
            .setMessage("Word: " + history.getWord() + "\nResults: " + history.getResultCount())
            .setPositiveButton("Search", (dialog, which) -> onHistoryItemClick(history))
            .setNegativeButton("Cancel", null)
            .show();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_history, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (item.getItemId() == R.id.action_clear_history) {
            showClearHistoryDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
} 