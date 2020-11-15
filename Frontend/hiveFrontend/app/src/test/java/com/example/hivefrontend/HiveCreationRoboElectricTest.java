package com.example.hivefrontend;


import android.content.Intent;
import android.widget.Button;

import com.example.hivefrontend.HiveCreation.HiveCreation;
import com.example.hivefrontend.Login.LoginActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28)
public class HiveCreationRoboElectricTest {

    @Mock
    private HiveCreation activity;

    @Before


    public void setUp() throws Exception
    {
        activity = Robolectric.buildActivity( HiveCreation.class )
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
    public void testCancelButton() throws Exception
    {
        Button back = activity.findViewById(R.id.cancelButton);
        back.performClick();

        Intent intent = Shadows.shadowOf(activity).peekNextStartedActivity();

        assertEquals(MainActivity.class.getCanonicalName(),intent.getComponent().getClassName());
    }


}
