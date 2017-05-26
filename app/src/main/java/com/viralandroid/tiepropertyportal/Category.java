package com.viralandroid.tiepropertyportal;

import android.content.Context;

import com.google.gson.JsonObject;

/**
 * Created by T on 27-04-2017.
 */

public class Category {
    public String id,title;
    Boolean checked;

    public Category(JsonObject jsonObject, Context context,Boolean checked){
        id = jsonObject.get("id").getAsString();
        title = jsonObject.get("title").getAsString();
        this.checked = checked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getName() {
        return title;
    }
    public void setName(String title) {
        this.title = title;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }


}
