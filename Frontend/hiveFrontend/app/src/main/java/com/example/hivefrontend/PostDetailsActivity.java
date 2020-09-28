package com.example.hivefrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class PostDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);
        //intent should have grabbed post title
        String title = getIntent().getStringExtra("postTitle");
        TextView titleTextView = findViewById(R.id.postTitle);
        titleTextView.setText(title);

    }
}