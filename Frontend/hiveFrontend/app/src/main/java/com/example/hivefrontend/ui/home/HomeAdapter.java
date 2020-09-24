package com.example.hivefrontend.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hivefrontend.R;
import com.example.hivefrontend.ui.profile.MyAdapter;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {


    private Context context;
    private List<String> titles;
    private List<String> postContent;
    private List<String> userNames;

    HomeAdapter(Context context,  List<String> titles, List<String> postContent, List<String> userNames) {
        this.titles= titles;
        this.postContent=postContent;
        this.userNames=userNames;
        this.context = context;
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
        holder.postTitle.setText(titles.get(position));
        holder.postContent.setText(postContent.get(position));
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return titles.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView postTitle;
        public TextView postContent;
        LinearLayout linearLayout;

        ViewHolder(View itemView) {
            super(itemView);
            postTitle = itemView.findViewById(R.id.postTitle);
            postContent = itemView.findViewById(R.id.postContent);
            linearLayout=itemView.findViewById(R.id.postViewLayout);
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return titles.get(id);
    }

}
