package com.pay2ved.recharge.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.pay2ved.recharge.R;
import com.pay2ved.recharge.model.ShowFormGetSet;
import com.pay2ved.recharge.other.AppsContants;
import com.pay2ved.recharge.service.callmodel.CallCommission;

import java.util.ArrayList;
import java.util.List;


public class My_CommissionAdapter extends RecyclerView.Adapter<My_CommissionAdapter.ViewHolder> {

    private List<CallCommission.ModelCommission> itemList;
    Context context;

    public My_CommissionAdapter(Context context, List<CallCommission.ModelCommission> products) {
        super();
        this.itemList = products;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.my_commission_adapter, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final CallCommission.ModelCommission item = itemList.get(i);

        viewHolder.txt_name.setText((item.getName()));
        viewHolder.txt_surcharge.setText((item.getSurcharge()));
        viewHolder.txt_service.setText((item.getService()));

        if (item.getCommission().equals("")) {
            viewHolder.txt_commission.setText((item.getCommission()));
        } else {
            viewHolder.txt_commission.setText(("₹" + " " + item.getCommission()));
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txt_name, txt_surcharge, txt_service, txt_commission;
        RelativeLayout relative;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            txt_name = (TextView) itemView.findViewById(R.id.txt_name);
            txt_surcharge = (TextView) itemView.findViewById(R.id.txt_surcharge);
            txt_service = (TextView) itemView.findViewById(R.id.txt_service);
            txt_commission = (TextView) itemView.findViewById(R.id.txt_commission);

            relative = (RelativeLayout) itemView.findViewById(R.id.relative);

        }

        @Override
        public void onClick(View v) {
            final CallCommission.ModelCommission nature = itemList.get(getPosition());

            AppsContants.sharedpreferences = context.getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
            editor.putString(AppsContants.ID, nature.getId());
            editor.putString(AppsContants.TITTLE, nature.getName());
            editor.commit();

        }
    }

}


