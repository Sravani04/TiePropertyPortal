package com.viralandroid.tiepropertyportal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by T on 18-05-2017.
 */

public class SiteVisitAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    ArrayList<SiteVisits> siteVisits;
    public SiteVisitAdapter(Context context,ArrayList<SiteVisits> siteVisits){
        this.context = context;
        this.siteVisits = siteVisits;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return siteVisits.size();
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
    public View getView(int position, View view, ViewGroup viewGroup) {
        View item_view = inflater.inflate(R.layout.site_visit_items,null);
        TextView name= (TextView) item_view.findViewById(R.id.name);
        TextView phone = (TextView) item_view.findViewById(R.id.phone);
        TextView address = (TextView) item_view.findViewById(R.id.address);
        TextView date_time = (TextView) item_view.findViewById(R.id.date_time);
        TextView prop = (TextView) item_view.findViewById(R.id.prop);
        TextView status = (TextView) item_view.findViewById(R.id.status);
        name.setText(siteVisits.get(position).name);
        phone.setText(siteVisits.get(position).phone);
        address.setText(siteVisits.get(position).address);
        date_time.setText(siteVisits.get(position).date +"  "+ siteVisits.get(position).time);
        prop.setText(siteVisits.get(position).title +"  "+ siteVisits.get(position).code);
        return item_view;
    }
}
