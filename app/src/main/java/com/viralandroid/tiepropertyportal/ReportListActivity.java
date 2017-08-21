package com.viralandroid.tiepropertyportal;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by yellowsoft on 13/7/17.
 */

public class ReportListActivity extends Activity {
    ImageView back_btn,add_report;
    RecyclerView recyclerView;
    ReportListAdapter adapter;
    ArrayList<ReportsList> reportsListsfrom_api;
    int ASK_MULTIPLE_PERMISSION_REQUEST_CODE;
    LinearLayout progress_holder;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reportlist_list);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        add_report = (ImageView) findViewById(R.id.add_report);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReportListActivity.this.onBackPressed();
            }
        });

        progress_holder = (LinearLayout) findViewById(R.id.progress_holder);
        progress_holder.setVisibility(View.GONE);
        recyclerView = (RecyclerView) findViewById(R.id.reports_list);
        reportsListsfrom_api = new ArrayList<>();

        adapter = new ReportListAdapter(this,reportsListsfrom_api);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        add_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = LayoutInflater.from(getApplicationContext());
                View form = li.inflate(R.layout.add_report_items, null);
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ReportListActivity.this);
                alertDialogBuilder.setView(form);
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                final EditText task = (EditText) form.findViewById(R.id.task);
                final EditText pending = (EditText) form.findViewById(R.id.pending);
                final EditText tomorrow = (EditText) form.findViewById(R.id.tomorrow);
                final EditText achievement = (EditText) form.findViewById(R.id.achievement);
                TextView submit_btn = (TextView) form.findViewById(R.id.submit_btn);
                ImageView close_btn = (ImageView) form.findViewById(R.id.close_btn);


                submit_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String task_string = task.getText().toString();
                        String pending_string = pending.getText().toString();
                        String tomorrow_string = tomorrow.getText().toString();
                        String achievement_string = achievement.getText().toString();
                        if (task_string.equals("")){
                            Toast.makeText(ReportListActivity.this,"Please Enter Task Done",Toast.LENGTH_SHORT).show();
                            task.requestFocus();
                        }else if (pending_string.equals("")){
                            Toast.makeText(ReportListActivity.this,"Please Enter Pending Task",Toast.LENGTH_SHORT).show();
                            pending.requestFocus();
                        }else if (tomorrow_string.equals("")){
                            Toast.makeText(ReportListActivity.this,"Please Enter Task to be Done Tomorrow",Toast.LENGTH_SHORT).show();
                            tomorrow.requestFocus();
                        }else if (achievement_string.equals("")){
                            Toast.makeText(ReportListActivity.this,"Please Enter Any Achievement",Toast.LENGTH_SHORT).show();
                            achievement.requestFocus();
                        }

                        else {
                            show_progress();
                            Ion.with(getApplicationContext())
                                    .load(Session.SERVER_URL+"add-report.php")
                                    .setBodyParameter("agent_id",Session.GetUserId(ReportListActivity.this))
                                    .setBodyParameter("task",task_string)
                                    .setBodyParameter("pending",pending_string)
                                    .setBodyParameter("tomorrow",tomorrow_string)
                                    .setBodyParameter("achievement",achievement_string)
                                    .asJsonObject()
                                    .setCallback(new FutureCallback<JsonObject>() {
                                        @Override
                                        public void onCompleted(Exception e, JsonObject result) {
                                            hide_progress();
                                            try {
                                                if (result.get("status").getAsString().equals("Success")) {
                                                    Toast.makeText(getApplicationContext(), result.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                                                    ReportListActivity.this.onBackPressed();
                                                } else {
                                                    Toast.makeText(getApplicationContext(), result.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                                                }
                                            }catch (Exception e1){
                                                e1.printStackTrace();
                                            }
                                        }
                                    });
                        }
                    }
                });

                final AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                close_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });





            }
        });

        get_reports();




    }

    public void show_progress(){
        progress_holder.setVisibility(View.VISIBLE);
    }

    public void hide_progress(){
        progress_holder.setVisibility(View.GONE);
    }

    public void get_reports(){
        show_progress();
        Ion.with(this)
                .load(Session.SERVER_URL+"reports-list.php")
                .setBodyParameter("agent_id",Session.GetUserId(getApplicationContext()))
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        hide_progress();
                        for (int i=0;i<result.size();i++){
                            ReportsList reportsList = new ReportsList(result.get(i).getAsJsonObject(),getApplicationContext());
                            reportsListsfrom_api.add(reportsList);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });

    }

}

