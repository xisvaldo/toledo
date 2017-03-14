package br.com.ite.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;

import br.com.ite.R;
import br.com.ite.interfaces.LoginAPI;
import br.com.ite.interfaces.OnLoginCallback;
import br.com.ite.models.Student;
import br.com.ite.utils.GlobalNames;
import br.com.ite.utils.SnackAlert;
import br.com.ite.utils.network.NetworkUtils;
import br.com.ite.utils.network.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by leonardo.borges on 26/01/2017.
 */
public class LoginFragment extends AppCompatDialogFragment {

    private EditText login;
    private EditText password;
    private boolean isShowingPassword = false;
    private boolean loginSucceed = false;
    private OnLoginCallback callback;

    public enum LOGIN_RESULT {
        SUCCESS,
        FAILED
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.login_fragment, parent, false);

        login = (EditText) fragment.findViewById(R.id.login_student_id);

        setPasswordVisibility(fragment);

        Button enter = (Button) fragment.findViewById(R.id.login_enter);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    validateData(v);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Bundle args = getArguments();
        callback = (OnLoginCallback) args.getSerializable(GlobalNames.ITE_LOGIN_CALLBACK);

        return fragment;
    }

    private void validateData(View v) throws IOException {
        // Close keyboard to show SnackBar message (if error occurs)
        InputMethodManager imm = (InputMethodManager) getActivity().getApplicationContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView()
                .getRootView().getWindowToken(), 0);

        if (login.getText().toString().trim().isEmpty()) {
            SnackAlert.errorMessage(v, getString(R.string.loginStudentIdObligatory));
            login.requestFocus();
        }
        else if (password.getText().toString().isEmpty()) {
            SnackAlert.errorMessage(v, getString(R.string.loginPasswordObligatory));
            password.requestFocus();
        }
        else {
            authentication(v);
        }
    }

    private void authentication(final View v) throws IOException {

        if (!NetworkUtils.checkInternetConnection(getActivity())) return;

        LoginAPI loginApi = ServiceGenerator.createService(LoginAPI.class);

        Call<Student> call = loginApi.postLogin(GlobalNames.ITE_CONTENT_TYPE_URLENCODED,
                login.getText().toString(),
                password.getText().toString());

        call.enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {

                if (response.code() == 200 && response.body() != null) {
                    loginSucceed = true;
                    SharedPreferences preferences = getActivity()
                            .getSharedPreferences(GlobalNames.ITE_PREFERENCES, Context.MODE_PRIVATE);

                    preferences.edit().putString(GlobalNames.ITE_PREFERENCES_USER_ID,
                            login.getText().toString()).commit();

                    preferences.edit().putString(GlobalNames.ITE_PREFERENCES_USER_PASSWORD,
                            password.getText().toString()).commit();

                    preferences.edit().putBoolean(GlobalNames.ITE_PREFERENCES_IS_LOGGED,
                            true).commit();

                    if (getDialog() != null) {
                        dismiss();
                    }
                    else {
                        setCallback();
                    }
                }
                else {
                    loginSucceed = false;
                    SnackAlert.errorMessage(v, getString(R.string.loginEmptyStudentError));
                }
            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {
                loginSucceed = false;
                setCallback();
                SnackAlert.errorMessage(v, getString(R.string.loginAuthenticationFailed));
            }
        });
    }

    @Override
    public void onDismiss(final DialogInterface dialog) {
        setCallback();
    }

    private void setCallback() {
        if (loginSucceed) {
            callback.onLoginComplete(LOGIN_RESULT.SUCCESS);
        }
        else {
            callback.onLoginComplete(LOGIN_RESULT.FAILED);
        }
    }

    private void setPasswordVisibility(View fragment) {
        password = (EditText) fragment.findViewById(R.id.login_password);
        final ImageView passwordVisibility = (ImageView) fragment
                .findViewById(R.id.login_password_visibility);

        if (password != null && passwordVisibility != null) {
            password.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    passwordVisibility.setVisibility(s.length() > 0 ? View.VISIBLE : View.GONE);
                }
            });

            // Set password visibility (eye) behavior
            passwordVisibility.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (password.getSelectionStart() != 0) {
                        // Change input type will reset cursor position, so we want to save it
                        final Integer cursor = password.getSelectionStart();

                        if (!isShowingPassword) {
                            password.setInputType(InputType.TYPE_CLASS_TEXT |
                                    InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                            isShowingPassword = true;

                            passwordVisibility.setImageResource(R.drawable.ic_visibility_on);

                        } else {
                            password.setInputType(InputType.TYPE_CLASS_TEXT |
                                    InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            isShowingPassword = false;

                            passwordVisibility.setImageResource(R.drawable.ic_visibility_off);
                        }

                        passwordVisibility.setColorFilter(getActivity()
                                .getApplicationContext().getResources()
                                .getColor(R.color.darkGray), PorterDuff.Mode.SRC_ATOP);

                        if (cursor != null) {
                            password.setSelection(cursor);
                        } else {
                            password.setSelection(0);
                        }
                    }
                }
            });
        }
    }
}
