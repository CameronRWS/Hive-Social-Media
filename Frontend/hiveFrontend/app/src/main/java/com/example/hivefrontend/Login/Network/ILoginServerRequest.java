package com.example.hivefrontend.Login.Network;

import com.example.hivefrontend.Login.Logic.ILoginVolleyListener;

public interface ILoginServerRequest {
    public void addVolleyListener(ILoginVolleyListener l);
    public void loginUser();
}
