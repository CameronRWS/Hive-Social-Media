package com.example.hivefrontend.Register.Logic;

import android.content.Context;
import android.widget.Toast;

import com.example.hivefrontend.Register.IRegisterView;
import com.example.hivefrontend.Register.Network.IRegisterServerRequest;
import com.example.hivefrontend.SharedPrefManager;
import com.example.hivefrontend.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterLogic implements IRegisterVolleyListener {

    IRegisterServerRequest server;
    IRegisterView registerView;

    public RegisterLogic(IRegisterView rv, IRegisterServerRequest rsr) {
        this.registerView = rv;
        this.server = rsr;
        server.addVolleyListener(this);
    }
    @Override
    public JSONObject createUser() {
        JSONObject object = new JSONObject();
        try {
            registerView.usernameCheck();
            registerView.passwordCheck();
            registerView.emailAddressCheck();
            registerView.validateEmailAddress();
            object.put("email", registerView.getEmailAddress());
            object.put("userRegistrationIdentity", null);
            object.put("password", registerView.getPassword());
            object.put("birthday", "test birthday");
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return object;
    }

    @Override
    public void onRegisterUserSuccess(JSONObject response) throws JSONException {
        User user = new User(registerView.getUsername(), registerView.getPassword(), response.getJSONObject("userRegistrationIdentity").getJSONObject("user").getInt("userId"));
        SharedPrefManager.getInstance(getRegisterContext()).userLogin(user);
        registerView.successfullyRegistered();
    }

    public Context getRegisterContext() {return registerView.getRegisterContext();}
}
