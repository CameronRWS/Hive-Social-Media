package com.example.hivefrontend.ui.hive;

import android.content.Context;

import org.json.JSONObject;

import java.util.ArrayList;

public interface IHiveView {
    public Context getHiveContext();
    public void displayBio(String description);
    public void displayMemberCount(int count);
    void clearData();
    int getUserId();
    public void addToHiveIdsHome(int hiveId);
    public void addToHiveOptionsHome(String hiveName);
    public void notifyDataChange();
    void sortPosts();
    void addToHomePosts(JSONObject post);
    ArrayList<Integer> getHiveIdsHome();
    ArrayList<String> getHiveOptionsHome();

}
