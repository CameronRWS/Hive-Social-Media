package com.example.hivefrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

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
        Toast.makeText(getApplicationContext(), "Great!", Toast.LENGTH_LONG).show();
        String url = "http://10.24.227.37:8080/userRegistrations";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.POST, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject obj = response.getJSONObject(1);
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        JSONObject userJson = obj.getJSONObject("user");

                        Toast.makeText(getApplicationContext(), "So", Toast.LENGTH_LONG).show();
                        User user = new User(
                                userJson.getInt("userId"),
                                userJson.getString("userName"),
                                userJson.getString("email")
                        );
                        Toast.makeText(getApplicationContext(), "far", Toast.LENGTH_LONG).show();

                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                        finish();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Toast.makeText(getApplicationContext(), "Great!", Toast.LENGTH_LONG).show();
                                Map<String, String> params = new HashMap<>();
                                params.put("userName", username);
                                params.put("email", emailAddress);
                                return params;
                            }
        };

                        VolleySingleton.getInstance(this).addToRequestQueue(jsonArrayRequest);
    }
}