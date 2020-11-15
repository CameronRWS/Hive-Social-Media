package com.example.hivefrontend;

import android.widget.EditText;

import com.example.hivefrontend.Login.LoginActivity;
import com.example.hivefrontend.Register.RegisterActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28)
public class RegisterRoboelectricTest {


    @Mock
    private RegisterActivity registerActivity;

    @Before
    public void setUp() throws Exception
    {
        
        registerActivity = Robolectric.buildActivity( RegisterActivity.class )
                .create()
                .resume()
                .get();

    }

    @Test
    public void shouldNotBeNull() throws Exception
    {
        assertNotNull( registerActivity );
    }

    @Test
    public void emailAddressValidity() {
        EditText emailAddress = registerActivity.findViewById(R.id.confirmEmailAddressField);
        emailAddress.setText("demo4@gmail.com");

        String emailStringLiteral = emailAddress.getText().toString();

        assertEquals(emailStringLiteral,"demo4@gmail.com");
    }



}
