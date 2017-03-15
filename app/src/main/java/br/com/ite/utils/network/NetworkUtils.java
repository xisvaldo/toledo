package br.com.ite.utils.network;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;

import br.com.ite.R;
import br.com.xisvaldo.android.dialog.AndroidDialog;

/**
 * Created by leonardo.borges on 29/06/2016.
 */
public class NetworkUtils {

    /**
     * Check if device is connected to Internet.
     * @param activity current activity;
     * @return true if has internet connection; false if not
     */
    public static boolean checkInternetConnection(Activity activity) throws IOException {
        ConnectivityManager connection =
                (ConnectivityManager) activity.getApplicationContext()
                        .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connection != null) {
            NetworkInfo networkInfo = connection.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                /*Log.i(activity.getApplicationContext()
                        .getResources()
                        .getString(R.string.app_name), "Network: DEVICE HAS INTERNET CONNECTION");*/
                return true;
            }
        }

        /*Log.w(activity.getApplicationContext()
                .getResources()
                .getString(R.string.app_name), "Network: DEVICE HAS NO INTERNET CONNECTION");*/

        AndroidDialog.show(activity,
                AndroidDialog.Type.ERROR,
                activity.getApplicationContext()
                        .getResources().getString(R.string.app_name),
                activity.getApplicationContext()
                        .getResources().getString(R.string.connectionError),
                new Handler());

        return false;
    }

}
