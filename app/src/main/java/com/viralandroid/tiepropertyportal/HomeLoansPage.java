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
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

/**
 * Created by yellowsoft on 14/6/17.
 */

public class HomeLoansPage extends Activity {
    RecyclerView recyclerView;
    HomeLoansPageAdapter adapter;
    ArrayList<HomeLoans> homeLoansfrom_api;
    ArrayList<Banks> banksfrom_api;
    ImageView back_btn,add_home_loan,edit_btn;
    TextView type,bank;
    String bank_id;
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_loan_list);

        recyclerView = (RecyclerView) findViewById(R.id.home_loan_list);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        add_home_loan = (ImageView) findViewById(R.id.add_home_loan);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeLoansPage.this.onBackPressed();
            }
        });

        homeLoansfrom_api = new ArrayList<>();
        banksfrom_api = new ArrayList<>();
        adapter = new HomeLoansPageAdapter(this,homeLoansfrom_api,banksfrom_api);

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                finish();
                startActivity(getIntent());
            }
        });



        add_home_loan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = LayoutInflater.from(getApplicationContext());
                final View form = li.inflate(R.layout.add_home_loan_page, null);
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(HomeLoansPage.this);
                alertDialogBuilder.setView(form);
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                final EditText name = (EditText) form.findViewById(R.id.name);
                final EditText address = (EditText) form.findViewById(R.id.name);
                final EditText email = (EditText) form.findViewById(R.id.email);
                final EditText phone = (EditText) form.findViewById(R.id.phone);
                final EditText property = (EditText) form.findViewById(R.id.property);
                type = (TextView) form.findViewById(R.id.type);
                bank = (TextView) form.findViewById(R.id.bank);
                final ImageView close_btn = (ImageView) form.findViewById(R.id.close_btn);
                final TextView submit_btn = (TextView) form.findViewById(R.id.submit_btn);
                type.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Dialog dialog = onSingleChoice();
                        dialog.show();
                    }
                });
                bank.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Dialog dialog = onBanksChoice();
                        dialog.show();
                    }
                });
                submit_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String name_string = name.getText().toString();
                        String address_string = address.getText().toString();
                        String email_string = email.getText().toString();
                        String phone_string = phone.getText().toString();
                        String property_string = property.getText().toString();
                        String type_string =type.getText().toString();
                        String bank_string = bank_id;
                        if (name_string.equals("")){
                            Toast.makeText(HomeLoansPage.this, "Please Enter Name", Toast.LENGTH_SHORT).show();
                            name.requestFocus();
                        }else  if (address_string.equals("")){
                            Toast.makeText(HomeLoansPage.this, "Please Enter Address", Toast.LENGTH_SHORT).show();
                            address.requestFocus();
                        }else if (email_string.equals("")){
                            Toast.makeText(HomeLoansPage.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                            email.requestFocus();
                        }else if (phone_string.equals("")){
                            Toast.makeText(HomeLoansPage.this, "Please Enter Phone", Toast.LENGTH_SHORT).show();
                            phone.requestFocus();
                        }else if (property_string.equals("")){
                            Toast.makeText(HomeLoansPage.this, "Please Enter Property", Toast.LENGTH_SHORT).show();
                            property.requestFocus();
                        }else if (type_string.equals("")){
                            Toast.makeText(HomeLoansPage.this, "Please Enter Type", Toast.LENGTH_SHORT).show();
                            type.requestFocus();
                        }else if (bank_string.equals("")){
                            Toast.makeText(HomeLoansPage.this, "Please Enter Bank", Toast.LENGTH_SHORT).show();
                            bank.requestFocus();
                        }else {
                            Ion.with(HomeLoansPage.this)
                                    .load(Session.SERVER_URL+"add-homeloan.php")
                                    .setBodyParameter("agent_id",Session.GetUserId(HomeLoansPage.this))
                                    .setBodyParameter("name",name_string)
                                    .setBodyParameter("address",address_string)
                                    .setBodyParameter("phone",phone_string)
                                    .setBodyParameter("email",email_string)
                                    .setBodyParameter("property",property_string)
                                    .setBodyParameter("type",type_string)
                                    .setBodyParameter("bank",bank_string)
                                    .asJsonObject()
                                    .setCallback(new FutureCallback<JsonObject>() {
                                        @Override
                                        public void onCompleted(Exception e, JsonObject result) {
                                            if (result.get("status").getAsString().equals("Success")){
                                                Toast.makeText(HomeLoansPage.this,result.get("message").getAsString(),Toast.LENGTH_SHORT).show();
                                                HomeLoansPage.this.onBackPressed();
                                            }else {
                                                Toast.makeText(HomeLoansPage.this,result.get("message").getAsString(),Toast.LENGTH_SHORT).show();
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


        get_home_loans();
        get_banks();



    }


    public void get_home_loans(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Ion.with(this)
                .load(Session.SERVER_URL+"homeloan-list.php")
                .setBodyParameter("agent_id",Session.GetUserId(this))
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        if (progressDialog!=null)
                            progressDialog.dismiss();
                        try {
                            for (int i = 0; i < result.size(); i++) {
                                HomeLoans homeLoans = new HomeLoans(result.get(i).getAsJsonObject(), HomeLoansPage.this);
                                homeLoansfrom_api.add(homeLoans);
                            }
                            adapter.notifyDataSetChanged();
                        }catch (Exception e1){
                            e1.printStackTrace();
                        }
                    }
                });
    }

    public Dialog onSingleChoice() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final CharSequence[] array = {"New","Re-Sale"};


        builder.setTitle("Select Time")
                .setSingleChoiceItems(array, 0, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selectedItem = array[which].toString();
                        Log.e("select",selectedItem);
                        type.setText(selectedItem);

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


    public void get_banks(){
        Ion.with(this)
                .load(Session.SERVER_URL+"banks.php")
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        try {
                            for (int i = 0; i < result.size(); i++) {
                                Banks banks = new Banks(result.get(i).getAsJsonObject(), HomeLoansPage.this);
                                banksfrom_api.add(banks);
                            }
                            adapter.notifyDataSetChanged();
                        }catch (Exception e1){
                            e1.printStackTrace();
                        }
                    }
                });
    }


    public Dialog onBanksChoice() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final CharSequence[] array = new CharSequence[banksfrom_api.size()];
        for(int i=0;i<banksfrom_api.size();i++){

            array[i] = banksfrom_api.get(i).title+"  "+banksfrom_api.get(i).percentage;
        }

        builder.setTitle("Select Time")
                .setSingleChoiceItems(array, 0, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        String selectedItem = array[i].toString();
                        Log.e("select",selectedItem);
                        bank.setText(selectedItem);
                        bank_id = banksfrom_api.get(i).id;


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

