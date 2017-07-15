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
    public static final String area = "areas";
    public static final String category = "categories";
    public static final String city = "cities";
    public static final String callback = "callbacks";

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


    public static void SetCities(Context context,String cities){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(city,cities);
        editor.commit();
    }

    public static String GetCities(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(city,"-1");
    }

    public static void SetCategories(Context context,String categories){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(category,categories);
        editor.commit();
    }

    public static String GetCategories(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(category,"-1");
    }

    public static void SetAreas(Context context,String areas){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(area,areas);
        editor.commit();
    }

    public static String GetAreas(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(area,"-1");
    }




}
