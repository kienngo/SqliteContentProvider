package com.example.admin.myapplication.custom_content_provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class DatabaseHelper extends SQLiteOpenHelper {
    // fields for my content provider
    public static final String PROVIDER_NAME = "com.example.admin.myapplication.Birthday";
    public static final String URL = "content://" + PROVIDER_NAME + "/friends";
    public static final Uri CONTENT_URI = Uri.parse(URL);

    // fields for the database
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String BIRTHDAY = "birthday";

    public static final String DATABASE_NAME = "BirthdayReminder";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "birthTable";

    private static final String CREATE_TABLE =
            " CREATE TABLE " + TABLE_NAME +
                    " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " name TEXT NOT NULL, " +
                    " birthday TEXT NOT NULL);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
