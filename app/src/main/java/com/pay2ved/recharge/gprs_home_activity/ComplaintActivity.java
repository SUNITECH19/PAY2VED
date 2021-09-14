package com.pay2ved.recharge.gprs_home_activity;

import android.annotation.SuppressLint;
import android.app.Activity;
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

import com.pay2ved.recharge.R;
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

public class ComplaintActivity extends Activity implements Listener.OnObjectQueryListener {

    EditText txn_no;
    Button ok, cancel;
    ImageView img_back;
    TextView txt_list;
    String s_Username = "", s_Password = "", s_TXN_NO = "";
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);

        pDialog = new ProgressDialog(ComplaintActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        s_TXN_NO = AppsContants.sharedpreferences.getString(AppsContants.ID, "");
        s_Username = AppsContants.sharedpreferences.getString(AppsContants.Username, "");
        s_Password = AppsContants.sharedpreferences.getString(AppsContants.Password, "");


        txt_list = (TextView) findViewById(R.id.txt_list);
        txn_no = (EditText) findViewById(R.id.txn_no);
        cancel = (Button) findViewById(R.id.cancel);
        ok = (Button) findViewById(R.id.ok);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txn_no.setText(s_TXN_NO);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                s_TXN_NO = txn_no.getText().toString().trim();

                boolean isError = false;

                if (s_TXN_NO.equals("")) {
                    isError = true;
                    txn_no.requestFocus();
                    Toast.makeText(ComplaintActivity.this, "Please Enter Txn No", Toast.LENGTH_LONG).show();

                } else {

                    if (!isError) {
                        if (NetConnection.isConnected(ComplaintActivity.this)) {

                            pDialog.show();
                            DBQuery.queryToComplaint( ComplaintActivity.this, s_Username, s_Password, s_TXN_NO );

//                            Httpjsontask task = new Httpjsontask();
//                            task.execute();
                        } else {
                            Toast.makeText(ComplaintActivity.this, "Internet Connection error", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            }
        });
    }

    @Override
    public void onResponse(@Nullable CallObject callObject) {

        pDialog.dismiss();

        if (callObject !=null){
            AlertDialog.Builder builder = new AlertDialog.Builder(ComplaintActivity.this);
            builder.setMessage("" + callObject.getMessage());
            builder.setCancelable(false);
            builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @SuppressLint("NewApi")
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(ComplaintActivity.this, Recharge_ReportActivity.class);
                    startActivity(intent);
                    finishAffinity();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }else{
            Toast.makeText(ComplaintActivity.this, "Something Went Wrong!" , Toast.LENGTH_SHORT).show();
        }
    }

    // TODO : Remove This Class... ========================================start_http==========================================/

    public class Httpjsontask extends AsyncTask<String, Void, String> {

        String Errormessage;
        String UserRegisterID;
        boolean IsError = false;
        String msg = "", s_data = "";
        String p_Txn_No = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ComplaintActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... arg0) {

            HttpClient htppclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(HttpUrlPath.urlPath + HttpUrlPath.action_complaint);

            try {
                List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

                nameValuePair.add(new BasicNameValuePair("username", s_Username));
                nameValuePair.add(new BasicNameValuePair("password", s_Password));
                nameValuePair.add(new BasicNameValuePair("txn_no", s_TXN_NO));

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePair, "UTF-8"));
                HttpResponse response = htppclient.execute(httppost);
                String object = EntityUtils.toString(response.getEntity());
                System.out.println("#####object registration=" + object);
                JSONObject js = new JSONObject(object);

                Errormessage = js.getString("message");
                s_data = js.getString("data");

            } catch (Exception e) {
                IsError = true;
                Log.v("22", "22" + e.getMessage());
                e.printStackTrace();
            }
            return msg;
        }


        protected void onPostExecute(String result1) {
            super.onPostExecute(result1);
            if (pDialog.isShowing())
                pDialog.dismiss();

            if (!IsError) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ComplaintActivity.this);
                builder.setMessage("" + Errormessage);
                builder.setCancelable(false);
                builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @SuppressLint("NewApi")
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(ComplaintActivity.this, Recharge_ReportActivity.class);
                        startActivity(intent);
                        finishAffinity();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

            } else {
                Toast.makeText(ComplaintActivity.this, "" + Errormessage, Toast.LENGTH_SHORT).show();
            }
        }

    }

    //============================================end ======================================//


}
