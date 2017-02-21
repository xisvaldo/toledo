package br.com.ite.interfaces;

import java.util.List;

import br.com.ite.models.Event;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by leonardo.borges on 14/02/2017.
 */
public interface EventsAPI {

    @GET("eventos")
    Call<List<Event>> getEvents(@Header("Content-Type") String contentType);
}
