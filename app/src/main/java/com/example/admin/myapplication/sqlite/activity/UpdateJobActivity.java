package com.example.admin.myapplication.sqlite.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.admin.myapplication.R;
import com.example.admin.myapplication.sqlite.common.Contants;
import com.example.admin.myapplication.sqlite.common.Utils;
import com.example.admin.myapplication.sqlite.database.DataBaseHelper;
import com.example.admin.myapplication.sqlite.listener.Inition;
import com.example.admin.myapplication.sqlite.model.Job;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateJobActivity extends AppCompatActivity implements Inition {
    @BindView(R.id.edittext_name)
    public EditText mEditName;
    @BindView(R.id.edittext_description)
    public EditText mEditDes;
    @BindView(R.id.image_job)
    public ImageView mImageJob;

    private DataBaseHelper mDbHelper;
    private Job mJob;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        ButterKnife.bind(this);

        inItData();
    }

    @Override
    public void inItData() {
        mDbHelper = new DataBaseHelper(this);

        mJob = (Job) getIntent().getSerializableExtra(Contants.KEY_SEND_JOB);
        mEditName.setText(mJob.getmName());
        mEditDes.setText(mJob.getmDescription());
        Bitmap bmHinhDaiDien = BitmapFactory.decodeByteArray(mJob.getmImage(), 0, mJob.getmImage().length);
        mImageJob.setImageBitmap(bmHinhDaiDien);
    }

    @Override
    public void inItView() {

    }

    @OnClick(R.id.button_update_job)
    public void onClick() {
        mDbHelper.updateContact(mJob.getmId(), mEditName.getText().toString(), mEditDes.getText().toString(), Utils.getByteArrayFromImageView(mImageJob));
        Intent intent = new Intent(UpdateJobActivity.this, JobActivity.class);
        startActivity(intent);
        finish();
    }
}
