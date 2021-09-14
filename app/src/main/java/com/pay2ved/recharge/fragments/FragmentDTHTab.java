package com.pay2ved.recharge.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pay2ved.recharge.R;
import com.pay2ved.recharge.adapter.AdaptorDTHPlans;
import com.pay2ved.recharge.helper.Listener;
import com.pay2ved.recharge.service.callmodel.CallDTHPlans;

import java.util.List;

/**
 * Created By Shailendra on 01-06-2021
 */
public class FragmentDTHTab extends Fragment {

    private Listener.OnItemSelectedListener parentListener;
    private List<CallDTHPlans.Plan> planList;

    public FragmentDTHTab(Listener.OnItemSelectedListener parentListener, List<CallDTHPlans.Plan> planList) {
        this.parentListener = parentListener;
        this.planList = planList;
    }

    private RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_d_t_h_tab, container, false);

        recyclerView = view.findViewById( R.id.recycler_view );
        setUIData();

        return view;
    }

    public void setUIData( ){
//        Log.e("SIZE",  "SI " + planList.size() );

        AdaptorDTHPlans adaptorDTHPlans = new AdaptorDTHPlans( parentListener, planList);

        if (recyclerView!=null)
            recyclerView.setAdapter(adaptorDTHPlans);
        adaptorDTHPlans.notifyDataSetChanged();
    }


}