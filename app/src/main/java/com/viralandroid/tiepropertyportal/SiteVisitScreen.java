package com.viralandroid.tiepropertyportal;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import java.util.ArrayList;

/**
 * Created by T on 16-05-2017.
 */

public class SiteVisitScreen extends Activity{
    ImageView back_btn,add_visit;
    ListView listView;
    TextView time;
    SiteVisitAdapter adapter;
    ArrayList<SiteVisits> siteVisitsfrom_api;
    ArrayList<Properties> propertiesfrom_api;
    TextView property;
    String prop_id;
    @Override
    public void onCreate(Bundle savedInstanceState){
      super.onCreate(savedInstanceState);
        setContentView(R.layout.site_visit_list);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SiteVisitScreen.this.onBackPressed();
            }
        });
        siteVisitsfrom_api = new ArrayList<>();
        propertiesfrom_api = new ArrayList<>();
        listView = (ListView) findViewById(R.id.visit_list);
        adapter = new SiteVisitAdapter(this,siteVisitsfrom_api);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        add_visit = (ImageView) findViewById(R.id.add_visit);
        add_visit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = LayoutInflater.from(getApplicationContext());
                View form = li.inflate(R.layout.add_site_visit_items, null);
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SiteVisitScreen.this);
                alertDialogBuilder.setView(form);
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                final EditText name = (EditText) form.findViewById(R.id.name);
                final EditText phone = (EditText) form.findViewById(R.id.phone);
                final EditText address = (EditText) form.findViewById(R.id.address);
                final TextView date = (TextView) form.findViewById(R.id.date);
                time = (TextView) form.findViewById(R.id.time);
                time.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(View view) {
//                        Dialog dialog = onCreateDialogSingleChoice();
//                        dialog.show();
                        Calendar mcurrentTime = Calendar.getInstance();
                        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                        int minute = mcurrentTime.get(Calendar.MINUTE);
                        TimePickerDialog mTimePicker;
                        mTimePicker = new TimePickerDialog(SiteVisitScreen.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                boolean isPM = (selectedHour >= 12);
                                time.setText(String.format("%02d:%02d %s", (selectedHour == 12 || selectedHour == 0) ? 12 : selectedHour % 12, selectedMinute, isPM ? "PM" : "AM"));
                            }

                        }, hour, minute, true);//Yes 24 hour time
                        mTimePicker.setTitle("Select Time");
                        mTimePicker.show();

                    }
                });
                date.setOnClickListener(new View.OnClickListener() {

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(View v) {
                        final Calendar mcurrentDate=Calendar.getInstance();
                        final int mYear = mcurrentDate.get(Calendar.YEAR);
                        final int mMonth = mcurrentDate.get(Calendar.MONTH);
                        final int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog mDatePicker=new DatePickerDialog(SiteVisitScreen.this, new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                                date.setText(selectedday +"-"+(selectedmonth+1) +"-"+selectedyear);
                            }
                        },mYear, mMonth, mDay);
                        mDatePicker.setTitle("Select date");
                        mDatePicker.show();  }
                });
                property = (TextView) form.findViewById(R.id.property);
                property.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Dialog dialog = onCreateDialogSingleChoiceProperties();
                        dialog.show();
                    }
                });
                TextView submit_btn = (TextView) form.findViewById(R.id.submit_btn);
                ImageView close_btn = (ImageView) form.findViewById(R.id.close_btn);
                submit_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String name_string = name.getText().toString();
                        String phone_string = phone.getText().toString();
                        String address_string = address.getText().toString();
                        String date_string = date.getText().toString();
                        String time_string = time.getText().toString();
                        String property_string = prop_id;
                        if (name_string.equals("")){
                            Toast.makeText(getApplicationContext(),"Please Enter Name",Toast.LENGTH_SHORT).show();
                            name.requestFocus();
                        }else if (phone_string.equals("")){
                            Toast.makeText(getApplicationContext(),"Please Enter Phone",Toast.LENGTH_SHORT).show();
                            phone.requestFocus();
                        }else if (address_string.equals("")){
                            Toast.makeText(getApplicationContext(),"Please Enter Address",Toast.LENGTH_SHORT).show();
                            address.requestFocus();
                        }else if (date_string.equals("")){
                            Toast.makeText(getApplicationContext(),"Please Enter Date",Toast.LENGTH_SHORT).show();
                            date.requestFocus();
                        }else if (time_string.equals("")){
                            Toast.makeText(getApplicationContext(),"Please Enter Time",Toast.LENGTH_SHORT).show();
                            time.requestFocus();
                        }else if (property_string.equals("")){
                            Toast.makeText(getApplicationContext(),"Please Enter Property",Toast.LENGTH_SHORT).show();
                            property.requestFocus();
                        }else {
                            Ion.with(getApplicationContext())
                                    .load(Session.SERVER_URL+"site-visit.php")
                                    .setBodyParameter("agent_id",Session.GetUserId(getApplicationContext()))
                                    .setBodyParameter("name",name_string)
                                    .setBodyParameter("phone",phone_string)
                                    .setBodyParameter("address",address_string)
                                    .setBodyParameter("date",date_string)
                                    .setBodyParameter("time",time_string)
                                    .setBodyParameter("prop_id",property_string)
                                    .asJsonObject()
                                    .setCallback(new FutureCallback<JsonObject>() {
                                        @Override
                                        public void onCompleted(Exception e, JsonObject result) {
                                            if (result.get("status").getAsString().equals("Success")){
                                                Toast.makeText(getApplicationContext(),result.get("message").getAsString(),Toast.LENGTH_SHORT).show();
                                                SiteVisitScreen.this.onBackPressed();
                                            }else {
                                                Toast.makeText(getApplicationContext(),result.get("message").getAsString(),Toast.LENGTH_SHORT).show();

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

        get_site_visits();
        get_properties();

    }

    public Dialog onCreateDialogSingleChoice() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final CharSequence[] array = {"06:00 AM", "06:30 AM", "07:00 AM","07:30 AM","08:00 AM","08:30 AM",
                "09:00 AM","09:30 AM","10:00 AM","10:30 AM","11:00 AM","11:30 AM","12:00 PM","12:30 PM",
                "01:00 PM","01:30 PM","02:00 PM","02:30 PM","03:00 PM","03:30 PM","04:00 PM","04:30 PM","05:00 PM",
                "05:30 PM","06:00 PM","06:30 PM","07:00 PM","07:30 PM","08:00 PM","08:30 PM","09:00 PM"};


        builder.setTitle("Select Time")
                .setSingleChoiceItems(array, 0, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       String selectedItem = array[which].toString();
                        Log.e("select",selectedItem);
                        time.setText(selectedItem);

                    }
                })

                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        return builder.create();
    }


    public void get_site_visits(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Ion.with(this)
                .load(Session.SERVER_URL+"site-visits.php")
                .setBodyParameter("agent_id",Session.GetUserId(this))
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        if (progressDialog!=null)
                            progressDialog.dismiss();
                        for (int i=0;i<result.size();i++){
                            SiteVisits siteVisits = new SiteVisits(result.get(i).getAsJsonObject(),SiteVisitScreen.this);
                            siteVisitsfrom_api.add(siteVisits);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }



    public void get_properties(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Ion.with(this)
                .load(Session.SERVER_URL+"properties.php")
                .asJsonArray()
                .withResponse()
                .setCallback(new FutureCallback<Response<JsonArray>>() {
                    @Override
                    public void onCompleted(Exception e, Response<JsonArray> result) {
                        try {
                            if (progressDialog != null)
                                progressDialog.dismiss();
                            Log.e("response", result.getServedFrom().toString());


                            if (e != null) {
                                e.printStackTrace();
                                Log.e("error", e.getLocalizedMessage());

                            } else
                                try {
                                    for (int i = 0; i < result.getResult().size(); i++) {
                                        Properties properties = new Properties(result.getResult().get(i).getAsJsonObject(), SiteVisitScreen.this);
                                        propertiesfrom_api.add(properties);
                                    }
                                    adapter.notifyDataSetChanged();

                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }
                        }catch (Exception e1){
                            e1.printStackTrace();
                        }


                    }
                });
    }

    public Dialog onCreateDialogSingleChoiceProperties() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final CharSequence[] array = new CharSequence[propertiesfrom_api.size()];
        for(int i=0;i<propertiesfrom_api.size();i++){

            array[i] = propertiesfrom_api.get(i).title+"  "+propertiesfrom_api.get(i).prop_code;
        }
        builder.setTitle("Select City").setSingleChoiceItems(array, 0, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int i) {
                String selectedItem = array[i].toString();
                Log.e("select",selectedItem);
                property.setText(selectedItem);
                prop_id = propertiesfrom_api.get(i).id;

            }
        })

                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        return builder.create();
    }




}
