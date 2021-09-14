package com.pay2ved.recharge.gprs_home_activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.pay2ved.recharge.R;
import com.pay2ved.recharge.activity.Bank_DetailsActivity;
import com.pay2ved.recharge.activity.ChangePasswordActivity;
import com.pay2ved.recharge.activity.Contact_UsActivity;
import com.pay2ved.recharge.activity.GprsHomeActivity;
import com.pay2ved.recharge.activity.ProfileActivity;
import com.pay2ved.recharge.activity.SplashActivity;
import com.pay2ved.recharge.other.AppsContants;

public class My_AccountActivity extends AppCompatActivity {

    // TODO : Delete this Activity...

    RelativeLayout rlt_edit_profile, rlt_change_password, rlt_my_commission, rlt_contact_us, rlt_bank_detail, rlt_Logout;
    RelativeLayout rlt_home, rlt_my_account, rlt_reports;
    ImageView img_back;
    TextView txt__profile, txt__change_password, txt__commission, txt__contact_us, txt__bank_detail, txt__Logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my__account);


        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txt__profile = (TextView) findViewById(R.id.txt__profile);
        txt__change_password = (TextView) findViewById(R.id.txt__change_password);
        txt__commission = (TextView) findViewById(R.id.txt__commission);
        txt__contact_us = (TextView) findViewById(R.id.txt__contact_us);
        txt__bank_detail = (TextView) findViewById(R.id.txt__bank_detail);
        txt__Logout = (TextView) findViewById(R.id.txt__Logout);

        rlt_edit_profile = (RelativeLayout) findViewById(R.id.rlt_edit_profile);
        rlt_change_password = (RelativeLayout) findViewById(R.id.rlt_change_password);
        rlt_my_commission = (RelativeLayout) findViewById(R.id.rlt_my_commission);
        rlt_contact_us = (RelativeLayout) findViewById(R.id.rlt_contact_us);
        rlt_bank_detail = (RelativeLayout) findViewById(R.id.rlt_bank_detail);
        rlt_Logout = (RelativeLayout) findViewById(R.id.rlt_Logout);

        //========================================================
        rlt_home = (RelativeLayout) findViewById(R.id.rlt_home);
        rlt_my_account = (RelativeLayout) findViewById(R.id.rlt_my_account);
        rlt_reports = (RelativeLayout) findViewById(R.id.rlt_reports);

        rlt_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(My_AccountActivity.this, GprsHomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        rlt_my_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* Intent intent = new Intent(My_AccountActivity.this, My_AccountActivity.class);
                startActivity(intent);
                finish();*/
            }
        });

        rlt_reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(My_AccountActivity.this, ReportsActivity.class);
                startActivity(intent);
                finish();
            }
        });


        //========================================================================

        txt__profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(My_AccountActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        txt__change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(My_AccountActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
            }
        });

        txt__commission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(My_AccountActivity.this, My_CommissionActivity.class);
                startActivity(intent);
            }
        });
        txt__contact_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(My_AccountActivity.this, Contact_UsActivity.class);
                startActivity(intent);
            }
        });
        txt__bank_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(My_AccountActivity.this, Bank_DetailsActivity.class);
                startActivity(intent);
            }
        });

        txt__Logout.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {

                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.Mobile, "");
                editor.putString(AppsContants.Password, "");
                editor.commit();

                Intent intent = new Intent(My_AccountActivity.this, SplashActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });

        rlt_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(My_AccountActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        rlt_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(My_AccountActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
            }
        });

        rlt_my_commission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(My_AccountActivity.this, My_CommissionActivity.class);
                startActivity(intent);
            }
        });

        rlt_contact_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(My_AccountActivity.this, Contact_UsActivity.class);
                startActivity(intent);
            }
        });

        rlt_bank_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(My_AccountActivity.this, Bank_DetailsActivity.class);
                startActivity(intent);
            }
        });

        rlt_Logout.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {

                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.Mobile, "");
                editor.putString(AppsContants.Password, "");
                editor.commit();

                Intent intent = new Intent(My_AccountActivity.this, SplashActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });
    }
}
