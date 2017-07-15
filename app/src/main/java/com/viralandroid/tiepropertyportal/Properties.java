package com.viralandroid.tiepropertyportal;

import android.content.Context;

import com.google.gson.JsonObject;

import java.io.Serializable;

/**
 * Created by yellowsoft on 14/7/17.
 */

public class Properties implements Serializable {
    public String id,title,prop_code,location,image,city,area,category,prices,direct_commission,override_commission;
    JsonObject jsonObject;
    public Properties(JsonObject jsonObject, Context context){
        this.jsonObject = jsonObject;
        id = jsonObject.get("id").getAsString();
        title = jsonObject.get("title").getAsString();
        prop_code = jsonObject.get("prop_code").getAsString();
        location = jsonObject.get("location").getAsString();
        image = jsonObject.get("image").getAsString();
        city = jsonObject.get("city").getAsString();
        area = jsonObject.get("area").getAsString();
        category = jsonObject.get("category").getAsString();
        prices = jsonObject.get("prices").getAsString();
        direct_commission = jsonObject.get("direct_commission").getAsString();
        override_commission = jsonObject.get("override_commission").getAsString();
    }

}
