package com.pay2ved.recharge.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.pay2ved.recharge.R;
import com.pay2ved.recharge.gprs_home_activity.My_AccountActivity;
import com.pay2ved.recharge.gprs_home_activity.ReportsActivity;
import com.pay2ved.recharge.other.AppsContants;

public class ProfileActivity extends AppCompatActivity {

    // TODO : Delete This Activity...

    RelativeLayout rlt_home, rlt_my_account, rlt_reports;
    String s_Username = "", s_Password = "", s_Mobile = "", s_Balance = "", s_Uid = "", s_Type = "", s_Name = "", s_Fullname = "", s_CompanyName = "", s_Email = "", s_Address = "", s_City = "", s_State = "";
    ImageView img_back;
    TextView txt_name, txt_Mobile, txt_u_id, txt_Fullname, txt_CompanyName, txt_Email, txt_Address, txt_City, txt_State, txt_Balance;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        s_Username = AppsContants.sharedpreferences.getString(AppsContants.Username, "");
        s_Password = AppsContants.sharedpreferences.getString(AppsContants.Password, "");
        s_Mobile = AppsContants.sharedpreferences.getString(AppsContants.Mobile, "");
        s_Uid = AppsContants.sharedpreferences.getString(AppsContants.Uid, "");
        s_Type = AppsContants.sharedpreferences.getString(AppsContants.Type, "");
        s_Fullname = AppsContants.sharedpreferences.getString(AppsContants.Fullname, "");
        s_CompanyName = AppsContants.sharedpreferences.getString(AppsContants.CompanyName, "");
        s_Email = AppsContants.sharedpreferences.getString(AppsContants.Email, "");
        s_Address = AppsContants.sharedpreferences.getString(AppsContants.Address, "");
        s_City = AppsContants.sharedpreferences.getString(AppsContants.City, "");
        s_State = AppsContants.sharedpreferences.getString(AppsContants.State, "");
        s_Balance = AppsContants.sharedpreferences.getString(AppsContants.Balance, "");

        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txt_name = (TextView) findViewById(R.id.txt_name);
        txt_Mobile = (TextView) findViewById(R.id.txt_Mobile);
        txt_u_id = (TextView) findViewById(R.id.txt_u_id);
        txt_Fullname = (TextView) findViewById(R.id.txt_Fullname);
        txt_CompanyName = (TextView) findViewById(R.id.txt_CompanyName);
        txt_Email = (TextView) findViewById(R.id.txt_Email);
        txt_Address = (TextView) findViewById(R.id.txt_Address);
        txt_City = (TextView) findViewById(R.id.txt_City);
        txt_State = (TextView) findViewById(R.id.txt_State);
        txt_Balance = (TextView) findViewById(R.id.txt_Balance);

        String[] arr = s_Fullname.split(" ");
        s_Name = arr[0];
        txt_name.setText(s_Name);

        txt_Mobile.setText(s_Mobile);
        txt_u_id.setText(s_Uid);
        txt_Fullname.setText(s_Fullname);
        txt_CompanyName.setText(s_CompanyName);
        txt_Email.setText(s_Email);
        txt_Address.setText(s_Address);
        txt_City.setText(s_City);
        txt_State.setText(s_State);
        txt_Balance.setText(s_Balance);

        //========================================================
        rlt_home = (RelativeLayout) findViewById(R.id.rlt_home);
        rlt_my_account = (RelativeLayout) findViewById(R.id.rlt_my_account);
        rlt_reports = (RelativeLayout) findViewById(R.id.rlt_reports);

        rlt_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, GprsHomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        rlt_my_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ProfileActivity.this, My_AccountActivity.class);
                startActivity(intent);
                finish();
            }
        });

        rlt_reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ProfileActivity.this, ReportsActivity.class);
                startActivity(intent);
                finish();
            }
        });


        //========================================================================

    }
}
