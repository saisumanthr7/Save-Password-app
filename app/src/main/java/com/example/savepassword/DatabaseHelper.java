package com.example.savepassword;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, "Login.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table user(Username name, email text primary key, password text, mobilenumber number, country text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists user");


    }

    //    inserting in database
    public boolean insert(String email, String password, String Username, int mobileno, String country) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", Username);
        contentValues.put("email", email);
        contentValues.put("password", password);
        contentValues.put("mobile_number", mobileno);
        contentValues.put("country", country);
        long ins = db.insert("user", null, contentValues);
        if (ins == -1) {
            return false;
        } else {
            return true;
        }
    }

    //    Check if email exists
    public Boolean checkEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where email=?", new String[]{email});

        if (cursor.getCount() > 0) {
            return false;
        } else {
            return true;
        }
    }

    //    Checking the email and password while login
    public Boolean checkemailpassword(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where email=? and password=?", new String[]{email, password});

        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;

        }
    }


}
