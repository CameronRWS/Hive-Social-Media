package com.example.hivefrontend.Hive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.hivefrontend.Hive.Logic.HiveLogic;
import com.example.hivefrontend.Hive.Network.ServerRequest;
import com.example.hivefrontend.R;
import com.example.hivefrontend.SharedPrefManager;
import com.example.hivefrontend.ui.home.HomeAdapter;
import com.example.hivefrontend.ui.home.HomeViewModel;
import com.example.hivefrontend.ui.home.Logic.HomeLogic;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class HiveActivity extends AppCompatActivity implements IHiveView {

    int joinState = 0;
    SharedPrefManager sharedPrefManager;
    int userId = 1;
    String givenHiveName;
    private HomeViewModel homeViewModel;
    public static ArrayList<Integer> hiveIds;
    public static ArrayList<String> hiveOptions;
    public static ArrayList<Integer> hiveIdsHome;
    public static ArrayList<String> hiveOptionsHome;
    public static ArrayList<Integer> hiveIdsDiscover;
    public static ArrayList<String> hiveOptionsDiscover;
    public static ArrayList<JSONObject> postObjects;
    public static ArrayList<JSONObject> discoverPostObjects;
    public static ArrayList<JSONObject> homePostObjects;
    public static HomeAdapter homeAdapter;
    public static HomeAdapter discoverAdapter;
    public int selectedTab;

    public static Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hive);
        final ImageButton joined = (ImageButton) findViewById(R.id.joinedHiveButton);
        final ImageButton join = (ImageButton) findViewById(R.id.joinHiveButton);
        final ImageButton requested = (ImageButton) findViewById(R.id.requestedHiveButton);
        final TextView tvHiveName = (TextView) findViewById(R.id.hiveName);
        givenHiveName = getIntent().getStringExtra("hiveName");
        tvHiveName.setText(givenHiveName);



        final ServerRequest serverRequest = new ServerRequest();
        HiveLogic logic = new HiveLogic(this, serverRequest);
        serverRequest.displayScreen(givenHiveName);
    }

    @Override
    public Context getHiveContext() {
        return this.getApplicationContext();
    }

    @Override
    public void displayBio(String description) {
        final TextView bio = (TextView) findViewById(R.id.hiveDescription);
        bio.setText(description);
    }

    @Override
    public void displayMemberCount(int count) {
        final TextView memberCount = (TextView) findViewById(R.id.hiveMemberCount);
        memberCount.setText(count + " bees and counting");
    }

    @Override
    public void clearData() {

            discoverPostObjects.clear();
            homePostObjects.clear();
            hiveIdsDiscover.clear();
            hiveOptionsDiscover.clear();
            hiveIdsHome.clear();
            hiveOptionsHome.clear();

    }


}