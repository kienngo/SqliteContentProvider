package com.example.admin.myapplication.sqlite.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.admin.myapplication.R;
import com.example.admin.myapplication.sqlite.adapter.JobAdapter;
import com.example.admin.myapplication.sqlite.common.Contants;
import com.example.admin.myapplication.sqlite.database.DataBaseHelper;
import com.example.admin.myapplication.sqlite.listener.Inition;
import com.example.admin.myapplication.sqlite.listener.OnClickItem;
import com.example.admin.myapplication.sqlite.model.Job;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JobActivity extends AppCompatActivity implements Inition, SearchView.OnQueryTextListener {
    @BindView(R.id.recycler_job)
    public RecyclerView mRecyclerJob;

    private JobAdapter mJobAdapter;
    private List<Job> mArrJob;

    private DataBaseHelper mDbHelper;
    private SearchView mSearchView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        inItData();
        inItView();
    }

    @Override
    public void inItData() {
        mDbHelper = new DataBaseHelper(this);
        mArrJob = new ArrayList<>();
        mArrJob.addAll(mDbHelper.getAllJob());
    }

    @Override
    public void inItView() {
        mJobAdapter = new JobAdapter(mArrJob, this, onClickItem);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerJob.setLayoutManager(layoutManager);
        mRecyclerJob.setAdapter(mJobAdapter);
    }

    @OnClick(R.id.floatingbutton_add_job)
    public void onClick() {
        Intent intent = new Intent(this, AddJobActivity.class);
        startActivity(intent);
        finish();
    }

    private OnClickItem onClickItem = new OnClickItem() {
        @Override
        public void onClickItem(View view, final int pos) {
            Intent intent = new Intent(JobActivity.this, UpdateJobActivity.class);
            intent.putExtra(Contants.KEY_SEND_JOB, mArrJob.get(pos));
            startActivity(intent);
            finish();
        }

        @Override
        public void onLongClickItem(View view, final int pos) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(JobActivity.this);
            builder1.setMessage(getString(R.string.delete_infomation));
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    getString(R.string.delete),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            mDbHelper.deleteContact(mArrJob.get(pos).getmId());
                            mArrJob.remove(pos);
                            mJobAdapter.notifyDataSetChanged();
                            dialog.cancel();
                        }
                    });

            builder1.setNegativeButton(
                    getString(R.string.no_delete),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.job_menu, menu);
        MenuItem itemSearch = menu.findItem(R.id.search_view);
        mSearchView = (SearchView) itemSearch.getActionView();
        mSearchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mJobAdapter.getFilter().filter(newText);
        return true;
    }
}
