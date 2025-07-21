package vn.edu.fpt.koreandictionary.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/favorites")
public class FavoritesController {
    // In-memory favorites store: word -> FavoriteInfo
    private final Map<String, FavoriteInfo> favorites = new ConcurrentHashMap<>();

    // DTO for favorite info
    public static class FavoriteInfo {
        public String word;
        public String definition;
        public String pos;
        public String pronunciation;
        public long timestamp;

        public FavoriteInfo() {}
        public FavoriteInfo(String word, String definition, String pos, String pronunciation, long timestamp) {
            this.word = word;
            this.definition = definition;
            this.pos = pos;
            this.pronunciation = pronunciation;
            this.timestamp = timestamp;
        }
    }

    @GetMapping
    public List<FavoriteInfo> getAllFavorites() {
        return new ArrayList<>(favorites.values());
    }

    @GetMapping("/is_favorite")
    public boolean isFavorite(@RequestParam String word) {
        return favorites.containsKey(word);
    }

    @PostMapping
    public ResponseEntity<?> addFavorite(@RequestBody FavoriteInfo favorite) {
        favorite.timestamp = System.currentTimeMillis();
        favorites.put(favorite.word, favorite);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<?> removeFavorite(@RequestParam String word) {
        favorites.remove(word);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/clear")
    public ResponseEntity<?> clearFavorites() {
        favorites.clear();
        return ResponseEntity.ok().build();
    }
} 