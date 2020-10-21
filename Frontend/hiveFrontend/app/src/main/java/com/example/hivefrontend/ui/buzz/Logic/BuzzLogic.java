package com.example.hivefrontend.ui.buzz.Logic;

import android.content.Context;

import com.example.hivefrontend.ui.buzz.IBuzzView;
import com.example.hivefrontend.ui.buzz.Network.IBuzzServerRequest;

import org.json.JSONArray;
import org.json.JSONException;

public class BuzzLogic implements IBuzzVolleyListener{

    IBuzzView buzzView;
    IBuzzServerRequest server;

    public BuzzLogic (IBuzzView bv, IBuzzServerRequest bsr) {
        this.buzzView = bv;
        this.server = bsr;
        server.addVolleyListener(this);
    }

    public void onGetHivesSuccess(JSONArray response) {
        try {

        }
        catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public Context getBuzzContext() { return buzzView.getBuzzContext();}

}
