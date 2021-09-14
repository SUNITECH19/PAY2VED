package com.pay2ved.recharge.sms_home_activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pay2ved.recharge.R;
import com.pay2ved.recharge.other.AppsContants;

public class Service_CodeActivity extends Activity {

    TextView txt_sms_mode;
    ImageView img_back;
    Button cancel, ok;
    String s_code = "", s_mobile = "", s_gateway_no = "", s_sms = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change__pin);

        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        s_code = AppsContants.sharedpreferences.getString(AppsContants.code, "");
        s_mobile = AppsContants.sharedpreferences.getString(AppsContants.mobile, "");
        s_gateway_no = AppsContants.sharedpreferences.getString(AppsContants.GateWay_No, "");

        txt_sms_mode = (TextView) findViewById(R.id.txt_sms_mode);
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

        txt_sms_mode.setText(s_code+s_mobile);
        s_sms = s_code+s_mobile;

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // SmsManager.getDefault().sendTextMessage(s_mobile, null, s_sms, null, null);

                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(s_gateway_no, null, s_sms, null, null);
                    Toast.makeText(getApplicationContext(), "Message Sent",
                            Toast.LENGTH_LONG).show();
                } catch (Exception ex) {
                    Toast.makeText(getApplicationContext(), ex.getMessage().toString(),
                            Toast.LENGTH_LONG).show();
                    ex.printStackTrace();
                }

                finish();

            }
        });
    }
}