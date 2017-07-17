package com.viralandroid.tiepropertyportal;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

/**
 * Created by T on 30-05-2017.
 */

public class TrendingPropertiesAdapter  extends BaseAdapter {
    Context context;
    int images[];
    LayoutInflater inflater;
    ArrayList<Properties> properties;
    TrendingPropertyActivity activity;
    ArrayList<TrendingProperties> trendingProperties;

    public TrendingPropertiesAdapter(Context context, ArrayList<TrendingProperties> trendingProperties,ArrayList<Properties> properties,TrendingPropertyActivity activity) {
        this.context = context;
        this.trendingProperties=trendingProperties;
        inflater = LayoutInflater.from(context);
        this.properties = properties;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return trendingProperties.size();
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
    public View getView(final int position, View view, ViewGroup viewGroup) {
        View itemView = inflater.inflate(R.layout.trending_property_image, null);
        final ImageView imageView = (ImageView) itemView.findViewById(R.id.property_image);
//        LinearLayout trending_slide = (LinearLayout) itemView.findViewById(R.id.trending_slide);
        final TextView title = (TextView) itemView.findViewById(R.id.title);

        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.get_single_property(trendingProperties.get(position).property_id);
            }
        });


//        imageView.setImageResource(images[position]);
        try {
            Ion.with(context)
                    .load(trendingProperties.get(position).image)
                    .withBitmap()
                    .intoImageView(imageView);
        }catch (Exception e){
            e.printStackTrace();
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.get_single_property(trendingProperties.get(position).property_id);
                Log.e("id",trendingProperties.get(position).id);


            }
        });
        title.setText(trendingProperties.get(position).property_name);
        return itemView;
    }



}
