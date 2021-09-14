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


public class Complaint_ReportAdapter extends RecyclerView.Adapter<Complaint_ReportAdapter.ViewHolder> {

    List<ShowFormGetSet> mItems;
    private ArrayList<ShowFormGetSet> arraylist;
    Context context;

    public Complaint_ReportAdapter(Context context, List<ShowFormGetSet> products) {
        super();
        arraylist = new ArrayList<ShowFormGetSet>();
        this.mItems = products;
        this.context = context;
        this.arraylist.addAll(mItems);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.complaint_report_adapter, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final ShowFormGetSet nature = mItems.get(i);

        viewHolder.txt_name.setText((nature.getTitle()));
        viewHolder.txt_remark.setText((nature.getRemark()));
        viewHolder.txt_ref_no.setText((nature.getRef_no()));
        viewHolder.txt_date.setText((nature.getDate()));
        viewHolder.txt_type.setText((nature.getType()));

        if (nature.getAmount().equals("")) {
            viewHolder.txt_amount.setText((nature.getAmount()));
        } else {
            viewHolder.txt_amount.setText(("₹" + " " + nature.getAmount()));
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txt_name, txt_remark, txt_ref_no, txt_date, txt_amount, txt_type;
        RelativeLayout relative;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            txt_name = (TextView) itemView.findViewById(R.id.txt_name);
            txt_remark = (TextView) itemView.findViewById(R.id.txt_remark);
            txt_ref_no = (TextView) itemView.findViewById(R.id.txt_ref_no);
            txt_date = (TextView) itemView.findViewById(R.id.txt_date);
            txt_amount = (TextView) itemView.findViewById(R.id.txt_amount);
            txt_type = (TextView) itemView.findViewById(R.id.txt_type);

            relative = (RelativeLayout) itemView.findViewById(R.id.relative);

        }

        @Override
        public void onClick(View v) {
            final ShowFormGetSet nature = mItems.get(getPosition());

            AppsContants.sharedpreferences = context.getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
            editor.putString(AppsContants.ID, nature.getId());
            editor.putString(AppsContants.TITTLE, nature.getTitle());
            editor.putString(AppsContants.REF_NO, nature.getRef_no());
            editor.putString(AppsContants.REMARK, nature.getRemark());
            editor.putString(AppsContants.DATE, nature.getDate());
            editor.putString(AppsContants.AMOUNT, nature.getAmount());
            editor.commit();

        }
    }

}


