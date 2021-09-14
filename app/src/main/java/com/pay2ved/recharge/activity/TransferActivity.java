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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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

public class TransferActivity extends AppCompatActivity {
    private ProgressDialog pDialog;
    EditText edt_name, edt_id, edt_account, edt_ifsc, edt_type, edt_aadhaar, edt_pan, edt_amount;
    Button btn_confirm, btn_cancel;
    ImageView img_back;
    String s_ben_id = "", s_name = "", s_account = "", s_ifsc = "", s_type = "", s_tran_type = "", s_aadhaar = "", s_pan = "", s_amount = "";
    String s_Username = "", s_Password = "", s_mobile = "", s_Name = "", s_ID = "", s_Account = "",
            s_Type = "", s_Ifsc = "", s_Money_Tran = "";
    RadioGroup group_type;
    RadioButton type;
    TextView dmt_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transfer_dmt);

        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        s_Username = AppsContants.sharedpreferences.getString(AppsContants.Username, "");
        s_Password = AppsContants.sharedpreferences.getString(AppsContants.Password, "");
        s_mobile = AppsContants.sharedpreferences.getString(AppsContants.Phone, "");
        s_ID = AppsContants.sharedpreferences.getString(AppsContants.ID, "");
        s_Name = AppsContants.sharedpreferences.getString(AppsContants.Name, "");
        s_Account = AppsContants.sharedpreferences.getString(AppsContants.Account, "");
        s_Type = AppsContants.sharedpreferences.getString(AppsContants.Type, "");
        s_Ifsc = AppsContants.sharedpreferences.getString(AppsContants.Ifsc, "");
        s_Money_Tran = AppsContants.sharedpreferences.getString(AppsContants.Money_Tran, "");


        dmt_text = (TextView) findViewById(R.id.dmt_text);
        edt_id = (EditText) findViewById(R.id.edt_id);
        edt_name = (EditText) findViewById(R.id.edt_name);
        edt_account = (EditText) findViewById(R.id.edt_account);
        edt_ifsc = (EditText) findViewById(R.id.edt_ifsc);
        edt_type = (EditText) findViewById(R.id.edt_type);
        edt_aadhaar = (EditText) findViewById(R.id.edt_aadhaar);
        edt_pan = (EditText) findViewById(R.id.edt_pan);
        edt_amount = (EditText) findViewById(R.id.edt_amount);
        btn_confirm = (Button) findViewById(R.id.btn_confirm);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        group_type = (RadioGroup) findViewById(R.id.group_type);

        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransferActivity.this, BeneficiaryActivity.class);
                startActivity(intent);
                finish();
            }
        });

        edt_id.setText(s_ID);
        edt_name.setText(s_Name);
        edt_account.setText(s_Account);
        edt_ifsc.setText(s_Ifsc);
        edt_type.setText(s_Type);
        dmt_text.setText(s_Money_Tran);


        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransferActivity.this, BeneficiaryActivity.class);
                startActivity(intent);
                finish();
            }
        });

        group_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                int selectedId = group_type.getCheckedRadioButtonId();
                type = (RadioButton) findViewById(selectedId);
                if (selectedId == -1) {
                    Toast.makeText(TransferActivity.this, "Nothing selected", Toast.LENGTH_SHORT).show();
                } else {
                    s_tran_type = type.getText().toString().trim();
                }

            }
        });


        int selectedId = group_type.getCheckedRadioButtonId();
        type = (RadioButton) findViewById(selectedId);
        if (selectedId == -1) {
            Toast.makeText(TransferActivity.this, "Nothing selected", Toast.LENGTH_SHORT).show();
        } else {
            s_tran_type = type.getText().toString().trim();
        }

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                s_name = edt_name.getText().toString().trim();
                s_ben_id = edt_id.getText().toString().trim();
                s_account = edt_account.getText().toString().trim();
                s_ifsc = edt_ifsc.getText().toString().trim();
                s_type = edt_type.getText().toString().trim();
                s_aadhaar = edt_aadhaar.getText().toString().trim();
                s_pan = edt_pan.getText().toString().trim();
                s_amount = edt_amount.getText().toString().trim();

                boolean isError = false;
                if (s_ben_id.equals("")) {
                    isError = true;
                    edt_id.requestFocus();
                    Toast.makeText(TransferActivity.this, "Please Enter ID", Toast.LENGTH_LONG).show();

                } else if (s_name.equals("")) {
                    isError = true;
                    edt_name.requestFocus();
                    Toast.makeText(TransferActivity.this, "Please Enter Name", Toast.LENGTH_LONG).show();

                } else if (s_account.equals("")) {
                    isError = true;
                    edt_account.requestFocus();
                    Toast.makeText(TransferActivity.this, "Please Enter Account Number", Toast.LENGTH_LONG).show();

                } else if (s_type.equals("")) {
                    isError = true;
                    edt_type.requestFocus();
                    Toast.makeText(TransferActivity.this, "Please Enter Account Type", Toast.LENGTH_LONG).show();

                } else if (s_ifsc.equals("")) {
                    isError = true;
                    edt_ifsc.requestFocus();
                    Toast.makeText(TransferActivity.this, "Please Enter IFSC Code", Toast.LENGTH_LONG).show();

                } else if (s_tran_type.equals("")) {
                    isError = true;
                    Toast.makeText(TransferActivity.this, "Please Select Transaction Type", Toast.LENGTH_LONG).show();

                } else if (s_amount.equals("")) {
                    isError = true;
                    edt_amount.requestFocus();
                    Toast.makeText(TransferActivity.this, "Please Enter Amount", Toast.LENGTH_LONG).show();

                } else {
                    if (!isError) {
                        if (NetConnection.isConnected(TransferActivity.this)) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(TransferActivity.this);
                            builder.setTitle("Are you sure?");
                            builder.setMessage("Do you want to money transfer!");
                            builder.setCancelable(false);
                            builder.setPositiveButton("Yes, Confirm!", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    BenTransfer task = new BenTransfer();
                                    task.execute();
                                }
                            });
                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(TransferActivity.this, BeneficiaryActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                            AlertDialog alert = builder.create();
                            alert.show();

                        } else {
                            Toast.makeText(TransferActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            }
        });
    }

    //========================================start_http==========================================/
    public class BenTransfer extends AsyncTask<String, Void, String> {

        String Errormessage, object_res;
        boolean IsError = false;
        String msg = "";
        String RSP_CODE = "", SEND_MOB = "", BEN_NAME = "", BEN_CODE = "", BEN_BANK_NAME = "", IMPS_REF_NO = "", TRANS_ID = "", API_TRANS_ID = "", API_STATUS = "";
        JSONObject jsonObject;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(TransferActivity.this);
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
                nameValuePair.add(new BasicNameValuePair("request", "moneyRemittance"));
                nameValuePair.add(new BasicNameValuePair("mobile", s_mobile));
                nameValuePair.add(new BasicNameValuePair("ben_code", s_ID));
                nameValuePair.add(new BasicNameValuePair("ben_name", s_name));
                nameValuePair.add(new BasicNameValuePair("ben_bank_account", s_account));
                nameValuePair.add(new BasicNameValuePair("ben_account_type", s_type));
                nameValuePair.add(new BasicNameValuePair("ben_ifsc", s_ifsc));
                nameValuePair.add(new BasicNameValuePair("trans_type", s_tran_type));
                nameValuePair.add(new BasicNameValuePair("ben_pan", s_pan));
                nameValuePair.add(new BasicNameValuePair("ben_aadhaar", s_aadhaar));
                nameValuePair.add(new BasicNameValuePair("amount", s_amount));

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
                    BEN_CODE = jsonObject.getString("BEN_CODE");
                    BEN_BANK_NAME = jsonObject.getString("BEN_BANK_NAME");
                    IMPS_REF_NO = jsonObject.getString("IMPS_REF_NO");
                    TRANS_ID = jsonObject.getString("TRANS_ID");
                    API_TRANS_ID = jsonObject.getString("API_TRANS_ID");
                    API_STATUS = jsonObject.getString("API_STATUS");
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(TransferActivity.this);
                    builder.setMessage(Errormessage);
                    builder.setCancelable(false);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(TransferActivity.this, BeneficiaryActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                } else if (RSP_CODE.equals("1")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(TransferActivity.this);
                    builder.setMessage(Errormessage);
                    builder.setCancelable(false);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(TransferActivity.this, BeneficiaryActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                } else if (RSP_CODE.equals("2")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(TransferActivity.this);
                    builder.setMessage(Errormessage);
                    builder.setCancelable(false);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(TransferActivity.this, BeneficiaryActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                } else if (RSP_CODE.equals("9")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(TransferActivity.this);
                    builder.setMessage(Errormessage);
                    builder.setCancelable(false);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(TransferActivity.this, BeneficiaryActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                }

            } else {
                Toast.makeText(TransferActivity.this, "" + Errormessage, Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(TransferActivity.this, BeneficiaryActivity.class);
        startActivity(intent);
        finish();
    }

//============================================end ======================================//
}