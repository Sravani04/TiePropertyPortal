package com.viralandroid.tiepropertyportal;

import android.content.Context;

import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by T on 17-05-2017.
 */

public class Commissions implements Serializable {
    public String total_commission_amount,total_tds_amount,total_payable_amount,total_visits,deducted_visits,total_amount,paid_amount,balance_amount;

    public ArrayList<CommissionList> commissionLists;
    public ArrayList<Sites> sites;


    public Commissions(JsonObject jsonObject, Context context) {
        total_commission_amount = jsonObject.get("total_commission_amount").getAsString();
        total_tds_amount = jsonObject.get("total_tds_amount").getAsString();
        total_payable_amount = jsonObject.get("total_payable_amount").getAsString();
        total_visits = jsonObject.get("total_visits").getAsString();
        deducted_visits = jsonObject.get("deducted_visits").getAsString();
        total_amount = jsonObject.get("total_amount").getAsString();
        paid_amount = jsonObject.get("paid_amount").getAsString();
        balance_amount = jsonObject.get("balance_amount").getAsString();

        commissionLists = new ArrayList<>();
        for (int i=0;i<jsonObject.get("commissions").getAsJsonArray().size();i++){
            CommissionList commissions = new CommissionList(jsonObject.get("commissions").getAsJsonArray().get(i).getAsJsonObject(),context);
            commissionLists.add(commissions);
        }

        sites = new ArrayList<>();
        for (int i=0;i<jsonObject.get("site_visits").getAsJsonArray().size();i++){
            Sites site  = new Sites(jsonObject.get("site_visits").getAsJsonArray().get(i).getAsJsonObject(),context);
            sites.add(site);

        }


    }

    public class CommissionList implements Serializable{

        public String id,customer,property,units,total_amount,paid_amount,amount,tds,payable_amount;

        public CommissionList(JsonObject jsonObject,Context context){
            id = jsonObject.get("id").getAsString();
            customer = jsonObject.get("customer").getAsString();
            property = jsonObject.get("property").getAsString();
            units = jsonObject.get("units").getAsString();
            total_amount = jsonObject.get("total_amount").getAsString();
            paid_amount = jsonObject.get("paid_amount").getAsString();
            amount = jsonObject.get("amount").getAsString();
            tds = jsonObject.get("tds").getAsString();
            payable_amount = jsonObject.get("payable_amount").getAsString();
        }
    }

    public class  Sites implements Serializable{

        public String id,name,phone,address,date,time,property,amount,status;

        public Sites(JsonObject jsonObject,Context context){
            id = jsonObject.get("id").getAsString();
            name = jsonObject.get("name").getAsString();
            phone = jsonObject.get("phone").getAsString();
            address = jsonObject.get("address").getAsString();
            date = jsonObject.get("date").getAsString();
            time = jsonObject.get("time").getAsString();
            property = jsonObject.get("property").getAsString();
            amount = jsonObject.get("amount").getAsString();
            try {
                status = jsonObject.get("status").getAsString();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }


}
