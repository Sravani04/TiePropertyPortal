package com.viralandroid.tiepropertyportal;

import android.content.Context;

import com.google.gson.JsonObject;

/**
 * Created by T on 15-05-2017.
 */



public class CallbackCustomers {
    public String id,name,email,phone,date,message,property_id,property_title,property_code;
    public CallbackCustomers(JsonObject jsonObject, Context context){
        id = jsonObject.get("id").getAsString();
        name = jsonObject.get("name").getAsString();
        email = jsonObject.get("email").getAsString();
        phone = jsonObject.get("phone").getAsString();
        date = jsonObject.get("date").getAsString();
        message = jsonObject.get("message").getAsString();
        try {
            property_id = jsonObject.get("property").getAsJsonObject().get("property_id").getAsString();
            property_title = jsonObject.get("property").getAsJsonObject().get("title").getAsString();
            if (jsonObject.has("code")) {
                property_code = jsonObject.get("property").getAsJsonObject().get("code").getAsString();
            } else {
                property_code = "null";
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
