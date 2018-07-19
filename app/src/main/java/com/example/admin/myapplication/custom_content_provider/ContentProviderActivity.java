package com.example.admin.myapplication.custom_content_provider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContentProviderActivity extends AppCompatActivity {
    private static final String URL = "content://com.example.admin.myapplication.Birthday/friends";
    private static final String RESULT_NOT_CONTENT = "no content yet!";
    private static final String WITH_ID = " with id ";
    private static final String HAS_BIRTHDAY = " has birthday: ";

    @BindView(R.id.edittext_name)
    public EditText mEditName;
    @BindView(R.id.edittext_birthday)
    public EditText mEditBrithday;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_provider);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.button_add_birth, R.id.button_show_birth, R.id.button_delete_birth})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_add_birth:
                addBirthday(mEditName.getText().toString(), mEditBrithday.getText().toString());
                break;
            case R.id.button_delete_birth:
                deleteAllBirthdays();
                break;
            case R.id.button_show_birth:
                showAllBirthdays();
                break;
        }
    }

    public void deleteAllBirthdays() {
        Uri friends = Uri.parse(URL);
        int count = this.getContentResolver().delete(
                friends, null, null);
        String countNum = String.valueOf(count);
        Toast.makeText(this,
                countNum, Toast.LENGTH_LONG).show();

    }

    public void addBirthday(String name, String birthday) {
        // Add a new birthday record
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.NAME, name);

        values.put(DatabaseHelper.BIRTHDAY, birthday);

        Uri uri = getContentResolver().insert(DatabaseHelper.CONTENT_URI, values);

        Toast.makeText(this, uri.toString(), Toast.LENGTH_LONG).show();
    }


    public void showAllBirthdays() {
        Uri friends = Uri.parse(URL);
        Cursor c = getContentResolver().query(friends, null, null, null, DatabaseHelper.NAME);
        String result = "Javacodegeeks Results:";

        if (!c.moveToFirst()) {
            Toast.makeText(this, result + RESULT_NOT_CONTENT, Toast.LENGTH_LONG).show();
        } else {
            do {
                result = result + "\n" + c.getString(c.getColumnIndex(DatabaseHelper.NAME)) +
                        WITH_ID + c.getString(c.getColumnIndex(DatabaseHelper.ID)) +
                        HAS_BIRTHDAY + c.getString(c.getColumnIndex(DatabaseHelper.BIRTHDAY));
            } while (c.moveToNext());
            Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        }

    }
}
