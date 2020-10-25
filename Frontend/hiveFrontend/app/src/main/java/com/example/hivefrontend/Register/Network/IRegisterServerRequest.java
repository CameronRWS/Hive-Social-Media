package com.example.hivefrontend.Register.Network;

import com.example.hivefrontend.Register.Logic.IRegisterVolleyListener;
import com.example.hivefrontend.ui.buzz.Logic.IBuzzVolleyListener;

public interface IRegisterServerRequest {

    public void addVolleyListener(IRegisterVolleyListener r);
    public void registerUser();
}
