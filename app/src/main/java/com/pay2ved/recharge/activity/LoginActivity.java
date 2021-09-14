package com.pay2ved.recharge.activity;

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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pay2ved.recharge.R;
import com.pay2ved.recharge.other.AppsContants;
import com.pay2ved.recharge.other.HttpUrlPath;
import com.pay2ved.recharge.other.NetConnection;
import com.pay2ved.recharge.wactivity.ActivityHome;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static com.pay2ved.recharge.service.MyFirebaseMessagingService.getFcmToken;
import static com.pay2ved.recharge.service.MyFirebaseMessagingService.retrieveCurrentToken;

public class LoginActivity extends AppCompatActivity {

    Button btn_login;
    TextView txt_sms_mode, txt_forgot;
    EditText mobiletext, passwordtext;
    private ProgressDialog pDialog;
    String s_mobile = "", s_password = "", ss_mobile = "", ss_Password = "";
    RelativeLayout sms_mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login = (Button) findViewById(R.id.btn_login);
        txt_forgot = (TextView) findViewById(R.id.txt_forgot);
        sms_mode = (RelativeLayout) findViewById(R.id.sms_mode);
        txt_sms_mode = (TextView) findViewById(R.id.txt_sms_mode);
        mobiletext = (EditText) findViewById(R.id.mobiletext);
        passwordtext = (EditText) findViewById(R.id.passwordtext);

        txt_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgetActivity.class);
                startActivity(intent);
            }
        });
        sms_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SmsHomeActivity.class);
                startActivity(intent);
            }
        });
        txt_sms_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SmsHomeActivity.class);
                startActivity(intent);
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (false){
                    // TODO : Unblock this if statement...

                    s_mobile = mobiletext.getText().toString().trim();
                    s_password = passwordtext.getText().toString().trim();

                    boolean isError = false;
                    if (s_mobile.equals("")) {
                        isError = true;
                        mobiletext.requestFocus();
                        Toast.makeText(LoginActivity.this, "Please Enter Username or Mobile No", Toast.LENGTH_LONG).show();

                    } else if (s_password.equals("")) {
                        isError = true;
                        passwordtext.requestFocus();
                        Toast.makeText(LoginActivity.this, "Please Enter Pin", Toast.LENGTH_LONG).show();
                    }

                    if (!isError) {
                        if (NetConnection.isConnected(LoginActivity.this)) {
                            Loginjsontask task = new Loginjsontask();
                            task.execute();
                        } else {
                            Toast.makeText(LoginActivity.this, "Internet Connection error", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
                // TODO : Remove below code

//                Intent intent = new Intent(LoginActivity.this, GprsHomeActivity.class);
                Intent intent = new Intent(LoginActivity.this, ActivityHome.class);
                startActivity(intent);
                finishAffinity();

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        // Retrieve FCM Token...
        retrieveCurrentToken();
    }

    //========================================login==========================================/
    public class Loginjsontask extends AsyncTask<String, Void, String> {

        int responseCode = -1;
        String Errormessage;
        String UserRegisterID;
        boolean IsError = false;
        String msg = "", s_data = "";
        String p_mobile = "", p_uid = "", p_type = "", p_username = "", p_password = "", p_fullname = "", p_company_name = "", p_email = "", p_address = "", p_city = "", p_state = "", p_balance = "";
        String fcm_token;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

            fcm_token = getFcmToken();

        }

        protected String doInBackground(String... arg0) {

            HttpClient htppclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(HttpUrlPath.urlPath + HttpUrlPath.action_login);

            try {
                List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

                nameValuePair.add(new BasicNameValuePair("username", s_mobile));
                nameValuePair.add(new BasicNameValuePair("password", s_password));
                // Add FCM Token into Login API...
                nameValuePair.add(new BasicNameValuePair("device_token", fcm_token ));

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePair, "UTF-8"));
                HttpResponse response = htppclient.execute(httppost);
                String object = EntityUtils.toString(response.getEntity());
                System.out.println("#####object registration=" + object);
                JSONObject js = new JSONObject(object);

                Errormessage = js.getString("message");
                if (js.has("error"))
                    responseCode = Integer.parseInt(js.getString("error"));

                if (js.has("data")) {
                    s_data = js.getString("data");
                    JSONObject data = new JSONObject(s_data);

                    UserRegisterID = data.getString("id");
                    p_uid = data.getString("uid");
                    p_username = data.getString("username");
                    p_password = data.getString("password");
                    p_type = data.getString("type");
                    p_fullname = data.getString("fullname");
                    p_company_name = data.getString("company_name");
                    p_mobile = data.getString("mobile");
                    p_email = data.getString("email");
                    p_address = data.getString("address");
                    p_city = data.getString("city");
                    p_state = data.getString("state");
                    p_balance = data.getString("balance");

                }

            } catch (Exception e) {
                IsError = true;
                Log.e("Error", "22" + e.getMessage());
                e.printStackTrace();
            }
            return msg;
        }


        protected void onPostExecute(String result1) {
            super.onPostExecute(result1);
            if (pDialog != null)
                pDialog.dismiss();

            if (!IsError) {

                if ( responseCode == 0 ) {

                    if (UserRegisterID != null) {
                        if (!UserRegisterID.equals("")) {

                            // Toast.makeText(LoginActivity.this, "" + Errormessage, Toast.LENGTH_SHORT).show();

                            AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                            editor.putString(AppsContants.Mobile, p_mobile);
                            editor.putString(AppsContants.Username, p_username);
                            editor.putString(AppsContants.Password, p_password);
                            editor.putString(AppsContants.Uid, p_uid);
                            editor.putString(AppsContants.Type, p_type);
                            editor.putString(AppsContants.Fullname, p_fullname);
                            editor.putString(AppsContants.CompanyName, p_company_name);
                            editor.putString(AppsContants.Email, p_email);
                            editor.putString(AppsContants.Address, p_address);
                            editor.putString(AppsContants.City, p_city);
                            editor.putString(AppsContants.State, p_state);
                            editor.putString(AppsContants.Balance, p_balance);
                            editor.commit();


                            Intent intent = new Intent(LoginActivity.this, GprsHomeActivity.class);
                            startActivity(intent);
                            finishAffinity();

                        }
                    }
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
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
//                Toast.makeText(LoginActivity.this, Errormessage, Toast.LENGTH_SHORT).show();
                Log.e("ERR_MSG", "ERR : " + Errormessage);
            }
        }

    }

    //============================================end login ======================================//

    @Override
    protected void onResume() {

        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        ss_mobile = AppsContants.sharedpreferences.getString(AppsContants.Mobile, "");
        ss_Password = AppsContants.sharedpreferences.getString(AppsContants.Password, "");

        mobiletext.setText(ss_mobile);
        passwordtext.setText(ss_Password);

        super.onResume();
    }

}
