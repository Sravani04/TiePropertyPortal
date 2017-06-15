package com.viralandroid.tiepropertyportal;

import android.content.Context;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by yellowsoft on 14/6/17.
 */

public class HomeLoans implements Serializable {
    public String id,name,address,phone,email,property,type,bank_id,bank_title;
    public HomeLoans(JsonObject jsonObject, Context context){
        id = jsonObject.get("id").getAsString();
        name = jsonObject.get("name").getAsString();
        address = jsonObject.get("address").getAsString();
        phone = jsonObject.get("phone").getAsString();
        email = jsonObject.get("email").getAsString();
        property = jsonObject.get("property").getAsString();
        type = jsonObject.get("type").getAsString();
        bank_id = jsonObject.get("bank").getAsJsonObject().get("bank_id").getAsString();
        bank_title = jsonObject.get("bank").getAsJsonObject().get("title").getAsString();
    }
}
