package com.example.hivefrontend.Register;

import android.content.Context;

/**
 * The interface that the RegisterActivity implements
 */
public interface IRegisterView {

    public String getUsername();
    public String getPassword();
    public String getEmailAddress();
    public void usernameCheck();
    public void passwordCheck();
    public void setRegParametersMet();
    public boolean getRegParametersMet();
    public void emailAddressCheck();
    public void validateEmailAddress();
    public Context getRegisterContext();
    public void takenName(String e);
    public void successfullyRegistered();
}
