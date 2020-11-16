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

/**
 * The logic for registering a new user.
 */
public class RegisterLogic implements IRegisterVolleyListener {

    IRegisterServerRequest server;
    IRegisterView registerView;

    public RegisterLogic(IRegisterView rv, IRegisterServerRequest rsr) {
        this.registerView = rv;
        this.server = rsr;
        server.addVolleyListener(this);
    }

    /**
     * Handles creating a new JSONObject for a new user
     * @return
     */
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

    /**
     * Handles the success of registering a new user
     * @param response The given JSONObject user
     * @throws JSONException error
     */
    @Override
    public void onRegisterUserSuccess(JSONObject response) throws JSONException {

        if (registerView.getRegParametersMet() == true) {
            User user = new User(registerView.getUsername(), registerView.getPassword(), response.getJSONObject("userRegistrationIdentity").getJSONObject("user").getInt("userId"));
            SharedPrefManager.getInstance(getRegisterContext()).userLogin(user);
            registerView.successfullyRegistered();
        }
        else {
            registerView.setRegParametersMet();
        }
    }

    @Override
    public boolean isAvailable(JSONArray response) throws JSONException {
        for(int i = 0; i < response.length(); i++) {
            JSONObject member = response.getJSONObject(i);
            if (registerView.getUsername().equals(member.getJSONObject("userRegistrationIdentity").getJSONObject("user").getString("userName"))) {
                registerView.takenName("username");
                return false;
            }
            if (registerView.getEmailAddress().equals(member.getString("email"))) {
                registerView.takenName("email");
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the register context.
     * @return The context.
     */
    public Context getRegisterContext() {return registerView.getRegisterContext();}
}
