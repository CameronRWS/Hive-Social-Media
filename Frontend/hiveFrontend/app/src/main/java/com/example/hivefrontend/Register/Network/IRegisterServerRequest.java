package com.example.hivefrontend.Register.Network;

import com.example.hivefrontend.Register.Logic.IRegisterVolleyListener;
import com.example.hivefrontend.ui.buzz.Logic.IBuzzVolleyListener;

/**
 * The interface tha the server request implements.
 */
public interface IRegisterServerRequest {

    public void availableCheck();
    public void addVolleyListener(IRegisterVolleyListener r);
    public void registerUser();
}
