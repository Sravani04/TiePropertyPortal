package com.viralandroid.tiepropertyportal;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

/**
 * Created by T on 17-05-2017.
 */

public class CommissionsPage  extends Activity{
    ImageView back_btn;
    TextView comm_list,site_visits_list,payouts_list;
    TextView tot_comm_amt,tds_amt,pay_amt,site_visits,ded_visit,tot_amt,paid_amt,bal_amt;
    String comm_amt,tds,pay,site,ded,tot,paid,bal;
    ArrayList<Commissions> commissionsfrom_api;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commissions_page);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        comm_list = (TextView) findViewById(R.id.comm_list);
        site_visits_list = (TextView) findViewById(R.id.site_visit_lists);
        payouts_list = (TextView) findViewById(R.id.payouts_list);
        tot_comm_amt = (TextView) findViewById(R.id.tot_comm_amt);
        tds_amt = (TextView) findViewById(R.id.tds_amt);
        pay_amt = (TextView) findViewById(R.id.pay_amt);
        site_visits = (TextView) findViewById(R.id.site_visits);
        ded_visit = (TextView) findViewById(R.id.ded_visit);
        tot_amt = (TextView) findViewById(R.id.tot_amt);
        paid_amt = (TextView) findViewById(R.id.paid_amt);
        bal_amt = (TextView) findViewById(R.id.bal_amt);

        commissionsfrom_api = new ArrayList<>();

//        if (getIntent()!=null && getIntent().hasExtra("tot_amt")){
//            comm_amt = getIntent().getStringExtra("tot_amt");
//            tds = getIntent().getStringExtra("tds_amt");
//            pay = getIntent().getStringExtra("pay_amt");
//            site = getIntent().getStringExtra("site_visit");
//            ded = getIntent().getStringExtra("ded_visit");
//            tot = getIntent().getStringExtra("total_amt");
//            paid = getIntent().getStringExtra("paid_amt");
//            bal = getIntent().getStringExtra("bal_amt");
//        }
//
//        tot_comm_amt.setText(comm_amt);
//        tds_amt.setText(tds);
//        pay_amt.setText(pay);
//        site_visits.setText(site);
//        ded_visit.setText(ded);
//        tot_amt.setText(tot);
//        paid_amt.setText(paid);
//        bal_amt.setText(bal);



        comm_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommissionsPage.this,CommissionList.class);
                intent.putExtra("commissions",commissionsfrom_api.get(0));
                Log.e("comm",commissionsfrom_api.get(0).commissionLists.toString());
                startActivity(intent);

            }
        });

        Log.e("comm_array",commissionsfrom_api.toString());

        site_visits_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommissionsPage.this,VisitScreen.class);
                intent.putExtra("commissions",commissionsfrom_api.get(0));
                startActivity(intent);
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommissionsPage.this.onBackPressed();
            }
        });

        get_commissions();


    }

    public void get_commissions(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Ion.with(getApplicationContext())
                .load(Session.SERVER_URL+"commissions.php")
                .setBodyParameter("agent_id",Session.GetUserId(CommissionsPage.this))
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        if (progressDialog!=null){
                            progressDialog.dismiss();
                        }
                        Log.e("result",result.toString());
                        try {
                            JsonObject jsonObject = result.get(0).getAsJsonObject();
                            tot_comm_amt.setText(jsonObject.get("total_commission_amount").getAsString());
                            tds_amt.setText(jsonObject.get("total_tds_amount").getAsString());
                            pay_amt.setText(jsonObject.get("total_payable_amount").getAsString());
                            site_visits.setText(jsonObject.get("total_visits").getAsString());
                            ded_visit.setText(jsonObject.get("deducted_visits").getAsString());
                            tot_amt.setText(jsonObject.get("total_amount").getAsString());
                            paid_amt.setText(jsonObject.get("paid_amount").getAsString());
                            bal_amt.setText(jsonObject.get("balance_amount").getAsString());
                            for (int i = 0; i < result.size(); i++) {
                                Commissions commissions = new Commissions(result.get(i).getAsJsonObject(), CommissionsPage.this);
                                commissionsfrom_api.add(commissions);

                            }
                        }catch (Exception e1){
                            e1.printStackTrace();
                        }



                    }
                });
    }






}
