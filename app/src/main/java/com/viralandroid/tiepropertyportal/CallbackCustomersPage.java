package com.viralandroid.tiepropertyportal;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import java.util.ArrayList;


/**
 * Created by T on 15-05-2017.
 */

public class CallbackCustomersPage extends Activity {
    ImageView add_call,back_btn;
    RecyclerView recyclerView;
    String agent_id,property_id;
    TextView date;
    TextView property;
    ArrayList<CallbackCustomers> callbackCustomersfrom_api;
    ArrayList<Properties> propertiesfrom_api;
    CallbackCustomersAdapter adapter;
    public void onCreate(Bundle savedinstanceState) {
        super.onCreate(savedinstanceState);
        setContentView(R.layout.add_callback_customer_list);
        add_call = (ImageView) findViewById(R.id.add_call);
        recyclerView = (RecyclerView) findViewById(R.id.callback_customers_list);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CallbackCustomersPage.this.onBackPressed();
            }
        });
        callbackCustomersfrom_api = new ArrayList<>();
        propertiesfrom_api = new ArrayList<>();

        if (getIntent()!=null && getIntent().hasExtra("agentId")){
            agent_id = getIntent().getStringExtra("agentId");
        }

        adapter = new CallbackCustomersAdapter(this,callbackCustomersfrom_api);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        add_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = LayoutInflater.from(getApplicationContext());
                final View form = li.inflate(R.layout.add_callback_items, null);
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CallbackCustomersPage.this);
                alertDialogBuilder.setView(form);
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                final EditText name = (EditText) form.findViewById(R.id.name);
                final EditText email = (EditText) form.findViewById(R.id.email);
                final EditText phone = (EditText) form.findViewById(R.id.phone);
                property = (TextView) form.findViewById(R.id.property);
                property.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Dialog dialog = onCreateDialogSingleChoice();
                        dialog.show();
                    }
                });
                 date = (TextView) form.findViewById(R.id.date);
                date.setOnClickListener(new View.OnClickListener() {

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(View v) {
                        final Calendar mcurrentDate=Calendar.getInstance();
                        final int mYear = mcurrentDate.get(Calendar.YEAR);
                        final int mMonth = mcurrentDate.get(Calendar.MONTH);
                        final int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog mDatePicker=new DatePickerDialog(CallbackCustomersPage.this, new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                                date.setText(selectedday +"-"+(selectedmonth+1) +"-"+selectedyear);
                            }
                        },mYear, mMonth, mDay);
                        mDatePicker.setTitle("Select date");
                        mDatePicker.show();  }
                });
                final EditText message = (EditText) form.findViewById(R.id.message);
                final ImageView close_btn = (ImageView) form.findViewById(R.id.close_btn);
                TextView submit_btn = (TextView) form.findViewById(R.id.submit_btn);
                submit_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String name_string = name.getText().toString();
                        String email_string = email.getText().toString();
                        String phone_string = phone.getText().toString();
                        String property_string = property_id;
                        String date_string =date.getText().toString();
                        String message_string = message.getText().toString();
                        if (name_string.equals("")){
                            Toast.makeText(CallbackCustomersPage.this, "Please Enter Name", Toast.LENGTH_SHORT).show();
                            name.requestFocus();
                        }else if (email_string.equals("")){
                            Toast.makeText(CallbackCustomersPage.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                            email.requestFocus();
                        }else if (phone_string.equals("")){
                            Toast.makeText(CallbackCustomersPage.this, "Please Enter Phone", Toast.LENGTH_SHORT).show();
                            phone.requestFocus();
                        }else if (property_string.equals("")){
                            Toast.makeText(CallbackCustomersPage.this, "Please Enter Property", Toast.LENGTH_SHORT).show();
                            property.requestFocus();
                        }else if (date_string.equals("")){
                            Toast.makeText(CallbackCustomersPage.this, "Please Enter Date", Toast.LENGTH_SHORT).show();
                            date.requestFocus();
                        }else if (message_string.equals("")){
                            Toast.makeText(CallbackCustomersPage.this, "Please Enter Message", Toast.LENGTH_SHORT).show();
                            message.requestFocus();
                        }else {
                            Ion.with(CallbackCustomersPage.this)
                                    .load(Session.SERVER_URL+"callback_customer.php")
                                    .setBodyParameter("name",name_string)
                                    .setBodyParameter("email",email_string)
                                    .setBodyParameter("phone",phone_string)
                                    .setBodyParameter("date",date_string)
                                    .setBodyParameter("message",message_string)
                                    .setBodyParameter("property_id",property_string)
                                    .setBodyParameter("agent_id",Session.GetUserId(CallbackCustomersPage.this))
                                    .asJsonObject()
                                    .setCallback(new FutureCallback<JsonObject>() {
                                        @Override
                                        public void onCompleted(Exception e, JsonObject result) {
                                            if (result.get("status").getAsString().equals("Success")){
                                                Toast.makeText(CallbackCustomersPage.this,result.get("message").getAsString(),Toast.LENGTH_SHORT).show();
                                                finish();
                                            }else {
                                                Toast.makeText(CallbackCustomersPage.this,result.get("message").getAsString(),Toast.LENGTH_SHORT).show();
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
        get_callback_customers();
        get_properties();
    }

    public void get_callback_customers(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Ion.with(this)
                .load(Session.SERVER_URL+"callback_customers.php")
                .setBodyParameter("agent_id",Session.GetUserId(CallbackCustomersPage.this))
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        if (progressDialog!=null)
                            progressDialog.dismiss();
                        for (int i=0;i<result.size();i++) {
                                CallbackCustomers callback = new CallbackCustomers(result.get(i).getAsJsonObject(), getApplicationContext());
                                callbackCustomersfrom_api.add(callback);
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
                                        Properties properties = new Properties(result.getResult().get(i).getAsJsonObject(), CallbackCustomersPage.this);
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

    public Dialog onCreateDialogSingleChoice() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final CharSequence[] array = new CharSequence[propertiesfrom_api.size()];
        for(int i=0;i<propertiesfrom_api.size();i++){

            array[i] = propertiesfrom_api.get(i).title +"  "+ propertiesfrom_api.get(i).prop_code;
        }
        builder.setTitle("Select City").setSingleChoiceItems(array, 0, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int i) {
                String selectedItem = array[i].toString();
                Log.e("select",selectedItem);
                property.setText(selectedItem);
                property_id = propertiesfrom_api.get(i).id;

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
