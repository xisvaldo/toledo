package br.com.ite.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;

import java.io.IOException;

import br.com.ite.R;
import br.com.ite.interfaces.LoginAPI;
import br.com.ite.models.Student;
import br.com.ite.utils.GlobalNames;
import br.com.ite.utils.SnackAlert;
import br.com.ite.utils.network.NetworkUtils;
import br.com.ite.utils.network.ServiceGenerator;
import br.com.xisvaldo.android.dialog.AndroidDialog;
import br.com.xisvaldo.android.dialog.AndroidProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by leonardo.borges on 26/01/2017.
 */
public class LoginActivity extends AppCompatActivity {

    private EditText login;
    private EditText password;
    private boolean isShowingPassword = false;
    private AndroidProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        setupViewsAndEvents();
    }

    @Override
    public void onBackPressed() {
        try {
            AndroidDialog.show(this,
                    AndroidDialog.Type.QUESTION,
                    getString(R.string.app_name),
                    getString(R.string.generalExit),
                    new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            if (msg != null) {
                                if (msg.what == AndroidDialog.Result.YES.ordinal()) {
                                    finish();
                                }
                            }
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupViewsAndEvents() {
        login = (EditText) findViewById(R.id.login_student_id);

        setPasswordVisibility();

        Button enter = (Button) findViewById(R.id.login_enter);
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
    }

    private void validateData(View v) throws IOException {
        // Close keyboard to show SnackBar message (if error occurs)
        InputMethodManager imm = (InputMethodManager) getApplicationContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getRootView().getWindowToken(), 0);

        if (login.getText().toString().trim().isEmpty()) {
            SnackAlert.errorMessage(v, getString(R.string.loginStudentIdObligatory));
            login.requestFocus();
        }
        else if (password.getText().toString().isEmpty()) {
            SnackAlert.errorMessage(v, getString(R.string.loginPasswordObligatory));
            password.requestFocus();
        }
        else {
            dialog = new AndroidProgressDialog();
            dialog.show(this, getString(R.string.app_name),
                    getString(R.string.loginValidatingData));

            authentication(v);
        }
    }

    private void authentication(final View v) throws IOException {

        if (!NetworkUtils.checkInternetConnection(this)) return;

        LoginAPI loginApi = ServiceGenerator.createService(LoginAPI.class);

        Call<Student> call = loginApi.postLogin(GlobalNames.ITE_CONTENT_TYPE_URLENCODED,
                login.getText().toString(),
                password.getText().toString());

        call.enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                dialog.dismiss();

                if (response.code() == 200 && response.body() != null) {
                    SharedPreferences preferences =
                            getSharedPreferences(GlobalNames.ITE_PREFERENCES, Context.MODE_PRIVATE);

                    preferences.edit().putString(GlobalNames.ITE_PREFERENCES_LOGGED_USER,
                            new Gson().toJson(response.body()))
                            .commit();

                    preferences.edit().putString(GlobalNames.ITE_PREFERENCES_USER_ID,
                            login.getText().toString())
                            .commit();

                    preferences.edit().putString(GlobalNames.ITE_PREFERENCES_USER_PASSWORD,
                            password.getText().toString())
                            .commit();

                    Intent baseActivity = new Intent(LoginActivity.this, BaseActivity.class);
                    startActivity(baseActivity);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
                else {
                   SnackAlert.errorMessage(v, getString(R.string.loginEmptyStudentError));
                }
            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {
                dialog.dismiss();
                SnackAlert.errorMessage(v, getString(R.string.loginAuthenticationFailed));
            }
        });
    }

    private void setPasswordVisibility() {
        password = (EditText) findViewById(R.id.login_password);
        final ImageView passwordVisibility = (ImageView) findViewById(R.id.login_password_visibility);

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

                        passwordVisibility.setColorFilter(getApplicationContext().getResources()
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
