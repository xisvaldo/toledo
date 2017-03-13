package br.com.ite.tasks;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import br.com.ite.interfaces.OnSplashCallback;

/**
 * Created by leonardo.borges on 26/01/2017.
 */
public class SplashTask extends AsyncTask<Void, Void, Void> {

    private OnSplashCallback callback;

    public SplashTask(AppCompatActivity activity) {
        this.callback = (OnSplashCallback) activity;
    }

    @Override
    protected Void doInBackground(Void... params) {

        try {
            Thread.sleep(1750);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onPostExecute(Void result) {
        this.callback.onSplashComplete();
    }
}
