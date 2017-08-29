package com.viralandroid.tiepropertyportal;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by yellowsoft on 29/8/17.
 */

public class ReportDetail extends Activity {
    ImageView close_btn;
    TextView task_desc,pending_desc,tommorrow_desc,achiv_desc;
    String task,pending,tomm,achiv;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_detail);
        close_btn = (ImageView) findViewById(R.id.close_btn);
        task_desc = (TextView) findViewById(R.id.task_desc);
        pending_desc = (TextView) findViewById(R.id.pending_desc);
        tommorrow_desc = (TextView) findViewById(R.id.tomorrow_desc);
        achiv_desc = (TextView) findViewById(R.id.achiv_desc);
        if (getIntent()!=null && getIntent().hasExtra("task")){
            task = getIntent().getStringExtra("task");
            pending = getIntent().getStringExtra("pending");
            tomm = getIntent().getStringExtra("tomm");
            achiv = getIntent().getStringExtra("achiv");
        }

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        task_desc.setText(task);
        pending_desc.setText(pending);
        tommorrow_desc.setText(tomm);
        achiv_desc.setText(achiv);


    }
}
