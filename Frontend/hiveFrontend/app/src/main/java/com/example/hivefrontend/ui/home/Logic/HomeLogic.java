package com.example.hivefrontend.ui.home.Logic;

import com.example.hivefrontend.ui.home.HomeFragment;
import com.example.hivefrontend.ui.home.Network.ServerRequest;

public class HomeLogic {

    HomeFragment home;
    public HomeLogic(HomeFragment homeFragment) {
        home = homeFragment;
    }

    public void setUserHives() {
        ServerRequest server = new ServerRequest(home);
        server.setUserHiveRequest();
    }

    public void onPageResume() {
        ServerRequest server = new ServerRequest(home);
        server.pageResumeRequests();
    }

    public void updatePostLogic() {
        ServerRequest server = new ServerRequest(home);
        server.updatePostRequest();
    }

    //gets the info about this post to see if the user has already liked it
    public void likePostLogic(int postId){
        ServerRequest server = new ServerRequest(home);
        server.checkLikes(postId);
    }

}
