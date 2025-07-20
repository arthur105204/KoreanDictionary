package vn.edu.fpt.koreandictionary.viewmodel;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import vn.edu.fpt.koreandictionary.data.entity.Favorite;
import vn.edu.fpt.koreandictionary.data.entity.SearchHistory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.fpt.koreandictionary.model.DictionaryItem;
import vn.edu.fpt.koreandictionary.model.Example;
import vn.edu.fpt.koreandictionary.model.ExampleItem;
import vn.edu.fpt.koreandictionary.model.ExampleResponse;
import vn.edu.fpt.koreandictionary.model.KRDictResponse;
import vn.edu.fpt.koreandictionary.model.POSGroup;
import vn.edu.fpt.koreandictionary.model.Sense;
import vn.edu.fpt.koreandictionary.data.repository.DictionaryRepository;
import vn.edu.fpt.koreandictionary.network.KRDictApiService;
import vn.edu.fpt.koreandictionary.network.RetrofitClient;
import vn.edu.fpt.koreandictionary.util.ApiKeyManager;
import vn.edu.fpt.koreandictionary.util.Resource;

public class DictionaryViewModel extends ViewModel {
    private final MutableLiveData<Resource<List<POSGroup>>> result = new MutableLiveData<>();
    private final MutableLiveData<List<SearchHistory>> searchHistory = new MutableLiveData<>();
    private final MutableLiveData<List<Favorite>> favorites = new MutableLiveData<>();
    private String baseWord = "";
    private String pos = "";
    private List<Example> examples = new ArrayList<>();
    private Context context;
    private DictionaryRepository repository;

    public LiveData<Resource<List<POSGroup>>> getResult() {
        return result;
    }

    public String getBaseWord() {
        return baseWord;
    }

    public String getPos() {
        return pos;
    }

    public void setContext(Context context) {
        this.context = context;
        if (repository == null) {
            repository = new DictionaryRepository(context);
        }
    }
    
    public LiveData<List<SearchHistory>> getSearchHistory() {
        return searchHistory;
    }
    
    public LiveData<List<Favorite>> getFavorites() {
        return favorites;
    }

    private String getApiKey() {
        if (context == null) {
            android.util.Log.e("DictionaryViewModel", "Context is null, cannot load API key");
            return "";
        }
        String apiKey = ApiKeyManager.getApiKey(context);
        android.util.Log.d("DictionaryViewModel", "API Key loaded: " + (apiKey.isEmpty() ? "EMPTY" : "SUCCESS"));
        return apiKey;
    }

    public void searchWord(String word) {
        result.setValue(Resource.loading(null));
        KRDictApiService api = RetrofitClient.getClient().create(KRDictApiService.class);
        
        // First, fetch word definitions
        Call<KRDictResponse> definitionCall = api.searchWord(getApiKey(), word, "y", "1", 10, 1, "dict", "word", "n");
        definitionCall.enqueue(new Callback<KRDictResponse>() {
            @Override
            public void onResponse(Call<KRDictResponse> call, Response<KRDictResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getItems() != null && !response.body().getItems().isEmpty()) {
                    DictionaryItem item = response.body().getItems().get(0);
                    baseWord = item.getWord();
                    pos = item.getPos();
                    List<Sense> senses = item.getSenses();
                    if (senses == null) senses = new ArrayList<>();
                    
                    // Save to search history
                    if (repository != null) {
                        repository.insertSearchHistory(word, response.body().getTotal());
                    }
                    
                    // Now fetch examples
                    fetchExamples(word, senses, pos);
                } else {
                    // Save to search history even if no results
                    if (repository != null) {
                        repository.insertSearchHistory(word, 0);
                    }
                    result.setValue(Resource.success(new ArrayList<>()));
                }
            }

            @Override
            public void onFailure(Call<KRDictResponse> call, Throwable t) {
                result.setValue(Resource.error("Failed to fetch definitions", null));
            }
        });
    }
    
    private void fetchExamples(String word, List<Sense> senses, String pos) {
        KRDictApiService api = RetrofitClient.getClient().create(KRDictApiService.class);
        Call<ExampleResponse> exampleCall = api.getExamples(getApiKey(), word, "exam", "y", "1", 10, 1);
        exampleCall.enqueue(new Callback<ExampleResponse>() {
            @Override
            public void onResponse(Call<ExampleResponse> call, Response<ExampleResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getItems() != null) {
                    // Convert ExampleItem to Example for example sentences
                    List<ExampleItem> items = response.body().getItems();
                    examples = new ArrayList<>();
                    for (ExampleItem item : items) {
                        Example example = new Example();
                        example.setTargetCode(item.getTargetCode());
                        example.setWord(item.getWord());
                        example.setSupNo(item.getSupNo());
                        example.setLink(item.getLink());
                        example.setExample(item.getExample());
                        examples.add(example);
                    }
                } else {
                    examples = new ArrayList<>();
                }
                
                // Group senses by POS and create POSGroups
                List<POSGroup> posGroups = groupSensesByPOS(senses, examples, pos);
                result.setValue(Resource.success(posGroups));
            }

            @Override
            public void onFailure(Call<ExampleResponse> call, Throwable t) {
                // Even if examples fail, still show definitions
                List<POSGroup> posGroups = groupSensesByPOS(senses, new ArrayList<>(), pos);
                result.setValue(Resource.success(posGroups));
            }
        });
    }
    
    private List<POSGroup> groupSensesByPOS(List<Sense> senses, List<Example> examples, String pos) {
        Map<String, List<Sense>> posMap = new HashMap<>();
        
        // Group senses by POS - since POS is at the DictionaryItem level, 
        // we'll group all senses under the same POS
        String posKey = (pos != null && !pos.isEmpty()) ? pos : "명사";
        posMap.put(posKey, senses);
        
        // Create POSGroups
        List<POSGroup> posGroups = new ArrayList<>();
        for (Map.Entry<String, List<Sense>> entry : posMap.entrySet()) {
            posGroups.add(new POSGroup(entry.getKey(), entry.getValue(), examples));
        }
        
        return posGroups;
    }
    
    // History and Favorites Management
    public void loadSearchHistory() {
        if (repository != null) {
            repository.getAllHistory().observeForever(history -> {
                searchHistory.setValue(history);
            });
        }
    }
    
    public void loadFavorites() {
        if (repository != null) {
            repository.getAllFavorites().observeForever(favs -> {
                favorites.setValue(favs);
            });
        }
    }
    
    public void addToFavorites(String word, String definition, String pos, String pronunciation) {
        if (repository != null) {
            repository.addToFavorites(word, definition, pos, pronunciation);
        }
    }
    
    public void removeFromFavorites(String word) {
        if (repository != null) {
            repository.removeFromFavorites(word);
        }
    }
    
    public boolean isFavorite(String word) {
        if (repository != null) {
            List<Favorite> currentFavorites = favorites.getValue();
            if (currentFavorites != null) {
                for (Favorite favorite : currentFavorites) {
                    if (favorite.getWord().equals(word)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public void clearSearchHistory() {
        if (repository != null) {
            repository.deleteAllHistory();
        }
    }
    
    public void clearFavorites() {
        if (repository != null) {
            repository.deleteAllFavorites();
        }
    }
    
    @Override
    protected void onCleared() {
        super.onCleared();
        if (repository != null) {
            repository.shutdown();
        }
    }
} 