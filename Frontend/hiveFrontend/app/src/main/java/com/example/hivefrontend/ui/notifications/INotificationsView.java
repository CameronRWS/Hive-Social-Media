package com.example.hivefrontend.ui.notifications;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

public interface INotificationsView {

    public Context getNotificationsContext();
    public String urlDeterminate(final JSONObject notification);
    public String urlDeterminateCreator(final JSONObject notification);
    public void updateNotifcations(JSONObject noti);

    int getUserId();
}
