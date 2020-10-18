package com.example.hivefrontend;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hivefrontend.ui.home.HomeAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class PostCommentAdapter extends RecyclerView.Adapter<PostCommentAdapter.ViewHolder> {


    private Context context;
    private List<JSONObject> comments;


    PostCommentAdapter(Context context, List<JSONObject> comments) {
        this.context = context;
        this.comments=comments;
    }

    // inflates the row layout from xml when needed
    @Override
    public PostCommentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_recycler_row, parent, false);
        PostCommentAdapter.ViewHolder viewHolder = new PostCommentAdapter.ViewHolder(view);
        return viewHolder;
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(PostCommentAdapter.ViewHolder holder, final int position) {
        try {
            holder.cv.setTag(position);
            holder.userDisplayName.setTag(position);
            holder.userName.setTag(position);

            holder.userName.setText("@" + comments.get(position).getJSONObject("user").getString("userName"));
            holder.userDisplayName.setText(comments.get(position).getJSONObject("user").getString("displayName"));
            holder.postContent.setText(comments.get(position).getString("textContent"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return comments.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView postContent;
        public TextView userName;
        public TextView userDisplayName;
        public CardView cv;

        ConstraintLayout constraintLayout;

        ViewHolder(View itemView) {
            super(itemView);
            postContent = itemView.findViewById(R.id.postContentComment);
            constraintLayout=itemView.findViewById(R.id.commentLayout);
            userDisplayName = itemView.findViewById(R.id.userDisplayNameComment);
            userName = itemView.findViewById(R.id.userNameComment);

            cv = itemView.findViewById(R.id.cardViewComment);
            userName.setOnClickListener(this);
            userDisplayName.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //for find item that hold in list
            int position = (Integer) v.getTag();
            Intent intent = new Intent(v.getContext(), ProfileActivity.class);
            try {
                //TO DO: go to the profile of the user that posted this comment
                int userId = comments.get(position).getJSONObject("user").getInt("userId");

                //start new activity and pass the user ID to it
                intent.putExtra("userId", userId);
                v.getContext().startActivity(intent);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    // convenience method for getting data at click position
    String getItem(int id) throws JSONException {
        return comments.get(id).getString("textContent");
    }

}