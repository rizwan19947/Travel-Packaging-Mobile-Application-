package com.example.databaseproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.RatingBar;
import android.widget.Spinner;
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

public class Hotels extends AppCompatActivity {


    DatabaseHelper helper;
    String destinationCity = "";
    String passengersnumber = "";
    String departingDate = "";
    String hotelname = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotels);
        final CalendarView calender = findViewById(R.id.calendarView2);
        final Spinner cityspinner = findViewById(R.id.spinner4);
        final Spinner hotelspinner = findViewById(R.id.spinner5);
        final Spinner guestspinner = findViewById(R.id.spinner3);
        final Spinner stayduration = findViewById(R.id.spinner6);
        Button proceed = findViewById(R.id.button6);
        final RatingBar hotelrating = findViewById(R.id.ratingBar3);
        final double[] totalPrice = {0};
        final double[] hotelPrice = {0};

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                destinationCity = cityspinner.getSelectedItem().toString();
                passengersnumber = guestspinner.getSelectedItem().toString();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                departingDate = sdf.format(new Date(calender.getDate()));



//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                helper = new DatabaseHelper(getApplicationContext());
                InputStream inputStream = getResources().openRawResource(R.raw.hotel_details);
                BufferedReader reader2 = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
                String priceline = "";
                try {
                    while ((priceline = reader2.readLine()) != null) {

                        String[] tokens2 = priceline.split(",");
                        if (tokens2[0] != null && tokens2[1] != null && tokens2[2] != null && tokens2[3] != null && tokens2[1].equals(hotelspinner.getSelectedItem().toString())) {
                            hotelPrice[0] = Double.parseDouble(tokens2[2]);
                        }


                    }
                } catch (IOException e) {
                    Log.wtf("MyActivity", "Error reading data file on line " + priceline, e);
                    e.printStackTrace();
                }
                totalPrice[0] = totalPrice[0] + hotelPrice[0];

                helper.close();
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                if(guestspinner.getSelectedItem().toString().equals("1") || guestspinner.getSelectedItem().toString().equals("2")){
                    totalPrice[0] = totalPrice[0] * 1;
                }
                else if(guestspinner.getSelectedItem().toString().equals("3") || guestspinner.getSelectedItem().toString().equals("4")){
                    totalPrice[0] = totalPrice[0] * 2;
                }

                totalPrice[0] = totalPrice[0] * (Double.parseDouble(stayduration.getSelectedItem().toString()));

                Bundle b = new Bundle();
                b.putDouble("key0",totalPrice[0]);
                hotelname = hotelspinner.getSelectedItem().toString();
                Intent flight = new Intent(Hotels.this, Flights.class);
                flight.putExtra("key", destinationCity);
                flight.putExtra("key1", passengersnumber);
                flight.putExtra("key2", departingDate);
                flight.putExtra("key3", hotelname);
                flight.putExtras(b);
                Toast.makeText(Hotels.this, "Proceeding to Flight Booking", Toast.LENGTH_SHORT).show();
                startActivity(flight);

            }
        });


        ////////////////////////////ASSIGNING HOTEL NAMES FROM THE CSV DATABASE TO THE SPINNER////////////////////////////
        /*List<String> hotelnames = new ArrayList<String>();
        helper = new DatabaseHelper(this);
        InputStream is = getResources().openRawResource(R.raw.hotel_details);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        String line = "";


        try {
            while ((line = reader.readLine()) != null) {

                String[] tokens = line.split(",");
                if (tokens[0] != null && tokens[1] != null && tokens[2] != null && tokens[3] != null) {
                    hotelnames.add(tokens[1]);
                }


            }
        } catch (IOException e) {
            Log.wtf("MyActivity", "Error reading data file on line " + line, e);
            e.printStackTrace();
        }

        String[] namearray = new String[hotelnames.size()];

        for (int a = 0; a < hotelnames.size(); a++) {
            namearray[a] = hotelnames.get(a);
        }
        helper.close();*/
        ////////////////////////////ASSIGNING HOTEL NAMES FROM THE CSV DATABASE TO THE SPINNER////////////////////////////


        ////////////////////////////ASSIGNING CITY NAMES FROM THE CSV DATABASE TO THE SPINNER////////////////////////////
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
        ////////////////////////////ASSIGNING CITY NAMES FROM THE CSV DATABASE TO THE SPINNER////////////////////////////


        //ADAPTER FOR GUESTS NUMBER SPINNER
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(Hotels.this, android.R.layout.simple_expandable_list_item_1, getResources().getStringArray(R.array.GuestNo));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        guestspinner.setAdapter(myAdapter);

        //ADAPTER FOR BOOKING DAYS SPINNER
        ArrayAdapter<String> stayAdapter = new ArrayAdapter<String>(Hotels.this, android.R.layout.simple_expandable_list_item_1, getResources().getStringArray(R.array.StayDuration));
        stayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stayduration.setAdapter(stayAdapter);

        /*//ADAPTER FOR HOTEL NAMES SPINNER
        ArrayAdapter<String> hotelAdapter = new ArrayAdapter<String>(Hotels.this, android.R.layout.simple_expandable_list_item_1, namearray);
        hotelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hotelspinner.setAdapter(hotelAdapter);*/

        //ADAPTER FOR CITY NAMES SPINNER
        ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(Hotels.this, android.R.layout.simple_expandable_list_item_1, namearray2);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cityspinner.setAdapter(cityAdapter);


        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        cityspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String spinnerValue = cityspinner.getSelectedItem().toString();
                if (spinnerValue.equals("KAR")) {

                    ////////////////////////////ASSIGNING HOTEL NAMES FROM THE CSV DATABASE TO THE SPINNER////////////////////////////
                    List<String> hotelnames = new ArrayList<String>();
                    helper = new DatabaseHelper(getApplicationContext());
                    InputStream is = getResources().openRawResource(R.raw.hotel_details);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                    String line = "";


                    try {
                        while ((line = reader.readLine()) != null) {

                            String[] tokens = line.split(",");
                            if (tokens[0] != null && tokens[1] != null && tokens[2] != null && tokens[3] != null && tokens[0].equals("KAR")) {
                                hotelnames.add(tokens[1]);
                            }


                        }
                    } catch (IOException e) {
                        Log.wtf("MyActivity", "Error reading data file on line " + line, e);
                        e.printStackTrace();
                    }

                    String[] namearray = new String[hotelnames.size()];

                    for (int a = 0; a < hotelnames.size(); a++) {
                        namearray[a] = hotelnames.get(a);
                    }
                    helper.close();

                    //ADAPTER FOR HOTEL NAMES SPINNER
                    ArrayAdapter<String> hotelAdapter = new ArrayAdapter<String>(Hotels.this, android.R.layout.simple_expandable_list_item_1, namearray);
                    hotelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    hotelspinner.setAdapter(hotelAdapter);
                    ////////////////////////////ASSIGNING HOTEL NAMES FROM THE CSV DATABASE TO THE SPINNER////////////////////////////

                } else if (spinnerValue.equals("HYD")) {

                    ////////////////////////////ASSIGNING HOTEL NAMES FROM THE CSV DATABASE TO THE SPINNER////////////////////////////
                    List<String> hotelnames = new ArrayList<String>();
                    helper = new DatabaseHelper(getApplicationContext());
                    InputStream is = getResources().openRawResource(R.raw.hotel_details);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                    String line = "";


                    try {
                        while ((line = reader.readLine()) != null) {

                            String[] tokens = line.split(",");
                            if (tokens[0] != null && tokens[1] != null && tokens[2] != null && tokens[3] != null && tokens[0].equals("HYD")) {
                                hotelnames.add(tokens[1]);
                            }


                        }
                    } catch (IOException e) {
                        Log.wtf("MyActivity", "Error reading data file on line " + line, e);
                        e.printStackTrace();
                    }

                    String[] namearray = new String[hotelnames.size()];

                    for (int a = 0; a < hotelnames.size(); a++) {
                        namearray[a] = hotelnames.get(a);
                    }
                    helper.close();

                    //ADAPTER FOR HOTEL NAMES SPINNER
                    ArrayAdapter<String> hotelAdapter = new ArrayAdapter<String>(Hotels.this, android.R.layout.simple_expandable_list_item_1, namearray);
                    hotelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    hotelspinner.setAdapter(hotelAdapter);
                    ////////////////////////////ASSIGNING HOTEL NAMES FROM THE CSV DATABASE TO THE SPINNER////////////////////////////
                } else if (spinnerValue.equals("LAH")) {
                    ////////////////////////////ASSIGNING HOTEL NAMES FROM THE CSV DATABASE TO THE SPINNER////////////////////////////
                    List<String> hotelnames = new ArrayList<String>();
                    helper = new DatabaseHelper(getApplicationContext());
                    InputStream is = getResources().openRawResource(R.raw.hotel_details);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                    String line = "";


                    try {
                        while ((line = reader.readLine()) != null) {

                            String[] tokens = line.split(",");
                            if (tokens[0] != null && tokens[1] != null && tokens[2] != null && tokens[3] != null && tokens[0].equals("LAH")) {
                                hotelnames.add(tokens[1]);
                            }


                        }
                    } catch (IOException e) {
                        Log.wtf("MyActivity", "Error reading data file on line " + line, e);
                        e.printStackTrace();
                    }

                    String[] namearray = new String[hotelnames.size()];

                    for (int a = 0; a < hotelnames.size(); a++) {
                        namearray[a] = hotelnames.get(a);
                    }
                    helper.close();

                    //ADAPTER FOR HOTEL NAMES SPINNER
                    ArrayAdapter<String> hotelAdapter = new ArrayAdapter<String>(Hotels.this, android.R.layout.simple_expandable_list_item_1, namearray);
                    hotelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    hotelspinner.setAdapter(hotelAdapter);
                    ////////////////////////////ASSIGNING HOTEL NAMES FROM THE CSV DATABASE TO THE SPINNER////////////////////////////
                } else if (spinnerValue.equals("ISL")) {
                    ////////////////////////////ASSIGNING HOTEL NAMES FROM THE CSV DATABASE TO THE SPINNER////////////////////////////
                    List<String> hotelnames = new ArrayList<String>();
                    helper = new DatabaseHelper(getApplicationContext());
                    InputStream is = getResources().openRawResource(R.raw.hotel_details);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                    String line = "";


                    try {
                        while ((line = reader.readLine()) != null) {

                            String[] tokens = line.split(",");
                            if (tokens[0] != null && tokens[1] != null && tokens[2] != null && tokens[3] != null && tokens[0].equals("ISL")) {
                                hotelnames.add(tokens[1]);
                            }


                        }
                    } catch (IOException e) {
                        Log.wtf("MyActivity", "Error reading data file on line " + line, e);
                        e.printStackTrace();
                    }

                    String[] namearray = new String[hotelnames.size()];

                    for (int a = 0; a < hotelnames.size(); a++) {
                        namearray[a] = hotelnames.get(a);
                    }
                    helper.close();

                    //ADAPTER FOR HOTEL NAMES SPINNER
                    ArrayAdapter<String> hotelAdapter = new ArrayAdapter<String>(Hotels.this, android.R.layout.simple_expandable_list_item_1, namearray);
                    hotelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    hotelspinner.setAdapter(hotelAdapter);
                    ////////////////////////////ASSIGNING HOTEL NAMES FROM THE CSV DATABASE TO THE SPINNER////////////////////////////
                } else if (spinnerValue.equals("RAW")) {
                    ////////////////////////////ASSIGNING HOTEL NAMES FROM THE CSV DATABASE TO THE SPINNER////////////////////////////
                    List<String> hotelnames = new ArrayList<String>();
                    helper = new DatabaseHelper(getApplicationContext());
                    InputStream is = getResources().openRawResource(R.raw.hotel_details);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                    String line = "";


                    try {
                        while ((line = reader.readLine()) != null) {

                            String[] tokens = line.split(",");
                            if (tokens[0] != null && tokens[1] != null && tokens[2] != null && tokens[3] != null && tokens[0].equals("RAW")) {
                                hotelnames.add(tokens[1]);
                            }


                        }
                    } catch (IOException e) {
                        Log.wtf("MyActivity", "Error reading data file on line " + line, e);
                        e.printStackTrace();
                    }

                    String[] namearray = new String[hotelnames.size()];

                    for (int a = 0; a < hotelnames.size(); a++) {
                        namearray[a] = hotelnames.get(a);
                    }
                    helper.close();

                    //ADAPTER FOR HOTEL NAMES SPINNER
                    ArrayAdapter<String> hotelAdapter = new ArrayAdapter<String>(Hotels.this, android.R.layout.simple_expandable_list_item_1, namearray);
                    hotelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    hotelspinner.setAdapter(hotelAdapter);
                    ////////////////////////////ASSIGNING HOTEL NAMES FROM THE CSV DATABASE TO THE SPINNER////////////////////////////
                } else if (spinnerValue.equals("QUE")) {
                    ////////////////////////////ASSIGNING HOTEL NAMES FROM THE CSV DATABASE TO THE SPINNER////////////////////////////
                    List<String> hotelnames = new ArrayList<String>();
                    helper = new DatabaseHelper(getApplicationContext());
                    InputStream is = getResources().openRawResource(R.raw.hotel_details);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                    String line = "";


                    try {
                        while ((line = reader.readLine()) != null) {

                            String[] tokens = line.split(",");
                            if (tokens[0] != null && tokens[1] != null && tokens[2] != null && tokens[3] != null && tokens[0].equals("QUE")) {
                                hotelnames.add(tokens[1]);
                            }


                        }
                    } catch (IOException e) {
                        Log.wtf("MyActivity", "Error reading data file on line " + line, e);
                        e.printStackTrace();
                    }

                    String[] namearray = new String[hotelnames.size()];

                    for (int a = 0; a < hotelnames.size(); a++) {
                        namearray[a] = hotelnames.get(a);
                    }
                    helper.close();

                    //ADAPTER FOR HOTEL NAMES SPINNER
                    ArrayAdapter<String> hotelAdapter = new ArrayAdapter<String>(Hotels.this, android.R.layout.simple_expandable_list_item_1, namearray);
                    hotelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    hotelspinner.setAdapter(hotelAdapter);
                    ////////////////////////////ASSIGNING HOTEL NAMES FROM THE CSV DATABASE TO THE SPINNER////////////////////////////
                } else if (spinnerValue.equals("SIA")) {
                    ////////////////////////////ASSIGNING HOTEL NAMES FROM THE CSV DATABASE TO THE SPINNER////////////////////////////
                    List<String> hotelnames = new ArrayList<String>();
                    helper = new DatabaseHelper(getApplicationContext());
                    InputStream is = getResources().openRawResource(R.raw.hotel_details);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                    String line = "";


                    try {
                        while ((line = reader.readLine()) != null) {

                            String[] tokens = line.split(",");
                            if (tokens[0] != null && tokens[1] != null && tokens[2] != null && tokens[3] != null && tokens[0].equals("SIA")) {
                                hotelnames.add(tokens[1]);
                            }


                        }
                    } catch (IOException e) {
                        Log.wtf("MyActivity", "Error reading data file on line " + line, e);
                        e.printStackTrace();
                    }

                    String[] namearray = new String[hotelnames.size()];

                    for (int a = 0; a < hotelnames.size(); a++) {
                        namearray[a] = hotelnames.get(a);
                    }
                    helper.close();

                    //ADAPTER FOR HOTEL NAMES SPINNER
                    ArrayAdapter<String> hotelAdapter = new ArrayAdapter<String>(Hotels.this, android.R.layout.simple_expandable_list_item_1, namearray);
                    hotelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    hotelspinner.setAdapter(hotelAdapter);
                    ////////////////////////////ASSIGNING HOTEL NAMES FROM THE CSV DATABASE TO THE SPINNER////////////////////////////
                } else if (spinnerValue.equals("SUK")) {
                    ////////////////////////////ASSIGNING HOTEL NAMES FROM THE CSV DATABASE TO THE SPINNER////////////////////////////
                    List<String> hotelnames = new ArrayList<String>();
                    helper = new DatabaseHelper(getApplicationContext());
                    InputStream is = getResources().openRawResource(R.raw.hotel_details);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                    String line = "";


                    try {
                        while ((line = reader.readLine()) != null) {

                            String[] tokens = line.split(",");
                            if (tokens[0] != null && tokens[1] != null && tokens[2] != null && tokens[3] != null && tokens[0].equals("SUK")) {
                                hotelnames.add(tokens[1]);
                            }


                        }
                    } catch (IOException e) {
                        Log.wtf("MyActivity", "Error reading data file on line " + line, e);
                        e.printStackTrace();
                    }

                    String[] namearray = new String[hotelnames.size()];

                    for (int a = 0; a < hotelnames.size(); a++) {
                        namearray[a] = hotelnames.get(a);
                    }
                    helper.close();

                    //ADAPTER FOR HOTEL NAMES SPINNER
                    ArrayAdapter<String> hotelAdapter = new ArrayAdapter<String>(Hotels.this, android.R.layout.simple_expandable_list_item_1, namearray);
                    hotelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    hotelspinner.setAdapter(hotelAdapter);
                    ////////////////////////////ASSIGNING HOTEL NAMES FROM THE CSV DATABASE TO THE SPINNER////////////////////////////
                } else if (spinnerValue.equals("MUL")) {
                    ////////////////////////////ASSIGNING HOTEL NAMES FROM THE CSV DATABASE TO THE SPINNER////////////////////////////
                    List<String> hotelnames = new ArrayList<String>();
                    helper = new DatabaseHelper(getApplicationContext());
                    InputStream is = getResources().openRawResource(R.raw.hotel_details);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                    String line = "";


                    try {
                        while ((line = reader.readLine()) != null) {

                            String[] tokens = line.split(",");
                            if (tokens[0] != null && tokens[1] != null && tokens[2] != null && tokens[3] != null && tokens[0].equals("MUL")) {
                                hotelnames.add(tokens[1]);
                            }


                        }
                    } catch (IOException e) {
                        Log.wtf("MyActivity", "Error reading data file on line " + line, e);
                        e.printStackTrace();
                    }

                    String[] namearray = new String[hotelnames.size()];

                    for (int a = 0; a < hotelnames.size(); a++) {
                        namearray[a] = hotelnames.get(a);
                    }
                    helper.close();

                    //ADAPTER FOR HOTEL NAMES SPINNER
                    ArrayAdapter<String> hotelAdapter = new ArrayAdapter<String>(Hotels.this, android.R.layout.simple_expandable_list_item_1, namearray);
                    hotelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    hotelspinner.setAdapter(hotelAdapter);
                    ////////////////////////////ASSIGNING HOTEL NAMES FROM THE CSV DATABASE TO THE SPINNER////////////////////////////
                } else if (spinnerValue.equals("PES")) {
                    ////////////////////////////ASSIGNING HOTEL NAMES FROM THE CSV DATABASE TO THE SPINNER////////////////////////////
                    List<String> hotelnames = new ArrayList<String>();
                    helper = new DatabaseHelper(getApplicationContext());
                    InputStream is = getResources().openRawResource(R.raw.hotel_details);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                    String line = "";


                    try {
                        while ((line = reader.readLine()) != null) {

                            String[] tokens = line.split(",");
                            if (tokens[0] != null && tokens[1] != null && tokens[2] != null && tokens[3] != null && tokens[0].equals("PES")) {
                                hotelnames.add(tokens[1]);
                            }


                        }
                    } catch (IOException e) {
                        Log.wtf("MyActivity", "Error reading data file on line " + line, e);
                        e.printStackTrace();
                    }

                    String[] namearray = new String[hotelnames.size()];

                    for (int a = 0; a < hotelnames.size(); a++) {
                        namearray[a] = hotelnames.get(a);
                    }
                    helper.close();

                    //ADAPTER FOR HOTEL NAMES SPINNER
                    ArrayAdapter<String> hotelAdapter = new ArrayAdapter<String>(Hotels.this, android.R.layout.simple_expandable_list_item_1, namearray);
                    hotelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    hotelspinner.setAdapter(hotelAdapter);
                    ////////////////////////////ASSIGNING HOTEL NAMES FROM THE CSV DATABASE TO THE SPINNER////////////////////////////
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        hotelspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String spinnerValue = hotelspinner.getSelectedItem().toString();
                if (spinnerValue.equals("RAMADA PLAZA")) {
                    hotelrating.setRating(5);
                } else if (spinnerValue.equals("REHAIS INN")) {
                    hotelrating.setRating(3);
                } else if (spinnerValue.equals("MOVENPICK")) {
                    hotelrating.setRating(4);
                } else if (spinnerValue.equals("GOLKANDA")) {
                    hotelrating.setRating(4);
                } else if (spinnerValue.equals("PARK HYATT")) {
                    hotelrating.setRating(5);
                } else if (spinnerValue.equals("DANDUM")) {
                    hotelrating.setRating(3);
                } else if (spinnerValue.equals("CROWN_INN")) {
                    hotelrating.setRating(5);
                } else if (spinnerValue.equals("ROSE PALACE")) {
                    hotelrating.setRating(3);
                } else if (spinnerValue.equals("NISHAT")) {
                    hotelrating.setRating(4);
                } else if (spinnerValue.equals("HILL VIEW")) {
                    hotelrating.setRating(5);
                } else if (spinnerValue.equals("SERENA")) {
                    hotelrating.setRating(4);
                } else if (spinnerValue.equals("DIPLOMAT")) {
                    hotelrating.setRating(3);
                } else if (spinnerValue.equals("ROYALTON")) {
                    hotelrating.setRating(4);
                } else if (spinnerValue.equals("SHELTON")) {
                    hotelrating.setRating(3);
                } else if (spinnerValue.equals("MIDTOWN")) {
                    hotelrating.setRating(5);
                } else if (spinnerValue.equals("ANARKALI")) {
                    hotelrating.setRating(4);
                } else if (spinnerValue.equals("SHINING STAR")) {
                    hotelrating.setRating(3);
                } else if (spinnerValue.equals("TRAVEL INN")) {
                    hotelrating.setRating(5);
                } else if (spinnerValue.equals("GRACE TOWN")) {
                    hotelrating.setRating(5);
                } else if (spinnerValue.equals("SHEZINA INN")) {
                    hotelrating.setRating(4);
                } else if (spinnerValue.equals("HOTEL ONE")) {
                    hotelrating.setRating(3);
                } else if (spinnerValue.equals("RAYWAL")) {
                    hotelrating.setRating(3);
                } else if (spinnerValue.equals("FIEATSA INN")) {
                    hotelrating.setRating(4);
                } else if (spinnerValue.equals("DE SHALIMAR")) {
                    hotelrating.setRating(5);
                } else if (spinnerValue.equals("AMIYS")) {
                    hotelrating.setRating(4);
                } else if (spinnerValue.equals("AVION")) {
                    hotelrating.setRating(5);
                } else if (spinnerValue.equals("BLING INN")) {
                    hotelrating.setRating(3);
                } else if (spinnerValue.equals("SAFE SQUARE")) {
                    hotelrating.setRating(3);
                } else if (spinnerValue.equals("EMARAAT")) {
                    hotelrating.setRating(4);
                } else if (spinnerValue.equals("HOTEL GRAND")) {
                    hotelrating.setRating(5);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




    }


}


   /*public void loadHotelSpinnerData() {
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////LOADING HOTEL NAME SPINNER DATA///////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // database handler
        /*DatabaseHelper db = new DatabaseHelper(getApplicationContext());

        // Spinner Drop down elements
        List<String> lables = db.getAllLabels();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        hotelspinner.setAdapter(dataAdapter);*/
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////LOADING HOTEL NAME SPINNER DATA///////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////