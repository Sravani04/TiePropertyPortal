package com.viralandroid.tiepropertyportal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by T on 15-05-2017.
 */

public class CallbackCustomersAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    ArrayList<CallbackCustomers> callbackCustomers;
    public CallbackCustomersAdapter(Context context,ArrayList<CallbackCustomers>callbackCustomers){
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.callbackCustomers = callbackCustomers;
    }
    @Override
    public int getCount() {
        return callbackCustomers.size();
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        View item_view = inflater.inflate(R.layout.callback_items,null);
        TextView name = (TextView) item_view.findViewById(R.id.name);
        TextView email = (TextView) item_view.findViewById(R.id.email);
        TextView phone = (TextView) item_view.findViewById(R.id.phone);
        TextView property = (TextView) item_view.findViewById(R.id.property);
        TextView date = (TextView) item_view.findViewById(R.id.date);
        TextView message = (TextView) item_view.findViewById(R.id.message);

        name.setText(callbackCustomers.get(i).name);
        email.setText(callbackCustomers.get(i).email);
        phone.setText(callbackCustomers.get(i).phone);
        property.setText(callbackCustomers.get(i).property_title);
        date.setText(callbackCustomers.get(i).date);
        message.setText(callbackCustomers.get(i).message);
        return item_view;
    }
}
