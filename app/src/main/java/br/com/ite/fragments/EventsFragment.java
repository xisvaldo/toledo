package br.com.ite.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import br.com.ite.R;
import br.com.ite.adapters.EventsAdapter;
import br.com.ite.interfaces.EventsAPI;
import br.com.ite.models.Event;
import br.com.ite.utils.GlobalNames;
import br.com.ite.utils.RecyclerViewSeparator;
import br.com.ite.utils.network.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by leonardo.borges on 26/01/2017.
 */
public class EventsFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.events_fragment, parent, false);

        final EventsAdapter adapter = new EventsAdapter(this);

        final ProgressBar loading = (ProgressBar) fragment.findViewById(R.id.events_loading);
        loading.setVisibility(View.VISIBLE);

        final TextView empty = (TextView) fragment.findViewById(R.id.empty);
        empty.setVisibility(View.GONE);

        RecyclerView eventList = (RecyclerView) fragment.findViewById(R.id.events_list);
        eventList.setLayoutManager(new LinearLayoutManager(getContext()));
        eventList.setAdapter(adapter);

        RecyclerViewSeparator separator =
                new RecyclerViewSeparator(getContext(), getResources().getColor(R.color.lightGray), 1f);
        eventList.addItemDecoration(separator);

        EventsAPI eventsAPI = ServiceGenerator.createService(EventsAPI.class);
        Call<List<Event>> call = eventsAPI.getEvents(GlobalNames.ITE_CONTENT_TYPE_APPLICATION_JSON);

        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if (response.isSuccessful()) {
                    adapter.setData(response.body());
                    adapter.notifyDataSetChanged();
                    loading.setVisibility(View.GONE);
                    empty.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                loading.setVisibility(View.GONE);
                empty.setVisibility(View.VISIBLE);
            }
        });

        return fragment;
    }
}
