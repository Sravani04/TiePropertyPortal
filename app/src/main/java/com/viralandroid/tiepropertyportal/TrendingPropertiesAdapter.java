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
        TextView code = (TextView) itemView.findViewById(R.id.code);
        TextView location = (TextView) itemView.findViewById(R.id.location);
        TextView area = (TextView) itemView.findViewById(R.id.area);
        TextView cat = (TextView) itemView.findViewById(R.id.cat);
        TextView direct_comm = (TextView) itemView.findViewById(R.id.direct_comm);
        TextView override_comm = (TextView) itemView.findViewById(R.id.override_comm);

        title.setText(trendingProperties.get(position).title);
        code.setText(trendingProperties.get(position).prop_code);
        location.setText(trendingProperties.get(position).location);
        area.setText(trendingProperties.get(position).area);
        cat.setText(trendingProperties.get(position).category);
        direct_comm.setText("Direct COMM:"+trendingProperties.get(position).direct_commission);
        override_comm.setText("Override COMM:"+trendingProperties.get(position).override_commission);

        if (Session.GetLevelId(context).equals("1")){
            direct_comm.setVisibility(View.VISIBLE);
            override_comm.setVisibility(View.VISIBLE);
        }else {
            direct_comm.setVisibility(View.GONE);
            override_comm.setVisibility(View.VISIBLE);
        }

        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.get_single_property(trendingProperties.get(position).id);
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
                activity.get_single_property(trendingProperties.get(position).id);
                Log.e("id",trendingProperties.get(position).id);


            }
        });

        return itemView;
    }



}
