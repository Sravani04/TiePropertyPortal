package com.viralandroid.tiepropertyportal;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by T on 25-05-2017.
 */

public class Session {
    public static String SERVER_URL = "https://tieproperty.in/api/";

    public  static  final String mem_id="mem_id";
    public  static  final String mem_name="mem_name";
    public  static  final String level="level";

    public  static void SetUserId(Context context, String id){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(mem_id,id);
        editor.commit();
    }

    public  static String GetUserId(Context context) {
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(mem_id,"-1");
    }

    public  static void SetLevelId(Context context, String id){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(level,id);
        editor.commit();
    }

    public  static String GetLevelId(Context context) {
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(level,"-1");
    }


}
