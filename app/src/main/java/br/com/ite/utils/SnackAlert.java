package br.com.ite.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

import br.com.ite.R;

public class SnackAlert {

    public static void errorMessage(View v, String message) {
        createSnack(v, message,  v.getResources().getColor(R.color.salmon)).show();
    }

    public static void showMessage(View v, String message) {
        createSnack(v, message, v.getResources().getColor(R.color.darkGray)).show();
    }

    private static Snackbar createSnack(View v, String message, int color) {
        Snackbar snackbar = Snackbar.make(v, message, Snackbar.LENGTH_LONG).setAction("Action", null);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(color);
        return snackbar;
    }
}
