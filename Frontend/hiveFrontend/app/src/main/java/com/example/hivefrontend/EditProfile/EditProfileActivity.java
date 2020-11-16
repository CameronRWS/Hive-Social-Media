package com.example.hivefrontend.EditProfile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.hivefrontend.EditProfile.Logic.EPLogic;
import com.example.hivefrontend.EditProfile.Logic.IEPVolleyListener;
import com.example.hivefrontend.EditProfile.Network.ServerRequest;
import com.example.hivefrontend.R;
import com.example.hivefrontend.SharedPrefManager;
import com.example.hivefrontend.VolleySingleton;
import com.example.hivefrontend.ui.profile.ProfileFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * The EditProfile activity
 */
public class EditProfileActivity extends AppCompatActivity implements IEPView {

    public int userId;
    private String imageFolder;
    private ImageButton editProfilePicture;
    private ImageButton editHeader;
    private Button cancelButton;
    private Button saveButton;
    private Button logoutButton;
    private Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private static final int GALLERY_REQUEST_CODE = 123;
    private ImageButton toSet;
    private EditText etFirstName;
    private EditText etUsername;
    private EditText etLastName;
    private EditText etBio;
    private String username;
    private String firstName;
    private String lastName;
    private String bio;
    private String emailAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        userId = SharedPrefManager.getInstance(this.getApplicationContext()).getUser().getId();


        etFirstName = findViewById(R.id.firstNameField);
        editProfilePicture = findViewById(R.id.editProfilePicture);
        cancelButton = findViewById(R.id.cancelButton);
        saveButton = findViewById(R.id.saveButton);
        logoutButton = findViewById(R.id.logoutButton);
        editHeader = findViewById(R.id.editHeader);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        etUsername = findViewById(R.id.usernameField);
        etLastName = findViewById(R.id.lastNameField);

        etBio = findViewById(R.id.bioField);

        final ServerRequest serverRequest = new ServerRequest();
        EPLogic logic = new EPLogic(this, serverRequest);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fm = getSupportFragmentManager();
                ProfileFragment profileFragment = new ProfileFragment();
                fm.beginTransaction().add(R.id.cancelButton, profileFragment).commit();

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.i("bigmacburger", "save button was clicked.");
                username = etUsername.getText().toString();
                Log.i("bigmacburger", "username done");
                //firstName = etFirstName.getText().toString();
                Log.i("bigmacburger", "firstname done");
                //lastName = etLastName.getText().toString();
                Log.i("bigmacburger", "lastname done");
                //bio = etBio.getText().toString();
                Log.i("bigmacburger", "bio done");

                onSave();
                //serverRequest.onSave();


                FragmentManager fm = getSupportFragmentManager();
                ProfileFragment profileFragment = new ProfileFragment();
                fm.beginTransaction().add(R.id.saveButton, profileFragment).commit();


            }
        });
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPrefManager.getInstance(getApplicationContext()).logout();
            }
        });

        editProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toSet = editProfilePicture;
                imageFolder = "profilePictures/";
                chooseImage();
            }
        });


        editHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toSet = editHeader;
                imageFolder = "profileBackgrounds/";
                chooseImage();
            }
        });
    }

    private void onSave() {
        String url ="http://10.24.227.37:8080/users";

        // Server name http://coms-309-tc-03.cs.iastate.edu:8080/posts

        //first request: user information
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i("bigmacburger", "it worked!");
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {


                    }
                });
        VolleySingleton.getInstance(getContext()).addToRequestQueue(jsonArrayRequest);
    }

    private String getUsername() {return username;}
    private String getFirstName() {return firstName;}
    private String getLastName() { return lastName;}
    private String getEmailAddress() {return emailAddress;}
    private String getBio() {return bio;}
    /**
     * Uploads an image
     */
    private void uploadImage() {
        if (imageUri != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference updatedPhoto = storageReference.child(imageFolder + userId + ".jpg");
            updatedPhoto.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(EditProfileActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(EditProfileActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }

    /**
     * Chooses a new image and opens the gallery activity
     */
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_REQUEST_CODE);
    }

    /**
     * Handles the selection of an image
     * @param requestCode The code of the specific request
     * @param resultCode The code of the specific result
     * @param data The data
     */
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
        uploadImage();
    }

    @Override
    public Context getContext() {return this.getApplicationContext();}

    @Override
    public int getUserId() {return userId;}

}