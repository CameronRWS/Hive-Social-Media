package com.example.hivefrontend.ui.search;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hivefrontend.PostDetails.PostDetailsActivity;
import com.example.hivefrontend.R;
import com.example.hivefrontend.SharedPrefManager;
import com.example.hivefrontend.ui.search.Logic.SearchLogic;
import com.example.hivefrontend.ui.search.Server.SearchServerRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Fragment for the search screen. Consists of two tabs, a map view and a list view showing nearby hives.
 * Will not show hives the user is already a part of, primary function is to discover new hives.
 */
public class SearchFragment extends Fragment implements ISearchView{

    /**
     * MapView used for this SearchFragment to hold the GoogleMap
     */
    MapView mMapView;
    /**
     * GoogleMap used for this SearchFragment
     */
    private GoogleMap googleMap;

    private SearchViewModel searchViewModel;

    /**
     * The selected tab, 0 if map tab 1 if list tab
     */
    private int selectedTab;
    /**
     * RecyclerView for this SearchFragment
     */
    private RecyclerView recycler;
    /**
     * SearchLogic for this SearchFragment
     */
    private SearchLogic logic;
    /**
     * SearchServerRequest for this SearchFragment
     */
    private SearchServerRequest server;
    /**
     * The current user's user id
     */
    private int userId;
    /**
     * The list of hive objects to be displayed on this SearchFragment
     */
    private ArrayList<JSONObject> hives;
    /**
     * The list of hive ids of hives this user is a part of
     * Used to ensure the user's hives are not displayed
     */
    private ArrayList<Integer> userHives;
    /**
     * The adapter used for this SearchFragment's RecyclerView
     */
    private SearchAdapter mAdapter;

    /**
     * Upon creation, makes a MapView to be displayed on a map tab, and sets up tabs
     * Also calls the logic class to get list of hives to show.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchViewModel =
                ViewModelProviders.of(this).get(SearchViewModel.class);
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        SearchServerRequest server = new SearchServerRequest();
        logic = new SearchLogic(this,server);
        userHives = new ArrayList<>();
        hives = new ArrayList<>();
        userId = SharedPrefManager.getInstance(this.getContext()).getUser().getId();

        recycler = rootView.findViewById(R.id.hiveListRecyler);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        mAdapter = new SearchAdapter(this.getContext(), hives);
        recycler.setAdapter(mAdapter);
        recycler.setVisibility(View.GONE);

        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For dropping a marker at a point on the Map
                LatLng ames = new LatLng(42.031, -93.612);
                googleMap.addMarker(new MarkerOptions().position(ames).title("Iowa State University").snippet("Ames, IA"));
                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(ames).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                googleMap.getUiSettings().setZoomControlsEnabled(true);
                googleMap.getUiSettings().setMapToolbarEnabled(true);
                googleMap.getUiSettings().setScrollGesturesEnabled(true);
                googleMap.setMaxZoomPreference(20);
            }
        });


        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tabLayout);
        //set on tab selected listener--on tab reselect or select, set the recyclerView's adapter to home/discover adapter as needed
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                selectedTab = tab.getPosition();
                if(selectedTab == 0){
                    mMapView.setVisibility(View.VISIBLE);
                    recycler.setVisibility(View.GONE);
                }
                else{
                    mMapView.setVisibility(View.GONE);
                    recycler.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                selectedTab = tab.getPosition();

                if(selectedTab == 0){
                    mMapView.setVisibility(View.VISIBLE);
                    recycler.setVisibility(View.GONE);
                }
                else{
                    mMapView.setVisibility(View.GONE);
                    recycler.setVisibility(View.VISIBLE);
                }
            }
        });

        logic.getHives();

        return rootView;
    }

    public void addMarkers() throws JSONException {
        for(int i = 0; i < hives.size(); i++){
            JSONObject hive = hives.get(i);
            double lat = hive.getDouble("latitude");
            double lon = hive.getDouble("longitude");
            String name = hive.getString("name");
            addMarker(lat,lon, name);
        }
    }
    public void addMarker(double lat, double lon, String name){
        // Creating a marker
        MarkerOptions markerOptions = new MarkerOptions();

        LatLng latLng = new LatLng(lat, lon);
        // Setting the position for the marker
        markerOptions.position(latLng);

        // Setting the title for the marker.
        // This will be displayed on tapping the marker
        markerOptions.title(name);

        googleMap.addMarker(markerOptions);
    }


    /**
     * Notifies the adapter of a data change
     */
    public void notifyAdapterChange(){
        mAdapter.notifyDataSetChanged();
    }

    /**
     * Adds the given hive object to the list of hives
     * @param hive The hive to add
     */
    public void addToDisplayedHiveList(JSONObject hive){
        hives.add(hive);
    }

    /**
     * Adds the given id to the list of user ids
     * @param id
     */
    public void addToUserHives(Integer id){
        userHives.add(id);
    }

    /**
     * Returns the list of hive ids of hives this user is a part of
     * @return The list of hive ids of hives this user is a part of
     */
    @Override
    public ArrayList<Integer> getUserHives() {
        return this.userHives;
    }

    /**
     * Returns the list of hive objects of hives to be displayed
     * @return The list of hive object of hives to be displayed
     */
    @Override
    public ArrayList<JSONObject> getDisplayedHives() {
        return this.hives;
    }

    /**
     * Returns the user id of the logged in user
     * @return The user id of the logged in user
     */
    public int getUserId(){
        return userId;
    }

    /**
     * Returns the Context for this fragment
     * @return Context for this fragment
     */
    public Context getSearchContext(){
        return this.getContext();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        mMapView.onPause();
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        mMapView.onDestroy();
//    }
//
//    @Override
//    public void onLowMemory() {
//        super.onLowMemory();
//        mMapView.onLowMemory();
//    }
}