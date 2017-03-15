package br.com.ite.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import br.com.ite.R;
import br.com.ite.adapters.NotificationsAdapter;
import br.com.ite.interfaces.NotificationAPI;
import br.com.ite.models.Notification;
import br.com.ite.utils.GlobalNames;
import br.com.ite.utils.RecyclerViewSeparator;
import br.com.ite.utils.network.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by leonardo.borges on 13/03/2017.
 */
public class NotificationsFragment extends AppCompatDialogFragment {

    private View fragment;
    private ProgressBar loading;
    private TextView empty;
    private NotificationsAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        fragment = inflater.inflate(R.layout.notifications_fragment, parent, false);

        adapter = new NotificationsAdapter(this);

        loading = (ProgressBar) fragment.findViewById(R.id.notifications_loading);
        loading.setVisibility(View.VISIBLE);

        empty = (TextView) fragment.findViewById(R.id.empty);
        empty.setVisibility(View.GONE);

        RecyclerView notificationList = (RecyclerView) fragment.findViewById(R.id.notifications_list);
        notificationList.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        notificationList.setAdapter(adapter);

        RecyclerViewSeparator separator =
                new RecyclerViewSeparator(getActivity().getApplicationContext(),
                        getResources().getColor(R.color.lightGray), 1f);
        notificationList.addItemDecoration(separator);

        SharedPreferences preferences = getActivity()
                .getSharedPreferences(GlobalNames.ITE_PREFERENCES, Context.MODE_PRIVATE);

        NotificationAPI notificationAPI = ServiceGenerator.createService(NotificationAPI.class);
        Call<List<Notification>> call = notificationAPI
                .getNotifications(preferences.getString(GlobalNames.ITE_PREFERENCES_USER_ID, ""));

        call.enqueue(new Callback<List<Notification>>() {
            @Override
            public void onResponse(Call<List<Notification>> call, Response<List<Notification>> response) {
                if (response.isSuccessful()) {
                    adapter.setData(response.body());
                    adapter.notifyDataSetChanged();
                    loading.setVisibility(View.GONE);
                    empty.setVisibility(View.GONE);
                }
                else {
                    loading.setVisibility(View.GONE);
                    empty.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<Notification>> call, Throwable t) {
                loading.setVisibility(View.GONE);
                empty.setVisibility(View.VISIBLE);
            }
        });

        return fragment;
    }
}
