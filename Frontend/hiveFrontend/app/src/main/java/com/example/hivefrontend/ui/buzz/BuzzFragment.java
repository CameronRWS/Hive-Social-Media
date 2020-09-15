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
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

public class BuzzFragment extends Fragment implements View.OnClickListener {

    EditText buzzTitle;
    EditText buzzContent;
    private BuzzViewModel mViewModel;

    public static BuzzFragment newInstance() {
        return new BuzzFragment();
    }
    //to post, will need a title, hive id, user id, and text content

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.buzz_fragment, container, false);
        buzzTitle = (EditText) rootView.findViewById(R.id.buzzTitleInput);
        buzzContent = (EditText) rootView.findViewById(R.id.buzzContentInput);
        Button b = (Button) rootView.findViewById(R.id.submitBuzz);
        b.setOnClickListener(this);
        return rootView;
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
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url ="http://10.24.227.37:8080/posts";

        // Server name http://coms-309-tc-03.cs.iastate.edu:8080/posts

        final JSONObject postObject = new JSONObject();
        try{
            postObject.put("hiveId",2);
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
}