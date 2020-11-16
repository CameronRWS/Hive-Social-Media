package com.example.hivefrontend.EditProfile.Logic;

import android.content.Context;
import android.util.Log;

import com.example.hivefrontend.EditProfile.IEPView;
import com.example.hivefrontend.EditProfile.Network.ServerRequest;
import com.example.hivefrontend.Login.ILoginView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EPLogic implements IEPVolleyListener {
    IEPView epView;
    ServerRequest server;

    public EPLogic(IEPView ev, ServerRequest sr) {
        this.epView = ev;
        this.server = sr;
        server.addVolleyListener(this);
    }

    @Override
    public Context getEPContext() {return epView.getContext();}

    @Override
    public void onSaveSuccess(JSONArray response) {
        try{
            Log.i("bigmacburger", "ok actually we got here.");
            JSONObject user1 = response.getJSONObject(epView.getUserId() - 1);
            //Log.i("bigmacburger", user1.getString("userName"));

        }
        catch (JSONException e){
            e.printStackTrace();
            Log.i("jsonAppError",e.toString());
        }
    }
}
