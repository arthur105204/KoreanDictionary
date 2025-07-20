package vn.edu.fpt.koreandictionary.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorites")
public class Favorite {
    @PrimaryKey(autoGenerate = true)
    private int id;
    
    private String word;
    private String definition;
    private String pos;
    private String pronunciation;
    private long timestamp;
    
    public Favorite(String word, String definition, String pos, String pronunciation, long timestamp) {
        this.word = word;
        this.definition = definition;
        this.pos = pos;
        this.pronunciation = pronunciation;
        this.timestamp = timestamp;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getWord() {
        return word;
    }
    
    public void setWord(String word) {
        this.word = word;
    }
    
    public String getDefinition() {
        return definition;
    }
    
    public void setDefinition(String definition) {
        this.definition = definition;
    }
    
    public String getPos() {
        return pos;
    }
    
    public void setPos(String pos) {
        this.pos = pos;
    }
    
    public String getPronunciation() {
        return pronunciation;
    }
    
    public void setPronunciation(String pronunciation) {
        this.pronunciation = pronunciation;
    }
    
    public long getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
} 