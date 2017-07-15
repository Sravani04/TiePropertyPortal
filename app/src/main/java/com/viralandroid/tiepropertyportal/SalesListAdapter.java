package com.viralandroid.tiepropertyportal;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by yellowsoft on 13/7/17.
 */

public class SalesListAdapter extends RecyclerView.Adapter<SalesListAdapter.SimpleViewHolder>{
    Context context;
    ArrayList<SalesList> salesLists;
    LayoutInflater inflater;

    public SalesListAdapter(Context context,ArrayList<SalesList> salesLists){
        this.context = context;
        this.salesLists = salesLists;
        inflater = LayoutInflater.from(context);
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder{
        TextView name,phone,email,dob,address,property,units,total_amt,home_loan,agent_name,date;
        public SimpleViewHolder(View view){
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            phone = (TextView) view.findViewById(R.id.phone);
            email = (TextView) view.findViewById(R.id.email);
            dob = (TextView) view.findViewById(R.id.dob);
            address = (TextView) view.findViewById(R.id.address);
            property = (TextView) view.findViewById(R.id.property);
            units = (TextView) view.findViewById(R.id.units);
            total_amt = (TextView) view.findViewById(R.id.total_amt);
            home_loan = (TextView) view.findViewById(R.id.home_loan);
            agent_name = (TextView) view.findViewById(R.id.agent_name);
            date = (TextView) view.findViewById(R.id.date);

        }
    }

    @Override
    public SalesListAdapter.SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.sales_list_items, parent, false);
        return new SalesListAdapter.SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SalesListAdapter.SimpleViewHolder holder, int i) {
        holder.name.setText(salesLists.get(i).name);
        holder.phone.setText(salesLists.get(i).phone);
        holder.email.setText(salesLists.get(i).email);
        holder.dob.setText(salesLists.get(i).dob);
        holder.address.setText(salesLists.get(i).address);
        holder.property.setText(salesLists.get(i).prop_title +' '+ salesLists.get(i).prop_code);
        holder.units.setText(salesLists.get(i).units);
        holder.total_amt.setText(salesLists.get(i).total_amount);
        holder.home_loan.setText(salesLists.get(i).loan);
        holder.agent_name.setText(salesLists.get(i).name +' '+salesLists.get(i).agent_code);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return salesLists.size();
    }


}
