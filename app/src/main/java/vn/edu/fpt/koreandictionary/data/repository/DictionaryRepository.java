package vn.edu.fpt.koreandictionary.data.repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import vn.edu.fpt.koreandictionary.data.AppDatabase;
import vn.edu.fpt.koreandictionary.data.dao.FavoriteDao;
import vn.edu.fpt.koreandictionary.data.dao.SearchHistoryDao;
import vn.edu.fpt.koreandictionary.data.entity.Favorite;
import vn.edu.fpt.koreandictionary.data.entity.SearchHistory;

public class DictionaryRepository {
    
    private SearchHistoryDao searchHistoryDao;
    private FavoriteDao favoriteDao;
    private ExecutorService executorService;
    
    public DictionaryRepository(Context context) {
        AppDatabase database = AppDatabase.getInstance(context);
        searchHistoryDao = database.searchHistoryDao();
        favoriteDao = database.favoriteDao();
        executorService = Executors.newFixedThreadPool(4);
    }
    
    // Search History Operations
    public void insertSearchHistory(String word, int resultCount) {
        executorService.execute(() -> {
            SearchHistory searchHistory = new SearchHistory(word, System.currentTimeMillis(), resultCount);
            searchHistoryDao.insert(searchHistory);
        });
    }
    
    public LiveData<List<SearchHistory>> getAllHistory() {
        return searchHistoryDao.getAllHistory();
    }
    
    public LiveData<List<SearchHistory>> searchHistory(String query) {
        return searchHistoryDao.searchHistory(query);
    }
    
    public void deleteAllHistory() {
        executorService.execute(() -> searchHistoryDao.deleteAllHistory());
    }
    
    public void deleteOldHistory(long timestamp) {
        executorService.execute(() -> searchHistoryDao.deleteOldHistory(timestamp));
    }
    
    // Favorite Operations
    public void addToFavorites(String word, String definition, String pos, String pronunciation) {
        executorService.execute(() -> {
            Favorite favorite = new Favorite(word, definition, pos, pronunciation, System.currentTimeMillis());
            favoriteDao.insert(favorite);
        });
    }
    
    public void removeFromFavorites(String word) {
        executorService.execute(() -> favoriteDao.deleteByWord(word));
    }
    
    public LiveData<List<Favorite>> getAllFavorites() {
        return favoriteDao.getAllFavorites();
    }
    
    public LiveData<List<Favorite>> searchFavorites(String query) {
        return favoriteDao.searchFavorites(query);
    }
    
    public boolean isFavorite(String word) {
        return favoriteDao.isFavorite(word);
    }
    
    public Favorite getFavoriteByWord(String word) {
        return favoriteDao.getFavoriteByWord(word);
    }
    
    public void deleteAllFavorites() {
        executorService.execute(() -> favoriteDao.deleteAllFavorites());
    }
    
    // Cleanup
    public void shutdown() {
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }
    }
} 