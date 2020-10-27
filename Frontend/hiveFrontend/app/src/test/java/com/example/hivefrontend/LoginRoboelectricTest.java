package com.example.hivefrontend;

import android.content.Context;
import android.content.Intent;
import android.widget.EditText;

import com.android.volley.toolbox.Volley;
import com.example.hivefrontend.Login.LoginActivity;
import com.example.hivefrontend.PostDetails.Logic.PostDetailsLogic;
import com.example.hivefrontend.PostDetails.Network.ServerRequest;
import com.example.hivefrontend.PostDetails.PostDetailsActivity;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28)
public class LoginRoboelectricTest {


    @Mock
    private LoginActivity activity;

    @Before


    public void setUp() throws Exception
    {
        
        activity = Robolectric.buildActivity( LoginActivity.class )
                .create()
                .resume()
                .get();

    }

    @Test
    public void shouldNotBeNull() throws Exception
    {
        assertNotNull( activity );
    }

    @Test
    public void typeUserName() {
        EditText userNameInput = activity.findViewById(R.id.loginUsernameField);
        userNameInput.setText("test");

        String userName = userNameInput.getText().toString();

        assertEquals(userName,"test");
    }



}
