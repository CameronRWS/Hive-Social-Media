package com.example.hivefrontend.Hive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.hivefrontend.Hive.Logic.HiveLogic;
import com.example.hivefrontend.Hive.Network.ServerRequest;
import com.example.hivefrontend.R;
import com.example.hivefrontend.SharedPrefManager;

import org.w3c.dom.Text;

public class HiveActivity extends AppCompatActivity implements IHiveView {

    int joinState = 0;
    SharedPrefManager sharedPrefManager;
    int userId = 1;
    String givenHiveName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hive);
        final ImageButton joined = (ImageButton) findViewById(R.id.joinedHiveButton);
        final ImageButton join = (ImageButton) findViewById(R.id.joinHiveButton);
        final ImageButton requested = (ImageButton) findViewById(R.id.requestedHiveButton);
        final TextView tvHiveName = (TextView) findViewById(R.id.hiveName);
        givenHiveName = getIntent().getStringExtra("hiveName");
        tvHiveName.setText(givenHiveName);
        

        final ServerRequest serverRequest = new ServerRequest();
        HiveLogic logic = new HiveLogic(this, serverRequest);
        serverRequest.displayScreen(givenHiveName);
    }

    @Override
    public Context getHiveContext() {
        return this.getApplicationContext();
    }


}