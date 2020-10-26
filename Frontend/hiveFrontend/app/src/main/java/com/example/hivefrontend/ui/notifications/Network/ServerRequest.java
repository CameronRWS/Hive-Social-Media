package com.example.hivefrontend.ui.notifications.Network;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.hivefrontend.VolleySingleton;
import com.example.hivefrontend.ui.notifications.Logic.INotificationsVolleyListener;

import org.json.JSONArray;
import org.json.JSONObject;

public class ServerRequest implements INotificationsServerRequest {

    private String tag_json_obj = "json_obj_req";
    private INotificationsVolleyListener notificationsVolleyListener;

    @Override
    public void addVolleyListener(INotificationsVolleyListener n) { this.notificationsVolleyListener = n; }

    @Override
    public void setNotifications() {
        String url = "http://10.24.227.37:8080/notifications";
        JsonArrayRequest notiRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                       notificationsVolleyListener.onSetNotificationSuccess(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.i("volleyAppError","Error: " + error.getMessage());
                        Log.i("volleyAppError","VolleyError: "+ error);
                    }
                });
        VolleySingleton.getInstance(notificationsVolleyListener.getNotificationsContext()).addToRequestQueue(notiRequest);
    }

    @Override
    public void addEntity(final JSONObject notification) {
    String url = notificationsVolleyListener.callUrlDeterminate(notification);
        JsonObjectRequest entityRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                     notificationsVolleyListener.onAddEntitySuccess(notification, response);
                    }
                    }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.i("volleyAppError","Error: " + error.getMessage());
                        Log.i("volleyAppError","VolleyError: "+ error);
                    }
                });
        VolleySingleton.getInstance(notificationsVolleyListener.getNotificationsContext()).addToRequestQueue(entityRequest);

    }

    @Override
    public void addCreator(final JSONObject notification) {
    String url = notificationsVolleyListener.callUrlDeterminateCreator(notification);
        JsonObjectRequest userRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        notificationsVolleyListener.onAddCreatorSuccess(notification, response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.i("volleyAppError","Error: " + error.getMessage());
                        Log.i("volleyAppError","VolleyError: "+ error);
                    }
                });
        VolleySingleton.getInstance(notificationsVolleyListener.getNotificationsContext()).addToRequestQueue(userRequest);

    }

}
