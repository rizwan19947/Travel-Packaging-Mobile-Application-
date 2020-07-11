package com.example.databaseproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UserPackage extends AppCompatActivity {


    String flight = "";
    String hotel = "";
    String departureDate = "";
    String origin = "";
    String destination = "";
    String noPeople = "";
    double totalAmount = 0;
    String flightclass = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.packages);

        Button finish = findViewById(R.id.button3);



        final TextView flightName = findViewById(R.id.textView13);
        final TextView hotelName = findViewById(R.id.textView25);
        final TextView originCity = findViewById(R.id.textView21);
        final TextView destinationCity = findViewById(R.id.textView17);
        final TextView departureTime = findViewById(R.id.textView19);

        final TextView totalAmountView = findViewById(R.id.textView27);
        Button generate = findViewById(R.id.button7);

        flight = getIntent().getStringExtra("key3");
        hotel = getIntent().getStringExtra("key7");
        departureDate = getIntent().getStringExtra("key2");
        origin = getIntent().getStringExtra("key4");
        destination = getIntent().getStringExtra("key");
        noPeople = getIntent().getStringExtra("key1");
        flightclass = getIntent().getStringExtra("key5");
        Bundle b = getIntent().getExtras();
        totalAmount = b.getDouble("key6");



        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //flightName.setText(flight);
                hotelName.setText(hotel);
                originCity.setText(origin);
                destinationCity.setText(destination);
                departureTime.setText(departureDate);
                //totalAmountView.setText(Double.toString(totalAmount));

            }
        });

        if(flightclass.equals("Economy Class")){
            totalAmount = totalAmount * 1;
        }
        else if(flightclass.equals("Business Class")){
            totalAmount = totalAmount * 1.35;

        }
        else{
            totalAmount = totalAmount * 1.8;
        }


        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UserPackage.this, "Your booking has been successful!", Toast.LENGTH_SHORT);
                Intent intent = new Intent(UserPackage.this, MainActivity.class);
                startActivity(intent);
            }
        });






    }








}
