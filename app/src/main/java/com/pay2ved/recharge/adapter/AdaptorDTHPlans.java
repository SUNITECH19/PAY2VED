package com.pay2ved.recharge.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pay2ved.recharge.R;
import com.pay2ved.recharge.helper.Listener;
import com.pay2ved.recharge.service.callmodel.CallDTHPlans;

import java.util.List;

/**
 * Created By Shailendra on 01-06-2021
 */
public class AdaptorDTHPlans extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Listener.OnItemSelectedListener listener;
    private List<CallDTHPlans.Plan> planList;

    public AdaptorDTHPlans(Listener.OnItemSelectedListener listener, List<CallDTHPlans.Plan> planList) {
        this.listener = listener;
        this.planList = planList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_item_dth_plans, viewGroup, false);
        return new ViewHolderDTH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolderDTH)holder).setData( position );
    }

    @Override
    public int getItemCount() {
        return planList.size();
    }

    // View Holder =-------------------------------------------------------------------
    public class ViewHolderDTH extends RecyclerView.ViewHolder{

        private TextView textViewPlanName;
        private TextView textViewDescription;
        private RecyclerView recyclerView;

        // Adaptor...
        private AdaptorPlanAmounts adaptor;

        public ViewHolderDTH(@NonNull View itemView) {
            super(itemView);
            textViewPlanName = (TextView) itemView.findViewById(R.id.planName);
            textViewDescription = (TextView) itemView.findViewById(R.id.textViewDescription);
            recyclerView =  itemView.findViewById(R.id.recyclerViewPlans);
        }

        private void setData( int position ){
            CallDTHPlans.Plan planItem = planList.get( position );

            textViewPlanName.setText( planItem.getPlan_name() );
            textViewDescription.setText( planItem.getDescription() );

            adaptor = new AdaptorPlanAmounts( planItem.getAmount(), planItem.getPlan_name() );
            recyclerView.setAdapter( adaptor );
            adaptor.notifyDataSetChanged();
        }

    }

    /// --- Adaptor for Set the Data of amounts...
    public class AdaptorPlanAmounts extends RecyclerView.Adapter<AdaptorPlanAmounts.ViewHolder>{

        private List<CallDTHPlans.Amount> amountList;
        private String planName;

        public AdaptorPlanAmounts(List<CallDTHPlans.Amount> amountList, String planName) {
            this.amountList = amountList;
            this.planName = planName;
        }

        @NonNull
        @Override
        public AdaptorPlanAmounts.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.layout_item_plan_amount, viewGroup, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull AdaptorPlanAmounts.ViewHolder holder, int position) {
            holder.setData( position );
        }

        @Override
        public int getItemCount() {
            return amountList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView textViewAmount;
            private TextView textViewDuration;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                textViewAmount = itemView.findViewById(R.id.textViewAmount);
                textViewDuration = itemView.findViewById(R.id.textViewDuration);
            }

            private void setData( int position ){
                textViewAmount.setText( "₹" + amountList.get(position).getPrice() );
                textViewDuration.setText( amountList.get(position).getDuration() );

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //  Send Data...
                        listener.onItemSelected( amountList.get( position ), planName );
                    }
                });
            }
        }
    }


}
