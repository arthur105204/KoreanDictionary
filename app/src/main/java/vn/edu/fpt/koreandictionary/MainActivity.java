package vn.edu.fpt.koreandictionary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import androidx.activity.ComponentActivity;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.ImageButton;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import vn.edu.fpt.koreandictionary.adapter.POSCardAdapter;
import vn.edu.fpt.koreandictionary.model.DictionaryItem;
import vn.edu.fpt.koreandictionary.model.POSGroup;
import vn.edu.fpt.koreandictionary.model.Sense;
import vn.edu.fpt.koreandictionary.viewmodel.DictionaryViewModel;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ComponentActivity {
    private TextInputEditText editTextWord;
    private MaterialButton buttonSearch;
    private ImageButton buttonHistory;
    private ImageButton buttonFavorites;
    private RecyclerView recyclerViewResults;
    private TextView textViewMessage;
    private POSCardAdapter adapter;
    private DictionaryViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextWord = findViewById(R.id.editTextWord);
        buttonSearch = findViewById(R.id.buttonSearch);
        buttonHistory = findViewById(R.id.buttonHistory);
        buttonFavorites = findViewById(R.id.buttonFavorites);
        recyclerViewResults = findViewById(R.id.recyclerViewResults);
        textViewMessage = findViewById(R.id.textViewMessage);
        recyclerViewResults.setLayoutManager(new LinearLayoutManager(this));
        viewModel = new ViewModelProvider(this).get(DictionaryViewModel.class);
        viewModel.setContext(this);

        buttonSearch.setOnClickListener(v -> search());
        buttonHistory.setOnClickListener(v -> openHistory());
        buttonFavorites.setOnClickListener(v -> openFavorites());
        
        // Load favorites and history
        viewModel.loadFavorites();
        viewModel.loadSearchHistory();
        
        // Observe favorites changes to refresh adapter
        viewModel.getFavorites().observe(this, favorites -> {
            if (adapter != null) {
                adapter.refreshFavoriteStates();
            }
        });
        
        editTextWord.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                search();
                return true;
            }
            return false;
        });
        
        // Check if we should search for a word from intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("search_word")) {
            String searchWord = intent.getStringExtra("search_word");
            editTextWord.setText(searchWord);
            search();
        }

        viewModel.getResult().observe(this, resource -> {
            switch (resource.status) {
                case LOADING:
                    textViewMessage.setVisibility(View.VISIBLE);
                    textViewMessage.setText("Loading...");
                    recyclerViewResults.setVisibility(View.GONE);
                    break;
                case SUCCESS:
                    if (resource.data == null || resource.data.isEmpty()) {
                        textViewMessage.setVisibility(View.VISIBLE);
                        textViewMessage.setText("No results found.");
                        recyclerViewResults.setVisibility(View.GONE);
                    } else {
                        textViewMessage.setVisibility(View.GONE);
                        recyclerViewResults.setVisibility(View.VISIBLE);
                        // For each POS group, create a card
                        List<POSGroup> posGroups = resource.data;
                        String baseWord = viewModel.getBaseWord();
                        adapter = new POSCardAdapter(this, posGroups, baseWord);
                        adapter.setViewModel(viewModel);
                        adapter.setOnFavoriteClickListener((word, definition, pos, pronunciation) -> {
                            viewModel.addToFavorites(word, definition, pos, pronunciation);
                        });
                        recyclerViewResults.setAdapter(adapter);
                    }
                    break;
                case ERROR:
                    textViewMessage.setVisibility(View.VISIBLE);
                    textViewMessage.setText(resource.message != null ? resource.message : "Error occurred");
                    recyclerViewResults.setVisibility(View.GONE);
                    break;
            }
        });
    }

    private void search() {
        String word = editTextWord.getText() != null ? editTextWord.getText().toString().trim() : "";
        if (!word.isEmpty()) {
            viewModel.searchWord(word);
        }
    }
    
    private void openHistory() {
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }
    
    private void openFavorites() {
        Intent intent = new Intent(this, FavoritesActivity.class);
        startActivity(intent);
    }
}
