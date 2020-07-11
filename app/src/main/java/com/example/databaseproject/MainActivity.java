package com.example.databaseproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.*;

public class MainActivity extends AppCompatActivity {

    private int STORAGE_PERMISSION_CODE = 1;

    DatabaseHelper helper;
    TextView registernow;
    EditText email, password;
    Button signin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readFlights();
        readCitiesdata();
        readHotelDetails();

        readFlightDetails();

        helper = new DatabaseHelper(this);
        registernow = findViewById(R.id.registerNow);
        email = findViewById(R.id.editText);
        password = findViewById(R.id.editText2);
        signin = findViewById(R.id.button);


        registernow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(MainActivity.this, "Proceeding to Registration", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, Register.class);
                    startActivity(intent);

                } else {
                    requestStoragePermission();
                }
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if((!email.getText().toString().equals("")) && (!password.getText().toString().equals(""))) {
                    boolean check = helper.checkLogin(email.getText().toString(), password.getText().toString());

                    if (check) {
                        Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainActivity.this, Hotels.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(MainActivity.this, "Email or Password incorrect", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(MainActivity.this, "Please fill the fields correctly and try again.", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public void readHotelDetails() {

        helper = new DatabaseHelper(this);
        InputStream is = getResources().openRawResource(R.raw.hotel_details);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        String line = "";


        try {
            while ((line = reader.readLine()) != null) {

                String[] tokens = line.split(",");
                if (tokens[0] != null && tokens[1] != null && tokens[2] != null && tokens[3] != null) {
                    helper.insertHotelDetails(tokens[0], tokens[1], tokens[2], tokens[3]);
                }


            }
        } catch (IOException e) {
            Log.wtf("MyActivity", "Error reading data file on line " + line, e);
            e.printStackTrace();
        }

    }



    public void readCitiesdata() {
        helper = new DatabaseHelper(this);
        InputStream is = getResources().openRawResource(R.raw.cities);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        String line = "";


        try {
            while ((line = reader.readLine()) != null) {

                String[] tokens = line.split(",");
                if (tokens[0] != null && tokens[1] != null) {
                    helper.insertCities(tokens[0], tokens[1]);
                }


            }
        } catch (IOException e) {
            Log.wtf("MyActivity", "Error reading data file on line " + line, e);
            e.printStackTrace();
        }


    }

    public void readFlights(){
        helper = new DatabaseHelper(this);
        InputStream is = getResources().openRawResource(R.raw.flights);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        String line = "";


        try {
            while ((line = reader.readLine()) != null) {

                String[] tokens = line.split(",");
                if (tokens[0] != null && tokens[1] != null && tokens[2] != null && tokens[3] != null && tokens[4] != null && tokens[5] != null) {
                    helper.insertFlights(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4], tokens[5]);
                }


            }
        } catch (IOException e) {
            Log.wtf("MyActivity", "Error reading data file on line " + line, e);
            e.printStackTrace();
        }
    }


    public void readFlightDetails(){
        helper = new DatabaseHelper(this);
        InputStream is = getResources().openRawResource(R.raw.flightdetails);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        String line = "";


        try {
            while ((line = reader.readLine()) != null) {

                String[] tokens = line.split(",");
                if (tokens[0] != null && tokens[1] != null && tokens[2] != null) {
                    helper.insertFlightDetails(tokens[0], tokens[1], tokens[2]);
                }


            }
        } catch (IOException e) {
            Log.wtf("MyActivity", "Error reading data file on line " + line, e);
            e.printStackTrace();
        }
    }


    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed to register")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }


}


