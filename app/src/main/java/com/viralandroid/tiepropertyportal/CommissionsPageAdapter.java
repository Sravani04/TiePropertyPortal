package com.viralandroid.tiepropertyportal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by T on 17-05-2017.
 */

public class CommissionsPageAdapter extends BaseAdapter {
    public LayoutInflater inflater;
    Context context;
    Commissions commissions;

    public CommissionsPageAdapter(Context context,Commissions commissions){
        this.context = context;
        this.commissions = commissions;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return commissions.commissionLists.size();
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
            View item_view = inflater.inflate(R.layout.commissions_list_items, null);
            TextView customer = (TextView) item_view.findViewById(R.id.customer);
            TextView property = (TextView) item_view.findViewById(R.id.property);
            TextView units = (TextView) item_view.findViewById(R.id.units);
            TextView total_amt = (TextView) item_view.findViewById(R.id.total_amt);
            TextView paid_amt = (TextView) item_view.findViewById(R.id.paid_amt);
            TextView amt = (TextView) item_view.findViewById(R.id.amt);
            TextView tds = (TextView) item_view.findViewById(R.id.tds);
            TextView pay_amt = (TextView) item_view.findViewById(R.id.pay_amt);
            customer.setText(commissions.commissionLists.get(i).customer);
            property.setText(commissions.commissionLists.get(i).property);
            units.setText(commissions.commissionLists.get(i).units);
            total_amt.setText(commissions.commissionLists.get(i).total_amount);
            paid_amt.setText(commissions.commissionLists.get(i).paid_amount);
            amt.setText(commissions.commissionLists.get(i).amount);
            tds.setText(commissions.commissionLists.get(i).tds);
            pay_amt.setText(commissions.commissionLists.get(i).payable_amount);
            return item_view;
        }

}
