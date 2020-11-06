package com.example.hivefrontend.Hive.Network;

import com.example.hivefrontend.Hive.Logic.IHiveVolleyListener;
import com.example.hivefrontend.ui.profile.ProfileVolleyListener;

public interface IHiveServerRequest {

    public void addVolleyListener(IHiveVolleyListener logic);
    public void displayScreen(String hiveName);
    public void fetchMemberCount(String hiveName);
}
