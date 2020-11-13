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

import com.example.hivefrontend.EditProfileActivity;
import com.example.hivefrontend.Login.LoginActivity;
import com.example.hivefrontend.MainActivity;
import com.example.hivefrontend.R;
import com.example.hivefrontend.Register.Logic.RegisterLogic;
import com.example.hivefrontend.Register.Network.ServerRequest;
import com.example.hivefrontend.SharedPrefManager;

/**
 * The activity for registering a new user.
 */
public class RegisterActivity extends AppCompatActivity implements IRegisterView {

    EditText usernameField, passwordField, emailAddressField, confirmEmailAddressField, confirmPasswordField;
    boolean regParametersMet = true;
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
        confirmEmailAddressField = (EditText) findViewById(R.id.confirmEmailAddressField);
        confirmPasswordField = (EditText) findViewById(R.id.confirmPasswordField);
        final Button cancelButton = (Button) findViewById(R.id.cancelButton);

        final ServerRequest serverRequest = new ServerRequest();
        RegisterLogic logic = new RegisterLogic(this, serverRequest);

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

    /**
     * Returns the register context.
     * @return The context.
     */
    @Override
    public Context getRegisterContext() {return this.getApplicationContext();}

    /**
     * Starts the Edit Profile activity upon successful registration.
     */
    @Override
    public void successfullyRegistered() {
        startActivity(new Intent(getApplicationContext(), EditProfileActivity.class));
    }

    /**
     * Returns the username of the newly-registered user.
     * @return the username
     */
    @Override
    public String getUsername() {
        return usernameField.getText().toString().trim();
    }

    /**
     * Returns the password of the newly-registered user.
     * @return The password.
     */
    @Override
    public String getPassword() {
        return passwordField.getText().toString().trim();
    }

    public void setRegParametersMet() {regParametersMet = true;}
    public boolean getRegParametersMet() {return regParametersMet;}
    /**
     * Returns the email address of the newly-registered user.
     * @return The email address
     */
    @Override
    public String getEmailAddress() {
        return emailAddressField.getText().toString().trim();
    }

    /**
     * Checks to see if the text box value is valid.
     */
    @Override
    public void usernameCheck() {
        if (TextUtils.isEmpty(getUsername())) {
            usernameField.setError("Oops! Please enter something for a username.");
            usernameField.requestFocus();
            return;
        }
    }

    /**
     * Checks to see if the text box value is valid.
     */
    @Override
    public void passwordCheck() {
        if (TextUtils.isEmpty(getPassword())) {
            passwordField.setError("Oops! Please enter something for a password.");
            passwordField.requestFocus();
            regParametersMet = false;
        }

        if (TextUtils.isEmpty(confirmPasswordField.getText().toString().trim())) {
            confirmPasswordField.setError("Oops! Please re-enter your password.");
            confirmPasswordField.requestFocus();
            regParametersMet = false;
        }

        if (!getPassword().equals(confirmPasswordField.getText().toString().trim())) {
            confirmPasswordField.setError("Your passwords don't match!");
            confirmPasswordField.requestFocus();
            regParametersMet = false;
        }
    }

    /**
     * Checks to see if the text box value is valid.
     */
    @Override
    public void emailAddressCheck() {
        if (TextUtils.isEmpty(confirmEmailAddressField.getText().toString().trim())) {
            confirmEmailAddressField.setError("Oops! Please re-enter your email address.");
            confirmEmailAddressField.requestFocus();
            regParametersMet = false;
        }
        if (TextUtils.isEmpty(getEmailAddress())) {
            emailAddressField.setError("Oops! Please enter something for an email address.");
            emailAddressField.requestFocus();
            regParametersMet = false;
        }
        if (!getEmailAddress().equals(confirmEmailAddressField.getText().toString().trim())) {
            confirmEmailAddressField.setError("Your email addresses don't match!");
            confirmEmailAddressField.requestFocus();
            regParametersMet = false;
        }

    }

    /**
     * Checks to see if the text box value is valid.
     */
    @Override
    public void validateEmailAddress() {
        if (!Patterns.EMAIL_ADDRESS.matcher(getEmailAddress()).matches()) {
            emailAddressField.setError("Darn. That email address isn't valid!");
            emailAddressField.requestFocus();
            return;
        }
    }


}