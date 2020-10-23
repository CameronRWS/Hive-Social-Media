package com.example.hivefrontend.Login.Logic;

import android.content.Context;
import android.content.Intent;

import com.example.hivefrontend.Login.ILoginView;
import com.example.hivefrontend.Login.Network.ILoginServerRequest;
import com.example.hivefrontend.MainActivity;
import com.example.hivefrontend.Register.IRegisterView;
import com.example.hivefrontend.Register.Network.IRegisterServerRequest;
import com.example.hivefrontend.SharedPrefManager;
import com.example.hivefrontend.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginLogic implements ILoginVolleyListener {

    ILoginServerRequest server;
    ILoginView loginView;

    public LoginLogic (ILoginView lv, ILoginServerRequest lsr) {
        this.server = lsr;
        this.loginView = lv;
        server.addVolleyListener(this);
    }

    public Context getLoginContext() {return loginView.getLoginContext();}
    public void loginUser() {server.loginUser();}

    @Override
    public void onLoginSuccess(JSONArray response) {

    }

    public void login(JSONArray response) throws JSONException {
        String username = loginView.getUsername();
        String password = loginView.getPassword();
        loginView.checkUsername();
        loginView.checkPassword();
        boolean userExists = false;
        for(int i = 0; i < response.length(); i++){
            JSONObject member = response.getJSONObject(i);
            if ((username.compareTo(member.getString("email")) == 0) && (password.compareTo(member.getString("password")) == 0)) {
                userExists = true;
                User user = new User(username, password);

                SharedPrefManager.getInstance(loginView.getLoginContext()).userLogin(user);
                loginView.startActivity();
            }
        }
        if (!userExists) {
            loginView.userDNE();
            return;
        }
    }
}
