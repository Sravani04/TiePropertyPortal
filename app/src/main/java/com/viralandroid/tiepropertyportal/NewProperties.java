package com.viralandroid.tiepropertyportal;

import android.content.Context;

import com.google.gson.JsonObject;

/**
 * Created by T on 16-05-2017.
 */

public class NewProperties {
    public String id,title,type_id,type_title,name,phone,address,city_id,city_title,area_id,area_title;
    public NewProperties(JsonObject jsonObject, Context context){
        id = jsonObject.get("id").getAsString();
        title = jsonObject.get("title").getAsString();
        if (jsonObject.has("type_id")) {
            type_id = jsonObject.get("type").getAsJsonObject().get("type_id").getAsString();
        }else {
            type_id ="0";
        }
        if (jsonObject.has("title")) {
            type_title = jsonObject.get("type").getAsJsonObject().get("title").getAsString();
        }else {
            type_title=null;
        }
        name = jsonObject.get("name").getAsString();
        phone = jsonObject.get("phone").getAsString();
        address = jsonObject.get("address").getAsString();
        if (jsonObject.has("city_id")) {
            city_id = jsonObject.get("city").getAsJsonObject().get("city_id").getAsString();
        }else {
            city_id="0";
        }
        if (jsonObject.has("title")) {
            city_title = jsonObject.get("city").getAsJsonObject().get("title").getAsString();
        }else {
            city_title=null;
        }
        if (jsonObject.has("area_id")) {
            area_id = jsonObject.get("area").getAsJsonObject().get("area_id").getAsString();
        }else {
            area_id="0";
        }
        if (jsonObject.has("title")) {
            area_title = jsonObject.get("area").getAsJsonObject().get("title").getAsString();
        }else {
            area_title = null;
        }
    }
}
