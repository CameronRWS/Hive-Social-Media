package com.example.hivefrontend;

import android.content.Context;

import com.example.hivefrontend.ui.buzz.BuzzFragment;
import com.example.hivefrontend.ui.buzz.Logic.BuzzLogic;
import com.example.hivefrontend.ui.home.HomeFragment;
import com.example.hivefrontend.ui.home.IHomeView;
import com.example.hivefrontend.ui.home.Logic.HomeLogic;
import com.example.hivefrontend.ui.home.Logic.IHomeVolleyListener;
import com.example.hivefrontend.ui.home.Network.IServerRequest;
import com.example.hivefrontend.ui.home.Network.ServerRequest;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class HomePageTestII {

    @Mock
    private Context context;

    @Test
    public void TestAddToHomeHiveIds(){
        final ArrayList<Integer> testIds = new ArrayList<>();
        testIds.add(1);
        testIds.add(2);
        HomeLogic logic;

        HomeFragment view = mock(HomeFragment.class);
        ServerRequest server = mock(ServerRequest.class);
        logic = new HomeLogic(view,server);

        when(view.getHiveIdsHome()).thenReturn(testIds);
        doAnswer( new Answer(){

                      @Override
                      public Object answer(InvocationOnMock invocation) throws Throwable {
                          int hiveId = invocation.getArgument(0);
                          testIds.add(hiveId);
                          return null;
                      }
                  }

        ).when(view).addToHiveIdsHome(anyInt());

        logic.addToHiveIdsHome(3);

        ArrayList<Integer> verify = new ArrayList<>();
        verify.add(1);
        verify.add(2);
        verify.add(3);

        verify(view,times(1)).addToHiveIdsHome(3);
        assertEquals(testIds,logic.getHiveIdsHome());
        assertEquals(testIds,verify);
        verify(view,times(1)).getHiveIdsHome();

    }

    @Test
    public void testHomeContextSuccess(){
        HomeFragment home = mock(HomeFragment.class);
        ServerRequest server = new ServerRequest();
        HomeLogic logic = mock(HomeLogic.class);

        when(home.getHomeContext()).thenReturn(context);

        logic.getHomeContext();

        verify(logic,times(1)).getHomeContext();
    }

}
