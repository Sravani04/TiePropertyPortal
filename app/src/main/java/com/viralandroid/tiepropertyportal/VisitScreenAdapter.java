package com.viralandroid.tiepropertyportal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by T on 26-05-2017.
 */

public class VisitScreenAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    Commissions commissions;

    public VisitScreenAdapter(Context context, Commissions commissions) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.commissions = commissions;
    }

    @Override
    public int getCount() {
        return commissions.sites.size();
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
        View item_view = inflater.inflate(R.layout.visit_items, null);
        TextView name = (TextView) item_view.findViewById(R.id.name);
        TextView phone = (TextView) item_view.findViewById(R.id.phone);
        TextView address = (TextView) item_view.findViewById(R.id.address);
        TextView date = (TextView) item_view.findViewById(R.id.date);
        TextView time = (TextView) item_view.findViewById(R.id.times);
        TextView prop = (TextView) item_view.findViewById(R.id.prop);
        TextView amount = (TextView) item_view.findViewById(R.id.amount);
        TextView status = (TextView) item_view.findViewById(R.id.status);
        name.setText(commissions.sites.get(position).name);
        phone.setText(commissions.sites.get(position).phone);
        address.setText(commissions.sites.get(position).address);
        date.setText(commissions.sites.get(position).date);
        time.setText(commissions.sites.get(position).time);
        prop.setText(commissions.sites.get(position).property);
        amount.setText(commissions.sites.get(position).amount);
        status.setText(commissions.sites.get(position).status);
        return item_view;
    }
}

