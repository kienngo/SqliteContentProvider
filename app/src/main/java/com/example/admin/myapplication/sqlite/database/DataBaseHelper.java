package com.example.admin.myapplication.sqlite.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.admin.myapplication.sqlite.model.Job;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "managerjob.db";
    private static final String CONTACTS_TABLE_NAME = "job";
    private static final String CONTACTS_COLUMN_ID = "id";
    private static final String CONTACTS_COLUMN_NAME = "name";
    private static final String CONTACTS_COLUMN_DES = "description";
    private static final String CONTACTS_COLUMN_IMAGE = "image";

    private static final String UPDATE_CONTACT = "id = ? ";
    private static final int DATABASE_VERSION = 1;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "create table job " +
                        "(id integer primary key, name text,description text,image blob)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS job");
        onCreate(sqLiteDatabase);
    }

    public boolean insertJob(String name, String description, byte[] image) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACTS_COLUMN_NAME, name);
        contentValues.put(CONTACTS_COLUMN_DES, description);
        contentValues.put(CONTACTS_COLUMN_IMAGE, image);
        sqLiteDatabase.insert(CONTACTS_TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean updateContact(Integer id, String name, String description, byte[] image) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACTS_COLUMN_NAME, name);
        contentValues.put(CONTACTS_COLUMN_DES, description);
        contentValues.put(CONTACTS_COLUMN_IMAGE, image);
        sqLiteDatabase.update(CONTACTS_TABLE_NAME, contentValues, UPDATE_CONTACT, new String[]{Integer.toString(id)});
        return true;
    }

    public Integer deleteContact(Integer id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(CONTACTS_TABLE_NAME,
                UPDATE_CONTACT,
                new String[]{Integer.toString(id)});
    }

    public List<Job> getAllJob() {
        List<Job> arrJobs = new ArrayList<Job>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("select * from job", null);
        res.moveToFirst();

        int indexId = res.getColumnIndex(CONTACTS_COLUMN_ID);
        int indexDescription = res.getColumnIndex(CONTACTS_COLUMN_DES);
        int indexName = res.getColumnIndex(CONTACTS_COLUMN_NAME);
        int indexImage = res.getColumnIndex(CONTACTS_COLUMN_IMAGE);

        while (res.isAfterLast() == false) {
            int id = res.getInt(indexId);
            String name = res.getString(indexName);
            String des = res.getString(indexDescription);
            byte[] image = res.getBlob(indexImage);

            Job job = new Job(id, name, des, image);
            arrJobs.add(job);
            res.moveToNext();
        }

        return arrJobs;
    }
}
