package com.example.hivefrontend.ui.buzz;

import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.example.hivefrontend.EditProfileActivity;
import com.example.hivefrontend.HiveCreation.HiveCreation;

import com.example.hivefrontend.MainActivity;
import com.example.hivefrontend.R;
import com.example.hivefrontend.SharedPrefManager;
import com.example.hivefrontend.ui.buzz.Logic.BuzzLogic;
import com.example.hivefrontend.ui.buzz.Network.ServerRequest;
import com.example.hivefrontend.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BuzzFragment extends Fragment implements IBuzzView, AdapterView.OnItemSelectedListener {

    EditText buzzTitle;
    EditText buzzContent;
    public BuzzViewModel mViewModel;
    public ArrayList<Integer> hiveIds;
    public ArrayList<String> hiveOptions;
    public RequestQueue queue;
    public Spinner mySpinner;
    public int selectedItemPos = 0;

    public int userId = 2;
    public ImageView imagePreview;
    private static final int GALLERY_REQUEST_CODE = 123;
    private static final int CAMERA_REQUEST = 1888;
    private Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;


    public static BuzzFragment newInstance() {
        return new BuzzFragment();
    }
    //to post, will need a title, hive id, user id, and text content

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.buzz_fragment, container, false);
        queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        hiveIds= new ArrayList<>();
        hiveOptions = new ArrayList<>();
        buzzTitle = (EditText) rootView.findViewById(R.id.buzzTitleInput);
        buzzContent = (EditText) rootView.findViewById(R.id.buzzContentInput);
        mySpinner = (Spinner) rootView.findViewById(R.id.hiveIdSpinner);
        mySpinner.setOnItemSelectedListener(this);
        Button back = (Button) rootView.findViewById(R.id.cancelButton);
        Button b = (Button) rootView.findViewById(R.id.submitBuzz);
        ImageButton accessGallery = (ImageButton) rootView.findViewById(R.id.accessGallery);
        ImageButton accessCamera = (ImageButton) rootView.findViewById(R.id.accessCamera);
        imagePreview = (ImageView) rootView.findViewById(R.id.imagePreview);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openHome(); }
        });

        accessGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

        accessCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, GALLERY_REQUEST_CODE);
            }
        });

        final ServerRequest serverRequest = new ServerRequest();
        BuzzLogic logic = new BuzzLogic(this, serverRequest);
        logic.displayBuzzScreen();


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serverRequest.makeBuzz();
                uploadImage();
                }
        });
            
        Button createHive = rootView.findViewById(R.id.addHiveButton);
        createHive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createHive();
            }
        });
        return rootView;
    }


    public void createHive(){
        Intent intent = new Intent(BuzzFragment.this.getActivity(), HiveCreation.class);
        startActivity(intent);
    }
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_REQUEST_CODE);
    }

    public int getUserId() {
        return userId;
    }

    public void addHiveIdValue(int i) {
        hiveIds.add(i);
    }

    public void addHiveOptionsValue(String s) {
        hiveOptions.add(s);
    }

    public Context getBuzzContext() {
        return this.getContext();
    }

    public void onOptionsSet(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity().getApplicationContext(),android.R.layout.simple_spinner_item,hiveOptions);
        mySpinner.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_REQUEST_CODE && data != null && data.getData() != null )
        {
            imageUri = data.getData();
            imagePreview.setImageURI(imageUri);
        }
    }

    private void uploadImage() {
        if (imageUri != null) {
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference updatedPhoto = storageReference.child("images/" + userId + ".jpg");
            updatedPhoto.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(BuzzViewModel.class);
        // TODO: Use the ViewModel
    }

    public int getSelectedItemPos() { return selectedItemPos; }
    public void openHome() {
        Intent intent = new Intent(BuzzFragment.this.getActivity(), MainActivity.class);
        startActivity(intent);
    }

    @Override
    public int getHiveId(int pos) {
        return hiveIds.get(pos);
    }

    @Override
    public String getBuzzTitle() {
        return buzzTitle.getText().toString();
    }

    @Override
    public String getBuzzContent() {
        return buzzContent.getText().toString();
    }

    @Override
    public String getHiveOption(int pos) {
        return hiveOptions.get(pos);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
        selectedItemPos = pos;
        Log.i("positionSelected",""+pos);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}