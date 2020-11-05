package com.example.hivefrontend.HiveRequests;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.example.hivefrontend.HiveRequests.Logic.HiveRequestLogic;
import com.example.hivefrontend.HiveRequests.Server.HiveRequestServerRequest;
import com.example.hivefrontend.R;
import com.example.hivefrontend.ui.notifications.NotificationsAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HiveRequestsActivity extends AppCompatActivity implements IHiveRequestView{

    private HiveRequestServerRequest server;
    private static HiveRequestLogic logic;

    private static ArrayList<JSONObject> hiveRequests;
    private HiveRequestAdapter hiveRequestAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hive_requests);

        server = new HiveRequestServerRequest();
        logic = new HiveRequestLogic(this, server);
        server.addVolleyListener(logic);

        hiveRequests = new ArrayList<>();

        final RecyclerView recyclerView = findViewById(R.id.hive_request_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        hiveRequestAdapter = new HiveRequestAdapter(getApplicationContext(), hiveRequests);
        recyclerView.setAdapter(hiveRequestAdapter);

        logic.getHiveRequests();
    }
    public static void denyRequest(int position) {

    }
    public static void acceptRequest(int position, String status) throws JSONException {
        logic.acceptRequestLogic(hiveRequests.get(position), status);
    }

    @Override
    public void setRequests(ArrayList<JSONObject> requests) {

    }

    @Override
    public void addToRequests(JSONObject request) {

        Log.i("adding to requests", " request ");
        hiveRequests.add(request);
        hiveRequestAdapter.notifyDataSetChanged();
    }

//    @Override
//    public void acceptRequest(int position) {
//
//    }
//
//    @Override
//    public void denyRequest(int position) {
//
//    }


    @Override
    public Context getRequestsContext() {
        return this.getApplicationContext();
    }

    @Override
    public void clearData(){
        hiveRequests.clear();
        hiveRequestAdapter.notifyDataSetChanged();
    }
}