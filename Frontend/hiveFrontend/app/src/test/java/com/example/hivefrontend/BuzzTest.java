package com.example.hivefrontend;

import android.content.Context;

import com.example.hivefrontend.PostDetails.PostDetailsActivity;
import com.example.hivefrontend.ui.buzz.BuzzFragment;
import com.example.hivefrontend.Profile.ProfileActivity;
import com.example.hivefrontend.ui.buzz.Logic.BuzzLogic;
import com.example.hivefrontend.ui.buzz.Network.ServerRequest;
import com.example.hivefrontend.ui.home.HomeFragment;
import com.example.hivefrontend.PostDetails.Logic.PostDetailsLogic;

import org.apache.maven.settings.Server;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class BuzzTest {
    @Mock
    private Context context;

    @Mock
    private JSONArray response;

    @Test
    public void testBuzzContextSuccess(){
        BuzzFragment buzz = mock(BuzzFragment.class);
        ServerRequest server = new ServerRequest();
        BuzzLogic logic = mock(BuzzLogic.class);

        when(buzz.getBuzzContext()).thenReturn(context);

        logic.getBuzzContext();

        verify(logic,times(1)).getBuzzContext();
    }

}
