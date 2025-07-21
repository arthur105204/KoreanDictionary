package vn.edu.fpt.koreandictionary.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/history")
public class HistoryController {
    private final Map<String, HistoryEntry> history = new ConcurrentHashMap<>();

    public static class HistoryEntry {
        public String word;
        public long timestamp;
        public HistoryEntry() {}
        public HistoryEntry(String word, long timestamp) {
            this.word = word;
            this.timestamp = timestamp;
        }
    }

    @GetMapping
    public List<HistoryEntry> getAllHistory() {
        return new ArrayList<>(history.values());
    }

    @PostMapping
    public ResponseEntity<?> addHistory(@RequestBody HistoryEntry entry) {
        entry.timestamp = System.currentTimeMillis();
        history.put(entry.word, entry);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<?> removeHistory(@RequestParam String word) {
        history.remove(word);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/clear")
    public ResponseEntity<?> clearHistory() {
        history.clear();
        return ResponseEntity.ok().build();
    }
} 