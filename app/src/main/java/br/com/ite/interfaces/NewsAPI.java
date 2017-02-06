package br.com.ite.interfaces;

import java.util.List;

import br.com.ite.models.News;
import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by leonardo.borges on 30/01/2017.
 */
public interface NewsAPI {

    @GET("noticias")
    Call<List<News>> getNews(@Header("Content-Type") String contentType);
}
