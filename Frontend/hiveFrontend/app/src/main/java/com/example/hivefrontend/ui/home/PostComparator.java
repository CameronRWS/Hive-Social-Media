package com.example.hivefrontend.ui.home;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

/**
 * Comparator to determine chronological order of posts.
 */
public class PostComparator implements Comparator<JSONObject> {
    final SimpleDateFormat ft = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");

    /**
     * Compares two posts as JSONObjects and determines which came first based on their "dateCreated"
     * @param obj1 The first post
     * @param obj2 The second post
     * @return
     */
    @Override
    public int compare(JSONObject obj1, JSONObject obj2) {
        String time1 = null;
        String time2 = null;
        try {
            time1 = obj1.getString("dateCreated");
            time2 = obj2.getString("dateCreated");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            Date d1 = ft.parse(time1);
            Date d2 = ft.parse(time2);
            return d2.compareTo(d1);

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return 0;
    }

}
