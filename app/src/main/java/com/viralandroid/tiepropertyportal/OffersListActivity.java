package com.viralandroid.tiepropertyportal;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.JsonArray;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

/**
 * Created by T on 25-05-2017.
 */

public class OffersListActivity extends Activity {
    ImageView back_btn;
    ListView listView;
    OffersListAdapter adapter;
    ArrayList<OffersList> offersListsfrom_api;
    LinearLayout progress_holder;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offers_list);
        offersListsfrom_api = new ArrayList<>();
        listView = (ListView) findViewById(R.id.offer_list);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        progress_holder = (LinearLayout) findViewById(R.id.progress_holder);
        progress_holder.setVisibility(View.GONE);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OffersListActivity.this.onBackPressed();
            }
        });

        adapter = new OffersListAdapter(this,offersListsfrom_api);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        get_offers_lists();


    }

    public void show_progress(){
        progress_holder.setVisibility(View.VISIBLE);
    }

    public void hide_progress(){
        progress_holder.setVisibility(View.GONE);
    }

    public void get_offers_lists(){
        show_progress();
        Ion.with(this)
                .load(Session.SERVER_URL+"offers.php")
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        hide_progress();
                        for (int i=0;i<result.size();i++){
                            OffersList offersList = new OffersList(result.get(i).getAsJsonObject(),OffersListActivity.this);
                            offersListsfrom_api.add(offersList);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}
