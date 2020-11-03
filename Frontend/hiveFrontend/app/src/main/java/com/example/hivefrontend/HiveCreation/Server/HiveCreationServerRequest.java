package com.example.hivefrontend.HiveCreation.Server;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.hivefrontend.HiveCreation.Logic.IHiveCreationVolleyListener;
import com.example.hivefrontend.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

public class HiveCreationServerRequest implements IHiveCreationServerRequest{

    private IHiveCreationVolleyListener logic;

    @Override
    public void addVolleyListener(IHiveCreationVolleyListener l) {
        this.logic = l;
    }

    @Override
    public void createHive(JSONObject hive) {
        String url ="http://10.24.227.37:8080/hives";


        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,
                hive, new Response.Listener<JSONObject>(){

            public void onResponse(JSONObject response) {
                try {
                    logic.onHiveCreationSuccess(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error){
                Log.i("request","fail!");
            }
        });
        // Add the request to the RequestQueue.
        VolleySingleton.getInstance(logic.getPostContext()).addToRequestQueue(jsonObjectRequest);
    }

    public void addMembership(int hiveId) throws JSONException {
        String url ="http://10.24.227.37:8080/members";

        JSONObject member = new JSONObject();
        member.put("hiveId",String.valueOf(hiveId));
        member.put("userId",logic.getUserId());
        member.put("isModerator",true);

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,
                member, new Response.Listener<JSONObject>(){

            public void onResponse(JSONObject response) {
                logic.onMemberCreationSuccess(response);
            }

        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error){
                Log.i("request","fail!");
            }
        });
        // Add the request to the RequestQueue.
        VolleySingleton.getInstance(logic.getPostContext()).addToRequestQueue(jsonObjectRequest);
    }
}
