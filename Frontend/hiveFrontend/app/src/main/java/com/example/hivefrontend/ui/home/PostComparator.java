package com.example.hivefrontend.ui.home;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class PostComparator implements Comparator<JSONObject> {
    final SimpleDateFormat ft = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
    @Override
    public int compare(JSONObject mock1, JSONObject mock2) {
        String time1 = null;
        String time2 = null;
        try {
            time1 = mock1.getString("dateCreated");
            time2 = mock2.getString("dateCreated");
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
