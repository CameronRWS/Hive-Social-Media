package com.example.hivefrontend.ui.profile;

public interface IProfileServerRequest {
    public void userInfoRequest();
    public void hiveListRequest();
    public void pollenCountRequest();
    public void addVolleyListener(ProfileVolleyListener logic);
}
