package com.pay2ved.recharge.gprs_home_activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsManager;
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

public class SendCodeActivity extends Activity {

    TextView txt_sms_mode;
    ImageView img_back;
    Button cancel, ok;
    String s_code = "", s_mobile = "", s_gateway_no = "", s_sms = "";
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_code);

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

        txt_sms_mode.setText(s_code + s_mobile);
        s_sms = s_code + s_mobile;

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // SmsManager.getDefault().sendTextMessage(s_mobile, null, s_sms, null, null);

                boolean isError = false;

                if (!isError) {
                    if (NetConnection.isConnected(SendCodeActivity.this)) {
                        Httpjsontask task = new Httpjsontask();
                        task.execute();
                    } else {
                        Toast.makeText(SendCodeActivity.this, "Internet Connection error", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });
    }

    //========================================start_http==========================================/
    public class Httpjsontask extends AsyncTask<String, Void, String> {

        String Errormessage;
        String UserRegisterID;
        boolean IsError = false;
        String msg = "", s_data = "";
        String p_code = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SendCodeActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... arg0) {

            HttpClient htppclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(HttpUrlPath.urlPath + HttpUrlPath.action_forget);

            try {
                List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

                nameValuePair.add(new BasicNameValuePair("code", s_sms));

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePair, "UTF-8"));
                HttpResponse response = htppclient.execute(httppost);
                String object = EntityUtils.toString(response.getEntity());
                System.out.println("#####object registration=" + object);
                JSONObject js = new JSONObject(object);

                Errormessage = js.getString("message");
                if (js.has("data")) {
                    s_data = js.getString("data");
                    JSONObject data = new JSONObject(s_data);

                    UserRegisterID = data.getString("id");
                    p_code = data.getString("code");
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

                if (Errormessage.equals("Password reset successfully and sent to your registered mobile number")) {

                    if (UserRegisterID != null) {
                        if (!UserRegisterID.equals("")) {

                            Toast.makeText(SendCodeActivity.this, "" + Errormessage, Toast.LENGTH_SHORT).show();

                            AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                            editor.putString(AppsContants.code, p_code);
                            editor.commit();


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

                            finishAffinity();


                        }
                    }
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SendCodeActivity.this);
                    builder.setMessage("" + Errormessage);
                    builder.setCancelable(false);
                    builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                }
            } else {
                Toast.makeText(SendCodeActivity.this, "please try again...", Toast.LENGTH_SHORT).show();
            }
        }

    }

    //============================================end ======================================//


}