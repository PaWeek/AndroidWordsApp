package com.example.paweek.projectjavapwr.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "projectjavapwr.db";
    public static final int DATABASE_VERSION = 1;

    public static final String CREATE_TABLE_USERS =
            "CREATE TABLE " + UsersTable.TABLE_NAME + " ("
            + UsersTable._ID + " INTEGER PRIMARY KEY,"
            + UsersTable.EMAIL + " TEXT,"
            + UsersTable.PASSWORD + " TEXT)";

    public static final String CREATE_TABLE_MODULES =
            "CREATE TABLE " + ModulesTable.TABLE_NAME + " ("
                    + ModulesTable._ID + " INTEGER PRIMARY KEY,"
                    + ModulesTable.USER_ID + " INTEGER,"
                    + ModulesTable.NAME + " TEXT)";

    public static final String CREATE_TABLE_TERMS =
            "CREATE TABLE " + TermsTable.TABLE_NAME + " ("
                    + TermsTable._ID + " INTEGER PRIMARY KEY,"
                    + TermsTable.MODULES_ID + " INTEGER,"
                    + TermsTable.SENTENCE + " TEXT,"
                    + TermsTable.TRANSLATION + " TEXT)";

    public static final String DROP_TABLE_USERS =
            "DROP TABLE IF EXISTS " + UsersTable.TABLE_NAME;

    public static final String DROP_TABLE_MODULES =
            "DROP TABLE IF EXISTS " + ModulesTable.TABLE_NAME;

    public static final String DROP_TABLE_TERMS =
            "DROP TABLE IF EXISTS " + TermsTable.TABLE_NAME;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_MODULES);
        db.execSQL(CREATE_TABLE_TERMS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_USERS);
        db.execSQL(DROP_TABLE_MODULES);
        db.execSQL(DROP_TABLE_TERMS);
        onCreate(db);
    }
}
