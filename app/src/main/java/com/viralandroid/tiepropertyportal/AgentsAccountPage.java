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
import com.google.gson.JsonParser;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

/**
 * Created by T on 17-05-2017.
 */

public class AgentsAccountPage extends Activity {
    ImageView back_btn,agent_image;
    TextView agent_name;
    LinearLayout agents,callback_customers,commissions,new_property,properties,site_visits,logout_btn,edit_btn;
    String name,image;
    String comm_amt,tds,pay,site,ded,tot,paid,bal,agent,id;
    ArrayList<Cities> citiesfrom_api;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agents_account_page);
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

        citiesfrom_api = new ArrayList<>();


        if (getIntent()!=null && getIntent().hasExtra("agent_name")){
            id = getIntent().getStringExtra("agentId");
            Log.e("agentids",id);
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

//        agent_name.setText(name);


        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AgentsAccountPage.this.onBackPressed();
            }
        });

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Session.SetUserId(AgentsAccountPage.this,"-1");
//                AgentsAccountPage.this.onBackPressed();
                finish();
            }
        });

        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AgentsEditProfile.class);
                startActivity(intent);
            }
        });

        agents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AgentsAccountPage.this,AgentsPage.class);
                startActivity(intent);
            }
        });

        callback_customers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AgentsAccountPage.this, CallbackCustomersPage.class);
                startActivity(intent);
            }
        });

        new_property.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AgentsAccountPage.this,NewPropertyScreen.class);
                intent.putExtra("cities",citiesfrom_api);
                startActivity(intent);
            }
        });

        site_visits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AgentsAccountPage.this,SiteVisitScreen.class);
                startActivity(intent);
            }
        });

        commissions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AgentsAccountPage.this,CommissionsPage.class);
                startActivity(intent);

            }
        });

        JsonParser jsonParser = new JsonParser();
        if (!Session.GetCities(AgentsAccountPage.this).equals("-1")){
            JsonArray jsonArray = (JsonArray) jsonParser.parse(Session.GetCities(AgentsAccountPage.this));
            for (int i=0;i<jsonArray.size();i++){
                Cities cities = new Cities(jsonArray.get(i).getAsJsonObject(),AgentsAccountPage.this);
                citiesfrom_api.add(cities);
            }
        }

        get_agents();

    }

    public void get_agents(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Ion.with(this)
                .load(Session.SERVER_URL+"agents.php")
                .setBodyParameter("agent_id",Session.GetUserId(AgentsAccountPage.this))
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        try {
                            if (progressDialog != null)
                                progressDialog.dismiss();
                            JsonObject jsonObject = result.get(0).getAsJsonObject();
                            agent_name.setText(jsonObject.get("fname").getAsString());
                            Ion.with(AgentsAccountPage.this).load(jsonObject.get("image").getAsString()).withBitmap().placeholder(R.drawable.placeholder_person).intoImageView(agent_image);
                        }catch (Exception e1){
                            e1.printStackTrace();
                        }
                    }
                });

    }



}
