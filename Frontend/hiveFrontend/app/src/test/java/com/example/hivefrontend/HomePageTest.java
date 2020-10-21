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
        ArrayList<Integer> testIds = new ArrayList<>();
        testIds.add(1);
        testIds.add(2);
        HomeLogic logic;

        HomeFragment view = mock(HomeFragment.class);
        ServerRequest server = mock(ServerRequest.class);
        logic = new HomeLogic(view,server);


        when(view.getHiveIdsDiscover()).thenReturn(testIds);

        logic.addToDiscoverIds(3);

        verify(view,times(1)).addToHiveIdsDiscover(3);
        assertEquals(testIds,view.getHiveIdsDiscover());
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
        empty.add(3);
    }
}
