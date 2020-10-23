package com.example.hivefrontend.Register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.hivefrontend.EditProfileActivity;
import com.example.hivefrontend.Login.LoginActivity;
import com.example.hivefrontend.MainActivity;
import com.example.hivefrontend.R;
import com.example.hivefrontend.SharedPrefManager;
import com.example.hivefrontend.User;
import com.example.hivefrontend.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity implements IRegisterView {

    EditText usernameField, passwordField, emailAddressField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Start Main activity if there's already a user logged in.
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
            return;
        }

        usernameField = (EditText) findViewById(R.id.usernameField);
        passwordField = (EditText) findViewById(R.id.passwordField);
        emailAddressField = (EditText) findViewById(R.id.emailAddressField);
        final Button cancelButton = (Button) findViewById(R.id.cancelButton);

        findViewById(R.id.signUpButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void registerUser() {
        final String username = usernameField.getText().toString().trim();
        final String emailAddress = emailAddressField.getText().toString().trim();
        final String password = passwordField.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            usernameField.setError("Oops! Please enter something for a username.");
            usernameField.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(emailAddress)) {
            emailAddressField.setError("Oops! Please enter something for an email address.");
            emailAddressField.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
            emailAddressField.setError("Darn. That email address isn't valid!");
            emailAddressField.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordField.setError("Oops! Please enter something for a password.");
            passwordField.requestFocus();
            return;
        }
        JSONObject object = new JSONObject();
        try {
            object.put("email", emailAddress);
            object.put("userRegistrationIdentity", null);
            object.put("password", password);
            object.put("birthday", "test birthday");
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        String url = "http://10.24.227.37:8080/userRegistrations";
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest
                (Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                User user = new User(username, password);
                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                successfullyRegistered();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                        VolleySingleton.getInstance(this).addToRequestQueue(jsonArrayRequest);
    }

    @Override
    public Context getRegisterContext() {return this.getApplicationContext();}

    @Override
    public void successfullyRegistered() {
        startActivity(new Intent(getApplicationContext(), EditProfileActivity.class));
    }

    @Override
    public String getUsername() {
        return usernameField.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return passwordField.getText().toString().trim();
    }

    @Override
    public String getEmailAddress() {
        return emailAddressField.getText().toString().trim();
    }

    @Override
    public void usernameCheck() {
        if (TextUtils.isEmpty(getUsername())) {
            usernameField.setError("Oops! Please enter something for a username.");
            usernameField.requestFocus();
            return;
        }
    }

    @Override
    public void passwordCheck() {
        if (TextUtils.isEmpty(getPassword())) {
            passwordField.setError("Oops! Please enter something for a password.");
            passwordField.requestFocus();
            return;
        }
    }

    @Override
    public void emailAddressCheck() {
        if (TextUtils.isEmpty(getEmailAddress())) {
            emailAddressField.setError("Oops! Please enter something for an email address.");
            emailAddressField.requestFocus();
            return;
        }
    }

    @Override
    public void validateEmailAddress() {
        if (!Patterns.EMAIL_ADDRESS.matcher(getEmailAddress()).matches()) {
            emailAddressField.setError("Darn. That email address isn't valid!");
            emailAddressField.requestFocus();
            return;
        }
    }


}