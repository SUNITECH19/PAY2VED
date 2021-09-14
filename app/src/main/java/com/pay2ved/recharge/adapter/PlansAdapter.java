package com.pay2ved.recharge.adapter;

import android.app.Activity;
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
import com.pay2ved.recharge.fragments.FragmentDialogDTHPlans;
import com.pay2ved.recharge.gprs_home_activity.MobileActivity;
import com.pay2ved.recharge.helper.Listener;
import com.pay2ved.recharge.model.ShowFormGetSet;
import com.pay2ved.recharge.other.AppsContants;
import com.pay2ved.recharge.service.callmodel.CallPlans;

import java.util.ArrayList;
import java.util.List;


public class PlansAdapter extends RecyclerView.Adapter<PlansAdapter.ViewHolder> {

    Context context;

    private Listener.OnItemSelectedListener listener;

    private List<CallPlans.ModelPlanList> planLists;

    public PlansAdapter(Context context, List<CallPlans.ModelPlanList> products) {
        super();
        this.context = context;
        this.planLists = products;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.plans_adapter, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final CallPlans.ModelPlanList item = planLists.get(i);

        viewHolder.txt_validity.setText((item.getValidity()));
        viewHolder.txt_description.setText((item.getDescription()));

        if (item.getAmount().equals("")) {
            viewHolder.txt_amount.setText((item.getAmount()));
        } else {
            viewHolder.txt_amount.setText(("₹" + item.getAmount()));
        }
    }

    @Override
    public int getItemCount() {
        return planLists.size();
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
            final CallPlans.ModelPlanList nature = planLists.get( getLayoutPosition() );

            if (listener != null){
                // send Info to Listener...
            }else{
                AppsContants.sharedpreferences = context.getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.amount, nature.getAmount());
                editor.commit();

                ((Activity) context).finish();
            }

        }
    }

}


