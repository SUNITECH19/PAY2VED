package com.pay2ved.recharge.gprs_home_activity;

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
import com.pay2ved.recharge.activity.GprsHomeActivity;
import com.pay2ved.recharge.other.AppsContants;

public class ReportsActivity extends AppCompatActivity {

    // TODO : Remove/Delete this Activity...

    RelativeLayout rlt_recharge_report, rlt_transaction_report, rlt_commission_report, rlt_complaint_report, rlt_payment_report;
    RelativeLayout rlt_home, rlt_my_account, rlt_reports;
    ImageView img_back;
    TextView txt__recharge_report, txt__transaction_report, txt__commission_report, txt__complaint_report, txt__payment_report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        txt__recharge_report = (TextView) findViewById(R.id.txt__recharge_report);
        txt__transaction_report = (TextView) findViewById(R.id.txt__transaction_report);
        txt__commission_report = (TextView) findViewById(R.id.txt__commission_report);
        txt__complaint_report = (TextView) findViewById(R.id.txt__complaint_report);
        txt__payment_report = (TextView) findViewById(R.id.txt__payment_report);

        rlt_recharge_report = (RelativeLayout) findViewById(R.id.rlt_recharge_report);
        rlt_transaction_report = (RelativeLayout) findViewById(R.id.rlt_transaction_report);
        rlt_commission_report = (RelativeLayout) findViewById(R.id.rlt_commission_report);
        rlt_complaint_report = (RelativeLayout) findViewById(R.id.rlt_complaint_report);
        rlt_payment_report = (RelativeLayout) findViewById(R.id.rlt_payment_report);

        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txt__recharge_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.NUMBER, "");
                editor.putString(AppsContants.DATE_FROM, "");
                editor.putString(AppsContants.DATE_TO, "");
                editor.commit();

                Intent intent = new Intent(ReportsActivity.this, Recharge_ReportActivity.class);
                startActivity(intent);

            }
        });

        txt__transaction_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.DATE_FROM, "");
                editor.putString(AppsContants.DATE_TO, "");
                editor.putString(AppsContants.Action, "rpt-transaction.php");
                editor.putString(AppsContants.Title, "Transaction Report");
                editor.commit();

                Intent intent = new Intent(ReportsActivity.this, Show_ReportActivity.class);
                startActivity(intent);

            }
        });

        txt__commission_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.DATE_FROM, "");
                editor.putString(AppsContants.DATE_TO, "");
                editor.putString(AppsContants.Action, "rpt-commission.php");
                editor.putString(AppsContants.Title, "Commission Report");
                editor.commit();

                Intent intent = new Intent(ReportsActivity.this, Commission_ReportActivity.class);
                startActivity(intent);

            }
        });


        txt__complaint_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.DATE_FROM, "");
                editor.putString(AppsContants.DATE_TO, "");
                editor.putString(AppsContants.Action, "rpt-complaint.php");
                editor.putString(AppsContants.Title, "Complaint Report");
                editor.commit();

                Intent intent = new Intent(ReportsActivity.this, Complaint_ReportActivity.class);
                startActivity(intent);

            }
        });

        txt__payment_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.DATE_FROM, "");
                editor.putString(AppsContants.DATE_TO, "");
                editor.putString(AppsContants.Action, "rpt-payment.php");
                editor.putString(AppsContants.Title, "Payment Report");
                editor.commit();

                Intent intent = new Intent(ReportsActivity.this, Complaint_ReportActivity.class);
                startActivity(intent);

            }
        });


        //=====================================================

        rlt_recharge_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.NUMBER, "");
                editor.putString(AppsContants.DATE_FROM, "");
                editor.putString(AppsContants.DATE_TO, "");
                editor.commit();

                Intent intent = new Intent(ReportsActivity.this, Recharge_ReportActivity.class);
                startActivity(intent);

            }
        });

        rlt_transaction_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.DATE_FROM, "");
                editor.putString(AppsContants.DATE_TO, "");
                editor.putString(AppsContants.Action, "rpt-transaction.php");
                editor.putString(AppsContants.Title, "Transaction Report");
                editor.commit();

                Intent intent = new Intent(ReportsActivity.this, Show_ReportActivity.class);
                startActivity(intent);

            }
        });

        rlt_commission_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.DATE_FROM, "");
                editor.putString(AppsContants.DATE_TO, "");
                editor.putString(AppsContants.Action, "rpt-commission.php");
                editor.putString(AppsContants.Title, "Commission Report");
                editor.commit();

                Intent intent = new Intent(ReportsActivity.this, Commission_ReportActivity.class);
                startActivity(intent);

            }
        });


        rlt_complaint_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.DATE_FROM, "");
                editor.putString(AppsContants.DATE_TO, "");
                editor.putString(AppsContants.Action, "rpt-complaint.php");
                editor.putString(AppsContants.Title, "Complaint Report");
                editor.commit();

                Intent intent = new Intent(ReportsActivity.this, Complaint_ReportActivity.class);
                startActivity(intent);

            }
        });

        rlt_payment_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.DATE_FROM, "");
                editor.putString(AppsContants.DATE_TO, "");
                editor.putString(AppsContants.Action, "rpt-payment.php");
                editor.putString(AppsContants.Title, "Payment Report");
                editor.commit();

                Intent intent = new Intent(ReportsActivity.this, Complaint_ReportActivity.class);
                startActivity(intent);

            }
        });


        //========================================================
        rlt_home = (RelativeLayout) findViewById(R.id.rlt_home);
        rlt_my_account = (RelativeLayout) findViewById(R.id.rlt_my_account);
        rlt_reports = (RelativeLayout) findViewById(R.id.rlt_reports);

        rlt_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReportsActivity.this, GprsHomeActivity.class);
                startActivity(intent);

                finish();
            }
        });

        rlt_my_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ReportsActivity.this, My_AccountActivity.class);
                startActivity(intent);

                finish();

            }
        });

        rlt_reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              /*  Intent intent = new Intent(ReportsActivity.this, ReportsActivity.class);
                startActivity(intent);

                finish();*/
            }
        });


        //========================================================================

    }
}
