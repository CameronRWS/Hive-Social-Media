package com.example.hivefrontend.HiveRequests;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.example.hivefrontend.PostDetails.PostDetailsActivity;
import com.example.hivefrontend.Profile.ProfileActivity;
import com.example.hivefrontend.R;
import com.example.hivefrontend.ui.home.HomeAdapter;
import com.example.hivefrontend.ui.home.HomeFragment;
import com.example.hivefrontend.ui.notifications.NotificationsAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for the hive requests in HiveRequestsActivity
 */
public class HiveRequestAdapter extends RecyclerView.Adapter<HiveRequestAdapter.ViewHolder> {

    private Context context;
    private List<JSONObject> hiveRequests;

    /**
     * Constructs an adapter from the given Context and list of hiveRequests
     * @param applicationContext The application context
     * @param hiveRequests The requests to display
     */
    public HiveRequestAdapter(Context applicationContext, ArrayList<JSONObject> hiveRequests) {
        this.context = applicationContext;
        this.hiveRequests = hiveRequests;
    }

    @NonNull
    @Override
    public HiveRequestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_recycler_row, parent, false);
        HiveRequestAdapter.ViewHolder viewHolder = new HiveRequestAdapter.ViewHolder(view);
        return viewHolder;
    }

    /**
     * Sets the data in the given ViewHolder to the data provided at the appropriate position
     * @param holder the ViewHolder to use
     * @param position The position in the RecyclerView this ViewHolder has
     */
    @Override
    public void onBindViewHolder(@NonNull HiveRequestAdapter.ViewHolder holder, int position) {
        holder.cv.setTag(position);
        holder.userName.setTag(position);
        holder.acceptButton.setTag(position);
        holder.denyButton.setTag(position);

        try {
            holder.userName.setText("@" + hiveRequests.get(position).getJSONObject("user").getString("userName"));
            if(!hiveRequests.get(position).getString("requestMessage").equals("null")){
                holder.requestContent.setText(hiveRequests.get(position).getString("requestMessage"));
            }
            else{
                holder.requestContent.setText("This user has not provided additional information.");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * Returns the number of hive requests
     * @return The number of hive requests
     */
    @Override
    public int getItemCount() {
        return hiveRequests.size();
    }


    /**
     * Viewholder for each row in the RecyclerView, containing one request
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView requestContent;
        public TextView userName;
        public CardView cv;
        //public ImageView icon;
        public Button acceptButton;
        public Button denyButton;

        ConstraintLayout constraintLayout;

        /**
         * Constructs a ViewHolder from the provided View and provides sets onClickListeners for the buttons
         * @param itemView The view for one row in the RecyclerView
         */
        ViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cardView);
            constraintLayout=itemView.findViewById(R.id.hiveReqLayout);
            requestContent = itemView.findViewById(R.id.hiveReq_text);

            userName = itemView.findViewById(R.id.userName);
            userName.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(final View view) {
                    Intent intent = new Intent(view.getContext(), ProfileActivity.class);
                    int position = (Integer) view.getTag();
                    try {
                        int userId = hiveRequests.get(position).getJSONObject("user").getInt("userId");
                        //start new activity and pass the user ID to it
                        intent.putExtra("userId", userId);
                        view.getContext().startActivity(intent);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }});

            acceptButton = itemView.findViewById(R.id.acceptRequest);
            acceptButton.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    int position = (int) view.getTag();

                    try {
                        HiveRequestsActivity.handleRequest(position, "accepted");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });


            denyButton = itemView.findViewById(R.id.denyRequest);
            denyButton.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    int position = (int) view.getTag();

                    try {
                        HiveRequestsActivity.handleRequest(position, "declined");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }

}
