package com.viralandroid.tiepropertyportal;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by yellowsoft on 14/7/17.
 */

public class BASVisitScreenAdapter extends RecyclerView.Adapter<BASVisitScreenAdapter.SimpleViewHolder> {
    Context mContext;
    LayoutInflater inflater;
    ArrayList<BASVisits> basVisitses;

    public static class SimpleViewHolder extends RecyclerView.ViewHolder{
        TextView name,address,phone,datetime,property,message,status;
        ImageView edit_basvisit;
        public SimpleViewHolder(View view){
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            address = (TextView) view.findViewById(R.id.address);
            phone = (TextView) view.findViewById(R.id.phone);
            datetime = (TextView) view.findViewById(R.id.datetime);
            property = (TextView) view.findViewById(R.id.property);
            message = (TextView) view.findViewById(R.id.message);
            status = (TextView) view.findViewById(R.id.status);
            edit_basvisit = (ImageView) view.findViewById(R.id.edit_basvisit);

        }
    }

    public BASVisitScreenAdapter(Context context,ArrayList<BASVisits> basVisitses){
        mContext = context;
        this.basVisitses = basVisitses;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public BASVisitScreenAdapter.SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.basvisits_items, parent, false);
        return new BASVisitScreenAdapter.SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BASVisitScreenAdapter.SimpleViewHolder holder, final int position) {
        holder.name.setText(basVisitses.get(position).name);
        holder.address.setText(basVisitses.get(position).address);
        holder.phone.setText(basVisitses.get(position).phone);
        holder.datetime.setText(basVisitses.get(position).date +' '+basVisitses.get(position).time);
        holder.property.setText(basVisitses.get(position).prop_title+' '+ basVisitses.get(position).prop_code);
        holder.message.setText(basVisitses.get(position).message);
        holder.status.setText(basVisitses.get(position).status);
        holder.edit_basvisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,EditBASVisitPage.class);
                intent.putExtra("id",basVisitses.get(position).id);
                intent.putExtra("message",basVisitses.get(position).message);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return basVisitses.size();
    }
}
