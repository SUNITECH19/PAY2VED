package com.pay2ved.recharge.fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.pay2ved.recharge.R;
import com.pay2ved.recharge.helper.Listener;
import com.pay2ved.recharge.other.HttpUrlPath;
import com.pay2ved.recharge.service.callmodel.CallDTHInfo;
import com.pay2ved.recharge.service.query.DBQuery;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By Shailendra on 01-06-2021
 */
public class FragmentDialogCustomerInfo extends DialogFragment implements Listener.OnDTHListener {

    private String s_Username;
    private String s_Password;
    private String s_provider;
    private String s_mobile;

    public FragmentDialogCustomerInfo(String s_Username, String s_Password, String s_provider, String s_mobile) {
        this.s_Username = s_Username;
        this.s_Password = s_Password;
        this.s_provider = s_provider;
        this.s_mobile = s_mobile;
    }

    //-------------------------------------

    private CallDTHInfo callDTHInfo;

    private ImageButton deleteBtn;

    private LinearLayout layoutMonthlyRecharge;

    private TextView operatorName;
    private TextView customerName;
    private TextView balance;
    private TextView nextRechargeDate;
    private TextView monthlyRecharge;
    private TextView planName;

    private LinearLayout layoutContent;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dialog_customer_info, container, false);
        if (getDialog()!=null){
            getDialog().requestWindowFeature( STYLE_NO_TITLE );

            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            getDialog().getWindow().setLayout(width, height);
        }

        progressBar = view.findViewById( R.id.progress_circular );
        layoutContent = view.findViewById( R.id.layoutContent );
        layoutMonthlyRecharge = view.findViewById( R.id.layoutMonthlyRecharge );

        operatorName = view.findViewById(R.id.operatorName);
        customerName = view.findViewById( R.id.customerName );
        balance = view.findViewById( R.id.balance );
        nextRechargeDate = view.findViewById( R.id.nextRechargeDate );
        monthlyRecharge = view.findViewById( R.id.monthlyRecharge );
        planName = view.findViewById( R.id.planName );
        deleteBtn = view.findViewById( R.id.imageButtonClose );

        // --- Click Action
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentDialogCustomerInfo.this.dismiss();
            }
        });

        if (callDTHInfo == null){
            // Query...
            DBQuery.queryToGetCustomerInfo( this, s_Username, s_Password, s_provider, s_mobile );

        }else {
            setData();
        }

        return view;
    }

    private void setData( ){
        progressBar.setVisibility(View.GONE);

        layoutContent.setVisibility(View.VISIBLE);
        operatorName.setText( callDTHInfo.getOperator() );
        customerName.setText( callDTHInfo.getInfo().getCustomer_name() );
        balance.setText( "₹" + callDTHInfo.getInfo().getBalance() );
        nextRechargeDate.setText( callDTHInfo.getInfo().getNext_recharge_date());
        planName.setText( callDTHInfo.getInfo().getPlan_name() );
//        operatorName.setText( responseModel.number);
        if (callDTHInfo.getInfo().getMonthly_recharge() != null && !callDTHInfo.getInfo().getMonthly_recharge().equals("")){
            layoutMonthlyRecharge.setVisibility(View.VISIBLE);
            monthlyRecharge.setText( "₹" + callDTHInfo.getInfo().getMonthly_recharge());
        }
    }

    @Override
    public void onDTHInfoLoad(@Nullable CallDTHInfo callDTHInfo) {
        progressBar.setVisibility(View.GONE);

        if (callDTHInfo != null){
            this.callDTHInfo = callDTHInfo;
            setData();
        }
    }



}
