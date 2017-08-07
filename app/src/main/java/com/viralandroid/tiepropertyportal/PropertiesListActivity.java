package com.viralandroid.tiepropertyportal;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import java.util.ArrayList;

/**
 * Created by T on 26-05-2017.
 */

public class PropertiesListActivity extends Activity implements AbsListView.OnScrollListener{
    ImageView back_btn,drop_btn;
    ListView listView;
    PropertiesListAdapter adapter;
    ArrayList<Properties> propertiesfrom_api;
    ArrayList<Cities> citiesfrom_api;
    LinearLayout progress_holder;
    CityAdapter cityAdapter;
    TextView city;
    String citiesId,id;

    private  int previouslast;
    TextView footer_text;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        citiesfrom_api = new ArrayList<>();
        setContentView(R.layout.property_list);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        listView = (ListView) findViewById(R.id.properties_list);

        progress_holder = (LinearLayout) findViewById(R.id.progress_holder);
        progress_holder.setVisibility(View.GONE);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PropertiesListActivity.this.onBackPressed();
            }
        });

        if (getIntent()!=null && getIntent().hasExtra("city_id")){
            id = getIntent().getStringExtra("city_id");
        }
        city = (TextView) findViewById(R.id.city);
        drop_btn = (ImageView) findViewById(R.id.drop_btn);

        city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_cities();
            }
        });

        drop_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_cities();
            }
        });


        propertiesfrom_api = new ArrayList<>();

        adapter = new PropertiesListAdapter(this,propertiesfrom_api);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                openApp(PropertiesListActivity.this,"in.tieproperty.app",propertiesfrom_api.get(i));
                get_single_property(propertiesfrom_api.get(i).id);
            }


        });
        listView.setOnScrollListener(this);

        View footerView =  ((LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_layout, null, false);
        listView.addFooterView(footerView);
        footer_text = (TextView) footerView.findViewById(R.id.footer_text);


        JsonParser jsonParser = new JsonParser();
        if (!Session.GetCities(PropertiesListActivity.this).equals("-1")){
            JsonArray jsonArray = (JsonArray) jsonParser.parse(Session.GetCities(PropertiesListActivity.this));
            for (int i=0;i<jsonArray.size();i++){
                Cities cities = new Cities(jsonArray.get(i).getAsJsonObject(),PropertiesListActivity.this);
                citiesfrom_api.add(cities);
            }
        }

        city.setText("All Cities");
        get_properties();




    }

    public void show_progress(){
        progress_holder.setVisibility(View.VISIBLE);
    }

    public void hide_progress(){
        progress_holder.setVisibility(View.GONE);
    }



    public static boolean openApp(Context context, String packageName, OldProperties property) {
       // PackageManager manager = context.getPackageManager();
        //Intent i = manager.getLaunchIntentForPackage(packageName);
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(packageName,"com.viralandroid.tieproperty.PropertyDetailPage"));
        intent.putExtra("propertystr",property.jsonObject.toString());
//        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        context.startActivity(intent);
        return true;
    }


    public void get_properties(){
        if (propertiesfrom_api.size() == 0)
        show_progress();
        Ion.with(this)
                .load(Session.SERVER_URL+"ad-properties.php")
                .setBodyParameter("agent_id",Session.GetUserId(this))
                .setBodyParameter("city",citiesId)
                .setBodyParameter("start",String.valueOf(propertiesfrom_api.size()))
                .setBodyParameter("end","10")
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        try {
                           hide_progress();
                            Log.e("trend_prop",result.toString());
                            if (result.size()<10)
                                footer_text.setText("End of Properties List");
                            else
                                footer_text.setText("Loading Properties List");
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

    public void get_single_property(String id) {
        show_progress();
        Ion.with(this)
                .load(Session.SERVER_URL + "properties.php")
                .setBodyParameter("property_id", id)
                .asJsonArray()
                .withResponse()
                .setCallback(new FutureCallback<Response<JsonArray>>() {
                    @Override
                    public void onCompleted(Exception e, Response<JsonArray> result) {

                        Log.e("response", String.valueOf(result.getResult().size()));
                        try {
                            hide_progress();


                            if (e != null) {
                                e.printStackTrace();
                                Log.e("error", e.getLocalizedMessage());

                            } else
                                try {
                                    for (int i = 0; i < result.getResult().size(); i++) {
                                        Log.e("trend_prop_resp", result.getRequest().toString());
                                        OldProperties oldProperties = new OldProperties(result.getResult().get(i).getAsJsonObject(), PropertiesListActivity.this);
//                                        Intent intent = new Intent(TrendingPropertyActivity.this,PropertyDetailPage.class);
//                                        intent.putExtra("property",properties);
//                                        intent.putExtra("mobile",mobile);
//                                        startActivity(intent);
                                        openApp(PropertiesListActivity.this, "in.tiepropertymainapp.app", oldProperties);
                                    }
                                    adapter.notifyDataSetChanged();

                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }


                    }
                });
    }


    private void show_cities(){

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(PropertiesListActivity.this);


// ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater=null;
        inflater = (LayoutInflater) PropertiesListActivity.this.
                getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.select_city_popup, null);

        dialogBuilder.setView(dialogView);
        final AlertDialog alertDialog = dialogBuilder.create();
        TextView pop_title = (TextView) dialogView.findViewById(R.id.pop_up_title);
        ListView listView = (ListView) dialogView.findViewById(R.id.city_list);
        cityAdapter = new CityAdapter(PropertiesListActivity.this,citiesfrom_api);

        listView.setAdapter(cityAdapter);
        pop_title.setText("Select City");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(cityAdapter!=null) {
                    city.setText(citiesfrom_api.get(i).title);
                    citiesId = citiesfrom_api.get(i).id;
                    propertiesfrom_api.clear();
                    adapter.notifyDataSetChanged();
                    get_properties();
                    alertDialog.dismiss();
                }
            }
        });

        final EditText search_city = (EditText) dialogView.findViewById(R.id.search_city);
        TextView save = (TextView) dialogView.findViewById(R.id.pop_save_btn);
        TextView cancel = (TextView) dialogView.findViewById(R.id.pop_cancel_btn);
        LinearLayout main_cat_select = (LinearLayout) dialogView.findViewById(R.id.select_product_category);
        main_cat_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




            }
        });

        search_city.setText("");

        search_city.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(cityAdapter!=null)
                    cityAdapter.getFilter().filter(charSequence);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });



        cityAdapter.notifyDataSetChanged();


        alertDialog.show();


    }


    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {
        int lastitem = i + i1;
        if (lastitem == i2-3){
            if (previouslast!=lastitem){
                Log.e("result","last");
                get_properties();
                previouslast = lastitem;
            }
        }
    }
}
