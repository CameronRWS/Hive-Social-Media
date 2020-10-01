package com.example.hivefrontend.ui.notifications;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.hivefrontend.PostCommentAdapter;
import com.example.hivefrontend.R;
import com.example.hivefrontend.ui.home.HomeAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment {

    private RequestQueue queue;
    private ArrayList<JSONObject> notifications;
    private NotificationsAdapter notiAdapter;

    private NotificationsViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        notifications = new ArrayList<>();

        final RecyclerView recyclerView = root.findViewById(R.id.noti_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        notiAdapter = new NotificationsAdapter(getActivity().getApplicationContext(), notifications);
        recyclerView.setAdapter(notiAdapter);

        queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        String url ="http://10.24.227.37:8080/notifications"; // add /byUserId/{userId} once we have user ID stored globally
        JsonArrayRequest notiRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            //have array populate backwards cuz we want to scroll down for old stuff.
                            for(int i = response.length()-1; i >= 0; i--){
                                notifications.add(response.getJSONObject(i));
                            }
                            notiAdapter.notifyDataSetChanged();
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
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
        queue.add(notiRequest);
        return root;
    }
}