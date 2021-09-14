package com.pay2ved.recharge.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pay2ved.recharge.R;
import com.pay2ved.recharge.gprs_home_activity.BeneficiaryActivity;
import com.pay2ved.recharge.gprs_home_activity.Money_TransActivity;
import com.pay2ved.recharge.gprs_home_activity.TransactionHistoryActivity;
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

public class DMT_DetailsActivity extends AppCompatActivity {
    ImageView img_back;
    RelativeLayout rlt_listbeneficiary, rlt_walletbalance, rlt_transaction_history;
    TextView txt__listbeneficiary, txt__walletbalance, txt__transaction_history;
    TextView txt_name, txt_number;
    String s_Username = "", s_Password = "", s_Name = "", s_Number = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dmt__details);

        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        s_Username = AppsContants.sharedpreferences.getString(AppsContants.Username, "");
        s_Password = AppsContants.sharedpreferences.getString(AppsContants.Password, "");
        s_Name = AppsContants.sharedpreferences.getString(AppsContants.Name, "");
        s_Number = AppsContants.sharedpreferences.getString(AppsContants.Phone, "");

        rlt_listbeneficiary = (RelativeLayout) findViewById(R.id.rlt_listbeneficiary);
        rlt_walletbalance = (RelativeLayout) findViewById(R.id.rlt_walletbalance);
        rlt_transaction_history = (RelativeLayout) findViewById(R.id.rlt_transaction_history);

        txt__listbeneficiary = (TextView) findViewById(R.id.txt__listbeneficiary);
        txt__walletbalance = (TextView) findViewById(R.id.txt__walletbalance);
        txt__transaction_history = (TextView) findViewById(R.id.txt__transaction_history);

        txt_name = (TextView) findViewById(R.id.txt_name);
        txt_number = (TextView) findViewById(R.id.txt_number);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.Phone, "");
                editor.commit();
            }
        });

        txt_name.setText(s_Name);
        txt_number.setText(s_Number);

        rlt_walletbalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isError = false;
                if (s_Number.equals("")) {
                    isError = true;
                    Toast.makeText(DMT_DetailsActivity.this, "Mobile Number Can't Blank", Toast.LENGTH_LONG).show();
                } else {
                    if (!isError) {
                        if (NetConnection.isConnected(DMT_DetailsActivity.this)) {
                            SenderBalance task = new SenderBalance();
                            task.execute();
                        } else {
                            Toast.makeText(DMT_DetailsActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            }
        });
        rlt_listbeneficiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (s_Number.equals("")) {
                    Toast.makeText(DMT_DetailsActivity.this, "Mobile Number Can't Blank", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(DMT_DetailsActivity.this, BeneficiaryActivity.class);
                    startActivity(intent);
                }
            }
        });

        rlt_transaction_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (s_Number.equals("")) {
                    Toast.makeText(DMT_DetailsActivity.this, "Mobile Number Can't Blank", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(DMT_DetailsActivity.this, TransactionHistoryActivity.class);
                    startActivity(intent);
                }
            }
        });

        //==============================================
        txt__walletbalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isError = false;
                if (s_Number.equals("")) {
                    isError = true;
                    Toast.makeText(DMT_DetailsActivity.this, "Mobile Number Can't Blank", Toast.LENGTH_LONG).show();
                } else {
                    if (!isError) {
                        if (NetConnection.isConnected(DMT_DetailsActivity.this)) {
                            SenderBalance task = new SenderBalance();
                            task.execute();
                        } else {
                            Toast.makeText(DMT_DetailsActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            }
        });
        txt__listbeneficiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (s_Number.equals("")) {
                    Toast.makeText(DMT_DetailsActivity.this, "Mobile Number Can't Blank", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(DMT_DetailsActivity.this, BeneficiaryActivity.class);
                    startActivity(intent);
                }
            }
        });

        txt__transaction_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (s_Number.equals("")) {
                    Toast.makeText(DMT_DetailsActivity.this, "Mobile Number Can't Blank", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(DMT_DetailsActivity.this, TransactionHistoryActivity.class);
                    startActivity(intent);
                }
            }
        });
        //==============================================
    }


    public class SenderBalance extends AsyncTask<String, Void, String> {
        String Errormessage, object_res;
        boolean IsError = false;
        String msg = "";
        String RSP_CODE = "", SEND_MOB = "", SENDER_NAME = "", SENDER_BALANCE = "";
        JSONObject jsonObject;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... arg0) {

            HttpClient htppclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(HttpUrlPath.urlPath + HttpUrlPath.action_money_transfer);

            try {
                List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
                nameValuePair.add(new BasicNameValuePair("username", s_Username));
                nameValuePair.add(new BasicNameValuePair("password", s_Password));
                nameValuePair.add(new BasicNameValuePair("request", "senderBalance"));
                nameValuePair.add(new BasicNameValuePair("mobile", s_Number));

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePair, "UTF-8"));
                HttpResponse response = htppclient.execute(httppost);
                object_res = EntityUtils.toString(response.getEntity());
                System.out.println("#####object registration=" + object_res);
                jsonObject = new JSONObject(object_res);
                if (jsonObject.length() == 3) {
                    RSP_CODE = jsonObject.getString("RSP_CODE");
                    Errormessage = jsonObject.getString("RSP_MSG");
                    SEND_MOB = jsonObject.getString("SEND_MOB");

                } else {
                    RSP_CODE = jsonObject.getString("RSP_CODE");
                    Errormessage = jsonObject.getString("RSP_MSG");
                    SEND_MOB = jsonObject.getString("SEND_MOB");
                    SENDER_NAME = jsonObject.getString("SENDER_NAME");
                    SENDER_BALANCE = jsonObject.getString("BALANCE");
                }

            } catch (Exception e) {
                IsError = true;
                Log.v("22", "22" + e.getMessage());
                e.printStackTrace();
            }
            return msg;
        }


        protected void onPostExecute(String result1) {
            super.onPostExecute(result1);
            if (!IsError) {
                if (RSP_CODE.equals("0")) {
                    AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                    editor.putString(AppsContants.Name, SENDER_NAME);
                    editor.putString(AppsContants.Sender_mobile, SEND_MOB);
                    editor.putString(AppsContants.Balance, SENDER_BALANCE);
                    editor.commit();

                    Intent intent = new Intent(DMT_DetailsActivity.this, Dmt_balanceActivity.class);
                    startActivity(intent);

                } else if (RSP_CODE.equals("1")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DMT_DetailsActivity.this);
                    builder.setMessage(Errormessage);
                    builder.setCancelable(false);
                    builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @SuppressLint("NewApi")
                        public void onClick(DialogInterface dialog, int which) {
                            AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                            editor.putString(AppsContants.Phone, "");
                            editor.commit();

                            Intent intent = new Intent(DMT_DetailsActivity.this, Money_TransActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                } else if (RSP_CODE.equals("2")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DMT_DetailsActivity.this);
                    builder.setMessage(Errormessage);
                    builder.setCancelable(false);
                    builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @SuppressLint("NewApi")
                        public void onClick(DialogInterface dialog, int which) {
                            AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                            editor.putString(AppsContants.Phone, "");
                            editor.commit();

                            Intent intent = new Intent(DMT_DetailsActivity.this, Money_TransActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                } else if (RSP_CODE.equals("9")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DMT_DetailsActivity.this);
                    builder.setMessage(Errormessage);
                    builder.setCancelable(false);
                    builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @SuppressLint("NewApi")
                        public void onClick(DialogInterface dialog, int which) {
                            AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                            editor.putString(AppsContants.Phone, "");
                            editor.commit();

                            Intent intent = new Intent(DMT_DetailsActivity.this, Money_TransActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                }
            } else {
                Toast.makeText(DMT_DetailsActivity.this, "" + Errormessage, Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
        editor.putString(AppsContants.Phone, "");
        editor.commit();
    }
}
