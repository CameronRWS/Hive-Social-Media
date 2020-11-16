package com.example.hivefrontend.ui.hive.Logic;

import android.content.Context;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public interface IHiveVolleyListener {
    Context getHiveContext();
    public void setUserHives();
    public void onPageResume();
    public void onGetHiveNameSuccess(JSONArray response, String hiveName);
    public void onFetchMemberCountSuccess(JSONArray response, String hiveName);
    public void clearAdapterData();
    public void notifyDataSetChanged();
    int getUserId();
    void sortData();
    void addToHomePosts(JSONObject post);
    public void likePostLogic(int postId);
    public void onHiveRequestSuccess(JSONArray response);
    public void onError(VolleyError error);
    ArrayList<Integer> getHiveIdsHome();

}
