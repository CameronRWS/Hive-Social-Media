package com.example.hivefrontend;

import com.example.hivefrontend.ui.home.PostComparator;

import org.json.JSONObject;
import org.junit.Test;

import org.json.JSONException;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class DateTimeSorterTest {

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();
    PostComparator postComp = new PostComparator();

    @Test
    public void testGreaterThan() throws JSONException {

        JSONObject json = new JSONObject();
        json.put("dateCreated", "04/28/2020 23:28:23");
        JSONObject json2 = new JSONObject();
        json2.put("dateCreated", "08/28/2020 13:28:23");
        int result = postComp.compare(json, json2);
        assertTrue("expected to be less than", result <= 1);

    }

    @Test
    public void testLessThan() throws JSONException {

        JSONObject json = new JSONObject();
        json.put("dateCreated", "04/28/2020 23:28:23");
        JSONObject json2 = new JSONObject();
        json2.put("dateCreated", "08/28/2020 13:28:23");
        int result = postComp.compare(json2, json);
        assertTrue("expected to be less than", result <= 1);

    }

    @Test
    public void testEqual() throws JSONException {

        JSONObject json = new JSONObject();
        json.put("dateCreated", "04/28/2020 23:28:23");
        int result = postComp.compare(json, json);
        assertTrue("expected to be less than", result == 0);

    }

}
