package com.example.hivefrontend.Hive;

import android.content.Context;

import com.example.hivefrontend.Hive.Network.ServerRequest;

/**
 * The interface that the HiveActivity implements.
 */
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

}