package com.example.hivefrontend;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.hivefrontend.Login.LoginActivity;

/**
 * The SharedPrefManager which stores the current session's user data
 */
public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "simplifiedcodingsharedpref";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_ID = "keyid";
    private static final String KEY_BIRTHDAY = "keybirthday";
    private static final String KEY_DISPLAY_NAME = "keydisplayname";
    private static final String KEY_PASSWORD = "keypassword";

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    /**
     * Invoked when a user is logged in and stores the information
     * @param user The user object.
     */
    public void userLogin(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, user.getId());
//        editor.putString(KEY_USERNAME, user.getUsername());
        editor.putString(KEY_EMAIL, user.getEmailAddress());
        editor.putString(KEY_PASSWORD, user.getPassword());
//        editor.putString(KEY_BIRTHDAY, user.getBirthday());
//        editor.putString(KEY_DISPLAY_NAME, user.getDisplayName());
        editor.apply();
    }

    /**
     * Checks if a user is logged in
     * @return Whether or not a user is logged in
     */
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMAIL, null) != null;
    }

    /**
     * Returns a user object
     * @return User object
     */
    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(sharedPreferences.getString(KEY_EMAIL, null),
                        sharedPreferences.getString(KEY_PASSWORD, null), sharedPreferences.getInt(KEY_ID, -1));
    }


    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, LoginActivity.class));
    }
}
