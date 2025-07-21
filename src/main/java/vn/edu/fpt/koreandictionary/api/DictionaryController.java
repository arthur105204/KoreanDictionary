package vn.edu.fpt.koreandictionary.api;

import org.springframework.web.bind.annotation.*;
import java.util.*;
import vn.edu.fpt.koreandictionary.model.KRDictItem;

@RestController
@RequestMapping("/api/dictionary")
public class DictionaryController {
    // Dummy dictionary data for demonstration
    private static final List<KRDictItem> DICTIONARY = new ArrayList<>();
    static {
        // Populate with some sample data
        KRDictItem item = new KRDictItem();
        item.setWord("안녕하세요");
        item.setPronunciation("annyeonghaseyo");
        item.setPos("interjection");
        item.setOrigin("Korean");
        item.setExample("안녕하세요! 만나서 반가워요.");
        DICTIONARY.add(item);
    }

    @GetMapping
    public List<KRDictItem> search(@RequestParam String query) {
        // Simple search: return items containing the query in the word
        List<KRDictItem> results = new ArrayList<>();
        for (KRDictItem item : DICTIONARY) {
            if (item.getWord() != null && item.getWord().contains(query)) {
                results.add(item);
            }
        }
        return results;
    }
} 