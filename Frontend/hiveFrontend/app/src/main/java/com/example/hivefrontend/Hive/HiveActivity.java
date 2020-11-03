package com.example.hivefrontend.Hive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageButton;

import com.example.hivefrontend.Hive.Logic.HiveLogic;
import com.example.hivefrontend.Hive.Network.ServerRequest;
import com.example.hivefrontend.R;
import com.example.hivefrontend.SharedPrefManager;

public class HiveActivity extends AppCompatActivity implements IHiveView {

    int joinState = 0;
    SharedPrefManager sharedPrefManager;
    int userId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final ImageButton joined = (ImageButton) findViewById(R.id.joinedHiveButton);
        final ImageButton join = (ImageButton) findViewById(R.id.joinHiveButton);
        final ImageButton requested = (ImageButton) findViewById(R.id.requestedHiveButton);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hive);

        final ServerRequest serverRequest = new ServerRequest();
        HiveLogic logic = new HiveLogic(this, serverRequest);
        serverRequest.displayScreen(userId);
    }

    @Override
    public int getUserId(ServerRequest server, String email) {
        return Integer.parseInt(server.getUserId(email));
    }

    @Override
    public Context getHiveContext() {
        return this.getApplicationContext();
    }
}