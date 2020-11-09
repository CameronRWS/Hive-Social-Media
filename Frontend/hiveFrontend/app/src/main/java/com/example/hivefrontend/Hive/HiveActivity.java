package com.example.hivefrontend.Hive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.fragment.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import org.json.JSONObject;
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
import java.util.Collections;

/**
 * Activity to display a hive
 */
public class HiveActivity extends AppCompatActivity implements IHiveView {

    public static HiveLogic logic;
    public static Context context;

    int joinState = 0;
    SharedPrefManager sharedPrefManager;
    int userId = 1;
    String givenHiveName;
    private HiveViewModel hiveViewModel;
    public static ArrayList<Integer> hiveIds;
    public static ArrayList<String> hiveOptions;
    public static ArrayList<Integer> hiveIdsHome;
    public static ArrayList<String> hiveOptionsHome;
    public static ArrayList<Integer> hiveIdsDiscover;
    public static ArrayList<String> hiveOptionsDiscover;
    public static ArrayList<JSONObject> postObjects;
    public static ArrayList<JSONObject> discoverPostObjects;
    public static ArrayList<JSONObject> homePostObjects;
    public static HiveAdapter hiveAdapter;
    public int selectedTab;


    public View onCreateView(@NonNull LayoutInflater inflater,
                                ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hive);

        ServerRequest serverRequest = new ServerRequest();
        logic = new HiveLogic(this, serverRequest);
        logic.setUserHives();


        serverRequest.displayScreen(givenHiveName);

        final ImageButton joined = (ImageButton) findViewById(R.id.joinedHiveButton);
        final ImageButton join = (ImageButton) findViewById(R.id.joinHiveButton);
        final ImageButton requested = (ImageButton) findViewById(R.id.requestedHiveButton);
        final TextView tvHiveName = (TextView) findViewById(R.id.hiveName);
        givenHiveName = getIntent().getStringExtra("hiveName");
        tvHiveName.setText(givenHiveName);

        userId = SharedPrefManager.getInstance(this.getApplicationContext()).getUser().getId();
        hiveIdsHome = new ArrayList<>();
        hiveOptionsHome = new ArrayList<>();
        hiveIdsDiscover = new ArrayList<>();
        hiveOptionsDiscover = new ArrayList<>();
        hiveIds= new ArrayList(hiveIdsHome);
        hiveOptions = new ArrayList(hiveOptionsHome);

        homePostObjects = new ArrayList<>();
        postObjects = new ArrayList(homePostObjects);
        discoverPostObjects = new ArrayList<>();

        hiveViewModel = ViewModelProviders.of(this).get(HiveViewModel.class);
        View root = inflater.inflate(R.layout.activity_hive, container, false);
        hiveViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

            }
        });


        final RecyclerView recyclerView = root.findViewById(R.id.hivePostRecyler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        hiveAdapter = new HiveAdapter(this.getApplicationContext(), discoverPostObjects, hiveIdsHome, hiveOptionsHome);
        recyclerView.setAdapter(hiveAdapter);

        context = root.getContext();
        return root;


    }

    /**
     * Gets the hive's context
     * @return The hive's context
     */
    @Override
    public Context getHiveContext() {
        return this.getApplicationContext();
    }

    /**
     * Changes the hive's bio via text view.
     * @param description The string literal which holds the description.
     */
    @Override
    public void displayBio(String description) {
        final TextView bio = (TextView) findViewById(R.id.hiveDescription);
        bio.setText(description);
    }

    /**
     * Handles the situation where a resume is invoked.
     */
    @Override
    public void onResume() {
        super.onResume();
       // logic.onPageResume();
    }

    /**
     * Handles liking a post
     * @param postId The id of the liked post.
     */
    public static void likePost(final int postId){
        logic.likePostLogic(postId);
    }

    /**
     * Displays the member count of the hive via text view.
     * @param count The member count
     */
    @Override
    public void displayMemberCount(int count) {
        final TextView memberCount = (TextView) findViewById(R.id.hiveMemberCount);
        memberCount.setText(count + " bees and counting");
    }

    /**
     * Gets the user id.
     * @return The user id.
     */
    public int getUserId() {return userId;}
    public void addToHiveIdsHome(int hiveId){
        hiveIdsHome.add(hiveId);
    }

    /**
     * Adds to the options of hives.
     * @param hiveName The string literal which holds the hive's display name.
     */
    public void addToHiveOptionsHome(String hiveName){
        hiveOptionsHome.add(hiveName);
    }

    /**
     * Clears data from classes.
     */
    @Override
    public void clearData() {

            discoverPostObjects.clear();
            homePostObjects.clear();
            hiveIdsDiscover.clear();
            hiveOptionsDiscover.clear();
            hiveIdsHome.clear();
            hiveOptionsHome.clear();

    }

    /**
     * Detects a change in data.
     */
    @Override
    public void notifyDataChange() {
        hiveAdapter.notifyDataSetChanged();
    }

    /**
     * Sorts posts by time using the Comparator class.
     */
    @Override
    public void sortPosts() {
        Collections.sort(homePostObjects, new PostComparator());
    }


}