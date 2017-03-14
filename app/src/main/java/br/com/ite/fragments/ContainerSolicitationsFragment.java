package br.com.ite.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;

import br.com.ite.R;
import br.com.ite.interfaces.OnLoginCallback;
import br.com.ite.utils.GlobalNames;
import br.com.ite.utils.UserStorage;

/**
 * Created by leonardo.borges on 14/03/2017.
 */
public class ContainerSolicitationsFragment extends Fragment implements Serializable, OnLoginCallback {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.container_solicitations_fragment, parent, false);
        checkLogin();

        return fragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser && getActivity() != null) {
            checkLogin();
        }
    }

    @Override
    public void onLoginComplete(LoginFragment.LOGIN_RESULT result) {

        if (result == LoginFragment.LOGIN_RESULT.SUCCESS) {
            loadFragment();
        }
    }

    private void checkLogin() {
        if (!UserStorage.isLogged(getContext())) {

            LoginFragment loginFragment = new LoginFragment();
            Bundle args = new Bundle();
            args.putSerializable(GlobalNames.ITE_LOGIN_CALLBACK, this);
            loginFragment.setArguments(args);

            getFragmentManager().beginTransaction()
                    .add(R.id.container_solicitations, loginFragment, "LOGIN_FRAGMENT")
                    .disallowAddToBackStack()
                    .commit();
        } else {
            loadFragment();
        }
    }

    private void loadFragment() {
        getFragmentManager().beginTransaction()
                .replace(R.id.container_solicitations, new SolicitationsFragment())
                .addToBackStack(null)
                .commit();
    }
}


