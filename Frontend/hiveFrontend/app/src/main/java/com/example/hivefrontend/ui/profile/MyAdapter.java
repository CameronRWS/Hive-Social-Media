package com.example.hivefrontend.ui.profile;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.hivefrontend.GlideApp;
import com.example.hivefrontend.R;
import com.example.hivefrontend.VolleySingleton;
import com.example.hivefrontend.ui.profile.Logic.ProfileLogic;
import com.example.hivefrontend.ui.profile.Network.ServerRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter used for the profile RecyclerView
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<String> mData;
    private Context context;
    private int memberCount;
    ServerRequest serverRequest = new ServerRequest();
    ProfileFragment profile = new ProfileFragment();
    ProfileLogic logic = new ProfileLogic(profile, serverRequest);
    private List<Integer> hives;


    /**
     * Creates an adapter from the given Context and list of Hives
     * @param context The Context for this adapter
     * @param data The data to be used, in the form of hive names
     */
    public MyAdapter(Context context, List<String> data) {
        this.mData = data;
        this.context = context;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    /**
     * Binds data to the given holder using its position
     * @param holder The ViewHolder to bind data to
     * @param position The ViewHolder's position
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.itemTxt.setText(mData.get(position));
        serverRequest.fetchMemberCount(mData.get(position));
        //serverRequest.fetchHiveDescription(mData.get(position));
        holder.memberCnt.setText( logic.getMemberCount()+ " bees and counting");
        String url = "http://10.24.227.37:8080/hives";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            for(int i = 0; i < response.length(); i++) {
                                JSONObject member = response.getJSONObject(i);
                                if (member.getString("name").equals(mData.get(position))) {
                                    Log.i("princess", "name: " + member.getString("name") + "description: " + member.getString("description"));
                                    holder.hiveDescrip.setText(member.getString("description"));
                                    break;
                                }
                            }
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
        VolleySingleton.getInstance(this.context).addToRequestQueue(jsonArrayRequest);
        //holder.hiveDescrip.setText(logic.getHiveDescrip());
    }

    /**
     * Returns the number of hives in this adapter
     * @return The number of hives in this adapter
     */
    @Override
    public int getItemCount() {
        return mData.size();
    }


    /**
     * ViewHolder for each row of the RecyclerView
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemTxt;
        public TextView memberCnt;
        public TextView hiveDescrip;
        ConstraintLayout relativeLayout;
        public ImageView hiveProfile;
        public ImageView hiveBanner;
        private FirebaseStorage storage;
        private StorageReference storageReference;
        private int position = 0;

        /**
         * Creates a ViewHolder from the given View
         * @param itemView The View to use
         */
        ViewHolder(View itemView) {
            super(itemView);
            itemTxt = itemView.findViewById(R.id.hiveCardName);
            relativeLayout=itemView.findViewById(R.id.postViewLayout);
            memberCnt = itemView.findViewById(R.id.hiveCardMemberCount);
            hiveDescrip = itemView.findViewById(R.id.hiveCardDescription);
            hiveProfile = itemView.findViewById(R.id.hiveCardPicture);
            hiveBanner = itemView.findViewById(R.id.hiveCardHeader);
            storage = FirebaseStorage.getInstance();
            storageReference = storage.getReference();

            int hiveId =  getHiveIds().get(3);
            position += 1;

            StorageReference test1 = storageReference.child("hivePictures/" + hiveId + ".jpg");
            StorageReference test2 = storageReference.child("hiveBackgrounds/" + hiveId + ".jpg");

            GlideApp.with(context)
                    .load(test1)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .skipMemoryCache(true)
                    .error(R.drawable.defaulth)
                    .into(hiveProfile);

            GlideApp.with(context)
                    .load(test2)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .skipMemoryCache(true)
                    .error(R.drawable.defaultb)
                    .into(hiveBanner);
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return mData.get(id);
    }

    private List<Integer> getHiveIds(){
        List<Integer> hiveIds = new ArrayList<>();
        hiveIds.add(3);
        hiveIds.add(4);
        hiveIds.add(7);
        hiveIds.add(9);
        hiveIds.add(10);
        hiveIds.add(11);
        return hiveIds;
    }
}