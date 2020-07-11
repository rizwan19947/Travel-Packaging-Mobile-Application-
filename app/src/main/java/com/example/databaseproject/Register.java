package com.example.databaseproject;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Register extends AppCompatActivity {
    //private int WRITE_PERMISSION_CODE = 1;

    Button nbutton;
    EditText first;
    EditText last;
    EditText email;
    EditText pass;
    DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);


        helper = new DatabaseHelper(getApplicationContext());
        nbutton = findViewById(R.id.button2);
        first = findViewById(R.id.editText5);
        last = findViewById(R.id.editText7);
        email = findViewById(R.id.editText3);
        pass = findViewById(R.id.editText4);

        nbutton.setOnClickListener(new OnClickListener() { //WAS IN THE ADDDATA METHOD FROM HERE     //ADDING NEWLY REGISTERING USERS TO THE SQLITE DATABASE
            @Override
            public void onClick(View v) {


                if (first.getText().toString().equals("") || last.getText().toString().equals("") || email.getText().toString().equals("") || pass.getText().toString().equals("")) {
                    Toast.makeText(Register.this, "Please fill the fields correctly and try again!", Toast.LENGTH_SHORT).show();

                } else {
                    insertData();
                    Intent intent3 = new Intent(Register.this, MainActivity.class);
                    startActivity(intent3);
                }



                /*if (ContextCompat.checkSelfPermission(Register.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {


                } else {
                    requestWritePermission();
                }        */


                //CHECK FOR BLANK FIELDS AND PRODUCE AN ERROR


                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
               /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {// THIS PIECE OF CODE CALLS THE FUNCTIONS OF THE ExportDatabaseCSVTask CLASS TO SAVE THE DATABASE  INTO A CSV FILE

                    new ExportDatabaseCSVTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                } else {

                    new ExportDatabaseCSVTask().execute();
                }*/
                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


            }
        });


    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //USING A NESTED CLASS TO EXPORT THE DATABASE FILE INTO A CSV

    /*public class ExportDatabaseCSVTask extends AsyncTask<String, Void, Boolean> {

        private final ProgressDialog dialog = new ProgressDialog(Register.this);
        DatabaseHelper dbhelper;

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Exporting database...");
            this.dialog.show();
            dbhelper = new DatabaseHelper(Register.this);
        }

        protected Boolean doInBackground(final String... args) {

            File exportDir = new File(Environment.getExternalStorageDirectory(), "C:\\Users\\rizwa\\Desktop/");
            if (!exportDir.exists()) {
                exportDir.mkdirs();
            }

            File file = new File(exportDir, "registeredUsers.csv");
            try {
                file.createNewFile();
                CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
                Cursor curCSV = dbhelper.raw();
                csvWrite.writeNext(curCSV.getColumnNames());
                while (curCSV.moveToNext()) {
                    String arrStr[] = null;
                    String[] mySecondStringArray = new String[curCSV.getColumnNames().length];
                    for (int i = 0; i < curCSV.getColumnNames().length; i++) {
                        mySecondStringArray[i] = curCSV.getString(i);
                    }
                    csvWrite.writeNext(mySecondStringArray);
                }
                csvWrite.close();
                curCSV.close();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        protected void onPostExecute(final Boolean success) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
            if (success) {
                Toast.makeText(Register.this, "Export successful!", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(Register.this, "Export failed", Toast.LENGTH_SHORT).show();
            }
        }
    }*/

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    /*private void requestWritePermission() {


        if (ActivityCompat.shouldShowRequestPermissionRationale(Register.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            new AlertDialog.Builder(Register.this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed to register")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(Register.this,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_PERMISSION_CODE);
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
            ActivityCompat.requestPermissions(Register.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_PERMISSION_CODE);
        }*/


   /*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == WRITE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(Register.this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Register.this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }*/

   /*@Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        if(requestCode == WRITE_PERMISSION_CODE){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(Register.this, "Registration Granted ", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(Register.this, "Registration Denied", Toast.LENGTH_SHORT).show();

            }

        }
    }*/


    public void insertData() {
        if (first.getText().toString().equals("") || last.getText().toString().equals("") || email.getText().toString().equals("") || pass.getText().toString().equals("")) {
            Toast.makeText(Register.this, "Please fill the fields correctly and try again!", Toast.LENGTH_SHORT).show();
            return;
        } else {
            boolean isInserted = helper.insertData(first.getText().toString(), last.getText().toString(), email.getText().toString(), pass.getText().toString());
            if (isInserted == true) {
                Toast.makeText(Register.this, "User Registered!", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(Register.this, MainActivity.class);
                startActivity(intent2);
            } else {
                Toast.makeText(Register.this, "Registration Unsuccessful!", Toast.LENGTH_SHORT).show();
            }

        }
    }

}



