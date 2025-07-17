package vn.edu.fpt.koreandictionary.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import vn.edu.fpt.koreandictionary.data.entity.SearchHistory;

@Dao
public interface SearchHistoryDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SearchHistory searchHistory);
    
    @Update
    void update(SearchHistory searchHistory);
    
    @Delete
    void delete(SearchHistory searchHistory);
    
    @Query("SELECT * FROM search_history ORDER BY timestamp DESC LIMIT 10")
    LiveData<List<SearchHistory>> getAllHistory();
    
    @Query("SELECT * FROM search_history WHERE word LIKE :searchQuery || '%' ORDER BY timestamp DESC")
    LiveData<List<SearchHistory>> searchHistory(String searchQuery);
    
    @Query("DELETE FROM search_history")
    void deleteAllHistory();
    
    @Query("DELETE FROM search_history WHERE timestamp < :timestamp")
    void deleteOldHistory(long timestamp);
    
    @Query("SELECT COUNT(*) FROM search_history WHERE word = :word")
    int getSearchCount(String word);
} 