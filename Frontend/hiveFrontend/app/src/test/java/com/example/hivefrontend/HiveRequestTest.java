package com.example.hivefrontend;

import com.example.hivefrontend.HiveRequests.HiveRequestsActivity;
import com.example.hivefrontend.HiveRequests.Logic.HiveRequestLogic;
import com.example.hivefrontend.HiveRequests.Server.HiveRequestServerRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class HiveRequestTest {

    @Test
    public void testOnHiveRequestSuccess() throws JSONException {

        HiveRequestLogic logic;
        HiveRequestsActivity view = mock(HiveRequestsActivity.class);
        HiveRequestServerRequest server = mock(HiveRequestServerRequest.class);

        logic = new HiveRequestLogic(view, server);
        final int[] count = new int[1];

        doAnswer( new Answer(){
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                               count[0]++;
                               return null;
                        }
        }).when(view).addToRequests((JSONObject) any());

        JSONArray fakeResponse = Mockito.mock(JSONArray.class);
        JSONObject mockObj1 = Mockito.mock(JSONObject.class);

        when(fakeResponse.getJSONObject(anyInt())).thenReturn(mockObj1);
        when(fakeResponse.length()).thenReturn(6);
        when(mockObj1.getBoolean(anyString())).thenReturn(true);
        logic.onHiveRequestSuccess(fakeResponse);
        assertEquals(count[0],6);
        verify(view,times(6)).addToRequests((JSONObject) any());
    }
}
