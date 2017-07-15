package com.viralandroid.tiepropertyportal;

import android.content.Context;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

import java.io.Serializable;

/**
 * Created by yellowsoft on 14/7/17.
 */

public class TrendingProperties implements Serializable {
    public String id,title,image,link,property_id,property_name;
    public TrendingProperties(JsonObject jsonObject,Context context){
        id = jsonObject.get("id").getAsString();
        title = jsonObject.get("title").getAsString();
        image = jsonObject.get("image").getAsString();
        link = jsonObject.get("link").getAsString();
        property_id = jsonObject.get("property_id").getAsString();
        property_name = jsonObject.get("property_name") != JsonNull.INSTANCE ? jsonObject.get("property_name").getAsString() : null;

    }
}
