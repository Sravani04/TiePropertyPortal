package com.viralandroid.tiepropertyportal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

/**
 * Created by T on 25-05-2017.
 */

public class OffersListAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    ArrayList<OffersList> offersLists;

    public OffersListAdapter(Context context,ArrayList<OffersList> offersLists){
        this.context = context;
        this.offersLists = offersLists;
        inflater = LayoutInflater.from(context);

    }
    @Override
    public int getCount() {
        return offersLists.size();
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
        View item_view = inflater.inflate(R.layout.offers_list_items,null);
        TextView title = (TextView) item_view.findViewById(R.id.title);
        TextView start_date = (TextView) item_view.findViewById(R.id.start_date);
        TextView end_date = (TextView) item_view.findViewById(R.id.end_date);
        TextView description = (TextView) item_view.findViewById(R.id.description);
        ImageView item_image =(ImageView) item_view.findViewById(R.id.item_image);
        title.setText(offersLists.get(i).title);
        start_date.setText(offersLists.get(i).start_date);
        end_date.setText(offersLists.get(i).end_date);
        description.setText(offersLists.get(i).description);
        Ion.with(context).load(offersLists.get(i).image).withBitmap().placeholder(R.drawable.placeholder_person).intoImageView(item_image);
        return item_view;
    }
}
