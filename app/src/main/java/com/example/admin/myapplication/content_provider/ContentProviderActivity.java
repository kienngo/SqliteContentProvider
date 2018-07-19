package com.example.admin.myapplication.content_provider;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.admin.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class ContentProviderActivity extends AppCompatActivity {
    private final int REQUEST_LOCATION = 1;

    private ContentAdapter mContentAdapter;
    private List<Contact> mLstContacts;
    private RecyclerView mRecyclerContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contentprovider);

        checkPermission();
        initData();
        initView();
    }

    private void initView() {
        mRecyclerContacts = (RecyclerView) findViewById(R.id.recycler_contacts);

        mContentAdapter = new ContentAdapter(this, mLstContacts);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerContacts.setLayoutManager(layoutManager);
        mRecyclerContacts.setAdapter(mContentAdapter);
    }

    private void initData() {
        ContentProviderManager contentProviderManager = new ContentProviderManager(this);
        mLstContacts = new ArrayList<>();
        mLstContacts.addAll(contentProviderManager.getContacts());
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        REQUEST_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            ContentProviderManager contentProviderManager = new ContentProviderManager(this);
            // Permission has already been granted
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION: {
                if (grantResults.length > Config.INIT_SIZE
                        && grantResults[Config.INIT_SIZE] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    ContentProviderManager contentProviderManager = new ContentProviderManager(this);
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }
}
