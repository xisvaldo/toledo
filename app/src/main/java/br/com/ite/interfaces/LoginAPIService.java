package br.com.ite.interfaces;

import java.util.List;

import br.com.ite.models.Student;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by leonardo.borges on 27/01/2017.
 */
public interface LoginAPIService {

    @FormUrlEncoded
    @POST("login.asp")
    Call<List<Student>> postLogin(@Field("usuario") String login, @Field("senha") String password);

    /*@GET("bla")
    Call<List<Student>> getStudents(@Query("turma") String turma);*/
}
