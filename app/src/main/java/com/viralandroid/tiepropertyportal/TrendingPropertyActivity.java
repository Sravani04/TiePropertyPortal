package com.viralandroid.tiepropertyportal;

import android.app.Activity;
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
 * Created by yellowsoft on 13/7/17.
 */

public class TrendingPropertyActivity extends Activity  {
    ImageView close_btn,drop_btn;
    ListView listView;
    TrendingPropertiesAdapter trendingPropertiesAdapter;
    ArrayList<TrendingProperties> trendingPropertiesfrom_api;
    ArrayList<Properties> propertiesfrom_api;
    Cities cities;
    String city_id,title,area_id,catid,pricefrom,priceto,mobile;
    LinearLayout progress_holder;
    MainActivity mainActivity;
    TextView city;
    ArrayList<Cities> categories;
    CityAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trending_property_list);
        trendingPropertiesfrom_api = new ArrayList<>();
        propertiesfrom_api = new ArrayList<>();
        categories = new ArrayList<>();
        listView  = (ListView) findViewById(R.id.trending_property_list);
        trendingPropertiesAdapter = new TrendingPropertiesAdapter(this,trendingPropertiesfrom_api,propertiesfrom_api,TrendingPropertyActivity.this);
        progress_holder = (LinearLayout) findViewById(R.id.progress_holder);
        progress_holder.setVisibility(View.GONE);
        city  = (TextView) findViewById(R.id.city);
        drop_btn = (ImageView) findViewById(R.id.drop_btn);
        close_btn = (ImageView) findViewById(R.id.close_btn);
        listView.setAdapter(trendingPropertiesAdapter);

        if (getIntent()!=null && getIntent().hasExtra("id")){
            city_id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            area_id = getIntent().getStringExtra("areas_id");
            catid = getIntent().getStringExtra("cat_id");
            pricefrom = getIntent().getStringExtra("price_from");
            priceto = getIntent().getStringExtra("price_to");
            mobile = getIntent().getStringExtra("phone");

        }

        city.setText(title);
        drop_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_alert_edit();
            }
        });

        city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_alert_edit();
            }
        });

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                get_single_property(trendingPropertiesfrom_api.get(i).property_id);
            }
        });

        JsonParser jsonParser = new JsonParser();
        if (!Session.GetCities(TrendingPropertyActivity.this).equals("-1")) {
            JsonArray parse = (JsonArray) jsonParser.parse(Session.GetCities(TrendingPropertyActivity.this));
            Log.e("cities",parse.toString());
            for (int i = 0; i < parse.size(); i++) {
                Cities temp = new Cities(parse.get(i).getAsJsonObject(), TrendingPropertyActivity.this);
                categories.add(temp);
            }
        }
        get_trending_properties();

    }

    public void show_progress(){
        progress_holder.setVisibility(View.VISIBLE);
    }

    public void hide_progress(){
        progress_holder.setVisibility(View.GONE);
    }

    public void get_trending_properties(){
        Ion.with(this)
                .load(Session.SERVER_URL+"trending-properties.php")
                .setBodyParameter("agent_id",Session.GetUserId(this))
                .setBodyParameter("city",city_id)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        try {
                            Log.e("trend_prop",result.toString());
                            for (int i = 0; i < result.size(); i++) {
                                TrendingProperties trendingProperties = new TrendingProperties(result.get(i).getAsJsonObject(), TrendingPropertyActivity.this);
                                trendingPropertiesfrom_api.add(trendingProperties);
                            }
                            trendingPropertiesAdapter.notifyDataSetChanged();
                        }catch (Exception e1){
                            e1.printStackTrace();
                        }
                    }
                });
    }



    public void show_alert_edit(){

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(TrendingPropertyActivity.this);


// ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater=null;
        inflater = (LayoutInflater) TrendingPropertyActivity.this.
                getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.select_city_popup, null);

        dialogBuilder.setView(dialogView);
        final AlertDialog alertDialog = dialogBuilder.create();
        TextView pop_title = (TextView) dialogView.findViewById(R.id.pop_up_title);
        final ListView listView = (ListView) dialogView.findViewById(R.id.city_list);
        adapter = new CityAdapter(TrendingPropertyActivity.this,categories);

        listView.setAdapter(adapter);
        pop_title.setText("Select City");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(adapter!=null) {
                    city.setText(categories.get(i).title);
                    city_id = categories.get(i).id;
                    trendingPropertiesfrom_api.clear();
                    get_trending_properties();
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

                if(adapter!=null)
                    adapter.getFilter().filter(charSequence);

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



        adapter.notifyDataSetChanged();


        alertDialog.show();


    }


    public void get_single_property(String id) {
        show_progress();
        Ion.with(this)
                .load(Session.SERVER_URL + "properties.php")
                .setBodyParameter("property_id", id)
                .setBodyParameter("city", city_id)
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
                                        OldProperties oldProperties = new OldProperties(result.getResult().get(i).getAsJsonObject(), TrendingPropertyActivity.this);
//                                        Intent intent = new Intent(TrendingPropertyActivity.this,PropertyDetailPage.class);
//                                        intent.putExtra("property",properties);
//                                        intent.putExtra("mobile",mobile);
//                                        startActivity(intent);
                                        openApp(TrendingPropertyActivity.this, "in.tiepropertymainapp.app", oldProperties);
                                    }
                                    trendingPropertiesAdapter.notifyDataSetChanged();

                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }


                    }
                });
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


    void new_to_old_prfop(){



    }

}
