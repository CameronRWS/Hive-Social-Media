package com.example.hivefrontend.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.hivefrontend.Login.Logic.LoginLogic;
import com.example.hivefrontend.Login.Network.ILoginServerRequest;
import com.example.hivefrontend.MainActivity;
import com.example.hivefrontend.R;
import com.example.hivefrontend.Register.Network.ServerRequest;
import com.example.hivefrontend.Register.RegisterActivity;
import com.example.hivefrontend.SharedPrefManager;
import com.example.hivefrontend.User;
import com.example.hivefrontend.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements ILoginView {

    EditText editTextUsername;
    EditText editTextPassword;
    boolean userExists = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextUsername = (EditText) findViewById(R.id.loginUsernameField);
        editTextPassword = (EditText) findViewById(R.id.loginPasswordField);
        final Button signUpButton = (Button) findViewById(R.id.signUpButton);

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

        findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               loginUser();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    public void loginUser() {
        final ServerRequest serverRequest = new ServerRequest();
        final LoginLogic logic = new LoginLogic(this, serverRequest);

    }
    @Override
    public Context getLoginContext() { return this.getApplicationContext(); }

    @Override
    public String getUsername() {
        return editTextUsername.getText().toString();
    }

    @Override
    public String getPassword() {
        return editTextPassword.getText().toString();
    }

    @Override
    public void checkUsername() {
        if (TextUtils.isEmpty(getUsername())) {
            editTextUsername.setError("Oops! Please enter your username.");
            editTextUsername.requestFocus();
            return;
        }
    }

    @Override
    public void checkPassword() {
        if (TextUtils.isEmpty(getPassword())) {
            editTextPassword.setError("Oops! Please enter your password.");
            editTextPassword.requestFocus();
            return;
        }
    }

    @Override
    public void startActivity() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    @Override
    public void userDNE() {
        editTextPassword.setError("Oops! The username or password is incorrect.");
        editTextPassword.requestFocus();
    }
}