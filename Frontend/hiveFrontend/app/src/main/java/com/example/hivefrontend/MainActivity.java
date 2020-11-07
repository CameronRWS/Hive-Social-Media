package com.example.hivefrontend;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hivefrontend.Login.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity {


    private WebSocketClient cc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!(SharedPrefManager.getInstance(this).isLoggedIn())) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        final BottomNavigationView navView = findViewById(R.id.nav_view);
        final ImageView hiveLogo = (ImageView) findViewById(R.id.hiveLogo);
        final TextView activeUsers = findViewById(R.id.activeUserCount);
        //activeUsers.setText("hello!!!");

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_search, R.id.navigation_notifications, R.id.navigation_buzz, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if(destination.getId() == R.id.navigation_buzz) {
                    navView.setVisibility(View.GONE);
                } else {
                    navView.setVisibility(View.VISIBLE);
                }

                if(destination.getId() == R.id.navigation_home) {
                    activeUsers.setVisibility(View.VISIBLE);
                }
                else {
                    activeUsers.setVisibility(View.GONE);
                }

                if(destination.getId() == R.id.navigation_profile || destination.getId() == R.id.navigation_buzz) {
                    hiveLogo.setVisibility(View.GONE);
                } else {
                    hiveLogo.setVisibility(View.VISIBLE);
                }

            }
        });

        NavigationUI.setupWithNavController(navView, navController);


        int userId = SharedPrefManager.getInstance(this.getApplicationContext()).getUser().getId();
        String w = "ws://10.24.227.37:8080/websocket/" + userId;
        //String w = "ws://echo.websocket.org";
        Draft[] drafts = {new Draft_6455()};
        try {
            Log.d("Socket:", "Trying socket");
            cc = new WebSocketClient(new URI(w),(Draft) drafts[0]) {
                @Override
                public void onMessage(String message) {
                    Log.d("", "run() returned: " + message);

                    //message is of form "onlineUsers: #"
                    String num = message.substring(13);
                    int count = Integer.parseInt(num);

                    activeUsers.setText("Total bees buzzing: " + count);

                }

                @Override
                public void onOpen(ServerHandshake handshake) {
                    Log.d("OPEN", "run() returned: " + "is connecting");

                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    Log.d("CLOSE", "onClose() returned: " + reason);
                }

                @Override
                public void onError(Exception e)
                {
                    Log.d("Exception:", e.toString());
                }

            };
        }
        catch (URISyntaxException e) {
            Log.d("Exception:", e.getMessage().toString());
            e.printStackTrace();
        }
        cc.connect();
        if (cc.isClosed()) {
            cc.connect();
        }

    }

    public static void hideKeyboard(Context context) {
        try {
            ((Activity) context).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            if ((((Activity) context).getCurrentFocus() != null) && (((Activity) context).getCurrentFocus().getWindowToken() != null)) {
                ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showKeyboard(Context context) {
        ((InputMethodManager) (context).getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }


}