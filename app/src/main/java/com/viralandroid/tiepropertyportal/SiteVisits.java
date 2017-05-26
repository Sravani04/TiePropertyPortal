package com.viralandroid.tiepropertyportal;

import android.content.Context;

import com.google.gson.JsonObject;

import java.io.Serializable;

/**
 * Created by T on 18-05-2017.
 */

public class SiteVisits implements Serializable {
    public String id,name,phone,address,date,time,prop_id,title,code;
    public SiteVisits(JsonObject jsonObject, Context context){
        id = jsonObject.get("id").getAsString();
        name = jsonObject.get("name").getAsString();
        phone = jsonObject.get("phone").getAsString();
        address = jsonObject.get("address").getAsString();
        date = jsonObject.get("date").getAsString();
        time = jsonObject.get("time").getAsString();
        try {
            prop_id = jsonObject.get("property").getAsJsonObject().get("prop_id").getAsString();
            title = jsonObject.get("property").getAsJsonObject().get("title").getAsString();
            code = jsonObject.get("property").getAsJsonObject().get("code").getAsString();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
