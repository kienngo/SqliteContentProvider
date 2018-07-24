package com.example.admin.myapplication.sqlite.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.myapplication.R;
import com.example.admin.myapplication.sqlite.listener.OnClickItem;
import com.example.admin.myapplication.sqlite.model.Job;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.MyViewHolder> implements Filterable {
    private static final int START_LENGTH = 0;
    private List<Job> mArrJob;
    private List<Job> mArrJobSearch;
    private Context mContext;
    private OnClickItem mOnClickItem;
    private ValueFilter mValueFilter;

    public JobAdapter(List<Job> arrJob, Context context, OnClickItem onClickItem) {
        this.mArrJob = arrJob;
        this.mContext = context;
        this.mOnClickItem = onClickItem;
        mArrJobSearch = arrJob;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_job, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Job job = mArrJob.get(position);
        holder.tvDes.setText(job.getmDescription());
        holder.tvName.setText(job.getmName());
        holder.tvId.setText(String.valueOf(job.getmId()));
        Bitmap bitmapAvatar = BitmapFactory.decodeByteArray(job.getmImage(), START_LENGTH, job.getmImage().length);
        holder.imJob.setImageBitmap(bitmapAvatar);
    }

    @Override
    public int getItemCount() {
        return mArrJob.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textview_des)
        public TextView tvDes;
        @BindView(R.id.textview_name)
        public TextView tvName;
        @BindView(R.id.textview_id)
        public TextView tvId;
        @BindView(R.id.image_job)
        public ImageView imJob;
        @BindView(R.id.ln_item_job)
        public LinearLayout lnItemJob;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            lnItemJob.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mOnClickItem.onLongClickItem(lnItemJob, getLayoutPosition());
                    return false;
                }
            });

            lnItemJob.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnClickItem.onClickItem(lnItemJob, getLayoutPosition());
                }
            });
        }
    }

    @Override
    public Filter getFilter() {
        if (mValueFilter == null) {
            mValueFilter = new ValueFilter();
        }
        return mValueFilter;
    }

    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > START_LENGTH) {
                ArrayList<Job> arr = new ArrayList<>();
                for (int i = START_LENGTH; i < mArrJobSearch.size(); i++) {
                    if ((mArrJobSearch.get(i).getmName().toUpperCase().contains(constraint.toString().toUpperCase()))) {
                        arr.add(mArrJobSearch.get(i));
                    }
                }
                results.count = arr.size();
                results.values = arr;
            } else {
                results.count = mArrJobSearch.size();
                results.values = mArrJobSearch;
            }
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            mArrJob = (List<Job>) results.values;
            notifyDataSetChanged();
        }

    }
}
