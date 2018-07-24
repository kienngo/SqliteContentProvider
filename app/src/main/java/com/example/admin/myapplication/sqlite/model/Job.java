package com.example.admin.myapplication.sqlite.model;

import java.io.Serializable;

public class Job implements Serializable {
    private int mId;
    private String mName;
    private String mDescription;
    private byte[] mImage;

    public Job(int mId, String mName, String mDescription, byte[] mImage) {
        this.mId = mId;
        this.mName = mName;
        this.mDescription = mDescription;
        this.mImage = mImage;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public byte[] getmImage() {
        return mImage;
    }

    public void setmImage(byte[] mImage) {
        this.mImage = mImage;
    }
}
