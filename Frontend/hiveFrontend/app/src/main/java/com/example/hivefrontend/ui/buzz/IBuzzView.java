package com.example.hivefrontend.ui.buzz;

import android.content.Context;

public interface IBuzzView {

    public void addHiveIdValue(int i);
    public Context getBuzzContext();
    public void openHome();
    public int getHiveId(int pos);
    public String getBuzzTitle();
    public String getBuzzContent();
    public String getHiveOption(int pos);
    public void onOptionsSet();
    public int getUserId();
    public int getSelectedItemPos();
    public void addHiveOptionsValue(String s);
}
