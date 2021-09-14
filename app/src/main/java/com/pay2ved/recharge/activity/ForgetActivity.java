package com.pay2ved.recharge.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pay2ved.recharge.R;
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

public class ForgetActivity extends AppCompatActivity {

    Button btn_forget;
    TextView txt_login;
    EditText mobiletext;
    private ProgressDialog pDialog;
    String s_mobile = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        btn_forget = (Button) findViewById(R.id.btn_forget);
        txt_login = (TextView) findViewById(R.id.txt_login);
        mobiletext = (EditText) findViewById(R.id.mobiletext);

        txt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgetActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                s_mobile = mobiletext.getText().toString().trim();

                boolean isError = false;
                if (s_mobile.equals("")) {
                    isError = true;
                    mobiletext.requestFocus();
                    Toast.makeText(ForgetActivity.this, "Please Enter Username or Mobile No", Toast.LENGTH_LONG).show();

                }

                if (!isError) {
                    if (NetConnection.isConnected(ForgetActivity.this)) {
                        Forgetjsontask task = new Forgetjsontask();
                        task.execute();
                    } else {
                        Toast.makeText(ForgetActivity.this, "Internet Connection error", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }

    //========================================start_http==========================================/
    public class Forgetjsontask extends AsyncTask<String, Void, String> {

        String Errormessage;
        String UserRegisterID;
        boolean IsError = false;
        String msg = "", s_data = "";
        String p_uid = "", p_username = "", p_type = "", p_fullname = "", p_company_name = "", p_mobile = "", p_email = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ForgetActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... arg0) {

            HttpClient htppclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(HttpUrlPath.urlPath + HttpUrlPath.action_forget);

            try {
                List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

                nameValuePair.add(new BasicNameValuePair("username", s_mobile));

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
                    p_uid = data.getString("uid");
                    p_username = data.getString("username");
                    p_type = data.getString("type");
                    p_fullname = data.getString("fullname");
                    p_company_name = data.getString("company_name");
                    p_mobile = data.getString("mobile");
                    p_email = data.getString("email");

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

                            Toast.makeText(ForgetActivity.this, "" + Errormessage, Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(ForgetActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finishAffinity();

                        }
                    }
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ForgetActivity.this);
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
                Toast.makeText(ForgetActivity.this, Errormessage, Toast.LENGTH_SHORT).show();
            }
        }

    }

    //============================================end ======================================//

}
