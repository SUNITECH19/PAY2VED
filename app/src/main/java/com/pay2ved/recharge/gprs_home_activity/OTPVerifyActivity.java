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

public class OTPVerifyActivity extends AppCompatActivity {
    private ProgressDialog pDialog;
    EditText edt_ref_code, edt_mobile, edt_otp;
    Button btn_confirm, btn_resend;
    ImageView img_back;
    String s_mobile = "", s_ref_code = "", s_otp = "";
    String s_Username = "", s_Password = "", S_phone = "", S_REF_CODE = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_verify);

        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        s_Username = AppsContants.sharedpreferences.getString(AppsContants.Username, "");
        s_Password = AppsContants.sharedpreferences.getString(AppsContants.Password, "");
        S_phone = AppsContants.sharedpreferences.getString(AppsContants.Phone, "");
        S_REF_CODE = AppsContants.sharedpreferences.getString(AppsContants.VERIFICATION_REF_CODE, "");

        edt_mobile = (EditText) findViewById(R.id.edt_mobile);
        edt_ref_code = (EditText) findViewById(R.id.edt_ref_code);
        edt_otp = (EditText) findViewById(R.id.edt_otp);
        btn_confirm = (Button) findViewById(R.id.btn_confirm);
        btn_resend = (Button) findViewById(R.id.btn_resend);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.Phone, "");
                editor.commit();
                finish();
            }
        });
        edt_mobile.setText(S_phone);
        edt_ref_code.setText(S_REF_CODE);

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                s_ref_code = edt_ref_code.getText().toString().trim();
                s_mobile = edt_mobile.getText().toString().trim();
                s_otp = edt_otp.getText().toString().trim();

                boolean isError = false;
                if (s_mobile.equals("")) {
                    isError = true;
                    edt_mobile.requestFocus();
                    Toast.makeText(OTPVerifyActivity.this, "Please Enter Mobile Number", Toast.LENGTH_LONG).show();

                } else if (s_ref_code.equals("")) {
                    isError = true;
                    edt_ref_code.requestFocus();
                    Toast.makeText(OTPVerifyActivity.this, "Please Enter Ref Code", Toast.LENGTH_LONG).show();

                } else if (s_otp.equals("")) {
                    isError = true;
                    edt_otp.requestFocus();
                    Toast.makeText(OTPVerifyActivity.this, "Please Enter OTP", Toast.LENGTH_LONG).show();

                } else {
                    if (!isError) {
                        if (NetConnection.isConnected(OTPVerifyActivity.this)) {
                            OTPConfirm task = new OTPConfirm();
                            task.execute();
                        } else {
                            Toast.makeText(OTPVerifyActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            }
        });

        btn_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                s_ref_code = edt_ref_code.getText().toString().trim();
                s_mobile = edt_mobile.getText().toString().trim();

                boolean isError = false;
                if (s_mobile.equals("")) {
                    isError = true;
                    edt_mobile.requestFocus();
                    Toast.makeText(OTPVerifyActivity.this, "Please Enter Mobile Number", Toast.LENGTH_LONG).show();

                } else if (s_ref_code.equals("")) {
                    isError = true;
                    edt_ref_code.requestFocus();
                    Toast.makeText(OTPVerifyActivity.this, "Please Enter Ref Code", Toast.LENGTH_LONG).show();

                } else {
                    if (!isError) {
                        if (NetConnection.isConnected(OTPVerifyActivity.this)) {
                            ReSendOTP reSend = new ReSendOTP();
                            reSend.execute();
                        } else {
                            Toast.makeText(OTPVerifyActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            }
        });
    }


    //========================================start_http==========================================/
    public class OTPConfirm extends AsyncTask<String, Void, String> {

        String Errormessage, object_res;
        boolean IsError = false;
        String msg = "", s_data = "";
        String RSP_CODE = "", SEND_MOB = "";
        JSONObject jsonObject;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(OTPVerifyActivity.this);
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
                nameValuePair.add(new BasicNameValuePair("request", "otpVerify"));
                nameValuePair.add(new BasicNameValuePair("mobile", s_mobile));
                nameValuePair.add(new BasicNameValuePair("otp_ref_code", s_ref_code));
                nameValuePair.add(new BasicNameValuePair("otp", s_otp));

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePair, "UTF-8"));
                HttpResponse response = htppclient.execute(httppost);
                object_res = EntityUtils.toString(response.getEntity());
                System.out.println("#####object registration=" + object_res);
                jsonObject = new JSONObject(object_res);

                RSP_CODE = jsonObject.getString("RSP_CODE");
                Errormessage = jsonObject.getString("RSP_MSG");
                SEND_MOB = jsonObject.getString("SEND_MOB");

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
                    AlertDialog.Builder builder = new AlertDialog.Builder(OTPVerifyActivity.this);
                    builder.setMessage(Errormessage);
                    builder.setCancelable(false);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(OTPVerifyActivity.this, DMT_DetailsActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                } else if (RSP_CODE.equals("1")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(OTPVerifyActivity.this);
                    builder.setMessage(Errormessage);
                    builder.setCancelable(false);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            edt_otp.setText("");
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                } else if (RSP_CODE.equals("2")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(OTPVerifyActivity.this);
                    builder.setMessage(Errormessage);
                    builder.setCancelable(false);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            edt_otp.setText("");
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                } else if (RSP_CODE.equals("9")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(OTPVerifyActivity.this);
                    builder.setMessage(Errormessage);
                    builder.setCancelable(false);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                            editor.putString(AppsContants.Phone, "");
                            editor.commit();

                            Intent intent = new Intent(OTPVerifyActivity.this, Money_TransActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                }

            } else {
                Toast.makeText(OTPVerifyActivity.this, "" + Errormessage, Toast.LENGTH_SHORT).show();
            }
        }

    }

//============================================end ======================================//

    public class ReSendOTP extends AsyncTask<String, Void, String> {

        String Errormessage, object_res;
        boolean IsError = false;
        String msg = "", s_data = "";
        String RSP_CODE = "", SEND_MOB = "", REF_TXN_NO = "", VERIFICATION_REF_NO = "";
        JSONObject jsonObject;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(OTPVerifyActivity.this);
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
                nameValuePair.add(new BasicNameValuePair("request", "otpResend"));
                nameValuePair.add(new BasicNameValuePair("mobile", s_mobile));
                nameValuePair.add(new BasicNameValuePair("otp_ref_code", s_ref_code));

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePair, "UTF-8"));
                HttpResponse response = htppclient.execute(httppost);
                object_res = EntityUtils.toString(response.getEntity());
                System.out.println("#####object registration=" + object_res);
                jsonObject = new JSONObject(object_res);

                RSP_CODE = jsonObject.getString("RSP_CODE");
                Errormessage = jsonObject.getString("RSP_MSG");
                SEND_MOB = jsonObject.getString("SEND_MOB");
                REF_TXN_NO = jsonObject.getString("REF_TXN_NO");
                VERIFICATION_REF_NO = jsonObject.getString("VERIFICATION_REF_NO");

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
                    AlertDialog.Builder builder = new AlertDialog.Builder(OTPVerifyActivity.this);
                    builder.setMessage(Errormessage);
                    builder.setCancelable(false);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                } else if (RSP_CODE.equals("1")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(OTPVerifyActivity.this);
                    builder.setMessage(Errormessage);
                    builder.setCancelable(false);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                            editor.putString(AppsContants.Phone, "");
                            editor.commit();

                            Intent intent = new Intent(OTPVerifyActivity.this, Money_TransActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                } else if (RSP_CODE.equals("2")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(OTPVerifyActivity.this);
                    builder.setMessage(Errormessage);
                    builder.setCancelable(false);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                            editor.putString(AppsContants.Phone, "");
                            editor.commit();

                            Intent intent = new Intent(OTPVerifyActivity.this, Money_TransActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                } else if (RSP_CODE.equals("9")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(OTPVerifyActivity.this);
                    builder.setMessage(Errormessage);
                    builder.setCancelable(false);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                            editor.putString(AppsContants.Phone, "");
                            editor.commit();

                            Intent intent = new Intent(OTPVerifyActivity.this, Money_TransActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                }

            } else {
                Toast.makeText(OTPVerifyActivity.this, "" + Errormessage, Toast.LENGTH_SHORT).show();
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