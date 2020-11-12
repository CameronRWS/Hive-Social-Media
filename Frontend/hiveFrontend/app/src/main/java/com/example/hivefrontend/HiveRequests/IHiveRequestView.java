package com.example.hivefrontend.HiveRequests;

import android.content.Context;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Interface implemented by HiveRequestsActivity
 */
public interface IHiveRequestView {

    void addToRequests(JSONObject request);

    Context getRequestsContext();

    void clearData();
}
