package br.com.ite.interfaces;

import br.com.ite.fragments.LoginFragment;

/**
 * Created by leonardo.borges on 13/03/2017.
 */
public interface OnLoginCallback {
    void onLoginComplete(LoginFragment.LOGIN_RESULT result);
}
