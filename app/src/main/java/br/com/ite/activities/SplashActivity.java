package br.com.ite.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import br.com.ite.R;
import br.com.ite.interfaces.OnSplashCallback;
import br.com.ite.tasks.SplashTask;

/**
 * Created by leonardo.borges on 26/01/2017.
 */
public class SplashActivity extends AppCompatActivity implements OnSplashCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        new SplashTask(this).execute();
    }

    @Override
    public void onSplashComplete() {

        Intent activity = new Intent(SplashActivity.this, BaseActivity.class);

        activity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
                | Intent.FLAG_ACTIVITY_NO_HISTORY
                | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(activity);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
