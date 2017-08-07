package com.viralandroid.tiepropertyportal;

import android.content.Context;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

import java.io.Serializable;

/**
 * Created by yellowsoft on 14/7/17.
 */

public class TrendingProperties implements Serializable {
    public String id,title,prop_code,location,image,area,category,prices,direct_commission,override_commission;
    public TrendingProperties(JsonObject jsonObject,Context context){
        id = jsonObject.get("id").getAsString();
        title = jsonObject.get("title").getAsString();
        prop_code = jsonObject.get("prop_code").getAsString();
        location = jsonObject.get("location").getAsString();
        image = jsonObject.get("image").getAsString();
        area = jsonObject.get("area").getAsString();
        category = jsonObject.get("category").getAsString();
        prices = jsonObject.get("prices").getAsString();
        direct_commission = jsonObject.get("direct_commission").getAsString();
        override_commission = jsonObject.get("override_commission").getAsString();

    }
}
