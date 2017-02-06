package br.com.ite.interfaces;

import java.util.List;

import br.com.ite.models.StudentGrades;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by leonardo.borges on 01/02/2017.
 */
public interface GradesAPI {

    @FormUrlEncoded
    @POST("notas")
    Call<List<StudentGrades>> getGrades(@Header("Content-Type") String contentType,
                                        @Field("RA") String studentId,
                                        @Field("SENHA") String password);
}
