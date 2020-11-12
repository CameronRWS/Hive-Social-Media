package com.example.hivefrontend.ui.search;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.hivefrontend.R;
import com.example.hivefrontend.SharedPrefManager;
import com.example.hivefrontend.VolleySingleton;
import com.example.hivefrontend.ui.home.HomeFragment;
import com.example.hivefrontend.ui.profile.Logic.ProfileLogic;
import com.example.hivefrontend.ui.profile.MyAdapter;
import com.example.hivefrontend.ui.profile.Network.ServerRequest;
import com.example.hivefrontend.ui.profile.ProfileFragment;
import com.example.hivefrontend.ui.search.Logic.SearchLogic;
import com.example.hivefrontend.ui.search.Server.SearchServerRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 *  Adapter to use for the RecyclerView in the SearchFragment
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>  {
    /**
     * List of hive JSONObjects to display in the RecyclerView
     */
    private List<JSONObject> hives;
    /**
     * Context for this adapter
     */
    private Context context;

    /**
     * Creates a SearchAdapter with the given Context and data
     * @param context The context for this adapter
     * @param data The list of hives to display
     */
    public SearchAdapter(Context context, List<JSONObject> data) {
        this.hives = data;
        this.context = context;
    }

    // inflates the row layout from xml when needed
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row, parent, false);
        SearchAdapter.ViewHolder viewHolder = new SearchAdapter.ViewHolder(view);
        return viewHolder;
    }

    /**
     * Binds data to a given ViewHolder
     * @param holder The ViewHolder to bind data to
     * @param position Position in the RecyclerView
     */
    @Override
    public void onBindViewHolder(final SearchAdapter.ViewHolder holder, final int position) {

//        serverRequest.fetchMemberCount(mData.get(position));
//        //serverRequest.fetchHiveDescription(mData.get(position));
//        holder.memberCnt.setText( logic.getMemberCount()+ " bees and counting");
//        String url = "http://10.24.227.37:8080/hives";
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
//                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//
//                        try {
//                            for(int i = 0; i < response.length(); i++) {
//                                JSONObject member = response.getJSONObject(i);
//                                if (member.getString("name").equals(mData.get(position))) {
//                                    Log.i("princess", "name: " + member.getString("name") + "description: " + member.getString("description"));
//                                    holder.hiveDescrip.setText(member.getString("description"));
//                                    break;
//                                }
//                            }
//                        }
//                        catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // TODO: Handle error
//                        Log.i("volleyAppError","Error: " + error.getMessage());
//                        Log.i("volleyAppError","VolleyError: "+ error);
//                    }
//                });
//        VolleySingleton.getInstance(this.context).addToRequestQueue(jsonArrayRequest);
        try {
            holder.joinHiveCardButton.setTag(position);
            holder.hiveDescrip.setText(hives.get(position).getString("description"));
            holder.itemTxt.setText(hives.get(position).getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets number of hives this adapter holds
     * @return Number of hives
     */
    @Override
    public int getItemCount() {
        return hives.size();
    }


    /**
     * ViewHolder for one row in the RecyclerView
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * TextView for hive name
         */
        public TextView itemTxt;
        /**
         * TextView for the number of members of the hive
         */
        public TextView memberCnt;
        /**
         * TextView for the HiveDescription
         */
        public TextView hiveDescrip;
        /**
         * ConstraintLayout for this ViewHolder
         */
        ConstraintLayout relativeLayout;

        public ImageButton joinHiveCardButton;

        /**
         * Constructs a ViewHolder from the given View
         * @param itemView The View for this ViewHolder
         */
        ViewHolder(View itemView) {
            super(itemView);
            itemTxt = itemView.findViewById(R.id.hiveCardName);
            relativeLayout=itemView.findViewById(R.id.postViewLayout);
            memberCnt = itemView.findViewById(R.id.hiveCardMemberCount);
            hiveDescrip = itemView.findViewById(R.id.hiveCardDescription);

            joinHiveCardButton = itemView.findViewById(R.id.joinedHiveCardButton);

            joinHiveCardButton.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(final View view) {

                    Log.i(" icon clicked ", " icon clicked ! ");
                    int position = (Integer) view.getTag();
                    try {
                        final int hiveId = hives.get(position).getInt("hiveId");
                        Log.i("post id in adapter ", " " + hiveId);

                        LayoutInflater li = LayoutInflater.from(context);
                        View promptsView = li.inflate(R.layout.request_prompts, null);

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                context);

                        // set prompts.xml to alertdialog builder
                        alertDialogBuilder.setView(promptsView);

                        final EditText userInput = (EditText) promptsView
                                .findViewById(R.id.editTextDialogUserInput);

                        // set dialog message
                        alertDialogBuilder
                                .setCancelable(false)
                                .setPositiveButton("OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,int id) {

                                                joinHiveRequest(userInput.getText().toString());

                                            }

                                            private void joinHiveRequest(String toString) {

                                                String url ="http://10.24.227.37:8080/requests";
                                                int userId = SharedPrefManager.getInstance(context).getUser().getId();

                                                final JSONObject postObject = new JSONObject();
                                                try{
                                                    postObject.put("hiveId", hiveId);
                                                    postObject.put("userId",userId);
                                                    postObject.put("requestMessage", toString);

                                                } catch (JSONException e){
                                                    e.printStackTrace();
                                                    Toast.makeText(context, "Error commenting on this post. Try again.", Toast.LENGTH_LONG).show();
                                                }

                                                final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,
                                                        postObject, new Response.Listener<JSONObject>(){

                                                    public void onResponse(JSONObject response) {
                                                        Toast.makeText(context, "Request successful!", Toast.LENGTH_LONG).show();
                                                    }

                                                }, new Response.ErrorListener() {
                                                    public void onErrorResponse(VolleyError error){
                                                        Log.i("request","fail!");
                                                    }
                                                });
                                                // Add the request to the RequestQueue.
                                                VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
                                            }
                                        })
                                .setNegativeButton("Cancel",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,int id) {
                                                dialog.cancel();
                                            }
                                        });

                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();

                        // show it
                        alertDialog.show();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

}