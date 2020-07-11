package com.example.databaseproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Flights extends AppCompatActivity {


    DatabaseHelper helper;


    String origin;

    String destination1 = "";
    String passengernumber = "";
    String departingdate = "";
    double currentTotal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flights);


        final TextView noPassengers = findViewById(R.id.textView28);
        final TextView departingDate = findViewById(R.id.textView13);
        final RadioButton economy = findViewById(R.id.radioButton2);
        final RadioButton business = findViewById(R.id.radioButton);
        final RadioButton firstclass = findViewById(R.id.radioButton3);
        final Spinner origin = findViewById(R.id.spinner1);
        final TextView destination = findViewById(R.id.textView29);
        final Spinner flight = findViewById(R.id.spinner);
        Button generate = findViewById(R.id.button4);
        Button proceed = findViewById(R.id.button5);
        final TextView flightid = findViewById(R.id.textView31);
        final double[] total = {0};
        final String[] flightclass = new String[1];

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((origin.getSelectedItem().toString().equals(destination.getText().toString()))) {
                    Toast.makeText(Flights.this, "Please choose different origin and destination cities!", Toast.LENGTH_SHORT).show();

                }
                else if(flightid.getText().toString().equals("flight_id")){
                    Toast.makeText(Flights.this, "Please choose your flight and regenerate your flight ID", Toast.LENGTH_SHORT).show();

                }
                else if ((!economy.isChecked() && !business.isChecked() && !firstclass.isChecked())) {
                    Toast.makeText(Flights.this, "Please choose your flight class!", Toast.LENGTH_SHORT).show();
                }

                else {
                    String destinationCity = destination.getText().toString();
                    String passengersnumber = noPassengers.getText().toString();
                    String departingdate = departingDate.getText().toString();
                    String flightName = flight.getSelectedItem().toString();
                    String originCity = origin.getSelectedItem().toString();

                    if (economy.isChecked()) {
                        flightclass[0] = "Economy Class";
                    } else if (business.isChecked()) {
                        flightclass[0] = "Business Class";
                    } else {
                        flightclass[0] = "First Class";
                    }


                    Intent intent = new Intent(Flights.this, UserPackage.class);
                    String hotelname = getIntent().getStringExtra("key3");

                    intent.putExtra("key", destinationCity);
                    intent.putExtra("key1", passengersnumber);
                    intent.putExtra("key2", departingdate);
                    intent.putExtra("key3", flightName);
                    intent.putExtra("key4", originCity);
                    intent.putExtra("key5", flightclass[0]);
                    intent.putExtra("key7", hotelname);
                    Bundle b = new Bundle();
                    b.putDouble("key6", currentTotal);
                    intent.putExtras(b);
                    Toast.makeText(Flights.this, "Packaging data", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            }
        });


        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                helper = new DatabaseHelper(getApplicationContext());
                InputStream inputStream2 = getResources().openRawResource(R.raw.flights);
                BufferedReader reader2 = new BufferedReader(new InputStreamReader(inputStream2, Charset.forName("UTF-8")));
                String priceline = "";
                try {
                    while ((priceline = reader2.readLine()) != null) {

                        String[] tokens2 = priceline.split(",");
                        if (tokens2[0] != null && tokens2[1] != null && tokens2[2] != null && tokens2[3] != null && tokens2[4] != null && tokens2[5] != null && tokens2[1].equals(flight.getSelectedItem().toString())) {
                            flightid.setText(tokens2[0]);
                        }


                    }
                } catch (IOException e) {
                    Log.wtf("MyActivity", "Error reading data file on line " + priceline, e);
                    e.printStackTrace();
                }

                helper.close();
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


                //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                helper = new DatabaseHelper(getApplicationContext());
                InputStream inputStream = getResources().openRawResource(R.raw.flight_price);
                BufferedReader reader3 = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
                String priceline2 = "";
                try {
                    while ((priceline2 = reader3.readLine()) != null) {

                        String[] tokens2 = priceline2.split(",");
                        if (tokens2[0] != null && tokens2[1] != null && tokens2[0].equals(flightid.getText().toString())) {
                            total[0] = Double.parseDouble(tokens2[1]);
                        }


                    }
                } catch (IOException e) {
                    Log.wtf("MyActivity", "Error reading data file on line " + priceline, e);
                    e.printStackTrace();
                }


                helper.close();
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


                String destinationCity = getIntent().getStringExtra("key");
                String passengersnumber = getIntent().getStringExtra("key1");
                String departDate = getIntent().getStringExtra("key2");
                Bundle b = getIntent().getExtras();
                double prevtotal = b.getDouble("key0");
                currentTotal = prevtotal + total[0];


                noPassengers.setText(passengersnumber);
                departingDate.setText(departDate);
                destination.setText(destinationCity);


            }
        });


        economy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (economy.isChecked()) {
                    business.setChecked(false);
                    firstclass.setChecked(false);
                }
            }
        });

        business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (business.isChecked()) {
                    economy.setChecked(false);
                    firstclass.setChecked(false);
                }

            }
        });

        firstclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firstclass.isChecked()) {
                    economy.setChecked(false);
                    business.setChecked(false);
                }
            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        List<String> citynames = new ArrayList<String>();
        helper = new DatabaseHelper(this);
        InputStream is2 = getResources().openRawResource(R.raw.cities);
        BufferedReader reader2 = new BufferedReader(new InputStreamReader(is2, Charset.forName("UTF-8")));
        String line2 = "";
        try {
            while ((line2 = reader2.readLine()) != null) {

                String[] tokens2 = line2.split(",");
                if (tokens2[0] != null && tokens2[1] != null) {
                    citynames.add(tokens2[1]);
                }


            }
        } catch (IOException e) {
            Log.wtf("MyActivity", "Error reading data file on line " + line2, e);
            e.printStackTrace();
        }

        String[] namearray2 = new String[citynames.size()];

        for (int a = 0; a < citynames.size(); a++) {
            namearray2[a] = citynames.get(a);
        }
        helper.close();


        //ADAPTER FOR ORIGIN SPINNER
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(Flights.this, android.R.layout.simple_expandable_list_item_1, namearray2);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        origin.setAdapter(myAdapter);
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        /*List<String> destcity = new ArrayList<String>();
        helper = new DatabaseHelper(this);
        InputStream is3 = getResources().openRawResource(R.raw.cities);
        BufferedReader reader3 = new BufferedReader(new InputStreamReader(is3, Charset.forName("UTF-8")));
        String line3 = "";
        try {
            while ((line3 = reader3.readLine()) != null) {

                String[] tokens2 = line3.split(",");
                if (tokens2[0] != null && tokens2[1] != null) {
                    destcity.add(tokens2[1]);
                }


            }
        } catch (IOException e) {
            Log.wtf("MyActivity", "Error reading data file on line " + line3, e);
            e.printStackTrace();
        }

        String[] namearray3 = new String[destcity.size()];

        for (int a = 0; a < destcity.size(); a++) {
            namearray3[a] = destcity.get(a);
        }
        helper.close();


        //ADAPTER FOR ORIGIN SPINNER
        ArrayAdapter<String> destAdapter = new ArrayAdapter<String>(Flights.this, android.R.layout.simple_expandable_list_item_1, namearray3);
        destAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        destination.setAdapter(destAdapter);*/
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        List<String> flightlist = new ArrayList<String>();
        helper = new DatabaseHelper(this);
        InputStream is4 = getResources().openRawResource(R.raw.flights);
        BufferedReader reader4 = new BufferedReader(new InputStreamReader(is4, Charset.forName("UTF-8")));
        String line4 = "";
        try {
            while ((line4 = reader4.readLine()) != null) {

                String[] tokens2 = line4.split(",");
                if (tokens2[0] != null && tokens2[1] != null && tokens2[2] != null && tokens2[3] != null && tokens2[4] != null) {
                    flightlist.add(tokens2[1]);
                }


            }
        } catch (IOException e) {
            Log.wtf("MyActivity", "Error reading data file on line " + line4, e);
            e.printStackTrace();
        }

        String[] namearray4 = new String[flightlist.size()];

        for (int a = 0; a < flightlist.size(); a++) {
            namearray4[a] = flightlist.get(a);
        }
        helper.close();


        //ADAPTER FOR ORIGIN SPINNER
        ArrayAdapter<String> flightAdapter = new ArrayAdapter<String>(Flights.this, android.R.layout.simple_expandable_list_item_1, namearray4);
        flightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        flight.setAdapter(flightAdapter);


    }


}
