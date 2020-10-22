package com.example.hivefrontend.Register;

import android.content.Context;

public interface IRegisterView {

    public String getUsername();
    public String getPassword();
    public String getEmailAddress();
    public void usernameCheck();
    public void passwordCheck();
    public void emailAddressCheck();
    public void validateEmailAddress();
    public Context getRegisterContext();
    public void successfullyRegistered();
}
