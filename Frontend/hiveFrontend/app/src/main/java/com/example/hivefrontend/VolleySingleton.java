package com.example.hivefrontend;

import android.content.Context;

import com.android.volley.RequestQueue;

import com.android.volley.Request;
import com.android.volley.toolbox.Volley;

/**
 * VolleySingleton adds app controller features
 */
public class VolleySingleton{

    private static VolleySingleton mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;

    private VolleySingleton(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    /**
     * Returns the instace
     * @param context The context of the application
     * @return Returns this instance
     */
    public static synchronized VolleySingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VolleySingleton(context);
        }
        return mInstance;
    }

    /**
     * Gets the RequestQueue
     * @return the RequestQueue
     */
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    /**
     * Adds a request to the request queue
     * @param req The request
     * @param <T> The queue
     */
    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
