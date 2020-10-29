package com.example.hivefrontend.HiveRequests.Server;

import com.example.hivefrontend.HiveRequests.Logic.HiveRequestLogic;
import com.example.hivefrontend.HiveRequests.Logic.IHiveRequestVolleyListener;

public interface IHiveRequestServerRequest {
    void addVolleyListener(IHiveRequestVolleyListener logic);

    void getHiveRequests();
}
