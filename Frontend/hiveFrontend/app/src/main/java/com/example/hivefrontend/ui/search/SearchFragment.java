package com.example.hivefrontend.ui.search;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

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

import org.json.JSONObject;

import java.util.ArrayList;


public class SearchFragment extends Fragment implements ISearchView{

    MapView mMapView;
    private GoogleMap googleMap;

    private SearchViewModel searchViewModel;
    private int selectedTab;
    private RecyclerView recycler;
    private SearchLogic logic;
    private SearchServerRequest server;
    private int userId;

    private ArrayList<JSONObject> hives;

    private ArrayList<Integer> userHives;

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
                googleMap.addMarker(new MarkerOptions().position(ames).title("Marker Title").snippet("Marker Description"));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(ames).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
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

    public void addToDisplayedHiveList(JSONObject hive){
        hives.add(hive);
    }

    public void addToUserHives(Integer id){
        userHives.add(id);
    }

    @Override
    public ArrayList<Integer> getUserHives() {
        return this.userHives;
    }

    @Override
    public ArrayList<JSONObject> getDisplayedHives() {
        return this.hives;
    }

    public int getUserId(){
        return userId;
    }

    public Context getSearchContext(){
        return this.getContext();
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        mMapView.onResume();
//    }
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