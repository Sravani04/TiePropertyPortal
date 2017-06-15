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
 * Created by mac on 2/3/17.
 */

public class CallbackCustomersAdapter extends RecyclerView.Adapter<CallbackCustomersAdapter.SimpleViewHolder> {

    Context mContext;
    LayoutInflater inflater;
    ArrayList<CallbackCustomers> callbackCustomers;

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        TextView name,email,phone,property,date,message;
        public SimpleViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            email = (TextView) view.findViewById(R.id.email);
            phone = (TextView) view.findViewById(R.id.phone);
            property = (TextView) view.findViewById(R.id.property);
            date = (TextView) view.findViewById(R.id.date);
            message = (TextView) view.findViewById(R.id.message);
        }
    }

    public CallbackCustomersAdapter(Context context,ArrayList<CallbackCustomers> callbackCustomers) {
        mContext = context;
        this.callbackCustomers = callbackCustomers;
        inflater = LayoutInflater.from(context);

    }

    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.callback_items, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder holder, final int position) {
        holder.name.setText(callbackCustomers.get(position).name);
        holder.email.setText(callbackCustomers.get(position).email);
        holder.phone.setText(callbackCustomers.get(position).phone);
        holder.property.setText(callbackCustomers.get(position).property_title);
        holder.date.setText(callbackCustomers.get(position).date);
        holder.message.setText(callbackCustomers.get(position).message);

    }


    @Override
    public int getItemCount() {
        return callbackCustomers.size();
    }


}