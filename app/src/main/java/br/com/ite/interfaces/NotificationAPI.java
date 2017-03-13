package br.com.ite.interfaces;

import java.util.List;

import br.com.ite.models.Notification;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by leonardo.borges on 13/03/2017.
 */
public interface NotificationAPI {

    @FormUrlEncoded
    @POST("notificacoes")
    Call<List<Notification>> getNotifications(@Field("RA") String ra);
}
