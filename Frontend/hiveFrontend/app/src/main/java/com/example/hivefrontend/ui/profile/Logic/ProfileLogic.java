package com.example.hivefrontend.ui.profile.Logic;

import com.example.hivefrontend.ui.profile.Network.ServerRequest;
import com.example.hivefrontend.ui.profile.ProfileFragment;

public class ProfileLogic {
    ProfileFragment profile;

    public ProfileLogic(ProfileFragment p){
        this.profile = p;
    }

    public void displayProfile(){
        ServerRequest server = new ServerRequest(profile);
        server.profileRequest();
    }
}
