package com.viralandroid.tiepropertyportal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by yellowsoft on 14/7/17.
 */

public class CityAdapter extends BaseAdapter implements Filterable {
    LayoutInflater inflater;
    Context context;
    PlanetFilter planetFilter;
    ArrayList<Cities> cities_all;


    //    ArrayList<String> cities;
    ArrayList<Cities> cities;
    public CityAdapter(Context context,ArrayList<Cities>cities){
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.cities = cities;
        this.cities_all = cities;
    }
    @Override
    public int getCount() {
        return cities.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Filter getFilter() {
        if(planetFilter==null)
            planetFilter=new PlanetFilter();
        return planetFilter;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final View item_view = inflater.inflate(R.layout.city_items,null);
        ImageView city_image = (ImageView) item_view.findViewById(R.id.city_image);
        TextView city_name = (TextView) item_view.findViewById(R.id.city_name);
        city_name.setText(cities.get(i).title);
        Picasso.with(context).load(cities.get(i).image).into(city_image);
        return item_view;
    }

    private class PlanetFilter extends Filter {
        Boolean clear_all=false;
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
// We implement here the filter logic
            clear_all=false;
            if (constraint == null || constraint.length() == 0) {
                clear_all=true;
// No filter implemented we return all the list
                results.values = cities;
                results.count = cities.size();
            }
            else {
// We perform filtering operation
                List<Cities> nPlanetList = new ArrayList<>();

                for (Cities p : cities_all) {

//Pattern.compile(Pattern.quote(s2), Pattern.CASE_INSENSITIVE).matcher(s1).find();

                    if (Pattern.compile(Pattern.quote(constraint.toString()), Pattern.CASE_INSENSITIVE).matcher(p.title).find())
                        nPlanetList.add(p);
                }

                results.values = nPlanetList;
                results.count = nPlanetList.size();

            }
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            if (results.count == 0) {
//                restaurants = (ArrayList<Restaurants>) results.values;
                notifyDataSetChanged();
            }
            else if(clear_all){

                cities = cities_all;
                notifyDataSetChanged();
            }


            else {
                cities = (ArrayList<Cities>) results.values;
                notifyDataSetChanged();
            }



        }

    }



}

