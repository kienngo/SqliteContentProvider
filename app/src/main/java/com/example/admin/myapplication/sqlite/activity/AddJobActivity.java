package com.example.admin.myapplication.sqlite.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.admin.myapplication.R;
import com.example.admin.myapplication.sqlite.common.Contants;
import com.example.admin.myapplication.sqlite.common.Utils;
import com.example.admin.myapplication.sqlite.database.DataBaseHelper;

import java.io.FileNotFoundException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddJobActivity extends AppCompatActivity {
    @BindView(R.id.edittext_name)
    public EditText mEditName;
    @BindView(R.id.edittext_description)
    public EditText mEditDes;
    @BindView(R.id.image_job)
    public ImageView mImageJob;

    private DataBaseHelper mDbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addjob);
        ButterKnife.bind(this);
        mDbHelper = new DataBaseHelper(this);
    }

    @OnClick({R.id.button_camera, R.id.button_library, R.id.button_add_job})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_add_job:
                mDbHelper.insertJob(mEditName.getText().toString(), mEditDes.getText().toString(),
                        Utils.getByteArrayFromImageView(mImageJob));
                Intent intent = new Intent(this, JobActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.button_camera:
                Intent intentC = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intentC, Contants.RESQUEST_TAKE_PHOTO);
                break;
            case R.id.button_library:
                Intent intentL = new Intent(Intent.ACTION_PICK);
                intentL.setType("image/*");
                startActivityForResult(intentL, Contants.REQUEST_CHOOSE_PHOTO);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == Contants.REQUEST_CHOOSE_PHOTO) {
                try {
                    Uri imageUri = data.getData();
                    InputStream is = getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    mImageJob.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == Contants.RESQUEST_TAKE_PHOTO) {
                Bitmap bitmap = (Bitmap) data.getExtras().get(Contants.DATA);
                mImageJob.setImageBitmap(bitmap);
            }
        }
    }
}
