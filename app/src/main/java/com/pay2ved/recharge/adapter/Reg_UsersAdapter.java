package com.pay2ved.recharge.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.pay2ved.recharge.R;
import com.pay2ved.recharge.model.ShowFormGetSet;
import com.pay2ved.recharge.other.AppsContants;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class Reg_UsersAdapter extends RecyclerView.Adapter<Reg_UsersAdapter.ViewHolder> {

    List<ShowFormGetSet> mItems;
    private ArrayList<ShowFormGetSet> arraylist;
    Context context;

    public Reg_UsersAdapter(Context context, List<ShowFormGetSet> products) {
        super();
        arraylist = new ArrayList<ShowFormGetSet>();
        this.mItems = products;
        this.context = context;
        this.arraylist.addAll(mItems);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.reg_users_adapter, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final ShowFormGetSet nature = mItems.get(i);

        viewHolder.txt_busi_name.setText((nature.getBusiness_name()));
        viewHolder.txt_name.setText((nature.getName()));
        viewHolder.txt_uid.setText((nature.getId()));
        viewHolder.txt_type.setText((nature.getType()));
        viewHolder.txt_mobile.setText((nature.getMobile()));
        viewHolder.txt_address.setText((nature.getAddress()));
        viewHolder.txt_balance.setText((nature.getBalance()));

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txt_busi_name, txt_name, txt_uid, txt_type, txt_mobile, txt_address, txt_balance;
        RelativeLayout relative;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            txt_busi_name = (TextView) itemView.findViewById(R.id.txt_busi_name);
            txt_name = (TextView) itemView.findViewById(R.id.txt_name);
            txt_uid = (TextView) itemView.findViewById(R.id.txt_uid);
            txt_type = (TextView) itemView.findViewById(R.id.txt_type);
            txt_mobile = (TextView) itemView.findViewById(R.id.txt_mobile);
            txt_address = (TextView) itemView.findViewById(R.id.txt_address);
            txt_balance = (TextView) itemView.findViewById(R.id.txt_balance);

            relative = (RelativeLayout) itemView.findViewById(R.id.relative);

        }

        @Override
        public void onClick(View v) {
            final ShowFormGetSet nature = mItems.get(getPosition());

            AppsContants.sharedpreferences = context.getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
            editor.putString(AppsContants.ID, nature.getId());
            editor.putString(AppsContants.TITTLE, nature.getName());
            editor.commit();

        }
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        mItems.clear();
        if (charText.length() == 0) {
            mItems.addAll(arraylist);
        } else {
            for (ShowFormGetSet wp : arraylist) {
                if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    mItems.add(wp);
                } else if (wp.getMobile().toLowerCase(Locale.getDefault()).contains(charText)) {
                    mItems.add(wp);
                } else if (wp.getBusiness_name().toLowerCase(Locale.getDefault()).contains(charText)) {
                    mItems.add(wp);
                } else if (wp.getId().toLowerCase(Locale.getDefault()).contains(charText)) {
                    mItems.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}


