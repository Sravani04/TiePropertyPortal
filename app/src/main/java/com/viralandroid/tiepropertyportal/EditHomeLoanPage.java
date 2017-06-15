package com.viralandroid.tiepropertyportal;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by yellowsoft on 14/6/17.
 */

public class EditHomeLoanPage extends Activity {
    ImageView back_btn;
    String loan_id,loan_name,loan_address,loan_email,loan_phone,loan_property,loan_type,loan_bank,bank_id,id;
    EditText name,address,phone,email,property;
    TextView submit_btn,type,bank;
    ArrayList<Banks> banksfrom_api;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_home_loan);
        name = (EditText) findViewById(R.id.name);
        address = (EditText) findViewById(R.id.address);
        phone = (EditText) findViewById(R.id.phone);
        email = (EditText) findViewById(R.id.email);
        property = (EditText) findViewById(R.id.property);
        type = (TextView) findViewById(R.id.type);
        bank = (TextView) findViewById(R.id.bank);
        submit_btn = (TextView) findViewById(R.id.submit_btn);
        banksfrom_api = new ArrayList<>();


        if (getIntent()!=null && getIntent().hasExtra("id")){
            loan_id = getIntent().getStringExtra("id");
            loan_name = getIntent().getStringExtra("name");
            loan_address = getIntent().getStringExtra("address");
            loan_email = getIntent().getStringExtra("email");
            loan_phone = getIntent().getStringExtra("phone");
            loan_property = getIntent().getStringExtra("property");
            loan_type = getIntent().getStringExtra("type");
            loan_bank = getIntent().getStringExtra("bank");
            bank_id =getIntent().getStringExtra("bank_id");
        }

        name.setText(loan_name);
        address.setText(loan_address);
        email.setText(loan_email);
        phone.setText(loan_phone);
        property.setText(loan_property);
        type.setText(loan_type);
        bank.setText(loan_bank);

        type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog  = onSingleChoice();
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
                String phone_string = phone.getText().toString();
                String email_string = email.getText().toString();
                String property_string = property.getText().toString();
                String type_string = loan_type;
                String bank_string = bank_id;


                type_string = "New";
                if (name_string.equals("")){
                    Toast.makeText(getApplicationContext(),"Please enter name",Toast.LENGTH_SHORT).show();
                }else if (address_string.equals("")){
                    Toast.makeText(getApplicationContext(),"Please enter address",Toast.LENGTH_SHORT).show();
                }else if (phone_string.equals("")){
                    Toast.makeText(getApplicationContext(),"Please enter phone",Toast.LENGTH_SHORT).show();
                }else if (email_string.equals("")){
                    Toast.makeText(getApplicationContext(),"Please enter email",Toast.LENGTH_SHORT).show();
                }else if (property_string.equals("")){
                    Toast.makeText(getApplicationContext(),"Please enter property",Toast.LENGTH_SHORT).show();
                }else if (loan_type.equals("")){
                    Toast.makeText(getApplicationContext(),"Please enter type",Toast.LENGTH_SHORT).show();
                }else if (bank_id.equals("")){
                    Toast.makeText(getApplicationContext(),"Please enter bank",Toast.LENGTH_SHORT).show();
                }else {
                    Ion.with(EditHomeLoanPage.this)
                            .load(Session.SERVER_URL+"edit-homeloan.php")
                            .setBodyParameter("loan_id",loan_id)
                            .setBodyParameter("name",name_string)
                            .setBodyParameter("address",address_string)
                            .setBodyParameter("phone",phone_string)
                            .setBodyParameter("email",email_string)
                            .setBodyParameter("property",property_string)
                            .setBodyParameter("type",loan_type)
                            .setBodyParameter("bank",bank_id)
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    if (result.get("status").getAsString().equals("Success")){
                                        Toast.makeText(getApplicationContext(),result.get("message").getAsString(),Toast.LENGTH_SHORT).show();
                                        finish();
                                    }else {
                                        Toast.makeText(getApplicationContext(),result.get("message").getAsString(),Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        get_banks();



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
                        loan_type = String.valueOf(selectedItem);

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
                         for (int i=0;i<result.size();i++){
                             Banks banks = new Banks(result.get(i).getAsJsonObject(),EditHomeLoanPage.this);
                             banksfrom_api.add(banks);
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
                        loan_bank = String.valueOf(selectedItem);


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
