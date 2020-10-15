package com.example.hivefrontend.ui.profile;

import android.content.Context;

import org.json.JSONArray;

public interface IProfileView {

    public Context getProfileContext();

    public int getUserId();

    void setDisplayName(String name);

    void setDisplayLocation(String location);

    void setLocationInvisible();

    void setUserName(String uName);

    void setBio(String biography);

    void setHiveListHeading(String your_hives);

    void addHiveId(Integer hiveId);

    void addToHiveOptions(String hiveName);

    void notifyChangeForAdapter();

    void setPollenCountText(String substring);
}
