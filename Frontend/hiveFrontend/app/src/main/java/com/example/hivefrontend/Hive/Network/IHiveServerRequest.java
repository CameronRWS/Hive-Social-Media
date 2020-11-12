package com.example.hivefrontend.Hive.Network;

import com.example.hivefrontend.Hive.Logic.IHiveVolleyListener;
import com.example.hivefrontend.ui.profile.ProfileVolleyListener;

/**
 * Interface implemented by ServerRequest
 */
public interface IHiveServerRequest {

    public void addVolleyListener(IHiveVolleyListener logic);
    public void displayScreen(String hiveName);
    public void fetchMemberCount(String hiveName);
    public void setUserHiveRequest(int userId);
    public void pageResumeRequests();
    public void checkLikes(final int postId);
    public void postLike(int postId);
}
