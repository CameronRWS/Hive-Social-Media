package com.example.hivefrontend.Login;

import android.content.Context;

public interface ILoginView {

    public String getUsername();
    public String getPassword();
    public Context getLoginContext();
    public void openHome();



}
