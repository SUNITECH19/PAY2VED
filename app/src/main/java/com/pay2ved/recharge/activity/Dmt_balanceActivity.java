package com.pay2ved.recharge.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pay2ved.recharge.R;
import com.pay2ved.recharge.other.AppsContants;

public class Dmt_balanceActivity extends Activity {
    TextView txt_name, txt_number, txt_balance;
    Button ok;
    String s_Balance = "", s_Name = "", s_Sender_mobile = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dmt_balance);

        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        s_Balance = AppsContants.sharedpreferences.getString(AppsContants.Balance, "");
        s_Name = AppsContants.sharedpreferences.getString(AppsContants.Name, "");
        s_Sender_mobile = AppsContants.sharedpreferences.getString(AppsContants.Sender_mobile, "");


        txt_name = findViewById(R.id.txt_name);
        txt_number = findViewById(R.id.txt_number);
        txt_balance = findViewById(R.id.txt_balance);
        ok = findViewById(R.id.ok);

        txt_name.setText(s_Name);
        txt_number.setText(s_Sender_mobile);
        txt_balance.setText(s_Balance);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
