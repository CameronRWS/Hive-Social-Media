package com.example.hivefrontend.Hive.Logic;

import android.content.Context;

import com.android.volley.VolleyError;

import org.json.JSONArray;

public interface IHiveVolleyListener {
    Context getHiveContext();
    public void setUserHives();
    public void onPageResume();
    public void onGetHiveNameSuccess(JSONArray response, String hiveName);
    public void onFetchMemberCountSuccess(JSONArray response, String hiveName);
    public void clearAdapterData();
    public void notifyDataSetChanged();
    int getUserId();
    public void likePostLogic(int postId);
    public void onHiveRequestSuccess(JSONArray response);
    public void onError(VolleyError error);
}
