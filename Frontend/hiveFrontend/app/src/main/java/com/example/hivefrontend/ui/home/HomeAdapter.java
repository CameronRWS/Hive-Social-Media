package com.example.hivefrontend.ui.home;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hivefrontend.MainActivity;
import com.example.hivefrontend.PostDetailsActivity;
import com.example.hivefrontend.R;
import com.example.hivefrontend.ui.buzz.BuzzFragment;
import com.example.hivefrontend.ui.profile.MyAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {


    private Context context;
    private List<JSONObject> posts;
    private List<Integer> hiveIds;
    private List<String> hiveNames;

    HomeAdapter(Context context, List<JSONObject> posts, List<Integer> hiveIds, List<String> hiveNames) {
        this.context = context;
        this.posts=posts;
        this.hiveIds=hiveIds;
        this.hiveNames=hiveNames;
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
    public void onBindViewHolder(HomeAdapter.ViewHolder holder, final int position) {
        try {
            holder.cv.setTag(position);
            holder.postTitle.setText(posts.get(position).getString("title"));
            holder.userName.setText("@" + posts.get(position).getJSONObject("user").getString("userName"));
            holder.userDisplayName.setText(posts.get(position).getJSONObject("user").getString("displayName"));
            holder.postContent.setText(posts.get(position).getString("textContent"));
            holder.commentNumber.setText(String.valueOf(posts.get(position).getJSONArray("comments").length()));
            holder.likeNumber.setText(String.valueOf(posts.get(position).getJSONArray("likes").length()));

            int id = posts.get(position).getInt("hiveId");
            String hive = hiveNames.get(hiveIds.indexOf(id));
            holder.hiveName.setText(hive);




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
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView postTitle;
        public TextView postContent;
        public TextView userName;
        public TextView userDisplayName;
        public TextView commentNumber;
        public TextView likeNumber;
        public TextView hiveName;
        public CardView cv;

        ConstraintLayout constraintLayout;

        ViewHolder(View itemView) {
            super(itemView);
            postTitle = itemView.findViewById(R.id.postTitle);
            postContent = itemView.findViewById(R.id.postContent);
            constraintLayout=itemView.findViewById(R.id.postViewLayout);
            userDisplayName = itemView.findViewById(R.id.userDisplayName);
            userName = itemView.findViewById(R.id.userName);
            commentNumber = itemView.findViewById(R.id.commentNumber);
            likeNumber = itemView.findViewById(R.id.likeNumber);
            hiveName = itemView.findViewById(R.id.hiveName);
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
                int postId = posts.get(position).getInt("postId");
                intent.putExtra("postId", postId);
                v.getContext().startActivity(intent);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    // convenience method for getting data at click position
    String getItem(int id) throws JSONException {
        return posts.get(id).getString("title");
    }

}
