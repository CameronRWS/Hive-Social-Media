package com.example.hivefrontend.ui.search;

import android.content.Context;

import org.json.JSONObject;

import java.util.ArrayList;

public interface ISearchView {

    void addToDisplayedHiveList(JSONObject hive);

    void addToUserHives(Integer id);

    ArrayList<Integer> getUserHives();

    ArrayList<JSONObject> getDisplayedHives();

    int getUserId();

    Context getSearchContext();
}
