package com.viralandroid.tiepropertyportal;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

/**
 * Created by T on 12-05-2017.
 */

public class LoginPage extends Activity {
    EditText email,password;
    TextView submit_btn;
    ImageView close_btn;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        submit_btn = (TextView) findViewById(R.id.submit_btn);
        close_btn = (ImageView) findViewById(R.id.close_btn);


        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email_string = email.getText().toString();
                String password_string = password.getText().toString();
                if (email_string.equals("")){
                    Toast.makeText(LoginPage.this,"Please Enter Your Email",Toast.LENGTH_SHORT).show();
                    email.requestFocus();
                }else if (password_string.equals("")){
                    Toast.makeText(LoginPage.this,"Please Enter Your Password",Toast.LENGTH_SHORT).show();
                    password.requestFocus();
                }else {
                    final ProgressDialog progressDialog = new ProgressDialog(LoginPage.this);
                    progressDialog.setMessage("please wait");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    Ion.with(LoginPage.this)
                            .load(Session.SERVER_URL + "login.php")
                            .setBodyParameter("email",email_string)
                            .setBodyParameter("password",password_string)
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    if (progressDialog!=null)
                                        progressDialog.dismiss();
                                    try {
                                        if (result.get("status").getAsString().equals("Success")) {
                                            Session.SetUserId(LoginPage.this, result.get("member_id").getAsString());
                                            Session.SetLevelId(LoginPage.this, result.get("level").getAsString());
                                            Toast.makeText(LoginPage.this, result.get("member_id").getAsString(), Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(LoginPage.this, MainActivity.class);
                                            startActivity(intent);
                                            LoginPage.super.onBackPressed();
                                            finish();
                                        } else {
                                            Toast.makeText(LoginPage.this, result.get("message").getAsString(), Toast.LENGTH_SHORT).show();

                                        }
                                    }catch (Exception e1){
                                        e1.printStackTrace();
                                    }
                                }
                            });
                }
            }
        });

    }
}
