package com.example.databaseproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Customer.db";
    public static final String first_table = "customer_table";
    public static final String COL_0 = "C_NO";
    public static final String COL_1 = "FIRST_NAME";
    public static final String COL_2 = "SECOND_NAME";
    public static final String COL_3 = "EMAIL";
    public static final String COL_4 = "PASSWORD";
    public static final String second_table = "cities_table";
    public static final String COLUMN_0 = "City";
    public static final String COLUMN_1 = "City_ID";
    public static final String third_table = "hotelDetail_table";
    public static final String detail_col1 = "CITY_CODE";
    public static final String detail_col2 = "HOTEL_NAME";
    public static final String detail_col3 = "PRICE";
    public static final String detail_col4 = "HOTEL_TYPE";
    public static final String fifth_table = "flightdetails_table";
    public static final String COLUM_0 = "FLIGHT_ID";
    public static final String COLUM_1 = "FLIGHT_DEP_DATE";
    public static final String  COLUM_2 = "PRICE";
    public static final String sixth_table = "flights_table";
    public static final String COLU_0 = "FLIGHT_ID";
    public static final String COLU_1 = "AIRLINE_NAME";
    public static final String COLU_2 = "DEPART_TIME";
    public static final String COLU_3 = "ARR_TIME";
    public static final String COLU_4 = "DEP_CITY";
    public static final String COLU_5 = "ARR_CITY";

    public static final String CREATE_TABLE_FLIGHTS= "CREATE TABLE IF NOT EXISTS "+sixth_table+" ("
            +COLU_0 +" TEXT primary key , "
            +COLU_1 +" TEXT, "
            +COLU_2 +" TEXT, "
            +COLU_3 +" TEXT, "
            +COLU_4 +" TEXT,"
            +COLU_5 +" TEXT  "
            +");";

    public boolean insertFlights(String flight_id, String airline_name, String depart_time, String arr_time, String dep_city, String arr_city) {

        SQLiteDatabase database = this.getWritableDatabase();
        database.beginTransaction();
        ContentValues contentvalues = new ContentValues();
        contentvalues.put(COLU_0, flight_id);
        contentvalues.put(COLU_1, airline_name);
        contentvalues.put(COLU_2, depart_time);
        contentvalues.put(COLU_3, arr_time);
        contentvalues.put(COLU_4, dep_city);
        contentvalues.put(COLU_5, arr_city);

        long result= database.insert(sixth_table, null, contentvalues);

        database.setTransactionSuccessful();
        database.endTransaction();
        if(result==-1){
            return false;

        }
        else return true;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.disableWriteAheadLogging();
        db.execSQL("CREATE TABLE " + first_table + " (C_NO INTEGER PRIMARY KEY AUTOINCREMENT, FIRST_NAME TEXT, SECOND_NAME TEXT, EMAIL TEXT, PASSWORD TEXT)");
        db.execSQL("CREATE TABLE " + second_table + " (CITY TEXT, CITY_ID TEXT PRIMARY KEY)");
        db.execSQL("CREATE TABLE " + third_table + " (CITY_CODE TEXT, HOTEL_NAME TEXT PRIMARY KEY, PRICE TEXT, HOTEL_TYPE TEXT)");
        db.execSQL("CREATE TABLE " + fifth_table + " (FLIGHT_ID TEXT PRIMARY KEY, FLIGHT_DEP_DATE TEXT, PRICE TEXT)");
        db.execSQL(CREATE_TABLE_FLIGHTS);

    }

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);


    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        db.disableWriteAheadLogging();
        super.onOpen(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + first_table);
        db.execSQL("DROP TABLE IF EXISTS " + second_table);
        db.execSQL("DROP TABLE IF EXISTS " + third_table);
        db.execSQL("DROP TABLE IF EXISTS " + fifth_table);
        db.execSQL("DROP TABLE IF EXISTS " + sixth_table);
        onCreate(db);

    }




    public boolean insertData(String firstname, String lastname, String email, String password) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        ContentValues contentvalues = new ContentValues();
        contentvalues.put(COL_1, firstname);
        contentvalues.put(COL_2, lastname);
        contentvalues.put(COL_3, email);
        contentvalues.put(COL_4, password);
        db.insert(first_table, null, contentvalues);
        db.setTransactionSuccessful();
        db.endTransaction();
        return true;
    }


    public void insertCities(String city, String cityID) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.beginTransaction();
        ContentValues contentvalues = new ContentValues();
        contentvalues.put(COLUMN_0, city);
        contentvalues.put(COLUMN_1, cityID);


        database.insert(second_table, null, contentvalues);

        database.setTransactionSuccessful();
        database.endTransaction();
    }

    public void insertHotelDetails(String city, String hotelname, String price, String type) {

        SQLiteDatabase database = this.getWritableDatabase();
        database.beginTransaction();
        ContentValues contentValues = new ContentValues();
        contentValues.put(detail_col1, city);
        contentValues.put(detail_col2, hotelname);
        contentValues.put(detail_col3, price);
        contentValues.put(detail_col4, type);
        database.insert(third_table, null, contentValues);
        database.setTransactionSuccessful();
        database.endTransaction();

    }

    public void insertFlightDetails(String flight_id, String flight_dep_date, String price) {

        SQLiteDatabase database = this.getWritableDatabase();
        database.beginTransaction();
        ContentValues contentvalues = new ContentValues();
        contentvalues.put(COLUM_0, flight_id);
        contentvalues.put(COLUM_1, flight_dep_date);
        contentvalues.put(COLUM_2, price);

        database.insert(fifth_table, null, contentvalues);

        database.setTransactionSuccessful();
        database.endTransaction();
    }








    public boolean checkLogin(String email, String password) {

        // array of columns to fetch
        String[] columns = {
                COL_0
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COL_3 + " = ?" + " AND " + COL_4 + " = ?";

        // selection arguments
        String[] selectionArgs = {email, password};

        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(first_table, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }





    /*public List<String> getAllLabels(){

        List<String> labels = new ArrayList<String>();
        String selectQuery = "SELECT HOTEL_NAME FROM " + third_table;
        SQLiteDatabase db = this.getReadableDatabase();                 //NEED TO CHECK THIS LINE LATER IF ERRORS POP UP
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                getAllLabels().add(cursor.getString(1));
            } while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return labels;

    }*/


}