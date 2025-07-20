package vn.edu.fpt.koreandictionary.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import vn.edu.fpt.koreandictionary.data.entity.Favorite;

@Dao
public interface FavoriteDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Favorite favorite);
    
    @Update
    void update(Favorite favorite);
    
    @Delete
    void delete(Favorite favorite);
    
    @Query("SELECT * FROM favorites ORDER BY timestamp DESC")
    LiveData<List<Favorite>> getAllFavorites();
    
    @Query("SELECT * FROM favorites WHERE word LIKE :searchQuery || '%' ORDER BY timestamp DESC")
    LiveData<List<Favorite>> searchFavorites(String searchQuery);
    
    @Query("SELECT * FROM favorites WHERE word = :word LIMIT 1")
    Favorite getFavoriteByWord(String word);
    
    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE word = :word)")
    boolean isFavorite(String word);
    
    @Query("DELETE FROM favorites WHERE word = :word")
    void deleteByWord(String word);
    
    @Query("DELETE FROM favorites")
    void deleteAllFavorites();
} 