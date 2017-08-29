package com.viralandroid.tiepropertyportal;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

/**
 * Created by yellowsoft on 13/7/17.
 */

public class ReportListAdapter extends BaseAdapter{
    LayoutInflater inflater;
    Context context;
    ArrayList<ReportsList> reportsLists;
    public ReportListAdapter(Context context,ArrayList<ReportsList>reportsLists){
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.reportsLists = reportsLists;
    }


    @Override
    public int getCount() {
        return reportsLists.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final View itemView = inflater.inflate(R.layout.reportlist_items,null);
        TextView task = (TextView) itemView.findViewById(R.id.task);
        TextView pending = (TextView) itemView.findViewById(R.id.pending);
        TextView tomorrow = (TextView) itemView.findViewById(R.id.tomorrow);
        TextView achiv = (TextView) itemView.findViewById(R.id.achievement);
        ImageView edit_report = (ImageView) itemView.findViewById(R.id.edit_report);
        TextView date  = (TextView) itemView.findViewById(R.id.date);

        task.setText(reportsLists.get(i).task);
        pending.setText(reportsLists.get(i).pending);
        tomorrow.setText(reportsLists.get(i).tomorrow);
        achiv.setText(reportsLists.get(i).achievement);
        date.setText(reportsLists.get(i).date);
        edit_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditReportListActivity.class);
                intent.putExtra("id", reportsLists.get(i).id);
                context.startActivity(intent);
            }
        });
        return itemView;
    }



}
