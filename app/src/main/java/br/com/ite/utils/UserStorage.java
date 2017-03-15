package br.com.ite.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by leonardo.borges on 13/03/2017.
 */
public class UserStorage {

    private static String userId;
    private static String userPassword;
    private static boolean isLogged;
    private static SharedPreferences preferences;

    public static String getUserId(Context context) {
        configSharedPreferences(context);
        return userId;
    }

    public static String getUserPassword(Context context) {
        configSharedPreferences(context);
        return userPassword;
    }

    public static boolean isLogged(Context context) {
        configSharedPreferences(context);
        return isLogged;
    }

    private static void configSharedPreferences(Context context) {
        preferences = context.getSharedPreferences(GlobalNames.ITE_PREFERENCES, Context.MODE_PRIVATE);
        userId = preferences.getString(GlobalNames.ITE_PREFERENCES_USER_ID, "");
        userPassword = preferences.getString(GlobalNames.ITE_PREFERENCES_USER_PASSWORD, "");
        isLogged = preferences.getBoolean(GlobalNames.ITE_PREFERENCES_IS_LOGGED, false);
    }

    public static void clearData(Context context) {
        preferences = context.getSharedPreferences(GlobalNames.ITE_PREFERENCES, Context.MODE_PRIVATE);
        preferences.edit().putString(GlobalNames.ITE_PREFERENCES_USER_ID, "").commit();
        preferences.edit().putString(GlobalNames.ITE_PREFERENCES_USER_PASSWORD, "").commit();
        preferences.edit().putBoolean(GlobalNames.ITE_PREFERENCES_IS_LOGGED, false).commit();
        userId = "";
        userPassword = "";
        isLogged = false;
    }
}
