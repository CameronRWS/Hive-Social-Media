package com.example.hivefrontend.Login.Logic;

import android.content.Context;

import com.example.hivefrontend.Login.ILoginView;
import com.example.hivefrontend.Login.Network.ServerRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginLogic implements ILoginVolleyListener {
    ILoginView loginView;
    ServerRequest server;

    public LoginLogic(ILoginView lv, ServerRequest sr) {
        this.loginView = lv;
        this.server = sr;
        server.addVolleyListener(this);
    }


    @Override
    public void onLoginUserSuccess(JSONArray response) {
        try {

        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Context getLoginContext() {
        return loginView.getLoginContext();
    }
}
