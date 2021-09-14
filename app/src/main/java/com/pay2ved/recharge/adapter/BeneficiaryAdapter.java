package com.pay2ved.recharge.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.pay2ved.recharge.R;
import com.pay2ved.recharge.activity.Delete_BeneficiaryActivity;
import com.pay2ved.recharge.activity.TransferActivity;
import com.pay2ved.recharge.activity.Validate_benefiActivity;
import com.pay2ved.recharge.activity.ViewBeneficiaryActivity;
import com.pay2ved.recharge.model.ShowFormGetSet;
import com.pay2ved.recharge.other.AppsContants;

import java.util.ArrayList;
import java.util.List;

public class BeneficiaryAdapter extends RecyclerView.Adapter<BeneficiaryAdapter.ViewHolder> {

    List<ShowFormGetSet> mItems;
    private ArrayList<ShowFormGetSet> arraylist;
    Context context;

    public BeneficiaryAdapter(Context context, List<ShowFormGetSet> products) {
        super();
        arraylist = new ArrayList<ShowFormGetSet>();
        this.mItems = products;
        this.context = context;
        this.arraylist.addAll(mItems);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.beneficiary_adapter, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final ShowFormGetSet nature = mItems.get(i);

        viewHolder.txt_name.setText((nature.getName()));
        viewHolder.txt_txn_id.setText((nature.getId()));
        viewHolder.txt_account.setText((nature.getAccount()));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txt_name, txt_txn_id, txt_account;
        TextView txt_view, txt_transfer, txt_validate, txt_delete;
        RelativeLayout relative;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            txt_name = (TextView) itemView.findViewById(R.id.txt_name);
            txt_txn_id = (TextView) itemView.findViewById(R.id.txt_txn_id);
            txt_account = (TextView) itemView.findViewById(R.id.txt_account);

            txt_view = (TextView) itemView.findViewById(R.id.txt_view);
            txt_transfer = (TextView) itemView.findViewById(R.id.txt_transfer);
            txt_validate = (TextView) itemView.findViewById(R.id.txt_validate);
            txt_delete = (TextView) itemView.findViewById(R.id.txt_delete);
            relative = (RelativeLayout) itemView.findViewById(R.id.relative);

            txt_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final ShowFormGetSet nature = mItems.get(getPosition());

                    AppsContants.sharedpreferences = context.getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                    editor.putString(AppsContants.ID, nature.getId());
                    editor.putString(AppsContants.Name, nature.getName());
                    editor.putString(AppsContants.Account, nature.getAccount());
                    editor.putString(AppsContants.Type, nature.getType());
                    editor.putString(AppsContants.Ifsc, nature.getIfsc());
                    editor.commit();

                    Intent intent = new Intent(context, ViewBeneficiaryActivity.class);
                    context.startActivity(intent);
                }
            });

            txt_transfer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final ShowFormGetSet nature = mItems.get(getPosition());

                    AppsContants.sharedpreferences = context.getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                    editor.putString(AppsContants.ID, nature.getId());
                    editor.putString(AppsContants.Name, nature.getName());
                    editor.putString(AppsContants.Account, nature.getAccount());
                    editor.putString(AppsContants.Type, nature.getType());
                    editor.putString(AppsContants.Ifsc, nature.getIfsc());
                    editor.commit();

                    Intent intent = new Intent(context, TransferActivity.class);
                    context.startActivity(intent);
                    ((Activity)context).finish();
                }
            });
            txt_validate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final ShowFormGetSet nature = mItems.get(getPosition());

                    AppsContants.sharedpreferences = context.getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                    editor.putString(AppsContants.ID, nature.getId());
                    editor.putString(AppsContants.Name, nature.getName());
                    editor.putString(AppsContants.Account, nature.getAccount());
                    editor.putString(AppsContants.Type, nature.getType());
                    editor.putString(AppsContants.Ifsc, nature.getIfsc());
                    editor.commit();

                    Intent intent = new Intent(context, Validate_benefiActivity.class);
                    context.startActivity(intent);
                    ((Activity)context).finish();
                }
            });
            txt_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final ShowFormGetSet nature = mItems.get(getPosition());

                    AppsContants.sharedpreferences = context.getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                    editor.putString(AppsContants.ID, nature.getId());
                    editor.putString(AppsContants.Name, nature.getName());
                    editor.putString(AppsContants.Account, nature.getAccount());
                    editor.putString(AppsContants.Type, nature.getType());
                    editor.putString(AppsContants.Ifsc, nature.getIfsc());
                    editor.commit();

                    Intent intent = new Intent(context, Delete_BeneficiaryActivity.class);
                    context.startActivity(intent);
                    ((Activity)context).finish();
                }
            });
        }

        @Override
        public void onClick(View v) {
            final ShowFormGetSet nature = mItems.get(getPosition());
        }
    }

}


