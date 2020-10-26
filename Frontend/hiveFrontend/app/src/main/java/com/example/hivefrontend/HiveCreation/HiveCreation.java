package com.example.hivefrontend.HiveCreation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.hivefrontend.HiveCreation.Logic.HiveCreationLogic;
import com.example.hivefrontend.HiveCreation.Logic.IHiveCreationVolleyListener;
import com.example.hivefrontend.HiveCreation.Server.HiveCreationServerRequest;
import com.example.hivefrontend.HiveCreation.Server.IHiveCreationServerRequest;
import com.example.hivefrontend.R;

public class HiveCreation extends AppCompatActivity implements IHiveCreationView{

    private HiveCreationLogic logic;
    private HiveCreationServerRequest server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hive_creation);
        server = new HiveCreationServerRequest();
        logic = new HiveCreationLogic(this, server);
        server.addVolleyListener(logic);


        Button createHive = findViewById(R.id.createHive);
        createHive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClick();
            }
        });
    }

    @Override
    public Context getViewContext() {
        return this.getApplicationContext();
    }

    @Override
    public void handleClick() {

        if(validInput()){
            logic.createHive();
        }
        else{
            Toast.makeText(this.getApplicationContext(), "Please choose a hive to share this post to.", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean validInput(){
        return false;
    }
}