package com.viralandroid.tiepropertyportal;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

public class MainActivity extends Activity {
    ImageView back_btn,agent_image;
    TextView agent_name;
    LinearLayout agents,callback_customers,commissions,new_property,properties,site_visits,logout_btn,edit_btn,offers_list,ba_line,offer_line,new_property_line,home_laons;
    String name,image;
    String comm_amt,tds,pay,site,ded,tot,paid,bal,agent,id;
    String first_name,last_name,email,password,address,city_title,state,phone,aadhar,pan;
    int backButtonCount;
    ArrayList<Cities>citiesfrom_api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edit_btn = (LinearLayout) findViewById(R.id.edit_btn);
        agents = (LinearLayout) findViewById(R.id.agents);
        callback_customers = (LinearLayout) findViewById(R.id.callback_customers);
        commissions = (LinearLayout) findViewById(R.id.commissions);
        new_property = (LinearLayout) findViewById(R.id.new_property);
        properties = (LinearLayout) findViewById(R.id.properties);
        site_visits = (LinearLayout) findViewById(R.id.site_visits);
        agent_name = (TextView) findViewById(R.id.agent_name);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        logout_btn = (LinearLayout) findViewById(R.id.logout_btn);
        agent_image = (ImageView) findViewById(R.id.agent_image);
        offers_list = (LinearLayout) findViewById(R.id.offer_list);
        ba_line = (LinearLayout) findViewById(R.id.ba_line);
        offer_line = (LinearLayout) findViewById(R.id.offer_line);
        new_property_line = (LinearLayout) findViewById(R.id.new_property_line);
        home_laons = (LinearLayout) findViewById(R.id.home_loans);
        citiesfrom_api = new ArrayList<>();

        if (getIntent()!=null && getIntent().hasExtra("agent_name")){
            id = getIntent().getStringExtra("agentId");
            Log.e("agent",id);
            name = getIntent().getStringExtra("agent_name");
            image = getIntent().getStringExtra("agent_image");
            comm_amt = getIntent().getStringExtra("tot_amt");
            tds = getIntent().getStringExtra("tds_amt");
            pay = getIntent().getStringExtra("pay_amt");
            site = getIntent().getStringExtra("site_visit");
            ded = getIntent().getStringExtra("ded_visit");
            tot = getIntent().getStringExtra("total_amt");
            paid = getIntent().getStringExtra("paid_amt");
            bal = getIntent().getStringExtra("bal_amt");
        }

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.onBackPressed();
            }
        });

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Session.SetUserId(MainActivity.this,"-1");
                Intent intent = new Intent(MainActivity.this,PortalScreen.class);
                startActivity(intent);
            }
        });

        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AgentsEditProfile.class);
                startActivity(intent);
            }
        });

        if (Session.GetLevelId(MainActivity.this).equals("1")){
            agents.setVisibility(View.VISIBLE);
            ba_line.setVisibility(View.VISIBLE);
            offers_list.setVisibility(View.VISIBLE);
            offer_line.setVisibility(View.VISIBLE);
            new_property.setVisibility(View.VISIBLE);
            new_property_line.setVisibility(View.VISIBLE);
        }else {
            agents.setVisibility(View.GONE);
            ba_line.setVisibility(View.GONE);
            offers_list.setVisibility(View.GONE);
            offer_line.setVisibility(View.GONE);
            new_property.setVisibility(View.GONE);
            new_property_line.setVisibility(View.GONE);
        }

        agents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AgentsPage.class);
                startActivity(intent);
            }
        });

        callback_customers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CallbackCustomersPage.class);
                startActivity(intent);
            }
        });

        new_property.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,NewPropertyScreen.class);
                startActivity(intent);
            }
        });

        site_visits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SiteVisitScreen.class);
                startActivity(intent);
            }
        });

        commissions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this,CommissionsPage.class);
                startActivity(intent);

            }
        });

        offers_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent offer = new Intent(MainActivity.this,OffersListActivity.class);
                startActivity(offer);
            }
        });

        properties.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent properties = new Intent(MainActivity.this,PropertiesListActivity.class);
                startActivity(properties);

            }
        });

        home_laons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,HomeLoansPage.class);
                startActivity(intent);
            }
        });



        get_agents();
        onBackPressed();


    }




    public void get_agents(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Ion.with(this)
                .load(Session.SERVER_URL+"agents.php")
                .setBodyParameter("agent_id",Session.GetUserId(MainActivity.this))
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        try {
                            if (progressDialog != null)
                                progressDialog.dismiss();
                            JsonObject jsonObject = result.get(0).getAsJsonObject();
                            agent_name.setText(jsonObject.get("fname").getAsString());
                            Ion.with(MainActivity.this).load(jsonObject.get("image").getAsString()).withBitmap().placeholder(R.drawable.placeholder_person).intoImageView(agent_image);
                        }catch (Exception e1){
                            e1.printStackTrace();
                        }
                    }
                });

    }



    @Override
    public void onBackPressed()
    {
        if(backButtonCount >= 1)
        {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else
        {
            //Toast.makeText(this, "Press the back button once again to close the application.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }




}
