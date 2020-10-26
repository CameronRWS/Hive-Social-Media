package com.example.hivefrontend.ui.notifications.Logic;

import android.content.Context;

import com.example.hivefrontend.ui.notifications.INotificationsView;
import com.example.hivefrontend.ui.notifications.Network.ServerRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NotificationsLogic implements INotificationsVolleyListener {

    INotificationsView notificationsView;
    ServerRequest server;

    public NotificationsLogic(INotificationsView nv, ServerRequest sr)
    {
        this.notificationsView = nv;
        this.server = sr;
        server.addVolleyListener(this);
    }

    @Override
    public Context getNotificationsContext() {
        return notificationsView.getNotificationsContext();
    }

    @Override
    public void onSetNotificationSuccess(JSONArray response) {
        try {
            for (int i = response.length() - 1; i >= 0; i--) {
                server.addEntity(response.getJSONObject(i));
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String callUrlDeterminate(final JSONObject notification) {
       return notificationsView.urlDeterminate(notification);
    }

    @Override
    public void onAddEntitySuccess(JSONObject notification, JSONObject response) {
        try {
            JSONObject noti = new JSONObject(notification.toString());
            noti.put("entity", response);
            server.addCreator(noti);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String callUrlDeterminateCreator(JSONObject notification) {
        return notificationsView.urlDeterminateCreator(notification);
    }

    @Override
    public void onAddCreatorSuccess(JSONObject notification, JSONObject response) {
        try {
            JSONObject noti = new JSONObject(notification.toString());
            noti.put("creator", response);
            notificationsView.updateNotifcations(noti);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
