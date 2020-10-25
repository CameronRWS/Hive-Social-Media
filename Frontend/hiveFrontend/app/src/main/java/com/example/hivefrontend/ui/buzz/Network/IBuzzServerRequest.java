package com.example.hivefrontend.ui.buzz.Network;

import com.example.hivefrontend.ui.buzz.Logic.IBuzzVolleyListener;

public interface IBuzzServerRequest {

    public void addVolleyListener(IBuzzVolleyListener l);
    public void getHives(int userId);
    public void makeBuzz();
}
