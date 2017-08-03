package leo.me.la.simpleman.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {
    @GET("v1/gifs/{gif_id}")
    Call<Result> getImage(
            @Path("gif_id") String id,
            @Query("api_key") String apiKey);
}
