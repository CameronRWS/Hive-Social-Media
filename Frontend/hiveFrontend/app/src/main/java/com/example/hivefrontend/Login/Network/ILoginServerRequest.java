package com.example.hivefrontend.Login.Network;

import com.example.hivefrontend.Login.Logic.ILoginVolleyListener;

/**
 * The interface implemented by the ServerRequest
 */
public interface ILoginServerRequest {
    public void addVolleyListener(ILoginVolleyListener l);
    public void loginUser();
}
