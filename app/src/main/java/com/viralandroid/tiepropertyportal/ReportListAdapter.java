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

public class ReportListAdapter extends RecyclerView.Adapter<ReportListAdapter.SimpleViewHolder>{
    LayoutInflater inflater;
    Context context;
    ArrayList<ReportsList> reportsLists;
    public ReportListAdapter(Context context,ArrayList<ReportsList>reportsLists){
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.reportsLists = reportsLists;
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder{
        TextView task,pending,tomorrow,achievement,date;
        ImageView edit_report;
        public SimpleViewHolder(View view){
            super(view);
            task = (TextView) view.findViewById(R.id.task);
            pending = (TextView) view.findViewById(R.id.pending);
            tomorrow = (TextView) view.findViewById(R.id.tomorrow);
            achievement = (TextView) view.findViewById(R.id.achievement);
            edit_report = (ImageView) view.findViewById(R.id.edit_report);
            date = (TextView) view.findViewById(R.id.date);

        }
    }



    @Override
    public ReportListAdapter.SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.reportlist_items, parent, false);
        return new ReportListAdapter.SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReportListAdapter.SimpleViewHolder holder, final int position) {
        holder.task.setText(reportsLists.get(position).task);
        holder.pending.setText(reportsLists.get(position).pending);
        holder.tomorrow.setText(reportsLists.get(position).tomorrow);
        holder.achievement.setText(reportsLists.get(position).achievement);
        holder.date.setText(reportsLists.get(position).date);

        holder.edit_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,EditReportListActivity.class);
                intent.putExtra("id",reportsLists.get(position).id);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return reportsLists.size();
    }

}
