package com.example.hivefrontend.ui.hive;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.hivefrontend.R;
import com.example.hivefrontend.SharedPrefManager;
import com.example.hivefrontend.ui.hive.Logic.HiveLogic;
import com.example.hivefrontend.ui.hive.Network.ServerRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class HiveFragment extends Fragment implements IHiveView{

    public static Context context;
    private com.example.hivefrontend.ui.hive.HiveViewModel hiveViewModel;

    int joinState = 0;
    SharedPrefManager sharedPrefManager;
    int userId = 1;
    String givenHiveName;
    String description;
    int memberCount;

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
    public static View root;
    public static com.example.hivefrontend.ui.hive.Logic.HiveLogic logic;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        com.example.hivefrontend.ui.hive.Network.ServerRequest serverRequest = new ServerRequest();
        logic = new HiveLogic(this, serverRequest);
        logic.setUserHives();
        hiveViewModel = ViewModelProviders.of(this).get(com.example.hivefrontend.ui.hive.HiveViewModel.class);
        View root = inflater.inflate(R.layout.fragment_hive, container, false);
        hiveViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });

        serverRequest.displayScreen("ISU Math Nerds");


        final ImageButton joined = (ImageButton) root.findViewById(R.id.joinedHiveButton);
        final ImageButton join = (ImageButton) root.findViewById(R.id.joinHiveButton);
        final ImageButton requested = (ImageButton) root.findViewById(R.id.requestedHiveButton);
        final TextView tvHiveName = (TextView) root.findViewById(R.id.hiveName);
        //givenHiveName = getActivity().getIntent().getStringExtra("hiveName");
        tvHiveName.setText("Test Hive");
        final TextView bio = (TextView) root.findViewById(R.id.hiveDescription);
        bio.setText(this.description);
        final TextView memberCount = (TextView) root.findViewById(R.id.hiveMemberCount);
        memberCount.setText(this.memberCount + " bees and counting");

        userId = SharedPrefManager.getInstance(this.getContext()).getUser().getId();
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
        root = inflater.inflate(R.layout.fragment_hive, container, false);
        hiveViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {

            }
        });


        final RecyclerView recyclerView = root.findViewById(R.id.hivePostRecyler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        hiveAdapter = new com.example.hivefrontend.ui.hive.HiveAdapter(getActivity().getApplicationContext(), discoverPostObjects, hiveIdsHome, hiveOptionsHome);
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
        return this.getContext();
    }

    /**
     * Changes the hive's bio via text view.
     * @param description The string literal which holds the description.
     */
    @Override
    public void displayBio(String description) {
        this.description = description;
    }

    /**
     * Handles the situation where a resume is invoked.
     */
    @Override
    public void onResume() {
        super.onResume();
        logic.onPageResume();
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
        this.memberCount = count;
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

    @Override
    public void addToHomePosts(JSONObject post) {
        homePostObjects.add(post);
    }

    @Override
    public ArrayList<Integer> getHiveIdsHome() {
        return hiveIdsHome;
    }

    @Override
    public ArrayList<String> getHiveOptionsHome() {
        return hiveOptionsHome;
    }

}