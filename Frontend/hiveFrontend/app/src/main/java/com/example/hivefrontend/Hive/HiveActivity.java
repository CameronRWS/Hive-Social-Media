package com.example.hivefrontend.Hive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hivefrontend.EditProfileActivity;
import com.example.hivefrontend.Login.LoginActivity;
import com.example.hivefrontend.MainActivity;
import com.example.hivefrontend.R;
import com.example.hivefrontend.Register.Logic.RegisterLogic;
import com.example.hivefrontend.Register.Network.ServerRequest;
import com.example.hivefrontend.SharedPrefManager;

public class HiveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hive);
    }

}