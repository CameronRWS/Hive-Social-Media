package com.example.hivefrontend.ui.search.Server;

import com.example.hivefrontend.ui.search.Logic.ISearchVolleyListener;
import com.example.hivefrontend.ui.search.Logic.SearchLogic;

public interface ISearchServerRequest {
    void addVolleyListener(ISearchVolleyListener searchLogic);

    void getUserHives(int userId);

    void getOtherHives();
}
