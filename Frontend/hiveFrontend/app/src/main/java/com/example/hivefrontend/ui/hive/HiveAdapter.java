package com.example.hivefrontend.ui.hive;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class HiveAdapter extends RecyclerView.Adapter<HiveAdapter.ViewHolder> {
    private Context context;
    private List<JSONObject> posts;
    private List<Integer> hiveIds;
    private List <String> hiveNames;

    HiveAdapter(Context context, List<JSONObject> posts, List<Integer> hiveIds, List<String> hiveNames) {

        this.context = context;
        this.posts = posts;
        this.hiveIds = hiveIds;
        this.hiveNames = hiveNames;
    }



    // @NonNull
    @Override
    public HiveAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_recycler_row, parent, false);
        HiveAdapter.ViewHolder viewHolder = new HiveAdapter.ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final HiveAdapter.ViewHolder holder, final int position) {
        try {
            holder.cv.setTag(position);
            holder.icon.setTag(position);
            holder.userDisplayName.setTag(position);
            holder.userName.setTag(position);
            holder.postTitle.setText(posts.get(position).getString("title"));
            holder.userName.setText("@" + posts.get(position).getJSONObject("user").getString("userName"));
            holder.userDisplayName.setText(posts.get(position).getJSONObject("user").getString("displayName"));
            holder.postContent.setText(posts.get(position).getString("textContent"));
            holder.commentNumber.setText(String.valueOf(posts.get(position).getJSONArray("comments").length()));
            holder.likeNumber.setText(String.valueOf(posts.get(position).getJSONArray("likes").length()));

            int id = posts.get(position).getInt("hiveId");
            String hive = hiveNames.get(hiveIds.indexOf(id));
            holder.hiveName.setText(hive);
//            holder.hiveName.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    hiveView.openHivePage();
//                    Intent intent = new Intent(view.getContext(), HiveFragment.class);
//                   intent.putExtra("hiveName", holder.hiveName.getText().toString());
//                    view.getContext().startActivity(intent);
//                }
//            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the amount of posts
     * @return the amount of posts in integer format
     */
    @Override
    public int getItemCount() {
        return posts.size();
    }

    /**
     * Defines the view holder
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView postTitle;
        public TextView postContent;
        public TextView userName;
        public TextView userDisplayName;
        public TextView commentNumber;
        public TextView likeNumber;
        public TextView hiveName;
        public CardView cv;
        public ImageView icon;

        ConstraintLayout constraintLayout;
        private RequestQueue queue;

        ViewHolder(View itemView) {
            super(itemView);
            postTitle = itemView.findViewById(R.id.postTitle);
            postContent = itemView.findViewById(R.id.postContent);
            constraintLayout=itemView.findViewById(R.id.postViewLayout);
            userDisplayName = itemView.findViewById(R.id.userDisplayName);
            userDisplayName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), ProfileActivity.class);
                    int position = (Integer) view.getTag();
                    try {
                        int userId = posts.get(position).getJSONObject("user").getInt("userId");
                        intent.putExtra("userId", userId);
                        view.getContext().startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            userName = itemView.findViewById(R.id.userName);
            userName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), ProfileActivity.class);
                    int position = (Integer) view.getTag();
                    try {
                        int userId = posts.get(position).getJSONObject("user").getInt("userId");
                        //start new activity and pass the user ID to it
                        intent.putExtra("userId", userId);
                        view.getContext().startActivity(intent);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            commentNumber = itemView.findViewById(R.id.commentNumber);
            likeNumber = itemView.findViewById(R.id.likeNumber);
            hiveName = itemView.findViewById(R.id.hiveName);
            icon = itemView.findViewById(R.id.likeCountIcon);
            icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = (Integer) view.getTag();
                    try {
                        int postId = posts.get(position).getInt("postId");
                        HiveFragment.likePost(postId);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
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

    /**
     * An easy way to retrieve a post
     * @param id the post's id
     * @return the post
     * @throws JSONException error
     */
    String getItem(int id) throws JSONException {
        return posts.get(id).getString("title");
    }

}
