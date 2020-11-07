package com.example.hivefrontend.ui.search.Server;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.hivefrontend.VolleySingleton;
import com.example.hivefrontend.ui.search.ISearchView;
import com.example.hivefrontend.ui.search.Logic.ISearchVolleyListener;
import com.example.hivefrontend.ui.search.Logic.SearchLogic;

import org.json.JSONArray;
import org.json.JSONException;

public class SearchServerRequest implements ISearchServerRequest {
    private ISearchVolleyListener logic;
    private ISearchView search;

    @Override
    public void addVolleyListener(ISearchVolleyListener searchLogic) {
        this.logic = searchLogic;
    }

    @Override
    public void getUserHives(int userId) {
        //get the ids of the hive this user is a part of
        String url ="http://10.24.227.37:8080/members/byUserId/" + userId;

        JsonArrayRequest hiveRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        logic.onHiveRequestSuccess(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        logic.onError(error);

                    }
                });

        // Add the request to the RequestQueue.
        VolleySingleton.getInstance(logic.getSearchContext()).addToRequestQueue(hiveRequest);
    }

    public void getOtherHives(){
        String url ="http://10.24.227.37:8080/hives";

        JsonArrayRequest hiveRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            logic.onGetOtherHivesRequestSuccess(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        logic.onError(error);

                    }
                });

        // Add the request to the RequestQueue.
        VolleySingleton.getInstance(logic.getSearchContext()).addToRequestQueue(hiveRequest);
    }
}
