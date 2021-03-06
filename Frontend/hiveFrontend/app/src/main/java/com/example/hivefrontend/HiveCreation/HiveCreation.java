package com.example.hivefrontend.HiveCreation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.hivefrontend.HiveCreation.Logic.HiveCreationLogic;
import com.example.hivefrontend.HiveCreation.Logic.IHiveCreationVolleyListener;
import com.example.hivefrontend.HiveCreation.Server.HiveCreationServerRequest;
import com.example.hivefrontend.HiveCreation.Server.IHiveCreationServerRequest;
import com.example.hivefrontend.MainActivity;
import com.example.hivefrontend.R;
import com.example.hivefrontend.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Activity to create a new hive.
 */
public class HiveCreation extends AppCompatActivity implements IHiveCreationView, AdapterView.OnItemSelectedListener {

    private HiveCreationLogic logic;
    private HiveCreationServerRequest server;
    private int userId;
    private int selectedItemPos;
    private Spinner mySpinner;
    ArrayList<String> hiveTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hive_creation);
        userId = SharedPrefManager.getInstance(this.getApplicationContext()).getUser().getId();
        server = new HiveCreationServerRequest();
        logic = new HiveCreationLogic(this, server);
        server.addVolleyListener(logic);
        hiveTypes=new ArrayList<>();
        hiveTypes.add("");
        hiveTypes.add("public");
        hiveTypes.add("protected");
        hiveTypes.add("private");
        selectedItemPos = 0;
        mySpinner = (Spinner) findViewById(R.id.hiveTypeSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item,hiveTypes);
        mySpinner.setAdapter(adapter);
        mySpinner.setOnItemSelectedListener(this);


        Button back = findViewById(R.id.cancelButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { goHome(); }
        });

        Button createHive = findViewById(R.id.createHive);
        createHive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    handleClick();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Returns the id of the current user
     * @return The user id of the current user
     */
    public int getUserId(){
        return userId;
    }

    /**
     * Called when an item is selected in the spinner and sets the selected item position variable appropriately.
     * @param adapterView
     * @param view
     * @param pos The selected position in the spinner
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
        selectedItemPos = pos;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    /**
     * Returns the context of this activity
     * @return
     */
    @Override
    public Context getViewContext() {
        return this.getApplicationContext();
    }

    /**
     * Called when a user presses the "create hive" button. Calls the logic class to handle the logic of hive creation.
     * @throws JSONException
     */
    @Override
    public void handleClick() throws JSONException {

        if(validInput()){
            JSONObject hive = new JSONObject();
            EditText title= findViewById(R.id.hiveTitleInput);
            EditText bio= findViewById(R.id.hiveBioInput);
            EditText latitude= findViewById(R.id.hiveLatitudeInput);
            EditText longitude= findViewById(R.id.hiveLongitudeInput);
            hive.put("name",title.getText().toString());
            hive.put("description",bio.getText().toString());

            if(selectedItemPos==1){
                hive.put("type","public");
            }
            if(selectedItemPos==2){
                hive.put("type","protected");
            }
            if(selectedItemPos==3){
                hive.put("type","private");
            }
            hive.put("latitude", Double.parseDouble(latitude.getText().toString()));
            hive.put("longitude", Double.parseDouble(longitude.getText().toString()));
            logic.createHive(hive);
        }
        else{
            Toast.makeText(this.getApplicationContext(), "Invalid name, description, or location.", Toast.LENGTH_LONG).show();
        }

    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    /**
     * Determines if the provided hive title and bio are valid
     * @return Returns true if input is valid, false if not
     */
    @Override
    public boolean validInput(){
        EditText title= findViewById(R.id.hiveTitleInput);
        EditText bio= findViewById(R.id.hiveBioInput);
        EditText latitude= findViewById(R.id.hiveLatitudeInput);
        EditText longitude= findViewById(R.id.hiveLongitudeInput);
        if(title.getText().toString().length()>0 && bio.getText().toString().length()>0 && isNumeric(latitude.getText().toString()) && isNumeric(longitude.getText().toString())) {
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Starts the intent to go back to the home screen
     */
    @Override
    public void goHome(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}