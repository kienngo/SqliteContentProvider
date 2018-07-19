package com.example.admin.myapplication.content_provider;

public class Contact {
    private int mId;
    private String mName;
    private String mNumberPhone;

    public Contact(int mId, String mName, String mNumberPhone) {
        this.mId = mId;
        this.mName = mName;
        this.mNumberPhone = mNumberPhone;
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

    public String getmNumberPhone() {
        return mNumberPhone;
    }

    public void setmNumberPhone(String mNumberPhone) {
        this.mNumberPhone = mNumberPhone;
    }
}

