package com.example.hivefrontend.ui.hive.Network;

import com.example.hivefrontend.ui.hive.Logic.IHiveVolleyListener;

public interface IHiveServerRequest {

    void addVolleyListener(IHiveVolleyListener l);
    public void displayScreen(String hiveName);
    public void fetchMemberCount(String hiveName);
    public void setUserHiveRequest(int userId);
    public void pageResumeRequests();
    public void checkLikes(final int postId);
    public void postLike(int postId);
}
