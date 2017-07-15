package com.viralandroid.tiepropertyportal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.gson.JsonArray;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

/**
 * Created by T on 25-05-2017.
 */

public class SplashActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (Session.GetUserId(SplashActivity.this).equals("-1")) {
//                    Intent intent = new Intent(SplashActivity.this, PortalScreen.class);
//                    startActivity(intent);
//                    finish();
//                } else {
//                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
//            }}, 1500);

        get_cities();

    }

    public void get_cities(){
        Ion.with(this)
                .load(Session.SERVER_URL+"cities.php")
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        Session.SetCities(SplashActivity.this,result.toString());
                       get_categories();

                    }
                });
    }

    public void get_categories(){
        Ion.with(this)
                .load(Session.SERVER_URL+"category.php")
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                      Session.SetCategories(SplashActivity.this,result.toString());
                        get_properties();
                    }
                });
    }

    public void get_properties(){
        Ion.with(this)
                .load(Session.SERVER_URL+"properties.php")
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        Session.SetAreas(SplashActivity.this,result.toString());
                        if (Session.GetUserId(SplashActivity.this).equals("-1")) {
                            Intent intent = new Intent(SplashActivity.this, PortalScreen.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }
}
