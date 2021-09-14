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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pay2ved.recharge.R;
import com.pay2ved.recharge.gprs_home_activity.BeneficiaryActivity;
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

public class Delete_BeneficiaryActivity extends AppCompatActivity {
    private ProgressDialog pDialog;
    EditText edt_name, edt_code, edt_account, edt_ifsc, edt_type;
    Button btn_delete, btn_cancel;
    ImageView img_back;
    String s_code = "", s_name = "", s_account = "", s_ifsc = "", s_type = "";
    String s_Username = "", s_Password = "", s_mobile = "", s_Name = "", s_ID = "", s_Account = "", s_Type = "", s_Ifsc = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_dmt);

        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        s_Username = AppsContants.sharedpreferences.getString(AppsContants.Username, "");
        s_Password = AppsContants.sharedpreferences.getString(AppsContants.Password, "");
        s_mobile = AppsContants.sharedpreferences.getString(AppsContants.Phone, "");
        s_ID = AppsContants.sharedpreferences.getString(AppsContants.ID, "");
        s_Name = AppsContants.sharedpreferences.getString(AppsContants.Name, "");
        s_Account = AppsContants.sharedpreferences.getString(AppsContants.Account, "");
        s_Type = AppsContants.sharedpreferences.getString(AppsContants.Type, "");
        s_Ifsc = AppsContants.sharedpreferences.getString(AppsContants.Ifsc, "");


        edt_code = (EditText) findViewById(R.id.edt_code);
        edt_name = (EditText) findViewById(R.id.edt_name);
        edt_account = (EditText) findViewById(R.id.edt_account);
        edt_ifsc = (EditText) findViewById(R.id.edt_ifsc);
        edt_type = (EditText) findViewById(R.id.edt_type);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);

        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Delete_BeneficiaryActivity.this, BeneficiaryActivity.class);
                startActivity(intent);
                finish();
            }
        });

        edt_code.setText(s_ID);
        edt_name.setText(s_Name);
        edt_account.setText(s_Account);
        edt_ifsc.setText(s_Ifsc);
        edt_type.setText(s_Type);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Delete_BeneficiaryActivity.this, BeneficiaryActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                s_code = edt_code.getText().toString().trim();
                s_name = edt_name.getText().toString().trim();
                s_account = edt_account.getText().toString().trim();
                s_ifsc = edt_ifsc.getText().toString().trim();
                s_type = edt_type.getText().toString().trim();

                boolean isError = false;
                if (s_mobile.equals("")) {
                    isError = true;
                    Toast.makeText(Delete_BeneficiaryActivity.this, "Mobile Number Can't Blank", Toast.LENGTH_LONG).show();
                } else if (s_ID.equals("")) {
                    isError = true;
                    Toast.makeText(Delete_BeneficiaryActivity.this, "Beneficiary Code Can't Blank", Toast.LENGTH_LONG).show();
                } else {
                    if (!isError) {
                        if (NetConnection.isConnected(Delete_BeneficiaryActivity.this)) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(Delete_BeneficiaryActivity.this);
                            builder.setTitle("Are you sure?");
                            builder.setMessage("Do you want to delete beneficiary account!");
                            builder.setCancelable(false);
                            builder.setPositiveButton("Yes, Confirm!", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    BenDelete task = new BenDelete();
                                    task.execute();
                                }
                            });
                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Delete_BeneficiaryActivity.this, BeneficiaryActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                            AlertDialog alert = builder.create();
                            alert.show();

                        } else {
                            Toast.makeText(Delete_BeneficiaryActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            }
        });
    }


    //========================================start_http==========================================/
    public class BenDelete extends AsyncTask<String, Void, String> {

        String Errormessage, object_res;
        boolean IsError = false;
        String msg = "";
        String RSP_CODE = "", SEND_MOB = "", BEN_NAME = "";
        JSONObject jsonObject;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Delete_BeneficiaryActivity.this);
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
                nameValuePair.add(new BasicNameValuePair("request", "beneficiaryDelete"));
                nameValuePair.add(new BasicNameValuePair("mobile", s_mobile));
                nameValuePair.add(new BasicNameValuePair("ben_code", s_ID));

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePair, "UTF-8"));
                HttpResponse response = htppclient.execute(httppost);
                object_res = EntityUtils.toString(response.getEntity());
                System.out.println("#####object registration=" + object_res);
                jsonObject = new JSONObject(object_res);
                if (jsonObject.length() == 2) {
                    RSP_CODE = jsonObject.getString("RSP_CODE");
                    Errormessage = jsonObject.getString("RSP_MSG");
                } else {
                    RSP_CODE = jsonObject.getString("RSP_CODE");
                    Errormessage = jsonObject.getString("RSP_MSG");
                    SEND_MOB = jsonObject.getString("SEND_MOB");
                    BEN_NAME = jsonObject.getString("BEN_NAME");
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(Delete_BeneficiaryActivity.this);
                    builder.setMessage(Errormessage);
                    builder.setCancelable(false);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Delete_BeneficiaryActivity.this, BeneficiaryActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                } else if (RSP_CODE.equals("1")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Delete_BeneficiaryActivity.this);
                    builder.setMessage(Errormessage);
                    builder.setCancelable(false);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Delete_BeneficiaryActivity.this, BeneficiaryActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                } else if (RSP_CODE.equals("2")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Delete_BeneficiaryActivity.this);
                    builder.setMessage(Errormessage);
                    builder.setCancelable(false);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Delete_BeneficiaryActivity.this, BeneficiaryActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                } else if (RSP_CODE.equals("9")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Delete_BeneficiaryActivity.this);
                    builder.setMessage(Errormessage);
                    builder.setCancelable(false);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Delete_BeneficiaryActivity.this, BeneficiaryActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                }

            } else {
                Toast.makeText(Delete_BeneficiaryActivity.this, "" + Errormessage, Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Delete_BeneficiaryActivity.this, BeneficiaryActivity.class);
        startActivity(intent);
        finish();
    }
//============================================end ======================================//
}