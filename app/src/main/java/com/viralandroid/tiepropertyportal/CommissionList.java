package com.viralandroid.tiepropertyportal;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * Created by T on 17-05-2017.
 */

public class CommissionList extends Activity {
    ImageView back_btn;
    ListView listView;
    CommissionsPageAdapter adapter;
    Commissions commissions_obj;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commission_list);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        listView = (ListView) findViewById(R.id.commissions_list);
        try {
            if (getIntent() != null && getIntent().hasExtra("commissions")) {
                commissions_obj = (Commissions) getIntent().getSerializableExtra("commissions");
                Log.e("commm", String.valueOf(commissions_obj.commissionLists.size()));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        adapter = new CommissionsPageAdapter(CommissionList.this,commissions_obj);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommissionList.this.onBackPressed();
            }
        });
    }




}
