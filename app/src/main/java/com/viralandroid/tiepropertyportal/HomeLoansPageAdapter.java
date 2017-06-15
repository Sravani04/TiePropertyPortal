package com.viralandroid.tiepropertyportal;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by yellowsoft on 14/6/17.
 */

public class HomeLoansPageAdapter extends RecyclerView.Adapter<HomeLoansPageAdapter.SimpleViewHolder> {
    Context mContext;
    LayoutInflater inflater;
    ArrayList<HomeLoans> homeLoans;
    ArrayList<Banks> banks;
    int lastPosition = -1;

    public static class SimpleViewHolder extends RecyclerView.ViewHolder{
        TextView name,address,phone,email,property,type,bank;
        ImageView edit_btn;
        public SimpleViewHolder(View view){
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            address = (TextView) view.findViewById(R.id.address);
            phone = (TextView) view.findViewById(R.id.phone);
            email = (TextView) view.findViewById(R.id.email);
            property = (TextView) view.findViewById(R.id.property);
            type = (TextView) view.findViewById(R.id.type);
            bank = (TextView) view.findViewById(R.id.bank);
            edit_btn = (ImageView) view.findViewById(R.id.edit_btn);

        }
    }

    public HomeLoansPageAdapter(Context context,ArrayList<HomeLoans> homeLoans,ArrayList<Banks> banks){
        mContext = context;
        this.homeLoans = homeLoans;
        this.banks = banks;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public HomeLoansPageAdapter.SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.home_loan_items, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final  SimpleViewHolder holder, final int position) {
        holder.name.setText(homeLoans.get(position).name);
        holder.address.setText(homeLoans.get(position).address);
        holder.phone.setText(homeLoans.get(position).phone);
        holder.email.setText(homeLoans.get(position).email);
        holder.property.setText(homeLoans.get(position).property);
        holder.type.setText(homeLoans.get(position).type);
        holder.bank.setText(homeLoans.get(position).bank_title);
        holder.edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,EditHomeLoanPage.class);
                intent.putExtra("id",homeLoans.get(position).id);
                intent.putExtra("name",homeLoans.get(position).name);
                intent.putExtra("address",homeLoans.get(position).address);
                intent.putExtra("phone",homeLoans.get(position).phone);
                intent.putExtra("email",homeLoans.get(position).email);
                intent.putExtra("property",homeLoans.get(position).property);
                intent.putExtra("type",homeLoans.get(position).type);
                intent.putExtra("bank",homeLoans.get(position).bank_title);
                intent.putExtra("bank_id",homeLoans.get(position).bank_id);
                mContext.startActivity(intent);
            }
        });

        if (position > lastPosition){
            Animation animation = AnimationUtils.loadAnimation(mContext,R.anim.animation);
            holder.itemView.startAnimation(animation);
            lastPosition = position;
        }


    }

    @Override
    public int getItemCount() {
        return homeLoans.size();
    }
}
