package br.com.ite.interfaces;

import java.util.List;

import br.com.ite.models.News;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by leonardo.borges on 30/01/2017.
 */
public interface NewsAPIService {

    @GET
    Call<List<News>> getNews();
}
