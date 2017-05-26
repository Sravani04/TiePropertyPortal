package com.viralandroid.tiepropertyportal;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by T on 26-05-2017.
 */

public class VisitScreen extends Activity {
    ImageView back_btn,add_visit;
    ListView listView;
    TextView time;
    VisitScreenAdapter adapter;
    TextView property;
    String prop_id;
    Commissions commissions_obj;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visit_list);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VisitScreen.this.onBackPressed();
            }
        });

        if (getIntent()!=null && getIntent().hasExtra("commissions")){
            commissions_obj = (Commissions) getIntent().getSerializableExtra("commissions");
            Log.e("comm",commissions_obj.sites.toString());
        }

        listView = (ListView) findViewById(R.id.visit_list);
        adapter = new VisitScreenAdapter(this,commissions_obj);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });


    }






}
