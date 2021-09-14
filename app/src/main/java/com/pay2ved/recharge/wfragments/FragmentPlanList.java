package com.pay2ved.recharge.wfragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pay2ved.recharge.R;
import com.pay2ved.recharge.activity.PlansActivity;
import com.pay2ved.recharge.adapter.PlansAdapter;
import com.pay2ved.recharge.databinding.FragmentPlanListBinding;
import com.pay2ved.recharge.fragments.FragmentDialogDTHPlans;
import com.pay2ved.recharge.helper.Listener;
import com.pay2ved.recharge.service.callmodel.CallPlans;

import java.util.List;

public class FragmentPlanList extends Fragment {
    private static FragmentPlanList fragmentPlanList;
    private List<CallPlans.ModelPlanList> planLists;

    public FragmentPlanList(List<CallPlans.ModelPlanList> planLists) {
        // Required empty public constructor
        this.planLists = planLists;
    }

    public static FragmentPlanList getInstance(List<CallPlans.ModelPlanList> planLists){
        fragmentPlanList = new FragmentPlanList( planLists );
        return fragmentPlanList;
    }

    private FragmentPlanListBinding planListBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        planListBinding = DataBindingUtil.inflate( inflater, R.layout.fragment_plan_list, container, false );
        setAdaptor();
        return planListBinding.getRoot();
    }

    private void setAdaptor(){
        PlansAdapter adapter = new PlansAdapter(getActivity(), planLists );
        planListBinding.recyclerViewPlans.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


}