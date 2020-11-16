package com.example.hivefrontend.EditProfile.Network;

import com.example.hivefrontend.EditProfile.Logic.IEPVolleyListener;
import com.example.hivefrontend.Login.Logic.ILoginVolleyListener;

public interface IEPServerRequest {
    public void addVolleyListener(IEPVolleyListener l);
    public void onSave();
}
