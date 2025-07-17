package vn.edu.fpt.koreandictionary.network;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import vn.edu.fpt.koreandictionary.util.ApiKeyManager;

public class RawApiTest {
    private static final String TAG = "RawApiTest";

    public static void testRawApi(Context context, String word, ApiCallback callback) {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                try {
                    String apiKey = ApiKeyManager.getApiKey(context);
                    String urlString = String.format(
                        "https://krdict.korean.go.kr/api/search?key=%s&q=%s&translated=y&trans_lang=1&num=10&start=1&sort=dict&part=word&advanced=n",
                        apiKey, java.net.URLEncoder.encode(params[0], "UTF-8")
                    );
                    URL url = new URL(urlString);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");

                    Log.d(TAG, "Requesting URL: " + urlString);

                    int responseCode = connection.getResponseCode();
                    Log.d(TAG, "Response Code: " + responseCode);

                    BufferedReader reader;
                    if (responseCode >= 200 && responseCode < 300) {
                        reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    } else {
                        reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                    }

                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();

                    return response.toString();
                } catch (Exception e) {
                    Log.e(TAG, "Error testing API", e);
                    return "Error: " + e.getMessage();
                }
            }

            @Override
            protected void onPostExecute(String result) {
                if (callback != null) {
                    callback.onResult(result);
                }
            }
        }.execute(word);
    }

    public interface ApiCallback {
        void onResult(String response);
    }
} 