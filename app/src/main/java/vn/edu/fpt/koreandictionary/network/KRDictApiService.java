package vn.edu.fpt.koreandictionary.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import vn.edu.fpt.koreandictionary.model.ExampleResponse;
import vn.edu.fpt.koreandictionary.model.KRDictResponse;

public interface KRDictApiService {
    @GET("search")
    Call<KRDictResponse> searchWord(
        @Query("key") String apiKey,
        @Query("q") String query,
        @Query("translated") String translated,
        @Query("trans_lang") String transLang,
        @Query("num") Integer num,
        @Query("start") Integer start,
        @Query("sort") String sort,
        @Query("part") String part,
        @Query("advanced") String advanced
    );

    @GET("search")
    Call<ExampleResponse> getExamples(
        @Query("key") String apiKey,
        @Query("q") String query,
        @Query("part") String part,
        @Query("translated") String translated,
        @Query("trans_lang") String transLang,
        @Query("num") Integer num,
        @Query("start") Integer start
    );
} 