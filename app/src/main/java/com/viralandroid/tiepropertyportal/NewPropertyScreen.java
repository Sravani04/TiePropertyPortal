package com.viralandroid.tiepropertyportal;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

/**
 * Created by T on 16-05-2017.
 */

public class NewPropertyScreen extends Activity {
    ImageView back_btn,add_new_property;
    ListView listView;
    ArrayList<NewProperties> newPropertiesfrom_api;
    NewPropertyScreenAdapter adapter;
    ArrayList<Cities>citiesfrom_api;
    ArrayList<Areas> areasfrom_api;
    ArrayList<Category> categoriesfrom_api;
    String city_id,area_id,type_id,cityId;
    TextView city;
    TextView area,type;
    int position;
    SwipeRefreshLayout swipeRefreshLayout;
    LinearLayout progress_holder;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_property_list);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        add_new_property = (ImageView) findViewById(R.id.add_new_property);
        progress_holder = (LinearLayout) findViewById(R.id.progress_holder);
        progress_holder.setVisibility(View.GONE);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewPropertyScreen.this.onBackPressed();
            }
        });
        newPropertiesfrom_api = new ArrayList<>();
        areasfrom_api = new ArrayList<>();
        categoriesfrom_api = new ArrayList<>();
        citiesfrom_api = new ArrayList<>();
        listView = (ListView) findViewById(R.id.new_property_list);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);
        adapter = new NewPropertyScreenAdapter(this,newPropertiesfrom_api);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                finish();
                startActivity(getIntent());
            }
        });

        JsonParser jsonParser = new JsonParser();
        if (!Session.GetCities(NewPropertyScreen.this).equals("-1")){
            JsonArray jsonArray = (JsonArray) jsonParser.parse(Session.GetCities(NewPropertyScreen.this));
            for (int i=0;i<jsonArray.size();i++){
                Cities cities = new Cities(jsonArray.get(i).getAsJsonObject(),NewPropertyScreen.this);
                citiesfrom_api.add(cities);
            }
        }

        JsonParser parser = new JsonParser();
        if (!Session.GetCategories(NewPropertyScreen.this).equals("-1")){
            JsonArray array = (JsonArray) parser.parse(Session.GetCategories(NewPropertyScreen.this));
            for (int i=0;i<array.size();i++){
                Category category = new Category(array.get(i).getAsJsonObject(),NewPropertyScreen.this,false);
                categoriesfrom_api.add(category);
            }
        }

        add_new_property.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = LayoutInflater.from(getApplicationContext());
                View form = li.inflate(R.layout.add_property_items, null);
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(NewPropertyScreen.this);
                alertDialogBuilder.setView(form);
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                final EditText title = (EditText) form.findViewById(R.id.title);
                type = (TextView) form.findViewById(R.id.type);
                final EditText name = (EditText) form.findViewById(R.id.name);
                final EditText phone = (EditText) form.findViewById(R.id.phone);
                final EditText address = (EditText) form.findViewById(R.id.address);
                city = (TextView) form.findViewById(R.id.city);
                area = (TextView) form.findViewById(R.id.area);
                TextView submit_btn = (TextView) form.findViewById(R.id.submit_btn);
                ImageView close_btn = (ImageView) form.findViewById(R.id.close_btn);
                city.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Dialog dialog = onCreateDialogSingleChoice();
                        dialog.show();
                    }
                });
                area.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Dialog dialog = onCreateDialogSingleChoiceAreas();
                        dialog.show();
                    }
                });

                type.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Dialog dialog = onCreateDialogSingleChoiceCategory();
                        dialog.show();
                    }
                });

                submit_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String title_string = title.getText().toString();
                        String type_string = type_id;
                        String name_string = name.getText().toString();
                        String phone_string = phone.getText().toString();
                        String address_string = address.getText().toString();
                        String city_string = city_id;
                        String area_string = area_id;
                        if (title_string.equals("")){
                            Toast.makeText(NewPropertyScreen.this,"Please Enter Title",Toast.LENGTH_SHORT).show();
                            title.requestFocus();
                        }else if (type_string.equals("")){
                            Toast.makeText(NewPropertyScreen.this,"Please Enter Type",Toast.LENGTH_SHORT).show();
                            type.requestFocus();
                        }else if (name_string.equals("")){
                            Toast.makeText(NewPropertyScreen.this,"Please Enter Name",Toast.LENGTH_SHORT).show();
                            name.requestFocus();
                        }else if (phone_string.equals("")){
                            Toast.makeText(NewPropertyScreen.this,"Please Enter Phone",Toast.LENGTH_SHORT).show();
                            phone.requestFocus();
                        }else if (address_string.equals("")){
                            Toast.makeText(NewPropertyScreen.this,"Please Enter Address",Toast.LENGTH_SHORT).show();
                            address.requestFocus();
                        }else if (city_string.equals("")){
                            Toast.makeText(NewPropertyScreen.this,"Please Enter City",Toast.LENGTH_SHORT).show();
                            city.requestFocus();
                        }else if (area_string.equals("")){
                            Toast.makeText(NewPropertyScreen.this,"Please Enter Area",Toast.LENGTH_SHORT).show();
                            area.requestFocus();
                        }else {
                            Ion.with(getApplicationContext())
                                    .load(Session.SERVER_URL+"new-property.php")
                                    .setBodyParameter("agent_id",Session.GetUserId(getApplicationContext()))
                                    .setBodyParameter("title",title_string)
                                    .setBodyParameter("type",type_string)
                                    .setBodyParameter("name",name_string)
                                    .setBodyParameter("phone",phone_string)
                                    .setBodyParameter("address",address_string)
                                    .setBodyParameter("city",city_string)
                                    .setBodyParameter("area",area_string)
                                    .asJsonObject()
                                    .setCallback(new FutureCallback<JsonObject>() {
                                        @Override
                                        public void onCompleted(Exception e, JsonObject result) {
                                            if (result.get("status").getAsString().equals("Success")){
                                                Log.e("resulr",result.toString());
                                                Toast.makeText(getApplicationContext(),result.get("message").getAsString(),Toast.LENGTH_SHORT).show();
                                                finish();
                                            }else {
                                                Toast.makeText(getApplicationContext(),result.get("message").getAsString(),Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    }
                });

                final AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                close_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

            }
        });
        get_new_properties_list();
        //get_cities();

        //get_categories();


    }

    public void show_progress(){
        progress_holder.setVisibility(View.VISIBLE);
    }

    public void hide_progress(){
        progress_holder.setVisibility(View.GONE);
    }

    public void get_new_properties_list(){
        show_progress();
        Ion.with(getApplicationContext())
                .load(Session.SERVER_URL+"new-properties.php")
                .setBodyParameter("agent_id",Session.GetUserId(getApplicationContext()))
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        hide_progress();
                        for (int i=0;i<result.size();i++){
                            NewProperties newProperties = new NewProperties(result.get(i).getAsJsonObject(),getApplicationContext());
                            newPropertiesfrom_api.add(newProperties);
                        }
                        adapter.notifyDataSetChanged();

                    }
                });
    }



    public void get_areas(){
        show_progress();
        Ion.with(this)
                .load(Session.SERVER_URL+"areas.php")
                .setBodyParameter("city",city_id)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        hide_progress();
                        try {
                            for (int i = 0; i < result.size(); i++) {
                                Areas areas = new Areas(result.get(i).getAsJsonObject(), getApplicationContext(),false);
                                areasfrom_api.add(areas);
                            }
                        }catch (Exception e1){
                            e1.printStackTrace();
                        }
                    }
                });
    }




    public Dialog onCreateDialogSingleChoice() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final CharSequence[] array = new CharSequence[citiesfrom_api.size()];
        for(int i=0;i<citiesfrom_api.size();i++){

            array[i] = citiesfrom_api.get(i).title;
        }
        builder.setTitle("Select City").setSingleChoiceItems(array, 0, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        String selectedItem = array[i].toString();
                        Log.e("select",selectedItem);
                        city.setText(selectedItem);
                        city_id = citiesfrom_api.get(i).id;
                        areasfrom_api.clear();
                        get_areas();

                    }
                })

                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        return builder.create();
    }

    public Dialog onCreateDialogSingleChoiceAreas() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final CharSequence[] array = new CharSequence[areasfrom_api.size()];
        for(int i=0;i<areasfrom_api.size();i++){

            array[i] = areasfrom_api.get(i).title;
        }
        builder.setTitle("Select Area").setSingleChoiceItems(array, 0, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int i) {
                String selectedItem = array[i].toString();
                Log.e("select",selectedItem);
                area.setText(selectedItem);
                area_id = areasfrom_api.get(i).id;

            }
        }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        return builder.create();
    }


    public Dialog onCreateDialogSingleChoiceCategory() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final CharSequence[] array = new CharSequence[categoriesfrom_api.size()];
        for(int i=0;i<categoriesfrom_api.size();i++){

            array[i] = categoriesfrom_api.get(i).title;
        }
        builder.setTitle("Select Area").setSingleChoiceItems(array, 0, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int i) {
                String selectedItem = array[i].toString();
                Log.e("select",selectedItem);
                type.setText(selectedItem);
                type_id = categoriesfrom_api.get(i).id;


            }
        }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

            }
        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        return builder.create();
    }
}
