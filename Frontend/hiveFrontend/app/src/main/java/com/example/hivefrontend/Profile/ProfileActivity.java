package com.example.hivefrontend.Profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hivefrontend.R;
import com.example.hivefrontend.ui.profile.IProfileView;
import com.example.hivefrontend.ui.profile.MyAdapter;
import com.example.hivefrontend.ui.profile.Network.ServerRequest;
import com.example.hivefrontend.ui.profile.ProfileViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity implements IProfileView {

    private ProfileViewModel mViewModel;
    public ArrayList<Integer> hiveIds;
    public ArrayList<String> hiveOptions;
    public int userId;
    private String pollen;
    public TextView displayName;
    public ImageView locationPin;
    public TextView userName;
    public TextView pollenCount;
    public TextView displayLocation;
    public TextView hiveListHeading;
    public TextView bio;
    public TextView dateJoined;
    public RecyclerView recyclerView;
    public MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        userId = getIntent().getIntExtra("userId", -1);


        //final ProfileLogic logic = new ProfileLogic(this);
        hiveIds = new ArrayList<>();
        hiveOptions = new ArrayList<>();
        TextView userDisplayName = findViewById(R.id.displayName);

        hiveIds= new ArrayList<>();
        hiveOptions = new ArrayList<>();
        displayName = (TextView) findViewById(R.id.displayName);
        locationPin = (ImageView) findViewById(R.id.locationPin);
        userName = (TextView) findViewById(R.id.userName);
        pollenCount = (TextView) findViewById(R.id.pollenCount);
        displayLocation = (TextView) findViewById(R.id.displayLocation);
        hiveListHeading = (TextView) findViewById(R.id.hiveListHeading);
        bio = (TextView) findViewById(R.id.bio);
        dateJoined = (TextView) findViewById(R.id.dateJoined);
        RecyclerView recyclerView = findViewById(R.id.hiveListRecyler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        myAdapter = new MyAdapter(getApplicationContext(), hiveOptions);
        recyclerView.setAdapter(myAdapter);


        ServerRequest serverRequest = new ServerRequest();
        com.example.hivefrontend.ui.profile.Logic.ProfileLogic logic = new com.example.hivefrontend.ui.profile.Logic.ProfileLogic(this, serverRequest);
        logic.displayProfile();

    }


    @Override
    public Context getProfileContext() {
        return this.getApplicationContext();
    }

    @Override
    public int getUserId() {
        return userId;
    }

    @Override
    public void setDisplayName(String name) {
        displayName.setText(name);
    }

    @Override
    public void setDisplayLocation(String location) {
        displayLocation.setText(location);
    }

    @Override
    public void setLocationInvisible() {
        displayLocation.setVisibility(View.INVISIBLE);
        locationPin.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setUserName(String uName) {
        userName.setText(uName);
    }

    @Override
    public void setBio(String biography) {
        bio.setText(biography);
    }

    @Override
    public void setHiveListHeading(String your_hives) {
        String heading = your_hives + "'s Public Hives:";
        hiveListHeading.setText(heading);
    }

    @Override
    public void addHiveId(Integer hiveId) {
        hiveIds.add(hiveId);
    }

    @Override
    public void addToHiveOptions(String hiveName) {
        hiveOptions.add(hiveName);
    }

    @Override
    public void notifyChangeForAdapter() {
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public void setPollenCountText(String substring) {
        pollenCount.setText(substring);
    }
}