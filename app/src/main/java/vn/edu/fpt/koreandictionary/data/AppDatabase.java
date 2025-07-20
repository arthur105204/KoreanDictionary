package vn.edu.fpt.koreandictionary.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import vn.edu.fpt.koreandictionary.data.dao.FavoriteDao;
import vn.edu.fpt.koreandictionary.data.dao.SearchHistoryDao;
import vn.edu.fpt.koreandictionary.data.entity.Favorite;
import vn.edu.fpt.koreandictionary.data.entity.SearchHistory;

@Database(entities = {SearchHistory.class, Favorite.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    
    private static final String DATABASE_NAME = "korean_dictionary_db";
    private static AppDatabase instance;
    
    public abstract SearchHistoryDao searchHistoryDao();
    public abstract FavoriteDao favoriteDao();
    
    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                context.getApplicationContext(),
                AppDatabase.class,
                DATABASE_NAME
            )
            .fallbackToDestructiveMigration()
            .build();
        }
        return instance;
    }
} 