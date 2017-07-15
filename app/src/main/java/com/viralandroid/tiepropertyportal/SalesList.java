package com.viralandroid.tiepropertyportal;

import android.content.Context;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

import java.io.Serializable;

/**
 * Created by yellowsoft on 13/7/17.
 */

public class SalesList implements Serializable {
    public String id,name,phone,email,dob,address,prop_id,prop_title,prop_code,agent_id,agent_name,agent_code,units,total_amount,loan;
    public SalesList(JsonObject jsonObject, Context context){
        id = jsonObject.get("id").getAsString();
        name = jsonObject.get("name").getAsString();
        phone = jsonObject.get("phone").getAsString();
        email = jsonObject.get("email").getAsString();
        dob = jsonObject.get("dob").getAsString();
        address = jsonObject.get("address").getAsString();
        prop_id = jsonObject.get("property").getAsJsonObject().get("prop_id").getAsString();
        prop_title = jsonObject.get("property").getAsJsonObject().get("title").getAsString();
        prop_code = jsonObject.get("property").getAsJsonObject().get("prop_code").getAsString();
        agent_id = jsonObject.get("agent").getAsJsonObject().get("agent_id").getAsString();
        agent_name = jsonObject.get("agent").getAsJsonObject().get("name") != JsonNull.INSTANCE ?jsonObject.get("agent").getAsJsonObject().get("name").getAsString() : null;
        agent_code = jsonObject.get("agent").getAsJsonObject().get("agent_code") != JsonNull.INSTANCE?jsonObject.get("agent").getAsJsonObject().get("agent_code").getAsString() : null;
        units = jsonObject.get("units").getAsString();
        total_amount = jsonObject.get("total_amount").getAsString();
        loan = jsonObject.get("loan").getAsString();

    }
}
