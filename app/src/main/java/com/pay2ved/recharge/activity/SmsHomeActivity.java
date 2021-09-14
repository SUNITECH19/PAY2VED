package com.pay2ved.recharge.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.pay2ved.recharge.R;
import com.pay2ved.recharge.other.AppsContants;
import com.pay2ved.recharge.sms_home_activity.Complaint_RegActivity;
import com.pay2ved.recharge.sms_home_activity.Fund_TransferActivity;
import com.pay2ved.recharge.sms_home_activity.Search_TransactionActivity;
import com.pay2ved.recharge.sms_home_activity.Service_CodeActivity;
import com.pay2ved.recharge.sms_home_activity.Gateway_SettingActivity;
import com.pay2ved.recharge.sms_home_activity.SpinnerListActivity;
import com.pay2ved.recharge.sms_home_activity.SettingsActivity;

public class SmsHomeActivity extends AppCompatActivity {

    ImageView img_back;
    RelativeLayout rlt_mobile, rlt_dth, rlt_datacard, rlt_postpaid, rlt_landline, rlt_balance, rlt_last_recharge, rlt_search_tran,
            rlt_transfer, rlt_register, rlt_status, rlt_setting, rlt_pin, rlt_gateway;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_home);

        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        rlt_mobile = (RelativeLayout) findViewById(R.id.rlt_mobile);

        rlt_dth = (RelativeLayout) findViewById(R.id.rlt_dth);
        rlt_datacard = (RelativeLayout) findViewById(R.id.rlt_datacard);
        rlt_postpaid = (RelativeLayout) findViewById(R.id.rlt_postpaid);
        rlt_landline = (RelativeLayout) findViewById(R.id.rlt_landline);
        rlt_balance = (RelativeLayout) findViewById(R.id.rlt_balance);
        rlt_last_recharge = (RelativeLayout) findViewById(R.id.rlt_last_recharge);
        rlt_search_tran = (RelativeLayout) findViewById(R.id.rlt_search_tran);
        rlt_transfer = (RelativeLayout) findViewById(R.id.rlt_transfer);
        rlt_register = (RelativeLayout) findViewById(R.id.rlt_register);
        rlt_status = (RelativeLayout) findViewById(R.id.rlt_status);
        rlt_setting = (RelativeLayout) findViewById(R.id.rlt_setting);
        rlt_pin = (RelativeLayout) findViewById(R.id.rlt_pin);
        rlt_gateway = (RelativeLayout) findViewById(R.id.rlt_gateway);

        rlt_mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.Phone, "");
                editor.putString(AppsContants.Spinner_list, "MOBILE");
                editor.putString(AppsContants.Title, "Mobile");
                editor.commit();

                Intent intent = new Intent(SmsHomeActivity.this, SpinnerListActivity.class);
                startActivity(intent);
            }
        });

        rlt_dth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.Phone, "");
                editor.putString(AppsContants.Spinner_list, "DTH");
                editor.putString(AppsContants.Title, "DTH");
                editor.commit();

                Intent intent = new Intent(SmsHomeActivity.this, SpinnerListActivity.class);
                startActivity(intent);
            }
        });

        rlt_datacard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.Phone, "");
                editor.putString(AppsContants.Spinner_list, "DATACARD");
                editor.putString(AppsContants.Title, "Datacard");
                editor.commit();

                Intent intent = new Intent(SmsHomeActivity.this, SpinnerListActivity.class);
                startActivity(intent);
            }
        });

        rlt_postpaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.Phone, "");
                editor.putString(AppsContants.Spinner_list, "POSTPAID");
                editor.putString(AppsContants.Title, "Postpaid");
                editor.commit();

                Intent intent = new Intent(SmsHomeActivity.this, SpinnerListActivity.class);
                startActivity(intent);
            }
        });
        rlt_landline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.Phone, "");
                editor.putString(AppsContants.Spinner_list, "LANDLINE");
                editor.putString(AppsContants.Title, "Landline");
                editor.commit();

                Intent intent = new Intent(SmsHomeActivity.this, SpinnerListActivity.class);
                startActivity(intent);
            }
        });
        rlt_balance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.mobile, "");
                editor.putString(AppsContants.code, "CB");
                editor.commit();

                Intent intent = new Intent(SmsHomeActivity.this, Service_CodeActivity.class);
                startActivity(intent);
            }
        });

        rlt_last_recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.mobile, "");
                editor.putString(AppsContants.code, "LS");
                editor.commit();

                Intent intent = new Intent(SmsHomeActivity.this, Service_CodeActivity.class);
                startActivity(intent);

            }
        });
        rlt_search_tran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.Phone, "");
                editor.putString(AppsContants.code, "SR");
                editor.commit();

                Intent intent = new Intent(SmsHomeActivity.this, Search_TransactionActivity.class);
                startActivity(intent);
            }
        });
        rlt_transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.Phone, "");
                editor.putString(AppsContants.code, "ST");
                editor.commit();

                Intent intent = new Intent(SmsHomeActivity.this, Fund_TransferActivity.class);
                startActivity(intent);
            }
        });

        rlt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.code, "TR");
                editor.putString(AppsContants.Title, "Complaint Register");
                editor.commit();

                Intent intent = new Intent(SmsHomeActivity.this, Complaint_RegActivity.class);
                startActivity(intent);
            }
        });

        rlt_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.code, "CS");
                editor.putString(AppsContants.Title, "Complaint Status");
                editor.commit();

                Intent intent = new Intent(SmsHomeActivity.this, Complaint_RegActivity.class);
                startActivity(intent);
            }
        });

        rlt_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SmsHomeActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        rlt_pin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.mobile, "");
                editor.putString(AppsContants.code, "CP");
                editor.commit();

                Intent intent = new Intent(SmsHomeActivity.this, Service_CodeActivity.class);
                startActivity(intent);
            }
        });

        rlt_gateway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.Position, "");
                editor.commit();

                Intent intent = new Intent(SmsHomeActivity.this, Gateway_SettingActivity.class);
                startActivity(intent);
            }
        });
    }
}
