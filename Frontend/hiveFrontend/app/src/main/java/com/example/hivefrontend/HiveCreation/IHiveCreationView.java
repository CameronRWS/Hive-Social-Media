package com.example.hivefrontend.HiveCreation;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

public interface IHiveCreationView {

    Context getViewContext();

    void handleClick() throws JSONException;

    boolean validInput();

    void goHome();
}
