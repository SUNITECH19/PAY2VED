package com.pay2ved.recharge.wfragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pay2ved.recharge.R;
import com.pay2ved.recharge.databinding.FragmentReportBinding;
import com.pay2ved.recharge.gprs_home_activity.Commission_ReportActivity;
import com.pay2ved.recharge.gprs_home_activity.Complaint_ReportActivity;
import com.pay2ved.recharge.gprs_home_activity.Recharge_ReportActivity;
import com.pay2ved.recharge.gprs_home_activity.ReportsActivity;
import com.pay2ved.recharge.gprs_home_activity.Show_ReportActivity;
import com.pay2ved.recharge.other.AppsContants;

public class FragmentReport extends Fragment {
    private static FragmentReport fragmentReport;

    public FragmentReport() {
        // Required empty public constructor
    }

    public static FragmentReport getInstance(){
        if (fragmentReport == null){
            fragmentReport = new FragmentReport();
        }
        return fragmentReport;
    }

    private FragmentReportBinding reportBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment...
        reportBinding = DataBindingUtil.inflate( inflater, R.layout.fragment_report, container, false );
        onButtonAction();

//        ReportsActivity
        return reportBinding.getRoot();
    }

    private void onButtonAction(){
        // Recharge Report
        reportBinding.layoutRechargeRpBtn.setOnClickListener(v -> {

            AppsContants.sharedpreferences = getContext().getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
            editor.putString(AppsContants.NUMBER, "");
            editor.putString(AppsContants.DATE_FROM, "");
            editor.putString(AppsContants.DATE_TO, "");
            editor.commit();

            Intent intent = new Intent( getContext(), Recharge_ReportActivity.class);
            startActivity(intent);

        });
        // Transaction Report
        reportBinding.layoutTransactionRpBtn.setOnClickListener(v -> {

            AppsContants.sharedpreferences = getContext().getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
            editor.putString(AppsContants.DATE_FROM, "");
            editor.putString(AppsContants.DATE_TO, "");
            editor.putString(AppsContants.Action, "rpt-transaction.php");
            editor.putString(AppsContants.Title, "Transaction Report");
            editor.commit();

            Intent intent = new Intent( getContext(), Show_ReportActivity.class);
            startActivity(intent);
        });
        // Commission Report
        reportBinding.layoutCommissionRpBtn.setOnClickListener(v -> {

            AppsContants.sharedpreferences = getContext().getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
            editor.putString(AppsContants.DATE_FROM, "");
            editor.putString(AppsContants.DATE_TO, "");
            editor.putString(AppsContants.Action, "rpt-commission.php");
            editor.putString(AppsContants.Title, "Commission Report");
            editor.commit();

            Intent intent = new Intent( getContext(), Commission_ReportActivity.class);
            startActivity(intent);
        });
        // Complaint Report
        reportBinding.layoutComplaintRpBtn.setOnClickListener(v -> {

            AppsContants.sharedpreferences = getContext().getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
            editor.putString(AppsContants.DATE_FROM, "");
            editor.putString(AppsContants.DATE_TO, "");
            editor.putString(AppsContants.Action, "rpt-complaint.php");
            editor.putString(AppsContants.Title, "Complaint Report");
            editor.commit();

            Intent intent = new Intent( getContext(), Complaint_ReportActivity.class);
            startActivity(intent);
        });
        // Payment Report
        reportBinding.layoutPaymentRpBtn.setOnClickListener(v -> {
            AppsContants.sharedpreferences = getContext().getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
            editor.putString(AppsContants.DATE_FROM, "");
            editor.putString(AppsContants.DATE_TO, "");
            editor.putString(AppsContants.Action, "rpt-payment.php");
            editor.putString(AppsContants.Title, "Payment Report");
            editor.commit();

            Intent intent = new Intent( getContext(), Complaint_ReportActivity.class);
            startActivity(intent);
        });

    }


}