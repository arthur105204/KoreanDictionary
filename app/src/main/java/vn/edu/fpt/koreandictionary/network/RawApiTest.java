package vn.edu.fpt.koreandictionary.network;

import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RawApiTest {
    private static final String TAG = "RawApiTest";
    private static final String API_KEY = BuildConfig.KR_DICT_API_KEY;

    public static void testRawApi(String word, ApiCallback callback) {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                try {
                    String urlString = String.format(
                        "https://krdict.korean.go.kr/api/search?key=%s&q=%s&translated=y&trans_lang=1&num=10&start=1&sort=dict&part=word&advanced=n",
                        API_KEY, java.net.URLEncoder.encode(params[0], "UTF-8")
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