package com.example.admin.myapplication.sqlite.common;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class Utils {
    // convert byte image
    private static final int QUALITY = 100;

    public static byte[] getByteArrayFromImageView(ImageView imgv) {

        BitmapDrawable drawable = (BitmapDrawable) imgv.getDrawable();
        Bitmap bmp = drawable.getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, QUALITY, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
}
