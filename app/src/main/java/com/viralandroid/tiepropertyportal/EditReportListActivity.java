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
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by yellowsoft on 13/7/17.
 */

public class EditReportListActivity extends Activity {
    EditText task,pending,tomorrow,achievement;
    ImageView back_btn;
    TextView update_btn;
    int ASK_MULTIPLE_PERMISSION_REQUEST_CODE;
    ArrayList<ReportsList> reportsListsfrom_api;
    String id;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_report_list);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        task = (EditText) findViewById(R.id.task);
        pending = (EditText) findViewById(R.id.pending);
        tomorrow = (EditText) findViewById(R.id.tomorrow);
        achievement = (EditText) findViewById(R.id.achievement);
        update_btn = (TextView) findViewById(R.id.update_btn);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        reportsListsfrom_api = new ArrayList<>();

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditReportListActivity.this.onBackPressed();
            }
        });


        if (getIntent()!=null && getIntent().hasExtra("id")){
            id = getIntent().getStringExtra("id");
        }

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String task_string = task.getText().toString();
                String pending_string = pending.getText().toString();
                String tomorrow_string = tomorrow.getText().toString();
                String achievement_string = achievement.getText().toString();
                if (task_string.equals("")){
                    Toast.makeText(EditReportListActivity.this,"Please Enter Task Done",Toast.LENGTH_SHORT).show();
                    task.requestFocus();
                }else if (pending_string.equals("")){
                    Toast.makeText(EditReportListActivity.this,"Please Enter Pending Task",Toast.LENGTH_SHORT).show();
                    pending.requestFocus();
                }else if (tomorrow_string.equals("")){
                    Toast.makeText(EditReportListActivity.this,"Please Enter Task to be Done Tomorrow",Toast.LENGTH_SHORT).show();
                    tomorrow.requestFocus();
                }else if (achievement_string.equals("")){
                    Toast.makeText(EditReportListActivity.this,"Please Enter Any Achievement",Toast.LENGTH_SHORT).show();
                    achievement.requestFocus();
                }else {
                    Ion.with(getApplicationContext())
                            .load(Session.SERVER_URL+"edit-report.php")
                            .setBodyParameter("report_id",id)
                            .setBodyParameter("task",task_string)
                            .setBodyParameter("pending",pending_string)
                            .setBodyParameter("tomorrow",tomorrow_string)
                            .setBodyParameter("achievement",achievement_string)
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    if (result.get("status").getAsString().equals("Success")){
                                        Toast.makeText(getApplicationContext(),result.get("message").getAsString(),Toast.LENGTH_SHORT).show();
                                         finish();
                                        get_reports();
                                    }else {
                                        Toast.makeText(getApplicationContext(),result.get("message").getAsString(),Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        get_reports();

    }

    public void get_reports(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Ion.with(this)
                .load(Session.SERVER_URL+"reports-list.php")
                .setBodyParameter("agent_id",Session.GetUserId(getApplicationContext()))
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        if (progressDialog!=null)
                            progressDialog.dismiss();
                        try {
                            JsonObject jsonObject = result.get(0).getAsJsonObject();
                            task.setText(jsonObject.get("task").getAsString());
                            pending.setText(jsonObject.get("pending").getAsString());
                            tomorrow.setText(jsonObject.get("tomorrow").getAsString());
                            achievement.setText(jsonObject.get("achievement").getAsString());
                        }catch (Exception e1){
                            e1.printStackTrace();
                        }
                    }
                });

    }





}

