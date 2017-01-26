package br.com.ite.tasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import br.com.ite.interfaces.OnSplashCallback;
import br.com.ite.utils.GlobalNames;

/**
 * Created by leonardo.borges on 26/01/2017.
 */
public class SplashTask extends AsyncTask<Void, Void, SplashTask.Result> {

    public enum Result {
        LOGGED_IN,
        NOT_LOGGED_IN
    }

    private OnSplashCallback callback;
    private SharedPreferences preferences;

    public SplashTask(AppCompatActivity activity) {
        this.callback = (OnSplashCallback) activity;
        this.preferences = activity.getApplicationContext()
                .getSharedPreferences(GlobalNames.ITE_PREFERENCES, Context.MODE_PRIVATE);
    }

    @Override
    protected Result doInBackground(Void... params) {

        try {
            Thread.sleep(1750);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (preferences.getString(GlobalNames.ITE_PREFERENCES_LOGGED_USER, "").isEmpty()) {
            return Result.NOT_LOGGED_IN;
        }

        return Result.LOGGED_IN;
    }

    @Override
    public void onPostExecute(SplashTask.Result result) {
        this.callback.onSplashComplete(result);
    }
}
