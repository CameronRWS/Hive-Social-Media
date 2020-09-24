package com.example.hivefrontend.ui.home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hivefrontend.R;
import com.example.hivefrontend.ui.profile.MyAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {


    private Context context;
    private List<JSONObject> posts;

    HomeAdapter(Context context, List<JSONObject> posts) {
        this.context = context;
        this.posts=posts;
    }

    // inflates the row layout from xml when needed
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_recycler_row, parent, false);
        HomeAdapter.ViewHolder viewHolder = new HomeAdapter.ViewHolder(view);
        return viewHolder;
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(HomeAdapter.ViewHolder holder, int position) {
        try {
            holder.postTitle.setText(posts.get(position).getString("title"));
            holder.userName.setText(posts.get(position).getJSONObject("user").getString("userName"));
            holder.postContent.setText(posts.get(position).getString("textContent"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return posts.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView postTitle;
        public TextView postContent;
        public TextView userName;
        LinearLayout linearLayout;

        ViewHolder(View itemView) {
            super(itemView);
            postTitle = itemView.findViewById(R.id.postTitle);
            postContent = itemView.findViewById(R.id.postContent);
            linearLayout=itemView.findViewById(R.id.postViewLayout);
            userName = itemView.findViewById(R.id.userDisplayName);
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) throws JSONException {
        return posts.get(id).getString("title");
    }

}
