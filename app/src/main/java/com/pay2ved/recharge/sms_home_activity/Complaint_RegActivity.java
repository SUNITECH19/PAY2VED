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
import com.pay2ved.recharge.other.AppsContants;

public class Complaint_RegActivity extends AppCompatActivity {

    EditText amount;
    Button ok, cancel;
    ImageView img_back;
    TextView txt_list;
    String s_mobile = "", s_Spinner_list = "", s_Title = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint__reg);

        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        s_Spinner_list = AppsContants.sharedpreferences.getString(AppsContants.Spinner_list, "");
        s_Title = AppsContants.sharedpreferences.getString(AppsContants.Title, "");

        txt_list = (TextView) findViewById(R.id.txt_list);
        txt_list.setText(s_Title);

        amount = (EditText) findViewById(R.id.amount);
        cancel = (Button) findViewById(R.id.cancel);
        ok = (Button) findViewById(R.id.ok);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

                s_mobile = amount.getText().toString().trim();

                if (s_mobile.equals("")) {
                    amount.requestFocus();
                    Toast.makeText(Complaint_RegActivity.this, "Please Enter Txn No", Toast.LENGTH_LONG).show();

                } else {
                    AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                    editor.putString(AppsContants.mobile, s_mobile);
                    editor.commit();

                    Intent intent = new Intent(Complaint_RegActivity.this, Service_CodeActivity.class);
                    startActivity(intent);

                    finish();
                }

            }
        });
    }

}