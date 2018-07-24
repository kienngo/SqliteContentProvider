package com.example.admin.myapplication.sqlite.listener;

import android.view.View;

public interface OnClickItem {
    void onClickItem(View view, int pos);
    void onLongClickItem(View view, int pos);
}
