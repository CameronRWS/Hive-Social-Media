package com.example.hivefrontend.Login;

import android.content.Context;

public interface ILoginView {
    public Context getLoginContext();
    public String getUsername();
    public String getPassword();
    public void checkUsername();
    public void checkPassword();
    public void startActivity();
    public void userDNE();
}
