package com.viralandroid.tiepropertyportal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by T on 16-05-2017.
 */

public class NewPropertyScreenAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    ArrayList<NewProperties> newProperties;

    public NewPropertyScreenAdapter(Context context,ArrayList<NewProperties> newProperties){
        this.context = context;
        this.newProperties = newProperties;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return newProperties.size();
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
        final View item_view = inflater.inflate(R.layout.new_property_list_items,null);
        TextView title = (TextView) item_view.findViewById(R.id.title);
        TextView name = (TextView) item_view.findViewById(R.id.name);
        TextView phone = (TextView) item_view.findViewById(R.id.phone);
        TextView address = (TextView) item_view.findViewById(R.id.address);
        TextView city = (TextView) item_view.findViewById(R.id.city);
        TextView area = (TextView) item_view.findViewById(R.id.area);
        TextView type = (TextView) item_view.findViewById(R.id.type);
        title.setText(newProperties.get(i).title);
        name.setText(newProperties.get(i).name);
        phone.setText(newProperties.get(i).phone);
        address.setText(newProperties.get(i).address);
        city.setText(newProperties.get(i).city_title);
        area.setText(newProperties.get(i).area_title);
        type.setText(newProperties.get(i).type_title);
        return item_view;
    }
}
