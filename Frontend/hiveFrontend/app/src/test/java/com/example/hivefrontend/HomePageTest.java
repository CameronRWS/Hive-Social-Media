package com.example.hivefrontend;

import android.content.Context;

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

public class HomePageTest {

    @Mock
    private Context context;


    @Test
    public void TestAddToDiscoverHiveIds(){
        final ArrayList<Integer> testIds = new ArrayList<>();
        testIds.add(1);
        testIds.add(2);
        HomeLogic logic;

        HomeFragment view = mock(HomeFragment.class);
        ServerRequest server = mock(ServerRequest.class);
        logic = new HomeLogic(view,server);

        when(view.getHiveIdsDiscover()).thenReturn(testIds);
        doAnswer( new Answer(){

                      @Override
                      public Object answer(InvocationOnMock invocation) throws Throwable {
                          int hiveId = invocation.getArgument(0);
                          testIds.add(hiveId);
                          return null;
                      }
                  }

        ).when(view).addToHiveIdsDiscover(anyInt());

        logic.addToDiscoverIds(3);

        ArrayList<Integer> verify = new ArrayList<>();
        verify.add(1);
        verify.add(2);
        verify.add(3);

        verify(view,times(1)).addToHiveIdsDiscover(3);
        assertEquals(testIds,logic.getHiveIdsDiscover());
        assertEquals(testIds,verify);
        verify(view,times(1)).getHiveIdsDiscover();

    }

    @Test
    public void TestUpdateLogic(){
        HomeFragment view = mock(HomeFragment.class);
        final ServerRequest server = mock(ServerRequest.class);
        final HomeLogic logic = new HomeLogic(view,server);

        doAnswer( new Answer(){

                      @Override
                      public Object answer(InvocationOnMock invocation) throws Throwable {
                          server.getDiscoverPosts();
                          return null;
                      }
                  }

        ).when(server).updatePostRequest();


        logic.updatePostLogic();

        verify(server,times(1)).updatePostRequest();
        verify(server,times(1)).getDiscoverPosts();

    }


    @Test
    public void TestClearAdapterData(){
        final ArrayList<Integer> testIds = new ArrayList<>();
        testIds.add(1);
        testIds.add(2);
        HomeLogic logic;

        HomeFragment view = mock(HomeFragment.class);
        ServerRequest server = mock(ServerRequest.class);
        logic = new HomeLogic(view,server);

        when(view.getHiveIdsDiscover()).thenReturn(testIds);

        doAnswer( new Answer(){

                      @Override
                      public Object answer(InvocationOnMock invocation) throws Throwable {
                          testIds.clear();
                          return null;
                      }
                  }

        ).when(view).clearData();

        logic.clearAdapterData();
        verify(view,times(1)).clearData();

        ArrayList<Integer> empty = new ArrayList<>();
        view.clearData();
        assertEquals(empty,view.getHiveIdsDiscover());
        assertEquals(testIds,empty);
    }
}
