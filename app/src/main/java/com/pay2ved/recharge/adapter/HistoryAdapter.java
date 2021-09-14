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

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    List<ShowFormGetSet> mItems;
    private ArrayList<ShowFormGetSet> arraylist;
    Context context;

    public HistoryAdapter(Context context, List<ShowFormGetSet> products) {
        super();
        arraylist = new ArrayList<ShowFormGetSet>();
        this.mItems = products;
        this.context = context;
        this.arraylist.addAll(mItems);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.history_adapter, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final ShowFormGetSet nature = mItems.get(i);

        viewHolder.txt_name.setText((nature.getName()));
        viewHolder.txt_type.setText((nature.getType()));
        viewHolder.txt_account.setText((nature.getAccount()));
        viewHolder.txt_ifsc.setText((nature.getIfsc()));
        viewHolder.txt_code.setText((nature.getCode()));
        viewHolder.txt_tran_amount.setText((nature.getAmount()));
        viewHolder.txt_charges.setText((nature.getCharges()));
        viewHolder.txt_status.setText((nature.getStatus()));
        viewHolder.txt_tran_date.setText((nature.getDate()));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txt_name, txt_account, txt_type, txt_ifsc, txt_code, txt_tran_amount, txt_charges
                , txt_status, txt_tran_date;
        RelativeLayout relative;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            txt_name = (TextView) itemView.findViewById(R.id.txt_name);
            txt_type = (TextView) itemView.findViewById(R.id.txt_type);
            txt_account = (TextView) itemView.findViewById(R.id.txt_account);

            txt_ifsc = (TextView) itemView.findViewById(R.id.txt_ifsc);
            txt_code = (TextView) itemView.findViewById(R.id.txt_code);
            txt_tran_amount = (TextView) itemView.findViewById(R.id.txt_tran_amount);
            txt_charges = (TextView) itemView.findViewById(R.id.txt_charges);
            txt_status = (TextView) itemView.findViewById(R.id.txt_status);
            txt_tran_date = (TextView) itemView.findViewById(R.id.txt_tran_date);
            relative = (RelativeLayout) itemView.findViewById(R.id.relative);
        }

        @Override
        public void onClick(View v) {
            final ShowFormGetSet nature = mItems.get(getPosition());

            AppsContants.sharedpreferences = context.getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
            editor.putString(AppsContants.ID, nature.getCode());
            editor.putString(AppsContants.Name, nature.getName());
            editor.putString(AppsContants.Account, nature.getAccount());
            editor.putString(AppsContants.Type, nature.getType());
            editor.putString(AppsContants.Ifsc, nature.getIfsc());
            editor.commit();

           /* Intent intent = new Intent(context, Delete_BeneficiaryActivity.class);
            context.startActivity(intent);*/

        }
    }

}


