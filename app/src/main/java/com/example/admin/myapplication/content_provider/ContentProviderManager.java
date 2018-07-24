package com.example.admin.myapplication.content_provider;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

public class ContentProviderManager {
    private static final String[] PROJECTION = {
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.Contacts.PHONETIC_NAME,
    };
    private Cursor mCursor;
    private ContentResolver mContentResolver;
    private Context mContext;
    private List<Contact> mLstContacts;

    public ContentProviderManager(Context context) {
        this.mContext = context;
        mLstContacts = new ArrayList<>();
    }

    public List<Contact> getContacts() {
        mContentResolver = mContext.getContentResolver();
        mCursor = mContentResolver.query(ContactsContract.Contacts.CONTENT_URI, PROJECTION, null, null, null);

        if ((mCursor != null ? mCursor.getCount() : Config.INIT_SIZE) > Config.INIT_SIZE) {
            while (mCursor.moveToNext()) {
                int id = mCursor.getInt(Config.INDEX_0);
                String name = mCursor.getString(Config.INDEX_1);
                String phone = mCursor.getString(Config.INDEX_2);

                Contact contact = new Contact(id, name, phone);
                mLstContacts.add(contact);
            }
        }

        return mLstContacts;
    }
}
