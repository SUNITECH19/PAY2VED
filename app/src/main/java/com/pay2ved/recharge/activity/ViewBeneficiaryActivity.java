package com.pay2ved.recharge.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pay2ved.recharge.R;
import com.pay2ved.recharge.other.AppsContants;
import com.pay2ved.recharge.other.HttpUrlPath;
import com.pay2ved.recharge.other.NetConnection;

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

public class ViewBeneficiaryActivity extends Activity {

    String s_Name = "", s_ID = "", s_Account = "", s_Type = "", s_Ifsc = "";
    TextView txt_name, txt_number, txt_type, txt_ifsc;
    Button ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_beneficiary);

        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        s_ID = AppsContants.sharedpreferences.getString(AppsContants.ID, "");
        s_Name = AppsContants.sharedpreferences.getString(AppsContants.Name, "");
        s_Account = AppsContants.sharedpreferences.getString(AppsContants.Account, "");
        s_Type = AppsContants.sharedpreferences.getString(AppsContants.Type, "");
        s_Ifsc = AppsContants.sharedpreferences.getString(AppsContants.Ifsc, "");

        txt_name = (TextView) findViewById(R.id.txt_name);
        txt_number = (TextView) findViewById(R.id.txt_number);
        txt_type = (TextView) findViewById(R.id.txt_type);
        txt_ifsc = (TextView) findViewById(R.id.txt_ifsc);
        ok = (Button) findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txt_name.setText(s_Name);
        txt_number.setText(s_Account);
        txt_type.setText(s_Type);
        txt_ifsc.setText(s_Ifsc);

    }

}
