package com.viralandroid.tiepropertyportal;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
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

import java.util.ArrayList;

/**
 * Created by yellowsoft on 14/7/17.
 */

public class EditBASVisitPage extends Activity {
    ImageView back_btn;
    String visit_id,mess;
    EditText message;
    TextView update_btn;
    ArrayList<BASVisits> basVisitsesfrom_api;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_basvisit);
        message = (EditText) findViewById(R.id.message);
        update_btn = (TextView) findViewById(R.id.update_btn);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditBASVisitPage.this.onBackPressed();
            }
        });
        basVisitsesfrom_api = new ArrayList<>();


        if (getIntent()!=null && getIntent().hasExtra("id")){
            visit_id = getIntent().getStringExtra("id");
            mess = getIntent().getStringExtra("message");

        }

        message.setText(mess);




        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message_string = message.getText().toString();
                if (message_string.equals("")){
                    Toast.makeText(getApplicationContext(),"Please enter Message",Toast.LENGTH_SHORT).show();
                }else {
                    Ion.with(EditBASVisitPage.this)
                            .load(Session.SERVER_URL+"edit-ba_visit.php")
                            .setBodyParameter("visit_id",visit_id)
                            .setBodyParameter("agent_id",Session.GetUserId(EditBASVisitPage.this))
                            .setBodyParameter("message",message_string)
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




    }





}
