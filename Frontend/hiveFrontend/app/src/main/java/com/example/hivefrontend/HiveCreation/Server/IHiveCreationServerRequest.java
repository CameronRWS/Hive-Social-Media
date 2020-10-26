package com.example.hivefrontend.HiveCreation.Server;


import com.example.hivefrontend.HiveCreation.Logic.IHiveCreationVolleyListener;

public interface IHiveCreationServerRequest {
    void addVolleyListener(IHiveCreationVolleyListener l);

    void createHive();
}
