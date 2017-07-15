package com.viralandroid.tiepropertyportal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by T on 26-05-2017.
 */

public class PropertiesListAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    ArrayList<Properties> properties;

    public PropertiesListAdapter(Context context,ArrayList<Properties> properties){
        this.context = context;
        this.properties = properties;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return properties.size();
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
        View item_view = inflater.inflate(R.layout.property_items,null);
        ImageView property_image = (ImageView) item_view.findViewById(R.id.property_image);
        TextView title = (TextView) item_view.findViewById(R.id.title);
        TextView code = (TextView) item_view.findViewById(R.id.code);
        TextView city = (TextView) item_view.findViewById(R.id.city);
        TextView area = (TextView) item_view.findViewById(R.id.area);

        Picasso.with(context).load(properties.get(i).image).into(property_image);
        title.setText(properties.get(i).title);
        code.setText(properties.get(i).prop_code);
        city.setText(properties.get(i).location);
        area.setText(properties.get(i).area);


        return item_view;
    }
}
