package com.example.hivefrontend.Login.Network;

import com.example.hivefrontend.Login.Logic.ILoginVolleyListener;

import org.json.JSONArray;

public interface ILoginServerRequest {
    public void loginUser();
    public void addVolleyListener(ILoginVolleyListener l);
}
