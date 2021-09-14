package com.pay2ved.recharge.activity;

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

public class ViewBillActivity extends Activity {

    String s_Username = "", s_Password = "", s_Spinner_list = "", s_Operator_code = "", s_Mobile = "", s_Service_No = "", s_TID_Number = "", s_Landline_Number = "", s_Service_Type = "", s_Bill_cycle = "", s_Bill_Unit = "", s_Mob_number = "", s_Sub_division = "";
    ImageView img_back;
    EditText edt_customer, edt_number, edt_bill_number, edt_bill_date, edt_due_date, edt_bill_amount;
    private ProgressDialog pDialog;
    Button btn_ok, btn_cancel;
    String s_customer = "", s_number = "", s_bill_number = "", s_bill_date = "", s_due_date = "", s_bill_amount = "";
    String ss_amount = "", ss_Partial_Pay = "", ss_Customer_Name = "", ss_Customer_Number = "", ss_Valid_Bill = "", ss_Bill_Number = "", ss_Bill_Date = "", ss_Bill_DueDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bill);

        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        s_Spinner_list = AppsContants.sharedpreferences.getString(AppsContants.Spinner_list, "");
        s_Username = AppsContants.sharedpreferences.getString(AppsContants.Username, "");
        s_Password = AppsContants.sharedpreferences.getString(AppsContants.Password, "");
        s_Operator_code = AppsContants.sharedpreferences.getString(AppsContants.Operator_code, "");
        s_Bill_cycle = AppsContants.sharedpreferences.getString(AppsContants.Bill_cycle, "");
        s_Bill_Unit = AppsContants.sharedpreferences.getString(AppsContants.Bill_Unit, "");
        s_Mobile = AppsContants.sharedpreferences.getString(AppsContants.Mobile, "");
        s_Sub_division = AppsContants.sharedpreferences.getString(AppsContants.Sub_division, "");
        s_TID_Number = AppsContants.sharedpreferences.getString(AppsContants.TID_Number, "");
        s_Landline_Number = AppsContants.sharedpreferences.getString(AppsContants.Landline_Number, "");
        s_Service_Type = AppsContants.sharedpreferences.getString(AppsContants.Service_Type, "");
        s_Service_No = AppsContants.sharedpreferences.getString(AppsContants.Service_No, "");

        ss_amount = AppsContants.sharedpreferences.getString(AppsContants.amount, "");
        ss_Partial_Pay = AppsContants.sharedpreferences.getString(AppsContants.Partial_Pay, "");
        ss_Customer_Name = AppsContants.sharedpreferences.getString(AppsContants.Customer_Name, "");
        ss_Customer_Number = AppsContants.sharedpreferences.getString(AppsContants.Customer_Number, "");
        ss_Valid_Bill = AppsContants.sharedpreferences.getString(AppsContants.Valid_Bill, "");
        ss_Bill_Number = AppsContants.sharedpreferences.getString(AppsContants.Bill_Number, "");
        ss_Bill_Date = AppsContants.sharedpreferences.getString(AppsContants.Bill_Date, "");
        ss_Bill_DueDate = AppsContants.sharedpreferences.getString(AppsContants.Bill_DueDate, "");

        edt_customer = (EditText) findViewById(R.id.edt_customer);
        edt_number = (EditText) findViewById(R.id.edt_number);
        edt_bill_number = (EditText) findViewById(R.id.edt_bill_number);
        edt_bill_date = (EditText) findViewById(R.id.edt_bill_date);
        edt_due_date = (EditText) findViewById(R.id.edt_due_date);
        edt_bill_amount = (EditText) findViewById(R.id.edt_bill_amount);

        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_ok = (Button) findViewById(R.id.btn_ok);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        edt_customer.setText(ss_Customer_Name);
        edt_number.setText(ss_Customer_Number);
        edt_due_date.setText(ss_Bill_DueDate);
        edt_bill_date.setText(ss_Bill_Date);
        edt_bill_amount.setText(ss_amount);
        edt_bill_number.setText(ss_Bill_Number);

        if (ss_Partial_Pay.equals("Y")) {
            edt_bill_amount.setEnabled(true);
        }


        if (ss_Valid_Bill.equals("Y")) {
            btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    s_customer = edt_customer.getText().toString().trim();
                    s_number = edt_number.getText().toString().trim();
                    s_bill_amount = edt_bill_amount.getText().toString().trim();
                    s_bill_date = edt_bill_date.getText().toString().trim();
                    s_bill_number = edt_bill_number.getText().toString().trim();
                    s_due_date = edt_due_date.getText().toString().trim();

                    boolean isError = false;
                    if (s_Operator_code.equals("null")) {
                        isError = true;
                        Toast.makeText(ViewBillActivity.this, "Provider Can't Blank", Toast.LENGTH_LONG).show();
                    } else if (s_number.equals("")) {
                        isError = true;
                        Toast.makeText(ViewBillActivity.this, "Account Number Can't Blank", Toast.LENGTH_LONG).show();
                    } else if (s_customer.equals("")) {
                        Toast.makeText(ViewBillActivity.this, "Customer Name Can't Blank", Toast.LENGTH_LONG).show();
                    } else if (s_bill_amount.equals("")) {
                        isError = true;
                        edt_bill_amount.requestFocus();
                        Toast.makeText(ViewBillActivity.this, "Amount Can't Blank", Toast.LENGTH_LONG).show();
                    } else {
                        if (!isError) {
                            if (NetConnection.isConnected(ViewBillActivity.this)) {

                                AlertDialog.Builder builder = new AlertDialog.Builder(ViewBillActivity.this);
                                builder.setTitle("Are you sure?");
                                builder.setMessage("Do you want to pay now!");
                                builder.setCancelable(false);
                                builder.setPositiveButton("Yes, Confirm!", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Httpjsontask task = new Httpjsontask();
                                        task.execute();
                                    }
                                });
                                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                AlertDialog alert = builder.create();
                                alert.show();

                            } else {
                                Toast.makeText(ViewBillActivity.this, "Internet Connection error", Toast.LENGTH_SHORT).show();
                            }

                        }

                    }
                }
            });
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(ViewBillActivity.this);
            builder.setMessage("Invalid Amount");
            builder.setCancelable(false);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }

    }

    //========================================start_http==========================================/
    public class Httpjsontask extends AsyncTask<String, Void, String> {

        String Errormessage;
        boolean IsError = false;
        String msg = "";
        String RSP_CODE = "", OPERATOR_ID = "", TRANS_ID = "", BBPS_REF_NO = "", APPROVAL_REF_NO = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ViewBillActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... arg0) {

            HttpClient htppclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(HttpUrlPath.urlPath + HttpUrlPath.action_pay_bill);

            try {
                List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

                nameValuePair.add(new BasicNameValuePair("username", s_Username));
                nameValuePair.add(new BasicNameValuePair("password", s_Password));
                nameValuePair.add(new BasicNameValuePair("operator", s_Operator_code));
                nameValuePair.add(new BasicNameValuePair("account", s_number));
                nameValuePair.add(new BasicNameValuePair("name", s_customer));
                nameValuePair.add(new BasicNameValuePair("duedate", s_due_date));
                nameValuePair.add(new BasicNameValuePair("amount", s_bill_amount));

                if (s_Operator_code.equals("MHSEDCL")) {
                    nameValuePair.add(new BasicNameValuePair("account1", s_Bill_Unit));
                } else if (s_Operator_code.equals("JBVNL")) {
                    nameValuePair.add(new BasicNameValuePair("account1", s_Sub_division));
                } else if (s_Operator_code.equals("REEL")) {
                    nameValuePair.add(new BasicNameValuePair("account1", s_Bill_cycle));
                } else if (s_Operator_code.equals("UHBVN")) {
                    nameValuePair.add(new BasicNameValuePair("account1", s_Mob_number));
                } else if (s_Operator_code.equals("WBE")) {
                    nameValuePair.add(new BasicNameValuePair("account1", s_Mob_number));
                } else if (s_Operator_code.equals("DHBVN")) {
                    nameValuePair.add(new BasicNameValuePair("account1", s_Mob_number));
                } else if (s_Operator_code.equals("TORRENTP")) {
                    nameValuePair.add(new BasicNameValuePair("account1", s_Service_No));
                }

                if (s_Operator_code.equals("BSNLPH")) {
                    nameValuePair.add(new BasicNameValuePair("account1", s_Landline_Number));
                    nameValuePair.add(new BasicNameValuePair("account2", s_Service_Type));
                } else if (s_Operator_code.equals("MTNLMB")) {
                    nameValuePair.add(new BasicNameValuePair("account1", s_Landline_Number));
                    nameValuePair.add(new BasicNameValuePair("account2", s_TID_Number));
                } else if (s_Operator_code.equals("MTNLDPH")) {
                    nameValuePair.add(new BasicNameValuePair("account1", s_Landline_Number));
                }

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePair, "UTF-8"));
                HttpResponse response = htppclient.execute(httppost);
                String object = EntityUtils.toString(response.getEntity());
                System.out.println("#####object registration=" + object);
                JSONObject js = new JSONObject(object);

                RSP_CODE = js.getString("RSP_CODE");
                Errormessage = js.getString("RSP_MSG");
                OPERATOR_ID = js.getString("OPERATOR_ID");
                TRANS_ID = js.getString("TRANS_ID");
                BBPS_REF_NO = js.getString("BBPS_REF_NO");
                APPROVAL_REF_NO = js.getString("APPROVAL_REF_NO");

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
                    AlertDialog.Builder builder = new AlertDialog.Builder(ViewBillActivity.this);
                    builder.setMessage(Errormessage);
                    builder.setCancelable(false);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @SuppressLint("NewApi")
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(ViewBillActivity.this, GprsHomeActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                } else if (RSP_CODE.equals("1")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ViewBillActivity.this);
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
                } else if (RSP_CODE.equals("2")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ViewBillActivity.this);
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
                } else if (RSP_CODE.equals("9")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ViewBillActivity.this);
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
                }
                //============================================


            } else {
                Toast.makeText(ViewBillActivity.this, "" + Errormessage, Toast.LENGTH_SHORT).show();
            }
        }

    }

    //============================================end ======================================//
}
