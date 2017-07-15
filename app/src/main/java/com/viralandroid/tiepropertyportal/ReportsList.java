package com.viralandroid.tiepropertyportal;

import android.content.Context;

import com.google.gson.JsonObject;

/**
 * Created by yellowsoft on 13/7/17.
 */

public class ReportsList {
    public String id,task,pending,tomorrow,achievement,date;
    public ReportsList(JsonObject jsonObject, Context context){
        id = jsonObject.get("id").getAsString();
        task = jsonObject.get("task").getAsString();
        pending = jsonObject.get("pending").getAsString();
        tomorrow = jsonObject.get("tomorrow").getAsString();
        achievement = jsonObject.get("achievement").getAsString();
        date = jsonObject.get("date").getAsString();
    }
}
