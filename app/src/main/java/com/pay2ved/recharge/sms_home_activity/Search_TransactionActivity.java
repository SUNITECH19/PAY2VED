package com.pay2ved.recharge.sms_home_activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pay2ved.recharge.R;
import com.pay2ved.recharge.activity.ContactActivity;
import com.pay2ved.recharge.other.AppsContants;

public class Search_TransactionActivity extends AppCompatActivity {

    EditText mobile;
    Button ok, cancel;
    ImageView img_back, img_contact;
    String s_mobile = "", s_phone = "", s_Spinner_list = "";
    TextView txt_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search__transaction);


        txt_list = (TextView) findViewById(R.id.txt_list);
        mobile = (EditText) findViewById(R.id.mobile);
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
                Intent intent = new Intent(Search_TransactionActivity.this, ContactActivity.class);
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

                if (s_mobile.equals("")) {
                    mobile.requestFocus();
                    Toast.makeText(Search_TransactionActivity.this, "Please Enter Mobile No or Txn No", Toast.LENGTH_LONG).show();


                } else {
                    AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                    editor.putString(AppsContants.mobile, s_mobile);
                    editor.commit();

                    Intent intent = new Intent(Search_TransactionActivity.this, Service_CodeActivity.class);
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