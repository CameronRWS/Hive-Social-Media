package com.example.hivefrontend.Hive;

import android.content.Context;

import com.example.hivefrontend.Hive.Network.ServerRequest;

public interface IHiveView {
    public int getUserId(ServerRequest server, String email);
    public Context getHiveContext();

}
