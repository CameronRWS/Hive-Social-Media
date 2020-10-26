package com.example.hivefrontend.ui.notifications.Network;

import com.example.hivefrontend.ui.notifications.Logic.INotificationsVolleyListener;

import org.json.JSONObject;

public interface INotificationsServerRequest {
    public void addVolleyListener(INotificationsVolleyListener n);
    public void setNotifications();
    public void addEntity(final JSONObject notification);
    public void addCreator(final JSONObject notification);

}
