package com.example.hivefrontend.ui.search;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Interface implemented by SearchFragment
 */
public interface ISearchView {

    void addToDisplayedHiveList(JSONObject hive);

    void addToUserHives(Integer id);

    ArrayList<Integer> getUserHives();

    ArrayList<JSONObject> getDisplayedHives();

    int getUserId();

    Context getSearchContext();

    void notifyAdapterChange();

    void addMarker(double lat, double lon, String name);

    void addMarkers() throws JSONException;
}
