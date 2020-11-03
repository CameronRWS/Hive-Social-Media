package com.example.hivefrontend.ui.buzz.Logic;

import android.content.Context;
import android.widget.Toast;

import com.example.hivefrontend.ui.buzz.IBuzzView;
import com.example.hivefrontend.ui.buzz.Network.IBuzzServerRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BuzzLogic implements IBuzzVolleyListener{

    IBuzzView buzzView;
    IBuzzServerRequest server;

    public void displayBuzzScreen() {
        server.getHives(buzzView.getUserId());
    }

    public BuzzLogic (IBuzzView bv, IBuzzServerRequest bsr) {
        this.buzzView = bv;
        this.server = bsr;
        server.addVolleyListener(this);
    }

    public int getUserId(){
        return buzzView.getUserId();
    }

    public void onGetHivesSuccess(JSONArray response) {
        try {
            buzzView.addHiveIdValue(-1);
            buzzView.addHiveOptionsValue("Choose your hive.");
            for(int i = 0; i < response.length(); i++){
                JSONObject member = response.getJSONObject(i); //should return user,hive pair
                Integer hiveId = (Integer) member.getJSONObject("hive").getInt("hiveId");
                buzzView.addHiveIdValue(hiveId);
                String hiveName =  member.getJSONObject("hive").getString("name");
                buzzView.addHiveOptionsValue(hiveName);

                buzzView.onOptionsSet();
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Context getBuzzContext() { return buzzView.getBuzzContext();}

    public JSONObject createBuzzPost() {
        if (buzzView.getSelectedItemPos() == 0) {
            Toast.makeText(buzzView.getBuzzContext(), "Please choose a hive to share this post to.", Toast.LENGTH_LONG).show();
            return null;
        }

        final JSONObject postObject = new JSONObject();

        try{
            postObject.put("hiveId", buzzView.getHiveId(buzzView.getSelectedItemPos()));
            postObject.put("userId",getUserId());
            postObject.put("title", buzzView.getBuzzTitle());
            postObject.put("textContent", buzzView.getBuzzContent());

            Toast.makeText(buzzView.getBuzzContext(), "You just made buzz in " + buzzView.getHiveOption(buzzView.getSelectedItemPos()) + "!", Toast.LENGTH_LONG).show();
            buzzView.openHome();
        } catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(buzzView.getBuzzContext(), "Error sharing this post. Try again.", Toast.LENGTH_LONG).show();
        }
        return postObject;
    }
}
