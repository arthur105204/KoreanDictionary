package vn.edu.fpt.koreandictionary;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import vn.edu.fpt.koreandictionary.adapter.FavoritesAdapter;
import vn.edu.fpt.koreandictionary.data.entity.Favorite;
import vn.edu.fpt.koreandictionary.viewmodel.DictionaryViewModel;

public class FavoritesActivity extends AppCompatActivity implements FavoritesAdapter.OnFavoriteItemClickListener {
    
    private RecyclerView recyclerViewFavorites;
    private TextView textViewEmpty;
    private FavoritesAdapter adapter;
    private DictionaryViewModel viewModel;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        
        // Setup toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Favorites");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        
        // Initialize views
        recyclerViewFavorites = findViewById(R.id.recyclerViewFavorites);
        textViewEmpty = findViewById(R.id.textViewEmpty);
        FloatingActionButton fabClear = findViewById(R.id.fabClear);
        
        // Setup RecyclerView
        recyclerViewFavorites.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FavoritesAdapter(this, this);
        recyclerViewFavorites.setAdapter(adapter);
        
        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(DictionaryViewModel.class);
        viewModel.setContext(this);
        
        // Load favorites
        viewModel.loadFavorites();
        
        // Observe favorites data
        viewModel.getFavorites().observe(this, this::updateFavorites);
        
        // Setup FAB
        fabClear.setOnClickListener(v -> showClearFavoritesDialog());
    }
    
    private void updateFavorites(List<Favorite> favorites) {
        if (favorites == null || favorites.isEmpty()) {
            recyclerViewFavorites.setVisibility(View.GONE);
            textViewEmpty.setVisibility(View.VISIBLE);
            textViewEmpty.setText("No favorites yet.\nTap the heart icon to add words to favorites!");
        } else {
            recyclerViewFavorites.setVisibility(View.VISIBLE);
            textViewEmpty.setVisibility(View.GONE);
            adapter.updateFavorites(favorites);
        }
    }
    
    private void showClearFavoritesDialog() {
        new AlertDialog.Builder(this)
            .setTitle("Clear Favorites")
            .setMessage("Are you sure you want to remove all favorites?")
            .setPositiveButton("Clear", (dialog, which) -> {
                viewModel.clearFavorites();
            })
            .setNegativeButton("Cancel", null)
            .show();
    }
    
    @Override
    public void onFavoriteItemClick(Favorite favorite) {
        // Navigate back to main activity with the word
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("search_word", favorite.getWord());
        startActivity(intent);
        finish();
    }
    
    @Override
    public void onFavoriteItemLongClick(Favorite favorite) {
        // Show options dialog
        new AlertDialog.Builder(this)
            .setTitle("Favorite Item")
            .setMessage("Word: " + favorite.getWord() + "\nDefinition: " + favorite.getDefinition())
            .setPositiveButton("Search", (dialog, which) -> onFavoriteItemClick(favorite))
            .setNegativeButton("Remove", (dialog, which) -> onRemoveFavorite(favorite))
            .setNeutralButton("Cancel", null)
            .show();
    }
    
    @Override
    public void onRemoveFavorite(Favorite favorite) {
        new AlertDialog.Builder(this)
            .setTitle("Remove Favorite")
            .setMessage("Remove '" + favorite.getWord() + "' from favorites?")
            .setPositiveButton("Remove", (dialog, which) -> {
                viewModel.removeFromFavorites(favorite.getWord());
            })
            .setNegativeButton("Cancel", null)
            .show();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_favorites, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (item.getItemId() == R.id.action_clear_favorites) {
            showClearFavoritesDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
} 