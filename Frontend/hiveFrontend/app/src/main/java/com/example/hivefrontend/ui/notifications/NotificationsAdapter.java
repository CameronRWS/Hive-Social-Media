package com.example.hivefrontend.ui.notifications;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hivefrontend.PostDetailsActivity;
import com.example.hivefrontend.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {


    private Context context;
    private List<JSONObject> notifications;


    NotificationsAdapter(Context context, List<JSONObject> notifications) {
        this.context = context;
        this.notifications = notifications;
    }

    // inflates the row layout from xml when needed
    @Override
    public NotificationsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_recycler_row, parent, false);
        NotificationsAdapter.ViewHolder viewHolder = new NotificationsAdapter.ViewHolder(view);
        return viewHolder;
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(NotificationsAdapter.ViewHolder holder, final int position) {
        try {
            holder.cv.setTag(position);
            holder.notiText.setText(notifications.get(position).getString("notiText"));
            String date = notifications.get(position).getString("dateCreated");
            holder.notiDateTime.setText(date);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return notifications.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView notiText;
        public TextView notiDateTime;
        public CardView cv;

        ConstraintLayout constraintLayout;

        ViewHolder(View itemView) {
            super(itemView);
            notiText = itemView.findViewById(R.id.noti_text);
            notiDateTime = itemView.findViewById(R.id.noti_datetime);
            constraintLayout=itemView.findViewById(R.id.notiViewLayout);
            cv = itemView.findViewById(R.id.cardView);
            cv.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //for find item that hold in list
            int position = (Integer) v.getTag();
            Intent intent = new Intent(v.getContext(), PostDetailsActivity.class);
            try {
                //start new activity and pass the post ID to it
                int entityId = notifications.get(position).getInt("entityId");
                String notiType = notifications.get(position).getString("notiType");
                if(notiType.equals("like") || notiType.equals("comment") || notiType.equals("post-mention")) {
                    Log.i("OK", "notiType:" + notiType);
                    intent.putExtra("postId", entityId);
                    v.getContext().startActivity(intent);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    // convenience method for getting data at click position
    String getItem(int id) throws JSONException {
        return notifications.get(id).getString("textContent");
    }

}