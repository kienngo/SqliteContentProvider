package com.example.admin.myapplication.custom_content_provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.HashMap;

public class CustomContentProvider extends ContentProvider {
    private static final String AND = " AND (";
    private static final char OPEN_FOREVER = ')';
    private static final String FRIENDS_PATH = "friends";
    private static final String FRIENDS_PATH_ID = "friends";
    private static final String RESULT_FRENDS = "vnd.android.cursor.dir/vnd.example.friends";
    private static final String RESULT_FRENDS_ID = "vnd.android.cursor.item/vnd.example.friends";
    private static final String EMPTY = "";
    private static final String EQUALS = " = ";

    private DatabaseHelper mDatabaseHelper;
    private SQLiteDatabase mSqLiteDatabase;

    private UriMatcher mUriMatcher;
    private static final int FRIENDS = 1;
    private static final int FRIENDS_ID = 2;

    private static HashMap<String, String> mBirthday;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        mDatabaseHelper = new DatabaseHelper(context);

        mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mUriMatcher.addURI(DatabaseHelper.PROVIDER_NAME, FRIENDS_PATH, FRIENDS);
        mUriMatcher.addURI(DatabaseHelper.PROVIDER_NAME, FRIENDS_PATH_ID, FRIENDS_ID);

        mSqLiteDatabase = mDatabaseHelper.getReadableDatabase();

        if (mSqLiteDatabase == null)
            return false;
        else
            return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(DatabaseHelper.TABLE_NAME);

        switch (mUriMatcher.match(uri)) {
            case FRIENDS:
                queryBuilder.setProjectionMap(mBirthday);
                break;
            case FRIENDS_ID:
                queryBuilder.appendWhere(DatabaseHelper.ID + EQUALS + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException(String.valueOf(uri));
        }
        if (sortOrder == null || sortOrder == EMPTY) {
            sortOrder = DatabaseHelper.NAME;
        }
        Cursor cursor = queryBuilder.query(mSqLiteDatabase, projection, selection,
                selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (mUriMatcher.match(uri)) {
            case FRIENDS:
                return RESULT_FRENDS;
            case FRIENDS_ID:
                return RESULT_FRENDS_ID;
            default:
                throw new IllegalArgumentException(String.valueOf(uri));
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        long row = mSqLiteDatabase.insert(DatabaseHelper.TABLE_NAME, EMPTY, contentValues);

        if (row > 0) {
            Uri newUri = ContentUris.withAppendedId(DatabaseHelper.CONTENT_URI, row);
            getContext().getContentResolver().notifyChange(newUri, null);
            return newUri;
        }
        throw new SQLException(String.valueOf(uri));
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count = 0;

        switch (mUriMatcher.match(uri)) {
            case FRIENDS:
                count = mSqLiteDatabase.delete(DatabaseHelper.TABLE_NAME, selection, selectionArgs);
                break;
            case FRIENDS_ID:
                String id = uri.getLastPathSegment();
                count = mSqLiteDatabase.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper.ID + EQUALS + id +
                        (!TextUtils.isEmpty(selection) ? AND +
                                selection + OPEN_FOREVER : EMPTY), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException(String.valueOf(uri));
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count = 0;

        switch (mUriMatcher.match(uri)) {
            case FRIENDS:
                count = mSqLiteDatabase.update(mDatabaseHelper.TABLE_NAME, contentValues, selection, selectionArgs);
                break;
            case FRIENDS_ID:
                count = mSqLiteDatabase.update(mDatabaseHelper.TABLE_NAME, contentValues, mDatabaseHelper.ID +
                        " = " + uri.getLastPathSegment() +
                        (!TextUtils.isEmpty(selection) ? AND +
                                selection + OPEN_FOREVER : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException(String.valueOf(uri));
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
