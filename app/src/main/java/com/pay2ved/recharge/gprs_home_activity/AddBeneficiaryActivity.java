package com.pay2ved.recharge.gprs_home_activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class AddBeneficiaryActivity extends AppCompatActivity {
    private ProgressDialog pDialog;
    EditText edt_name, edt_mobile, edt_account, edt_ifsc;
    Button btn_beneficiary;
    ImageView img_back;
    String s_mobile = "", s_name = "", s_account = "", s_ifsc = "";
    String s_Username = "", s_Password = "", S_phone = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_beneficiary);

        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        s_Username = AppsContants.sharedpreferences.getString(AppsContants.Username, "");
        s_Password = AppsContants.sharedpreferences.getString(AppsContants.Password, "");
        S_phone = AppsContants.sharedpreferences.getString(AppsContants.Phone, "");

        edt_mobile = (EditText) findViewById(R.id.edt_mobile);
        edt_name = (EditText) findViewById(R.id.edt_name);
        edt_account = (EditText) findViewById(R.id.edt_account);
        edt_ifsc = (EditText) findViewById(R.id.edt_ifsc);
        btn_beneficiary = (Button) findViewById(R.id.btn_beneficiary);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        edt_mobile.setText(S_phone);


        btn_beneficiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                s_name = edt_name.getText().toString().trim();
                s_mobile = edt_mobile.getText().toString().trim();
                s_account = edt_account.getText().toString().trim();
                s_ifsc = edt_ifsc.getText().toString().trim();

                boolean isError = false;

                if (s_mobile.equals("")) {
                    isError = true;
                    edt_mobile.requestFocus();
                    Toast.makeText(AddBeneficiaryActivity.this, "Please Enter Mobile Number", Toast.LENGTH_LONG).show();

                } else if (s_name.equals("")) {
                    isError = true;
                    edt_name.requestFocus();
                    Toast.makeText(AddBeneficiaryActivity.this, "Please Enter Name", Toast.LENGTH_LONG).show();

                } else if (s_account.equals("")) {
                    isError = true;
                    edt_account.requestFocus();
                    Toast.makeText(AddBeneficiaryActivity.this, "Please Enter Account Number", Toast.LENGTH_LONG).show();

                } else if (s_ifsc.equals("")) {
                    isError = true;
                    edt_ifsc.requestFocus();
                    Toast.makeText(AddBeneficiaryActivity.this, "Please Enter Ifsc Code", Toast.LENGTH_LONG).show();

                } else {
                    if (!isError) {
                        if (NetConnection.isConnected(AddBeneficiaryActivity.this)) {
                            SenderValidate task = new SenderValidate();
                            task.execute();
                        } else {
                            Toast.makeText(AddBeneficiaryActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            }
        });
    }


    //========================================start_http==========================================/
    public class SenderValidate extends AsyncTask<String, Void, String> {

        String Errormessage, object_res;
        boolean IsError = false;
        String msg = "", s_data = "";
        String RSP_CODE = "", SEND_MOB = "", BEN_NAME = "", BEN_CODE = "";
        JSONObject jsonObject;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AddBeneficiaryActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... arg0) {

            HttpClient htppclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(HttpUrlPath.urlPath + HttpUrlPath.action_money_transfer);

            try {
                List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
                nameValuePair.add(new BasicNameValuePair("username", s_Username));
                nameValuePair.add(new BasicNameValuePair("password", s_Password));
                nameValuePair.add(new BasicNameValuePair("request", "beneficiaryAdd"));
                nameValuePair.add(new BasicNameValuePair("mobile", s_mobile));
                nameValuePair.add(new BasicNameValuePair("ben_name", s_name));
                nameValuePair.add(new BasicNameValuePair("ben_account", s_account));
                nameValuePair.add(new BasicNameValuePair("ifsc", s_ifsc));

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
                    BEN_NAME = jsonObject.getString("BEN_NAME");
                    BEN_CODE = jsonObject.getString("BEN_CODE");
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
            if (pDialog.isShowing())
                pDialog.dismiss();

            if (!IsError) {
                if (RSP_CODE.equals("0")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddBeneficiaryActivity.this);
                    builder.setMessage(Errormessage);
                    builder.setCancelable(false);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(AddBeneficiaryActivity.this, BeneficiaryActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                } else if (RSP_CODE.equals("1")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddBeneficiaryActivity.this);
                    builder.setMessage(Errormessage);
                    builder.setCancelable(false);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                            editor.putString(AppsContants.Phone, "");
                            editor.commit();

                            Intent intent = new Intent(AddBeneficiaryActivity.this, Money_TransActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                } else if (RSP_CODE.equals("2")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddBeneficiaryActivity.this);
                    builder.setMessage(Errormessage);
                    builder.setCancelable(false);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                            editor.putString(AppsContants.Phone, "");
                            editor.commit();

                            Intent intent = new Intent(AddBeneficiaryActivity.this, Money_TransActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                } else if (RSP_CODE.equals("9")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddBeneficiaryActivity.this);
                    builder.setMessage(Errormessage);
                    builder.setCancelable(false);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                            editor.putString(AppsContants.Phone, "");
                            editor.commit();

                            Intent intent = new Intent(AddBeneficiaryActivity.this, Money_TransActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                }

            } else {
                Toast.makeText(AddBeneficiaryActivity.this, "" + Errormessage, Toast.LENGTH_SHORT).show();
            }
        }

    }

//============================================end ======================================//
}