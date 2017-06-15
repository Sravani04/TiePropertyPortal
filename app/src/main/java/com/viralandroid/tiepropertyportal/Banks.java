package com.viralandroid.tiepropertyportal;

import android.content.Context;

import com.google.gson.JsonObject;

import java.io.Serializable;

/**
 * Created by yellowsoft on 14/6/17.
 */

public class Banks implements Serializable {
    public String id,title,percentage,image;
    public Banks(JsonObject jsonObject, Context context){
        id = jsonObject.get("id").getAsString();
        title = jsonObject.get("title").getAsString();
        percentage = jsonObject.get("percentage").getAsString();
        image = jsonObject.get("image").getAsString();
    }
}
