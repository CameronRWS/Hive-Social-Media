package com.example.hivefrontend;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.FileNotFoundException;
import java.io.IOException;

public class EditProfileActivity extends AppCompatActivity {

    public int userId = 2;
    private ImageButton editProfilePicture;
    private ImageButton editHeader;
    private ImageButton profilePictureActual;
    private ImageButton headerActual;
    private Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private static final int GALLERY_REQUEST_CODE = 123;
    private ImageButton toSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        editProfilePicture = findViewById(R.id.editProfilePicture);
        editHeader = findViewById(R.id.editHeader);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        profilePictureActual = findViewById(R.id.profilePictureActual);
        headerActual = findViewById(R.id.headerActual);

//        StorageReference currentP = storageReference.child("profilePictures/" + userId + ".jpg");
//        StorageReference currentB = storageReference.child("profileBackgrounds/" + userId + ".jpg");

//        GlideApp.with(this)
//                .load(currentP)
//                .error(R.drawable.defaulth)
//                .into(profilePic);

//        GlideApp.with(this)
//                .load(currentB)
//                .error(R.drawable.defaultb)
//                .into(header);

        editProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toSet = editProfilePicture;
                chooseImage();
                //uploadImage();
            }
        });

        editHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toSet = editHeader;
                chooseImage();
            }
        });

//        headerActual.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                chooseImage();
//                //uploadImage();
//            }
//        });
    }

    private void uploadImage() {
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null )
        {
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                toSet.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}