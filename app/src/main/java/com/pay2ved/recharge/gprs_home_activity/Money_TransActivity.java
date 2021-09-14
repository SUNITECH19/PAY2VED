package com.pay2ved.recharge.gprs_home_activity;

import android.annotation.SuppressLint;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pay2ved.recharge.R;
import com.pay2ved.recharge.activity.ContactActivity;
import com.pay2ved.recharge.activity.DMT_DetailsActivity;
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

public class Money_TransActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    EditText mobile;
    TextView txt_list;
    Button btn_validate;
    ImageView img_back, img_contact;
    String s_mobile = "", s_phone = "";
    String s_Username = "", s_Password = "", s_Title = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money);

        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        s_Username = AppsContants.sharedpreferences.getString(AppsContants.Username, "");
        s_Password = AppsContants.sharedpreferences.getString(AppsContants.Password, "");
        s_Title = AppsContants.sharedpreferences.getString(AppsContants.Title, "");

        txt_list = (TextView) findViewById(R.id.txt_list);
        txt_list.setText(s_Title);

        mobile = (EditText) findViewById(R.id.mobile);
        btn_validate = (Button) findViewById(R.id.btn_validate);

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
                Intent intent = new Intent(Money_TransActivity.this, ContactActivity.class);
                startActivity(intent);
            }
        });


        btn_validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                s_mobile = mobile.getText().toString().trim();
                boolean isError = false;
                if (s_mobile.equals("")) {
                    isError = true;
                    mobile.requestFocus();
                    Toast.makeText(Money_TransActivity.this, "Please Enter Mobile Number", Toast.LENGTH_LONG).show();

                } else if (s_mobile.length() < 10) {
                    isError = true;
                    mobile.requestFocus();
                    Toast.makeText(Money_TransActivity.this, "Invalid Number", Toast.LENGTH_LONG).show();

                }else if (s_mobile.length() > 10) {
                    isError = true;
                    mobile.requestFocus();
                    Toast.makeText(Money_TransActivity.this, "Invalid Number", Toast.LENGTH_LONG).show();

                } else {
                    if (!isError) {
                        if (NetConnection.isConnected(Money_TransActivity.this)) {
                            SenderValidate task = new SenderValidate();
                            task.execute();
                        } else {
                            Toast.makeText(Money_TransActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
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
        String RSP_CODE = "", SEND_MOB = "", SENDER_NAME = "", SENDER_MOBILE = "";
        JSONObject jsonObject;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Money_TransActivity.this);
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
                nameValuePair.add(new BasicNameValuePair("request", "senderValidate"));
                nameValuePair.add(new BasicNameValuePair("mobile", s_mobile));

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
                    SENDER_MOBILE = jsonObject.getString("SENDER_MOBILE");

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
                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.Phone, SEND_MOB);
                editor.putString(AppsContants.Name, SENDER_NAME);
                editor.commit();

                if (RSP_CODE.equals("0")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Money_TransActivity.this);
                    builder.setMessage(Errormessage);
                    builder.setCancelable(false);
                    builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @SuppressLint("NewApi")
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Money_TransActivity.this, DMT_DetailsActivity.class);
                            startActivity(intent);
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();

                } else if (RSP_CODE.equals("1")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Money_TransActivity.this);
                    builder.setMessage(Errormessage);
                    builder.setCancelable(false);
                    builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @SuppressLint("NewApi")
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                } else if (RSP_CODE.equals("2")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Money_TransActivity.this);
                    builder.setMessage(Errormessage);
                    builder.setCancelable(false);
                    builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @SuppressLint("NewApi")
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Money_TransActivity.this, AddSenderActivity.class);
                            startActivity(intent);
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                } else if (RSP_CODE.equals("9")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Money_TransActivity.this);
                    builder.setMessage(Errormessage);
                    builder.setCancelable(false);
                    builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @SuppressLint("NewApi")
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                }
            } else {
                Toast.makeText(Money_TransActivity.this, "" + Errormessage, Toast.LENGTH_SHORT).show();
            }
        }

    }

//============================================end ======================================//

    @Override
    protected void onResume() {
        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        s_phone = AppsContants.sharedpreferences.getString(AppsContants.Phone, "");

        mobile.setText(s_phone);
        super.onResume();
    }
}