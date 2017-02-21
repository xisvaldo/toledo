package br.com.ite.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.Fade;
import android.transition.TransitionSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import br.com.ite.R;
import br.com.ite.activities.BaseActivity;
import br.com.ite.adapters.GradesAdapter;
import br.com.ite.interfaces.GradesAPI;
import br.com.ite.interfaces.OnItemClickTransition;
import br.com.ite.models.StudentGrades;
import br.com.ite.utils.GlobalNames;
import br.com.ite.utils.network.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by leonardo.borges on 26/01/2017.
 */
public class GradesFragment extends Fragment implements OnItemClickTransition {

    private RecyclerView gradesSubjects;
    private GradesAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.grades_fragment, parent, false);

        adapter = new GradesAdapter(this);

        gradesSubjects = (RecyclerView) fragment.findViewById(R.id.grades_subject_list);
        gradesSubjects.setLayoutManager(new LinearLayoutManager(getContext()));
        gradesSubjects.setAdapter(adapter);

        final ProgressBar loading = (ProgressBar) fragment.findViewById(R.id.grades_loading);
        loading.setVisibility(View.VISIBLE);

        final TextView empty = (TextView) fragment.findViewById(R.id.empty);
        empty.setVisibility(View.GONE);

        SharedPreferences preferences = getContext()
                .getSharedPreferences(GlobalNames.ITE_PREFERENCES, Context.MODE_PRIVATE);

        GradesAPI gradesAPI = ServiceGenerator.createService(GradesAPI.class);
        Call<List<StudentGrades>> call =  gradesAPI.getGrades(
                GlobalNames.ITE_CONTENT_TYPE_URLENCODED,
                preferences.getString(GlobalNames.ITE_PREFERENCES_USER_ID, ""),
                preferences.getString(GlobalNames.ITE_PREFERENCES_USER_PASSWORD, ""));

        call.enqueue(new Callback<List<StudentGrades>>() {
            @Override
            public void onResponse(Call<List<StudentGrades>> call, Response<List<StudentGrades>> response) {
                if (response.isSuccessful()) {
                    adapter.setData(response.body());
                    adapter.notifyDataSetChanged();
                    loading.setVisibility(View.GONE);
                    empty.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<StudentGrades>> call, Throwable t) {
                loading.setVisibility(View.GONE);
                empty.setVisibility(View.VISIBLE);
            }
        });

        return fragment;
    }

    @Override
    public void onItemClicked(Object viewHolder, int position, Bundle args) {
        try {
            TransitionSet transitionSet = new TransitionSet();
            transitionSet.addTransition(new ChangeBounds());
            transitionSet.addTransition(new ChangeTransform());
            transitionSet.addTransition(new ChangeImageTransform());

            setExitTransition(new Fade());

            GradesAdapter.NormalViewHolder normalViewHolder =
                    (GradesAdapter.NormalViewHolder) viewHolder;

            GradesDetailsNormalFragment detailsFragment = new GradesDetailsNormalFragment();
            detailsFragment.setArguments(args);
            detailsFragment.setSharedElementEnterTransition(transitionSet);
            detailsFragment.setEnterTransition(new Fade());
            detailsFragment.setSharedElementReturnTransition(transitionSet);

            getFragmentManager().beginTransaction()
                    .addSharedElement(normalViewHolder.subject, "gradesNormalSubject")
                    .replace(R.id.grades_container, detailsFragment)
                    .addToBackStack(null)
                    .commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
