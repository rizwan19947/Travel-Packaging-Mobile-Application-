package com.example.databaseproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class RegistrationDatabaseHelper extends SQLiteOpenHelper {


    public RegistrationDatabaseHelper(@Nullable Context context) {
        super(context, "usersDatabase.db", null, 1);
        //THIS CONSTRUCTOR CREATES A NEW DATABASE FILE IF IT IS ALREADY NOT CREATED

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
