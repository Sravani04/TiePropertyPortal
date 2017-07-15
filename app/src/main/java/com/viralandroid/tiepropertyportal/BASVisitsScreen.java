package com.viralandroid.tiepropertyportal;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.JsonArray;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

/**
 * Created by yellowsoft on 14/7/17.
 */

public class BASVisitsScreen extends Activity {
    ImageView back_btn;
    RecyclerView recyclerView;
    ArrayList<BASVisits> basVisitsesfrom_api;
    BASVisitScreenAdapter adapter;
    LinearLayout progress_holder;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bassite_visit_list);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        recyclerView = (RecyclerView) findViewById(R.id.bas_visits);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        basVisitsesfrom_api = new ArrayList<>();
        adapter = new BASVisitScreenAdapter(this,basVisitsesfrom_api);
        recyclerView.setAdapter(adapter);
        progress_holder = (LinearLayout) findViewById(R.id.progress_holder);
        progress_holder.setVisibility(View.GONE);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BASVisitsScreen.this.onBackPressed();
            }
        });

        get_bas_visits();

    }


    public void show_progress() {
        progress_holder.setVisibility(View.VISIBLE);
    }

    public void hide_progress(){
        progress_holder.setVisibility(View.GONE);
    }

    public void get_bas_visits(){
        show_progress();
        Ion.with(this)
                .load(Session.SERVER_URL+"ba_visits.php")
                .setBodyParameter("agent_id",Session.GetUserId(this))
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        hide_progress();
                        try {
                            for (int i = 0; i < result.size(); i++) {
                                BASVisits basVisits = new BASVisits(result.get(i).getAsJsonObject(), BASVisitsScreen.this);
                                basVisitsesfrom_api.add(basVisits);
                            }
                            adapter.notifyDataSetChanged();
                        }catch (Exception e1){
                            e1.printStackTrace();
                        }
                    }
                });
    }
}
