package com.example.hivefrontend.ui.profile;

public interface IProfileServerRequest {
    public void userInfoRequest();
    public void hiveListRequest();
    public int getMemberCount();
    public void fetchHiveDescription(final String hiveName);
    public void pollenCountRequest();
    public void addVolleyListener(ProfileVolleyListener logic);
    public void fetchMemberCount(String hiveName);
}
