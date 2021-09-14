package com.pay2ved.recharge.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.pay2ved.recharge.R;
import com.pay2ved.recharge.fragments.FragmentDialogDTHPlans;
import com.pay2ved.recharge.helper.Listener;
import com.pay2ved.recharge.model.ShowFormGetSet;
import com.pay2ved.recharge.other.AppsContants;
import com.pay2ved.recharge.service.callmodel.CallDTHPlans;
import com.pay2ved.recharge.service.callmodel.CallROffer;

import java.util.List;

public class PlansROfferAdapter  extends RecyclerView.Adapter<PlansROfferAdapter.ViewHolder> {

    Context context;
    private boolean isROffer;

    private Listener.OnItemSelectedListener listener;

    private List<CallROffer.ROfferItem> rOfferItemsList;
    private List<CallROffer.DataItem> dataItemList;

    public PlansROfferAdapter( Listener.OnItemSelectedListener listener, Context context,
                               List<CallROffer.ROfferItem> rOfferItemsList,  List<CallROffer.DataItem> dataItemList, boolean isROffer ) {
        this.rOfferItemsList = rOfferItemsList;
        this.dataItemList = dataItemList;
        this.context = context;
        this.listener = listener;
        this.isROffer = isROffer;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.plans_adapter, viewGroup, false);
        PlansROfferAdapter.ViewHolder viewHolder = new PlansROfferAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder( ViewHolder viewHolder, int i) {
        String amount;
        String description;
        if (isROffer){
            amount = rOfferItemsList.get(i).getDesc();
            description = rOfferItemsList.get(i).getRs();
        }else{
            amount = dataItemList.get(i).getAmount();
            description = dataItemList.get(i).getDescription();
        }
//        viewHolder.txt_validity.setText((nature.getValidity()));
        viewHolder.txt_description.setText(description);

        if (amount.equals("")) {
            viewHolder.txt_amount.setText(amount);
        } else {
            viewHolder.txt_amount.setText(("₹" + amount));
        }
    }

    @Override
    public int getItemCount() {
        if (isROffer){
            return rOfferItemsList.size();
        }else {
            return dataItemList.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txt_amount, txt_validity, txt_description;
        RelativeLayout relative;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            txt_amount = (TextView) itemView.findViewById(R.id.txt_amount);
            txt_validity = (TextView) itemView.findViewById(R.id.txt_validity);
            txt_description = (TextView) itemView.findViewById(R.id.txt_description);
            relative = (RelativeLayout) itemView.findViewById(R.id.relative);
        }

        @Override
        public void onClick(View v) {
            String amountVal;
            if (isROffer){
                amountVal = rOfferItemsList.get(getLayoutPosition()).getDesc();
            }else{
                amountVal = dataItemList.get(getLayoutPosition()).getAmount();
            }
            if (listener != null){
                // send Info to Listener...
                CallDTHPlans.Amount amount = new CallDTHPlans.Amount();
                amount.setPrice( amountVal );
                listener.onItemSelected( amount, null );
            }else{
                AppsContants.sharedpreferences = context.getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.amount, amountVal);
                editor.commit();
                ((Activity) context).finish();
            }

        }
    }

}
