package com.viralandroid.tiepropertyportal;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;


/**
 * Created by T on 15-05-2017.
 */

public class AgentHomePage extends Activity {
    ImageView menu_btn;
    SlidingPaneLayout slidingPaneLayout;
    ListView listView;
    TextView callback_customers,commissions,new_property,properties,site_visits,my_account,logout_btn,agents,agent_name,agent_code;
    TextView tot_comm_amt,tot_tds_amt,tot_pay_amt,tot_site_visit,ded_site_visits,tot_amt,paid_amt,balance_amt;
    String name,image;
    ArrayList<Commissions>commissionsfrom_api;
    ArrayList<Agents> agentsfrom_api;
    String cityid;



    @Override
    public void onCreate(Bundle savedinstanceState) {
        super.onCreate(savedinstanceState);
        setContentView(R.layout.agents_home_page);
        menu_btn = (ImageView) findViewById(R.id.menu_btn);
        slidingPaneLayout = (SlidingPaneLayout) findViewById(R.id.sliding_pane_layout);
        callback_customers = (TextView) findViewById(R.id.callback_customers);
        commissions = (TextView) findViewById(R.id.commissions);
        new_property = (TextView) findViewById(R.id.new_property);
        properties = (TextView) findViewById(R.id.properties);
        site_visits = (TextView) findViewById(R.id.site_visits);
        my_account = (TextView) findViewById(R.id.my_account);
        logout_btn = (TextView) findViewById(R.id.logout_btn);
        agents = (TextView) findViewById(R.id.agents);
        agent_name = (TextView) findViewById(R.id.agent_name);
        agent_code = (TextView) findViewById(R.id.agent_code);
        tot_comm_amt = (TextView) findViewById(R.id.tot_comm_amt);
        tot_tds_amt = (TextView) findViewById(R.id.tot_tds_amt);
        tot_pay_amt = (TextView) findViewById(R.id.tot_pay_amt);
        tot_site_visit = (TextView) findViewById(R.id.tot_site_visit);
        ded_site_visits = (TextView) findViewById(R.id.ded_site_visits);
        tot_amt = (TextView) findViewById(R.id.tot_amt);
        paid_amt = (TextView) findViewById(R.id.paid_amt);
        balance_amt = (TextView) findViewById(R.id.balance_amt);
        commissionsfrom_api = new ArrayList<>();
        agentsfrom_api = new ArrayList<>();

      if (getIntent()!=null && getIntent().hasExtra("id")){
          cityid = getIntent().getStringExtra("id");

      }

        menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slidingPaneLayout.openPane();
            }
        });


        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Session.SetUserId(AgentHomePage.this,"-1");
                AgentHomePage.this.onBackPressed();
            }
        });


        agents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agents.setBackgroundColor(Color.parseColor("#CCCCCC"));
                agents.setTextColor(Color.parseColor("#09366C"));
                callback_customers.setBackgroundColor(Color.parseColor("#ffffff"));
                callback_customers.setTextColor(Color.parseColor("#000000"));
                new_property.setBackgroundColor(Color.parseColor("#ffffff"));
                new_property.setTextColor(Color.parseColor("#000000"));
                site_visits.setBackgroundColor(Color.parseColor("#ffffff"));
                site_visits.setTextColor(Color.parseColor("#000000"));
                commissions.setBackgroundColor(Color.parseColor("#ffffff"));
                commissions.setTextColor(Color.parseColor("#000000"));
                my_account.setBackgroundColor(Color.parseColor("#ffffff"));
                my_account.setTextColor(Color.parseColor("#000000"));
                properties.setBackgroundColor(Color.parseColor("#ffffff"));
                properties.setTextColor(Color.parseColor("#000000"));
                Intent intent = new Intent(getApplicationContext(),AgentsPage.class);
                startActivity(intent);
                slidingPaneLayout.closePane();
            }
        });



        callback_customers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback_customers.setBackgroundColor(Color.parseColor("#CCCCCC"));
                callback_customers.setTextColor(Color.parseColor("#09366C"));
                new_property.setBackgroundColor(Color.parseColor("#ffffff"));
                new_property.setTextColor(Color.parseColor("#000000"));
                site_visits.setBackgroundColor(Color.parseColor("#ffffff"));
                site_visits.setTextColor(Color.parseColor("#000000"));
                agents.setBackgroundColor(Color.parseColor("#ffffff"));
                agents.setTextColor(Color.parseColor("#000000"));
                commissions.setBackgroundColor(Color.parseColor("#ffffff"));
                commissions.setTextColor(Color.parseColor("#000000"));
                my_account.setBackgroundColor(Color.parseColor("#ffffff"));
                my_account.setTextColor(Color.parseColor("#000000"));
                properties.setBackgroundColor(Color.parseColor("#ffffff"));
                properties.setTextColor(Color.parseColor("#000000"));
                Intent intent = new Intent(AgentHomePage.this, CallbackCustomersPage.class);
                startActivity(intent);
                slidingPaneLayout.closePane();
            }
        });

        new_property.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new_property.setBackgroundColor(Color.parseColor("#CCCCCC"));
                new_property.setTextColor(Color.parseColor("#09366C"));
                callback_customers.setBackgroundColor(Color.parseColor("#ffffff"));
                callback_customers.setTextColor(Color.parseColor("#000000"));
                site_visits.setBackgroundColor(Color.parseColor("#ffffff"));
                site_visits.setTextColor(Color.parseColor("#000000"));
                agents.setBackgroundColor(Color.parseColor("#ffffff"));
                agents.setTextColor(Color.parseColor("#000000"));
                commissions.setBackgroundColor(Color.parseColor("#ffffff"));
                commissions.setTextColor(Color.parseColor("#000000"));
                my_account.setBackgroundColor(Color.parseColor("#ffffff"));
                my_account.setTextColor(Color.parseColor("#000000"));
                properties.setBackgroundColor(Color.parseColor("#ffffff"));
                properties.setTextColor(Color.parseColor("#000000"));
                Intent intent = new Intent(AgentHomePage.this,NewPropertyScreen.class);
                startActivity(intent);
                slidingPaneLayout.closePane();
            }
        });

        site_visits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                site_visits.setBackgroundColor(Color.parseColor("#CCCCCC"));
                site_visits.setTextColor(Color.parseColor("#09366C"));
                new_property.setBackgroundColor(Color.parseColor("#ffffff"));
                new_property.setTextColor(Color.parseColor("#000000"));
                callback_customers.setBackgroundColor(Color.parseColor("#ffffff"));
                callback_customers.setTextColor(Color.parseColor("#000000"));
                agents.setBackgroundColor(Color.parseColor("#ffffff"));
                agents.setTextColor(Color.parseColor("#000000"));
                commissions.setBackgroundColor(Color.parseColor("#ffffff"));
                commissions.setTextColor(Color.parseColor("#000000"));
                my_account.setBackgroundColor(Color.parseColor("#ffffff"));
                my_account.setTextColor(Color.parseColor("#000000"));
                properties.setBackgroundColor(Color.parseColor("#ffffff"));
                properties.setTextColor(Color.parseColor("#000000"));
                Intent intent = new Intent(AgentHomePage.this,SiteVisitScreen.class);
                startActivity(intent);
                slidingPaneLayout.closePane();
            }
        });

        commissions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commissions.setBackgroundColor(Color.parseColor("#CCCCCC"));
                commissions.setTextColor(Color.parseColor("#09366C"));
                new_property.setBackgroundColor(Color.parseColor("#ffffff"));
                new_property.setTextColor(Color.parseColor("#000000"));
                callback_customers.setBackgroundColor(Color.parseColor("#ffffff"));
                callback_customers.setTextColor(Color.parseColor("#000000"));
                agents.setBackgroundColor(Color.parseColor("#ffffff"));
                agents.setTextColor(Color.parseColor("#000000"));
                site_visits.setBackgroundColor(Color.parseColor("#ffffff"));
                site_visits.setTextColor(Color.parseColor("#000000"));
                my_account.setBackgroundColor(Color.parseColor("#ffffff"));
                my_account.setTextColor(Color.parseColor("#000000"));
                properties.setBackgroundColor(Color.parseColor("#ffffff"));
                properties.setTextColor(Color.parseColor("#000000"));
                Intent intent= new Intent(AgentHomePage.this,CommissionsPage.class);
                intent.putExtra("tot_amt",tot_comm_amt.getText().toString());
                intent.putExtra("tds_amt",tot_tds_amt.getText().toString());
                intent.putExtra("pay_amt",tot_pay_amt.getText().toString());
                intent.putExtra("site_visit",tot_site_visit.getText().toString());
                intent.putExtra("ded_visit",ded_site_visits.getText().toString());
                intent.putExtra("total_amt",tot_amt.getText().toString());
                intent.putExtra("paid_amt",paid_amt.getText().toString());
                intent.putExtra("bal_amt",balance_amt.getText().toString());
                startActivity(intent);
                slidingPaneLayout.closePane();

            }
        });

        my_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                my_account.setBackgroundColor(Color.parseColor("#CCCCCC"));
                my_account.setTextColor(Color.parseColor("#09366C"));
                new_property.setBackgroundColor(Color.parseColor("#ffffff"));
                new_property.setTextColor(Color.parseColor("#000000"));
                callback_customers.setBackgroundColor(Color.parseColor("#ffffff"));
                callback_customers.setTextColor(Color.parseColor("#000000"));
                agents.setBackgroundColor(Color.parseColor("#ffffff"));
                agents.setTextColor(Color.parseColor("#000000"));
                site_visits.setBackgroundColor(Color.parseColor("#ffffff"));
                site_visits.setTextColor(Color.parseColor("#000000"));
                commissions.setBackgroundColor(Color.parseColor("#ffffff"));
                commissions.setTextColor(Color.parseColor("#000000"));
                properties.setBackgroundColor(Color.parseColor("#ffffff"));
                properties.setTextColor(Color.parseColor("#000000"));
                Intent intent = new Intent(AgentHomePage.this,AgentsAccountPage.class);
                intent.putExtra("agent_name",name);
                intent.putExtra("agent_image",image);
                intent.putExtra("tot_amt",tot_comm_amt.getText().toString());
                intent.putExtra("tds_amt",tot_tds_amt.getText().toString());
                intent.putExtra("pay_amt",tot_pay_amt.getText().toString());
                intent.putExtra("site_visit",tot_site_visit.getText().toString());
                intent.putExtra("ded_visit",ded_site_visits.getText().toString());
                intent.putExtra("total_amt",tot_amt.getText().toString());
                intent.putExtra("paid_amt",paid_amt.getText().toString());
                intent.putExtra("bal_amt",balance_amt.getText().toString());
                startActivity(intent);
                startActivity(intent);
                slidingPaneLayout.closePane();
            }
        });




        get_commissions();
        get_agents();


    }



    public void get_commissions(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Ion.with(getApplicationContext())
                .load(Session.SERVER_URL+"commissions.php")
                .setBodyParameter("agent_id",Session.GetUserId(AgentHomePage.this))
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        if (progressDialog!=null){
                            progressDialog.dismiss();
                        }
                        JsonObject jsonObject = result.get(0).getAsJsonObject();
                        tot_comm_amt.setText(jsonObject.get("total_commission_amount").getAsString());
                        tot_tds_amt.setText(jsonObject.get("total_tds_amount").getAsString());
                        tot_pay_amt.setText(jsonObject.get("total_payable_amount").getAsString());
                        tot_site_visit.setText(jsonObject.get("total_visits").getAsString());
                        ded_site_visits.setText(jsonObject.get("deducted_visits").getAsString());
                        tot_amt.setText(jsonObject.get("total_amount").getAsString());
                        paid_amt.setText(jsonObject.get("paid_amount").getAsString());
                        balance_amt.setText(jsonObject.get("balance_amount").getAsString());

                    }
                });
    }


    public void get_agents(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Ion.with(this)
                .load(Session.SERVER_URL+"agents.php")
                .setBodyParameter("agent_id",Session.GetUserId(AgentHomePage.this))
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        try {
                            if (progressDialog != null)
                                progressDialog.dismiss();
                            JsonObject jsonObject = result.get(0).getAsJsonObject();
                            agent_name.setText(jsonObject.get("fname").getAsString());
                            agent_code.setText(jsonObject.get("code").getAsString());
                            name = jsonObject.get("fname").getAsString();
                            image = jsonObject.get("image").getAsString();
                        }catch (Exception e1){
                            e1.printStackTrace();
                        }
                    }
                });

    }



}
