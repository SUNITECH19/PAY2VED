package com.pay2ved.recharge.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pay2ved.recharge.R;
import com.pay2ved.recharge.gprs_home_activity.My_AccountActivity;
import com.pay2ved.recharge.helper.Listener;
import com.pay2ved.recharge.other.AppsContants;
import com.pay2ved.recharge.other.HttpUrlPath;
import com.pay2ved.recharge.other.NetConnection;
import com.pay2ved.recharge.service.callmodel.CallObject;
import com.pay2ved.recharge.service.query.DBQuery;

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

public class ChangePasswordActivity extends AppCompatActivity implements Listener.OnObjectQueryListener {

    // TODO : Change Code And UI

    Button btn_change, cancel;
    EditText old_password, new_password;
    private ProgressDialog pDialog;
    String s_old_password = "", s_new_password = "", s_Username;
    ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        s_Username = AppsContants.sharedpreferences.getString(AppsContants.Username, "");

        cancel = (Button) findViewById(R.id.cancel);
        btn_change = (Button) findViewById(R.id.btn_change);
        old_password = (EditText) findViewById(R.id.old_password);
        new_password = (EditText) findViewById(R.id.new_password);
        img_back = (ImageView) findViewById(R.id.img_back);

        pDialog = new ProgressDialog(ChangePasswordActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

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
        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                s_old_password = old_password.getText().toString().trim();
                s_new_password = new_password.getText().toString().trim();

                boolean isError = false;
                if (s_old_password.equals("")) {
                    isError = true;
                    old_password.requestFocus();
                    Toast.makeText(ChangePasswordActivity.this, "Please Enter Pin", Toast.LENGTH_LONG).show();

                } else if (s_new_password.equals("")) {
                    isError = true;
                    new_password.requestFocus();
                    Toast.makeText(ChangePasswordActivity.this, "Please Enter New Pin", Toast.LENGTH_LONG).show();

                } else {
                    if (!isError) {
                        if (NetConnection.isConnected(ChangePasswordActivity.this)) {
                            pDialog.show();
                            DBQuery.queryToChangePassword( ChangePasswordActivity.this, s_Username, s_old_password, s_new_password );
                        } else {
                            Toast.makeText(ChangePasswordActivity.this, "Internet Connection error", Toast.LENGTH_SHORT).show();
                        }

                    }
                }


            }
        });

    }

    @Override
    public void onResponse(@Nullable CallObject callObject) {
        pDialog.dismiss();
        if (callObject != null){
            // Success... or Failed Response from server...
            Toast.makeText( this, callObject.getMessage() + "", Toast.LENGTH_LONG ).show();
            finish();
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(ChangePasswordActivity.this);
            builder.setMessage("Failed! Please try again.!");
            builder.setCancelable(false);
            builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();
        }

    }

//============================================end ======================================//

}
