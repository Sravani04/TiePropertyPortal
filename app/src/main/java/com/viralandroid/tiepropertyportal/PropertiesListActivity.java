package com.viralandroid.tiepropertyportal;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.JsonArray;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

/**
 * Created by T on 26-05-2017.
 */

public class PropertiesListActivity extends Activity {
    ImageView back_btn;
    ListView listView;
    PropertiesListAdapter adapter;
    ArrayList<Properties> propertiesfrom_api;
    Cities cities;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.property_list);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        listView = (ListView) findViewById(R.id.properties_list);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PropertiesListActivity.this.onBackPressed();
            }
        });

        propertiesfrom_api = new ArrayList<>();
        adapter = new PropertiesListAdapter(this,propertiesfrom_api);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openApp(PropertiesListActivity.this,"com.viralandroid.tieproperty",propertiesfrom_api.get(i));
            }


        });

        get_properties();

    }

    public void get_properties(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Ion.with(this)
                .load(Session.SERVER_URL+"properties.php")
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        if (progressDialog!=null)
                            progressDialog.dismiss();
                        try {
                            for (int i = 0; i < result.size(); i++) {
                                Properties properties = new Properties(result.get(i).getAsJsonObject(), PropertiesListActivity.this);
                                propertiesfrom_api.add(properties);
                            }

                            adapter.notifyDataSetChanged();
                        }catch (Exception e1){
                            e1.printStackTrace();
                        }
                    }
                });
    }

    public static boolean openApp(Context context, String packageName, Properties property) {
       // PackageManager manager = context.getPackageManager();
        //Intent i = manager.getLaunchIntentForPackage(packageName);
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(packageName,"com.viralandroid.tieproperty.PropertyDetailPage"));
        intent.putExtra("propertystr",property.jsonObject.toString());
//        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        context.startActivity(intent);
        return true;
    }
}
