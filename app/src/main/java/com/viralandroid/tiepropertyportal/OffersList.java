package com.viralandroid.tiepropertyportal;

import android.content.Context;

import com.google.gson.JsonObject;

import java.io.Serializable;

/**
 * Created by T on 25-05-2017.
 */

public class OffersList implements Serializable {
    public String id,title,start_date,end_date,description,image;
    public OffersList(JsonObject jsonObject, Context context){
        id = jsonObject.get("id").getAsString();
        title = jsonObject.get("title").getAsString();
        start_date = jsonObject.get("start_date").getAsString();
        end_date = jsonObject.get("end_date").getAsString();
        description = jsonObject.get("description").getAsString();
        image = jsonObject.get("image").getAsString();
    }
}
