package com.viralandroid.tiepropertyportal;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

/**
 * Created by T on 15-05-2017.
 */

public class AgentsHomePageAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    ArrayList<Agents> agents;
    public AgentsHomePageAdapter(Context context,ArrayList<Agents>agents){
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.agents = agents;
    }
    @Override
    public int getCount() {
        return agents.size();
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
        View item_view = inflater.inflate(R.layout.agents_list,null);
        final ImageView agent_image = (ImageView) item_view.findViewById(R.id.agent_image);
        final TextView  name = (TextView) item_view.findViewById(R.id.name);
        final TextView  code = (TextView) item_view.findViewById(R.id.code);
        final TextView  phone = (TextView) item_view.findViewById(R.id.phone);
        final TextView  address = (TextView) item_view.findViewById(R.id.address);
        Ion.with(context).load(agents.get(position).image).intoImageView(agent_image);
        name.setText(agents.get(position).fname);
        code.setText(agents.get(position).code);
        phone.setText(agents.get(position).phone);
        address.setText(agents.get(position).address);
        final ImageView edit_ba = (ImageView) item_view.findViewById(R.id.edit_ba);
        edit_ba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,EditBasPage.class);
                intent.putExtra("agents",agents.get(position));
                context.startActivity(intent);
            }
        });
        return item_view;
    }
}
