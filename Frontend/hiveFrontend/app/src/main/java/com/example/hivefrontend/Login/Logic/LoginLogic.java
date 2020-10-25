package com.example.hivefrontend.Login.Logic;

import android.content.Context;

import com.example.hivefrontend.Login.ILoginView;
import com.example.hivefrontend.Login.Network.ServerRequest;
import com.example.hivefrontend.SharedPrefManager;
import com.example.hivefrontend.User;

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
        loginView.fieldChecks();
        try {
            for(int i = 0; i < response.length(); i++) {
                JSONObject member = response.getJSONObject(i);
                if ((loginView.getUsername().compareTo(member.getString("email")) == 0) && (loginView.getPassword().compareTo(member.getString("password")) == 0)) {
                    loginView.setExistsTrue();
                    User user = new User(loginView.getUsername(), loginView.getPassword());

                    SharedPrefManager.getInstance(loginView.getLoginContext()).userLogin(user);
                    loginView.openHome();
                }
            }
            if (loginView.getUserExists() == false) {
                loginView.userDNE();
            }
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
