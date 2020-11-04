package com.example.hivefrontend.ui.notifications.Logic;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

public interface INotificationsVolleyListener {

    Context getNotificationsContext();
    public void onSetNotificationSuccess(JSONArray response);
    public String callUrlDeterminate(final JSONObject notification);
    public void onAddEntitySuccess(final JSONObject notification, JSONObject response);
    public String callUrlDeterminateCreator(final JSONObject notification);
    public void onAddCreatorSuccess(final JSONObject notification, JSONObject response);
    int getUserId();
}
