package com.example.hivefrontend.ui.notifications;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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
import com.example.hivefrontend.R;
import com.example.hivefrontend.ui.notifications.Logic.NotificationsLogic;
import com.example.hivefrontend.ui.notifications.Network.ServerRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment implements INotificationsView {

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
        //String url ="http://localhost:8080/notifications";
        Log.i("rightBefore","rightBefore: ");

        final ServerRequest serverRequest = new ServerRequest();
        NotificationsLogic logic = new NotificationsLogic(this, serverRequest);

        serverRequest.setNotifications();
        return root;
    }


    @Override
    public Context getNotificationsContext() {
        return this.getContext();
    }

    @Override
    public void updateNotifcations(JSONObject noti) {
        notifications.add(noti);
        notiAdapter.notifyDataSetChanged();
    }
    @Override
    public String urlDeterminate(final JSONObject notification) {
        String url = "";
        try {
            int entityId = notification.getInt("entityId");
            String notiType = notification.getString("notiType");
            String entityType = notiType.split("-")[0];
            if (entityType.equals("post")) {
                url = "http://10.24.227.37:8080/posts/byPostId/" + entityId;
            } else if (entityType.equals("hive")) {
                url = "http://10.24.227.37:8080/hives/byHiveId/" + entityId;
            }
        } catch (Exception e) {
            Log.i("addRelatedEntities","Error: " + e.getMessage());
        }
        return url;
    }

    @Override
    public String urlDeterminateCreator(final JSONObject notification) {
        String url = "";

        try {
            int creatorUserId = notification.getInt("creatorUserId");
            if(creatorUserId != -1) {
                url = "http://10.24.227.37:8080/users/byUserId/" + creatorUserId;
            } else {
                notifications.add(notification);
                notiAdapter.notifyDataSetChanged();
                return "";
            }
        }
        catch (Exception e){
            Log.i("addRelatedEntities","Error: " + e.getMessage());
        }
        return url;
    }
}