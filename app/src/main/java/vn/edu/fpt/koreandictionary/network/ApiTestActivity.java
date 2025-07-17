package vn.edu.fpt.koreandictionary.network;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.fpt.koreandictionary.R;
import vn.edu.fpt.koreandictionary.model.KRDictResponse;
import vn.edu.fpt.koreandictionary.util.ApiKeyManager;

public class ApiTestActivity extends AppCompatActivity {
    private static final String TAG = "ApiTestActivity";
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_test);
        textViewResult = findViewById(R.id.textViewResult);
        // Test with a simple Korean word
        testApi("안녕");
        // Also test raw API
        testRawApi("안녕");
    }

    private void testApi(String word) {
        KRDictApiService api = RetrofitClient.getClient().create(KRDictApiService.class);
        String apiKey = ApiKeyManager.getApiKey(this);
        Call<KRDictResponse> call = api.searchWord(apiKey, word, "y", "1", 10, 1, "dict", "word", "n");
        call.enqueue(new Callback<KRDictResponse>() {
            @Override
            public void onResponse(Call<KRDictResponse> call, Response<KRDictResponse> response) {
                Log.d(TAG, "Response code: " + response.code());
                Log.d(TAG, "Response headers: " + response.headers());
                if (response.isSuccessful()) {
                    KRDictResponse body = response.body();
                    if (body != null) {
                        Log.d(TAG, "Title: " + body.getTitle());
                        Log.d(TAG, "Description: " + body.getDescription());
                        Log.d(TAG, "Total: " + body.getTotal());
                        Log.d(TAG, "Items: " + (body.getItems() != null ? body.getItems().size() : "null"));
                        String result = "Success!\n";
                        result += "Title: " + body.getTitle() + "\n";
                        result += "Description: " + body.getDescription() + "\n";
                        result += "Total: " + body.getTotal() + "\n";
                        result += "Items count: " + (body.getItems() != null ? body.getItems().size() : "null") + "\n";
                        if (body.getItems() != null && !body.getItems().isEmpty()) {
                            result += "\nFirst item word: " + body.getItems().get(0).getWord() + "\n";
                            result += "Senses count: " + (body.getItems().get(0).getSenses() != null ? body.getItems().get(0).getSenses().size() : "null");
                        }
                        textViewResult.setText(result);
                    } else {
                        Log.e(TAG, "Response body is null");
                        textViewResult.setText("Response body is null");
                    }
                } else {
                    Log.e(TAG, "Error response: " + response.code() + " " + response.message());
                    try {
                        String errorBody = response.errorBody() != null ? response.errorBody().string() : "No error body";
                        Log.e(TAG, "Error body: " + errorBody);
                        textViewResult.setText("Error: " + response.code() + "\n" + errorBody);
                    } catch (Exception e) {
                        textViewResult.setText("Error: " + response.code() + "\n" + e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<KRDictResponse> call, Throwable t) {
                Log.e(TAG, "Network failure", t);
                textViewResult.setText("Network failure: " + t.getMessage());
            }
        });
    }

    private void testRawApi(String word) {
        RawApiTest.testRawApi(this, word, new RawApiTest.ApiCallback() {
            @Override
            public void onResult(String response) {
                runOnUiThread(() -> {
                    String currentText = textViewResult.getText().toString();
                    textViewResult.setText(currentText + "\n\n=== RAW API RESPONSE ===\n" + response);
                });
            }
        });
    }
} 