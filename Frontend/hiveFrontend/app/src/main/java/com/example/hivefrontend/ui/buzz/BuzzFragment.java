package com.example.hivefrontend.ui.buzz;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.hivefrontend.MainActivity;
import com.example.hivefrontend.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BuzzFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    EditText buzzTitle;
    EditText buzzContent;
    private BuzzViewModel mViewModel;
    private ArrayList<Integer> hiveIds;
    private ArrayList<String> hiveOptions;
    private RequestQueue queue;
    private Spinner mySpinner;
    private int selectedItemPos;

    public static BuzzFragment newInstance() {
        return new BuzzFragment();
    }
    //to post, will need a title, hive id, user id, and text content

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.buzz_fragment, container, false);
        queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        hiveIds= new ArrayList<>();
        hiveOptions = new ArrayList<>();
        buzzTitle = (EditText) rootView.findViewById(R.id.buzzTitleInput);
        buzzContent = (EditText) rootView.findViewById(R.id.buzzContentInput);
        mySpinner = (Spinner) rootView.findViewById(R.id.hiveIdSpinner);
        mySpinner.setOnItemSelectedListener(this);
        Button b = (Button) rootView.findViewById(R.id.submitBuzz);
        b.setOnClickListener(this);
        selectedItemPos = 0;

        //get the hives the user is part of HARDCODED USER

        getHives(1);
        return rootView;
    }

    private void getHives(int userId){

        String url ="http://10.24.227.37:8080/members/byUserId/" + userId;
        // Server name http://coms-309-tc-03.cs.iastate.edu:8080/posts


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            Log.i("responselength", response.length()+"");
                            for(int i = 0; i < response.length(); i++){
                                JSONObject member = response.getJSONObject(i); //should return user,hive pair
                                Integer hiveId = (Integer) member.getJSONObject("hive").getInt("hiveId");
                                hiveIds.add(hiveId);
                                String hiveName =  member.getJSONObject("hive").getString("name");
                                hiveOptions.add(hiveName);

                            }
                            //here the hive options and corresponding ids have been set appropriately, can add the needed spinner
                            onOptionsSet();
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                            Log.i("jsonAppError",e.toString());
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.i("volleyAppError","Error: " + error.getMessage());
                        Log.i("volleyAppError","VolleyError: "+ error);
                    }
                });



// Add the request to the RequestQueue.
        queue.add(jsonArrayRequest);
    }

    private void onOptionsSet(){

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity().getApplicationContext(),android.R.layout.simple_spinner_item,hiveOptions);
        mySpinner.setAdapter(adapter);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(BuzzViewModel.class);
        // TODO: Use the ViewModel


    }

    @Override
    public void onClick(View view) {

        // Instantiate the RequestQueue.
        //RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url ="http://10.24.227.37:8080/posts";

        // Server name http://coms-309-tc-03.cs.iastate.edu:8080/posts

        final JSONObject postObject = new JSONObject();
        try{
            postObject.put("hiveId",hiveIds.get(selectedItemPos));
            postObject.put("userId",1);
            postObject.put("title", buzzTitle.getText().toString());
            postObject.put("textContent", buzzContent.getText().toString());

        } catch (JSONException e){
            e.printStackTrace();
        }

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,
                postObject, new Response.Listener<JSONObject>(){


            public void onResponse(JSONObject response) {
                Log.i("request","success!");
            }

        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error){
                Log.i("request","fail!");
            }
        });
// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        selectedItemPos = pos;
        Log.i("positionSelected",""+pos);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}