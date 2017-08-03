package leo.me.la.simpleman.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {
    @GET("v1/gifs/random")
    Call<Result> getImage(
            @Query("tag") String tag,
            @Query("rating") String rating,
            @Query("api_key") String apiKey);
}
