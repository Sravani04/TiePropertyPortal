package com.viralandroid.tiepropertyportal;

import android.content.Context;

import com.google.gson.JsonObject;

import java.io.Serializable;

/**
 * Created by yellowsoft on 14/7/17.
 */

public class BASVisits implements Serializable {
    public String id,name,phone,address,date,time,prop_id,prop_title,prop_code,message,status;
    public BASVisits(JsonObject jsonObject, Context context){
        id = jsonObject.get("id").getAsString();
        name = jsonObject.get("name").getAsString();
        phone = jsonObject.get("phone").getAsString();
        address = jsonObject.get("address").getAsString();
        date = jsonObject.get("date").getAsString();
        time = jsonObject.get("time").getAsString();
        prop_id = jsonObject.get("property").getAsJsonObject().get("prop_id").getAsString();
        prop_title = jsonObject.get("property").getAsJsonObject().get("title").getAsString();
        prop_code = jsonObject.get("property").getAsJsonObject().get("prop_code").getAsString();
        message = jsonObject.get("message").getAsString();
        status = jsonObject.get("status").getAsString();

    }
}
