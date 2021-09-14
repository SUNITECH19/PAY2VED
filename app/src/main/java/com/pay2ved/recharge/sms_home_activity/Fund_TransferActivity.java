package com.pay2ved.recharge.sms_home_activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pay2ved.recharge.R;
import com.pay2ved.recharge.activity.ContactActivity;
import com.pay2ved.recharge.other.AppsContants;

public class Fund_TransferActivity extends AppCompatActivity {

    EditText mobile, amount;
    Button ok, cancel;
    ImageView img_back, img_contact;
    String s_mobile = "", s_amount = "", s_phone = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fund__transfer);

        mobile = (EditText) findViewById(R.id.mobile);
        amount = (EditText) findViewById(R.id.amount);
        cancel = (Button) findViewById(R.id.cancel);
        ok = (Button) findViewById(R.id.ok);
        img_contact = (ImageView) findViewById(R.id.img_contact);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        img_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Fund_TransferActivity.this, ContactActivity.class);
                startActivity(intent);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                s_mobile = mobile.getText().toString().trim();
                s_amount = amount.getText().toString().trim();

                if (s_mobile.equals("")) {
                    mobile.requestFocus();
                    Toast.makeText(Fund_TransferActivity.this, "Please Enter Mobile No", Toast.LENGTH_LONG).show();

                } else if (s_amount.equals("")) {
                    amount.requestFocus();
                    Toast.makeText(Fund_TransferActivity.this, "Please Enter Amount", Toast.LENGTH_LONG).show();

                } else {
                    AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                    editor.putString(AppsContants.mobile, s_mobile);
                    editor.putString(AppsContants.amount, s_amount);
                    editor.commit();

                    Intent intent = new Intent(Fund_TransferActivity.this, SendSmsActivity.class);
                    startActivity(intent);

                    finish();
                }

            }
        });
    }

    @Override
    protected void onResume() {

        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        s_phone = AppsContants.sharedpreferences.getString(AppsContants.Phone, "");

        mobile.setText(s_phone);

        super.onResume();
    }
}