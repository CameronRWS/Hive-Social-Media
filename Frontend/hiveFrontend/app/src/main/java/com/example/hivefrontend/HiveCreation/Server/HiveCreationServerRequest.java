package com.example.hivefrontend.HiveCreation.Server;

import com.example.hivefrontend.HiveCreation.Logic.IHiveCreationVolleyListener;

public class HiveCreationServerRequest implements IHiveCreationServerRequest{

    private IHiveCreationVolleyListener logic;

    @Override
    public void addVolleyListener(IHiveCreationVolleyListener l) {
        this.logic = l;
    }

    @Override
    public void createHive() {

    }
}
