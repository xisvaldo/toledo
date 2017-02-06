package br.com.ite.interfaces;

import br.com.ite.models.Student;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by leonardo.borges on 27/01/2017.
 */
public interface LoginAPI {

    @FormUrlEncoded
    @POST("Login")
    Call<Student> postLogin(@Header("Content-Type") String contentType,
                            @Field("RA") String login,
                            @Field("SENHA") String password);
}