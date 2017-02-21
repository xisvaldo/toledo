package br.com.ite.interfaces;

import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;

import br.com.ite.models.Solicitation;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by leonardo.borges on 20/02/2017.
 */
public interface SolicitationAPI {

    @POST("Servicos")
    Call<ArrayList<Solicitation>> getSolicitations(@Header("Content-type") String contentType);

    @POST("Servicos/Solicitar")
    Call<LinkedTreeMap> postSolicitation(@Header("Content-type") String contentType, @Body RequestBody body);
}
