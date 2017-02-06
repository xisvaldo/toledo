package br.com.ite.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.ite.R;
import br.com.ite.interfaces.GradesAPI;
import br.com.ite.models.StudentGrades;
import br.com.ite.utils.GlobalNames;
import br.com.ite.utils.network.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by leonardo.borges on 01/02/2017.
 */
public class GradesDetailsFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.grades_details_fragment, parent, false);

        SharedPreferences preferences = getContext()
                .getSharedPreferences(GlobalNames.ITE_PREFERENCES, Context.MODE_PRIVATE);

        /*GradesAPI gradesAPI = ServiceGenerator.createService(GradesAPI.class);

        Call<List<StudentGrades>> call = gradesAPI.getGrades(
                preferences.getString(GlobalNames.ITE_PREFERENCES_USER_ID, ""),
                getArguments().getString(GlobalNames.ITE_ARGS_SELECTED_GRADES_MONTHS));

        call.enqueue(new Callback<List<StudentGrades>>() {
            @Override
            public void onResponse(Call<List<StudentGrades>> call, Response<List<StudentGrades>> response) {

            }

            @Override
            public void onFailure(Call<List<StudentGrades>> call, Throwable t) {

            }
        });*/

        return fragment;
    }

}
