package com.example.admin.myapplication.content_provider;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.myapplication.R;

import java.util.List;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.MyViewHolder> {
    private Context mContext;
    private List<Contact> mLstContacts;

    public ContentAdapter(Context context, List<Contact> lstContacts) {
        this.mContext = context;
        this.mLstContacts = lstContacts;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_contacts, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Contact contact = mLstContacts.get(position);
        holder.mTextId.setText(String.valueOf(contact.getmId()));
        holder.mTextName.setText(contact.getmName());
        holder.mTextPhone.setText(contact.getmNumberPhone());
    }

    @Override
    public int getItemCount() {
        return mLstContacts != null ? mLstContacts.size() : Config.INIT_SIZE;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextId;
        private TextView mTextName;
        private TextView mTextPhone;

        public MyViewHolder(View itemView) {
            super(itemView);

            mTextId = (TextView) itemView.findViewById(R.id.textview_id);
            mTextName = (TextView) itemView.findViewById(R.id.textview_name);
            mTextPhone = (TextView) itemView.findViewById(R.id.textview_phone);
        }
    }
}
