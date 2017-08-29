package com.viralandroid.tiepropertyportal;

import android.content.Context;

import com.google.gson.JsonObject;

import java.io.Serializable;

/**
 * Created by T on 15-05-2017.
 */

public class Agents implements Serializable {
    public String id,fname,lname,code,email,address,city_id,city_title,state,phone,aadhar,pancard,level_id,level_title,image;
    public Agents(JsonObject jsonObject, Context context){
        id = jsonObject.get("id").getAsString();
        fname = jsonObject.get("fname").getAsString();
        lname = jsonObject.get("lname").getAsString();
        code = jsonObject.get("code").getAsString();
        email = jsonObject.get("email").getAsString();
        address = jsonObject.get("address").getAsString();
        city_id = jsonObject.get("city").getAsJsonObject().get("city_id").getAsString();
        city_title = jsonObject.get("city").getAsJsonObject().get("title").getAsString();
        state = jsonObject.get("state").getAsString();
        phone = jsonObject.get("phone").getAsString();
        aadhar = jsonObject.get("aadhar").getAsString();
        pancard = jsonObject.get("pancard").getAsString();
        level_id =jsonObject.get("level").getAsJsonObject().get("level_id").getAsString();
        level_title = jsonObject.get("level").getAsJsonObject().get("title").getAsString();
        image = jsonObject.get("image").getAsString();
    }
}
