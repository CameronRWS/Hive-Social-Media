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

/**
 * Activity to handle manage hive requests. A hive beekeeper will be able to see this page and accept or deny users' requests.
 */
public class HiveRequestsActivity extends AppCompatActivity implements IHiveRequestView{

    private HiveRequestServerRequest server;
    private static HiveRequestLogic logic;

    private static ArrayList<JSONObject> hiveRequests;
    private HiveRequestAdapter hiveRequestAdapter;

    /**
     * Upon creation of the HiveRequestsActivity, instantiates local variables and makes call to logic to get the hive requests.
     * @param savedInstanceState
     */
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

    /**
     * Calls the logic function to handle the logic for accepting or denying the given request.
     * @param position Position in the RecyclerView that the request to handle holds
     * @param status Status of the request, either accepted or denied
     * @throws JSONException
     */
    public static void handleRequest(int position, String status) throws JSONException {
        logic.handleRequestLogic(hiveRequests.get(position), status);
    }

    /**
     * Adds a request to the list of requests
     * @param request The request to be added
     */
    @Override
    public void addToRequests(JSONObject request) {

        Log.i("adding to requests", " request ");
        hiveRequests.add(request);
        hiveRequestAdapter.notifyDataSetChanged();
    }

    /**
     * Returns the context for this activity
     * @return The context for this activity
     */
    @Override
    public Context getRequestsContext() {
        return this.getApplicationContext();
    }

    /**
     * Clears the data from the hive requests and notifies the adapter of the data change
     */
    @Override
    public void clearData(){
        hiveRequests.clear();
        hiveRequestAdapter.notifyDataSetChanged();
    }
}