package com.example.paweek.projectjavapwr.Services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.paweek.projectjavapwr.AppStrings;
import com.example.paweek.projectjavapwr.DataBase.DbHelper;
import com.example.paweek.projectjavapwr.DataBase.UsersTable;
import com.example.paweek.projectjavapwr.Models.User;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class AuthenticationService {

    private DbHelper dbHelper;

    public AuthenticationService(Context context)
    {
        dbHelper = new DbHelper(context);
    }

    public Boolean Login(String email, String password)
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.query(UsersTable.TABLE_NAME, new String[]{UsersTable._ID, UsersTable.EMAIL, UsersTable.PASSWORD},null,
                null, null, null, null);

        while (c.moveToNext())
        {
            Log.d("SERVICE", c.getString(c.getColumnIndex(UsersTable.EMAIL))+ c.getString(c.getColumnIndex(UsersTable.PASSWORD)));

            if(c.getString(c.getColumnIndex(UsersTable.EMAIL)).equals(email) && c.getString(c.getColumnIndex(UsersTable.PASSWORD)).equals(password))
            {
                AppStrings.Token = c.getInt(c.getColumnIndex(UsersTable._ID));
                return true;
            }
        }

        return false;
    }

    public Boolean Register(String email, String password)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor c = db.query(UsersTable.TABLE_NAME, new String[]{UsersTable._ID, UsersTable.EMAIL, UsersTable.PASSWORD},null,
                null, null, null, null);

        while (c.moveToNext())
        {
            if(c.getString(c.getColumnIndex(UsersTable.EMAIL)).equals(email))
                return false;
        }

        ContentValues cv = new ContentValues();
        cv.put(UsersTable.EMAIL, email);
        cv.put(UsersTable.PASSWORD, password);

        db.insert(UsersTable.TABLE_NAME, null, cv);

        return true;
    }
}
