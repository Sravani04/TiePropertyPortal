package com.viralandroid.tiepropertyportal;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

/**
 * Created by yellowsoft on 13/7/17.
 */

public class SalesListActivity extends Activity {
    ImageView back_btn;
    RecyclerView recyclerView;
    SalesListAdapter adapter;
    ArrayList<SalesList> salesListsfrom_api;
    int ASK_MULTIPLE_PERMISSION_REQUEST_CODE;
    LinearLayout progress_holder;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sales_list);
        back_btn = (ImageView) findViewById(R.id.back_btn);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SalesListActivity.this.onBackPressed();
            }
        });

        progress_holder = (LinearLayout) findViewById(R.id.progress_holder);
        progress_holder.setVisibility(View.GONE);
        recyclerView = (RecyclerView) findViewById(R.id.sales_list);
        salesListsfrom_api = new ArrayList<>();

        adapter = new SalesListAdapter(this,salesListsfrom_api);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        get_sales();

    }

    public void show_progress(){
        progress_holder.setVisibility(View.VISIBLE);
    }

    public void hide_progress(){
        progress_holder.setVisibility(View.GONE);
    }

    public void get_sales(){
        show_progress();
        Ion.with(this)
                .load(Session.SERVER_URL+"sales.php")
                .setBodyParameter("agent_id",Session.GetUserId(getApplicationContext()))
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        hide_progress();
                        for (int i=0;i<result.size();i++){
                            SalesList salesList = new SalesList(result.get(i).getAsJsonObject(),getApplicationContext());
                            salesListsfrom_api.add(salesList);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });

    }

}
