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
import com.example.hivefrontend.LoginActivity;
import com.example.hivefrontend.MainActivity;
import com.example.hivefrontend.R;
import com.example.hivefrontend.Register.Logic.RegisterLogic;
import com.example.hivefrontend.Register.Network.ServerRequest;
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

        final ServerRequest serverRequest = new ServerRequest();
        final RegisterLogic logic = new RegisterLogic();

        findViewById(R.id.signUpButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serverRequest.registerUser();
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