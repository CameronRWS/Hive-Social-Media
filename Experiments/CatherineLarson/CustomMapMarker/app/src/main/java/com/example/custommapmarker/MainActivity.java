package com.example.custommapmarker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.custommapmarker.LATITUDE";
    public static final String EXTRA_MESSAGE2 = "com.example.custommapmarker.LONGITUDE";
    public static final String EXTRA_MESSAGE3 = "com.example.custommapmarker.DESCRIPTION";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when the user taps the submit button */
    public void submitInfo(View view){
        Intent intent = new Intent(this, MapWithMarker.class);

        EditText lat = (EditText) findViewById(R.id.latitude);
        String message = lat.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);

        EditText longitude = (EditText) findViewById(R.id.longitude);
        String message2 = longitude.getText().toString();
        intent.putExtra(EXTRA_MESSAGE2, message2);

        EditText desc = (EditText) findViewById(R.id.description);
        String message3 = desc.getText().toString();
        intent.putExtra(EXTRA_MESSAGE3, message3);

        startActivity(intent);
    }


}