package com.pay2ved.recharge.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.pay2ved.recharge.R;
import com.pay2ved.recharge.gprs_home_activity.ComplaintActivity;
import com.pay2ved.recharge.helper.Listener;
import com.pay2ved.recharge.model.ShowFormGetSet;
import com.pay2ved.recharge.other.AppsContants;
import com.pay2ved.recharge.service.callmodel.CallRechargeReport;


import java.util.ArrayList;
import java.util.List;


public class Recharge_ReportAdapter extends RecyclerView.Adapter<Recharge_ReportAdapter.ViewHolder> {

    private List<CallRechargeReport.ModelRechargeRpt> arraylist;
    Context context;

    public Recharge_ReportAdapter(Context context, List<CallRechargeReport.ModelRechargeRpt> products) {
        super();
        this.arraylist = products;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recharge_report_adapter, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final CallRechargeReport.ModelRechargeRpt item = arraylist.get(i);

        viewHolder.txt_provider.setText( item.getName() + "(" + item.getAccount() + ")" );
        viewHolder.txt_retailer.setText(item.getRetailer());
        viewHolder.txt_txn_no.setText(item.getId());
        viewHolder.txt_ref_no.setText(item.getRef_no());
        viewHolder.txt_date.setText(item.getDate());
        viewHolder.txt_success.setText(item.getStatus());

        if (item.getAmount().equals("")) {
            viewHolder.txt_amount.setText(item.getAmount());
        } else {
            viewHolder.txt_amount.setText("₹" + " " + item.getAmount());
        }


        if (item.getStatus().equals("SUCCESS")) {
            viewHolder.rlt_success.setBackgroundColor(Color.parseColor("#FF2ECE2C"));
        } else if (item.getStatus().equals("PENDING")) {
            viewHolder.rlt_success.setBackgroundColor(Color.parseColor("#fff06b00"));
        } else if (item.getStatus().equals("FAILURE")) {
            viewHolder.rlt_success.setBackgroundColor(Color.parseColor("#FFF22E2E"));
        } else {
            viewHolder.rlt_success.setBackgroundColor(Color.parseColor("#ff005abb"));
        }


    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txt_provider, txt_retailer, txt_txn_no, txt_ref_no, txt_date, txt_amount, txt_success;
        RelativeLayout relative, rlt_success, rlt_complaint;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            txt_provider = (TextView) itemView.findViewById(R.id.txt_provider);
            txt_retailer = (TextView) itemView.findViewById(R.id.txt_retailer);
            txt_txn_no = (TextView) itemView.findViewById(R.id.txt_txn_no);
            txt_ref_no = (TextView) itemView.findViewById(R.id.txt_ref_no);
            txt_date = (TextView) itemView.findViewById(R.id.txt_date);
            txt_amount = (TextView) itemView.findViewById(R.id.txt_amount);
            txt_success = (TextView) itemView.findViewById(R.id.txt_success);

            relative = (RelativeLayout) itemView.findViewById(R.id.relative);
            rlt_success = (RelativeLayout) itemView.findViewById(R.id.rlt_success);
            rlt_complaint = (RelativeLayout) itemView.findViewById(R.id.rlt_complaint);

            rlt_complaint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final CallRechargeReport.ModelRechargeRpt nature = arraylist.get(getPosition());

                    AppsContants.sharedpreferences = context.getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                    editor.putString(AppsContants.ID, nature.getId());
                    editor.putString(AppsContants.TITTLE, nature.getName());
                    editor.putString(AppsContants.REF_NO, nature.getRef_no());
                    editor.putString(AppsContants.DATE, nature.getDate());
                    editor.putString(AppsContants.AMOUNT, nature.getAmount());
                    editor.putString(AppsContants.STATUS, nature.getStatus());
                    editor.commit();

                    Intent intent = new Intent(context, ComplaintActivity.class);
                    context.startActivity(intent);

                }
            });
        }

        @Override
        public void onClick(View v) {
            final CallRechargeReport.ModelRechargeRpt nature = arraylist.get(getPosition());
        }
    }

}


