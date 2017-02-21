package br.com.ite.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import com.google.gson.internal.LinkedTreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import br.com.ite.R;
import br.com.ite.adapters.SolicitationSpinnerAdapter;
import br.com.ite.interfaces.SolicitationAPI;
import br.com.ite.models.Solicitation;
import br.com.ite.utils.GlobalNames;
import br.com.ite.utils.network.ServiceGenerator;
import br.com.xisvaldo.android.dialog.AndroidDialog;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by leonardo.borges on 26/01/2017.
 */
public class SolicitationsFragment extends Fragment {

    Spinner solicitationSpinner;
    SolicitationAPI solicitationAPI;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.solicitations_fragment, parent, false);

        solicitationSpinner = (Spinner) fragment.findViewById(R.id.solicitations_types);

        solicitationAPI = ServiceGenerator.createService(SolicitationAPI.class);

        Call<ArrayList<Solicitation>> call = solicitationAPI
                .getSolicitations(GlobalNames.ITE_CONTENT_TYPE_URLENCODED);

        call.enqueue(new Callback<ArrayList<Solicitation>>() {
            @Override
            public void onResponse(Call<ArrayList<Solicitation>> call, Response<ArrayList<Solicitation>> response) {

                if (response.isSuccessful()) {
                    SolicitationSpinnerAdapter adapter = new
                            SolicitationSpinnerAdapter(SolicitationsFragment.this, response.body());
                    solicitationSpinner.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Solicitation>> call, Throwable t) {
                //todo
            }
        });

        Button send = (Button) fragment.findViewById(R.id.solicitations_send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Solicitation selectedSolicitation = (Solicitation) solicitationSpinner
                        .getSelectedItem();

                NumberFormat formatter = NumberFormat.getCurrencyInstance();

                String question = String.format(Locale.getDefault(),
                        getContext().getString(R.string.solicitation_confirmation),
                        formatter.format(Float.valueOf(selectedSolicitation.getValue()))
                        );

                try {
                    AndroidDialog.show(
                            getActivity(),
                            AndroidDialog.Type.QUESTION,
                            getContext().getString(R.string.app_name),
                            question,
                            new Handler() {
                                @Override
                                public void handleMessage(Message msg) {
                                    super.handleMessage(msg);
                                    if (msg != null) {
                                        if (msg.what == AndroidDialog.Result.YES.ordinal()) {
                                            sendSolicitation(selectedSolicitation);
                                        }
                                    }
                                }
                            });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        return fragment;
    }

    private void sendSolicitation(Solicitation selectedSolicitation) {

        JSONArray json = new JSONArray();

        SharedPreferences preferences = getContext()
                .getSharedPreferences(GlobalNames.ITE_PREFERENCES, Context.MODE_PRIVATE);

        try {
            JSONObject solicitationJSON = new JSONObject();
            solicitationJSON.put("Servico", selectedSolicitation.getService());
            solicitationJSON.put("Descricao", selectedSolicitation.getDescription());
            solicitationJSON.put("Aluno", preferences.getString(GlobalNames.ITE_PREFERENCES_USER_ID, ""));

            json.put(0, solicitationJSON);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody
                .create(MediaType.parse("application/json"), json.toString());

        try {
            Call<LinkedTreeMap> result = solicitationAPI
                    .postSolicitation(GlobalNames.ITE_CONTENT_TYPE_APPLICATION_JSON, body);

            result.enqueue(new Callback<LinkedTreeMap>() {
                @Override
                public void onResponse(Call<LinkedTreeMap> call, Response<LinkedTreeMap> response) {
                    if (response.isSuccessful()) {

                        try {
                            if (response.body().containsKey("erro") &&
                                    response.body().get("erro").equals(false)) {
                                AndroidDialog.show(
                                        getActivity(),
                                        AndroidDialog.Type.SUCCESS,
                                        getContext().getString(R.string.app_name),
                                        getContext().getString(R.string.solicitationSendSuccess),
                                        new Handler() {
                                        }
                                );
                            }
                            else {
                                AndroidDialog.show(
                                        getActivity(),
                                        AndroidDialog.Type.SUCCESS,
                                        getContext().getString(R.string.app_name),
                                        getContext().getString(R.string.solicitationSendFailed),
                                        new Handler() {}
                                );
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<LinkedTreeMap> call, Throwable t) {
                    try {
                        AndroidDialog.show(
                                getActivity(),
                                AndroidDialog.Type.SUCCESS,
                                getContext().getString(R.string.app_name),
                                getContext().getString(R.string.solicitationSendFailed),
                                new Handler() {}
                        );
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
