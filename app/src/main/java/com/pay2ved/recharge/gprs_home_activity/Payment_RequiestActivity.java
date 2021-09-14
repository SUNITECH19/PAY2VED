package com.pay2ved.recharge.gprs_home_activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pay2ved.recharge.R;
import com.pay2ved.recharge.activity.GprsHomeActivity;
import com.pay2ved.recharge.helper.Listener;
import com.pay2ved.recharge.other.AppsContants;
import com.pay2ved.recharge.other.HttpUrlPath;
import com.pay2ved.recharge.other.NetConnection;
import com.pay2ved.recharge.service.callmodel.CallObject;
import com.pay2ved.recharge.service.query.DBQuery;

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
import java.util.Calendar;
import java.util.List;

public class Payment_RequiestActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,
        AdapterView.OnItemSelectedListener, Listener.OnObjectQueryListener {

    EditText amount, account, txnid, remark;
    Button ok, cancel;
    ImageView img_back;
    String s_Password = "", s_Username = "";
    String s_mode = "", s_amount = "", s_date = "", s_account = "", s_txnid = "", s_remark = "";
    TextView date;
    private ProgressDialog pDialog;
    RelativeLayout rlt_date;
    DatePickerDialog datePickerDialog;
    int startYear, startMonth, startDay;
    Spinner spinner_mode;
    String[] mode = {"Select", "NEFT/RTGS/IMPS", "ATM TRANSFER", "CHEQUE/DD", "CASH DEPOSIT", "CASH IN HAND", "OTHER TRANSFER"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        pDialog = new ProgressDialog(Payment_RequiestActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        s_Username = AppsContants.sharedpreferences.getString(AppsContants.Username, "");
        s_Password = AppsContants.sharedpreferences.getString(AppsContants.Password, "");

        date = (TextView) findViewById(R.id.date);
        spinner_mode = (Spinner) findViewById(R.id.spinner_mode);
        amount = (EditText) findViewById(R.id.amount);
        account = (EditText) findViewById(R.id.account);
        txnid = (EditText) findViewById(R.id.txnid);
        remark = (EditText) findViewById(R.id.remark);
        cancel = (Button) findViewById(R.id.cancel);
        ok = (Button) findViewById(R.id.ok);
        rlt_date = (RelativeLayout) findViewById(R.id.rlt_date);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        date.setText(s_date);

        spinner_mode.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, mode);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_mode.setAdapter(aa);


        Calendar c = Calendar.getInstance();
        startYear = c.get(Calendar.YEAR);
        startMonth = c.get(Calendar.MONTH);
        startDay = c.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(Payment_RequiestActivity.this, this, startYear, startMonth, startDay);
        rlt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                s_amount = amount.getText().toString().trim();
                s_account = account.getText().toString().trim();
                s_txnid = txnid.getText().toString().trim();
                s_remark = remark.getText().toString().trim();
                s_date = date.getText().toString().trim();

                boolean isError = false;

                if (s_mode.equals("Select")) {
                    isError = true;
                    Toast.makeText(Payment_RequiestActivity.this, "Please Select Mode", Toast.LENGTH_LONG).show();

                } else if (s_amount.equals("")) {
                    isError = true;
                    amount.requestFocus();
                    Toast.makeText(Payment_RequiestActivity.this, "Please Enter Amount", Toast.LENGTH_LONG).show();

                } else if (s_date.equals("")) {
                    isError = true;
                    Toast.makeText(Payment_RequiestActivity.this, "Please Select Date", Toast.LENGTH_LONG).show();

                } else if (s_account.equals("")) {
                    isError = true;
                    account.requestFocus();
                    Toast.makeText(Payment_RequiestActivity.this, "Please Enter Account No", Toast.LENGTH_LONG).show();

                } else {

                    if (!isError) {
                        if (NetConnection.isConnected(Payment_RequiestActivity.this)) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(Payment_RequiestActivity.this);
                            builder.setTitle("Do you want to sure confirm !" + "\n ");
                            builder.setMessage("Account No. :  " + s_account + "\n " + "Mode :  " + s_mode + "\n " + "Amount :  " + s_amount);
                            builder.setCancelable(false);
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @SuppressLint("NewApi")
                                public void onClick(DialogInterface dialog, int which) {
                                    pDialog.show();
                                    DBQuery.queryToPayRequest( Payment_RequiestActivity.this,
                                            s_Username, s_Password, s_mode, s_amount, s_date, s_account,s_txnid, s_remark );
//                                    Httpjsontask task = new Httpjsontask();
//                                    task.execute();
                                }
                            });
                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @SuppressLint("NewApi")
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });

                            AlertDialog alert = builder.create();
                            alert.show();

                            //====================================================
                        } else {
                            Toast.makeText(Payment_RequiestActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        startYear = year;
        startMonth = month;
        startDay = dayOfMonth;

        //=========================================
        int Day = startDay;
        int Month = startMonth + 1;

        String s_dayOfMonth = Day + "";
        String s_month = Month + "";

        if (Day < 10) {
            s_dayOfMonth = "0" + Day;
        }

        if (Month < 10) {
            s_month = "0" + Month;
        }

        date.setText(startYear + "-" + (s_month) + "-" + s_dayOfMonth);
        //========================================


      /*  date.setText(new StringBuilder()
                .append(startMonth + 1).append("/").append(startDay).append("/")
                .append(startYear).append(" "));*/


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        s_mode = mode[position].toString().trim();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onResponse(@Nullable CallObject callObject) {
        pDialog.dismiss();
        if (callObject!=null){
            AlertDialog.Builder builder = new AlertDialog.Builder(Payment_RequiestActivity.this);
            builder.setMessage("" + callObject.getMessage());
            builder.setCancelable(false);
            builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @SuppressLint("NewApi")
                public void onClick(DialogInterface dialog, int which) {

                    Intent intent = new Intent(Payment_RequiestActivity.this, GprsHomeActivity.class);
                    startActivity(intent);
                    finishAffinity();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();
        }else Toast.makeText(this, "Something went Wrong!", Toast.LENGTH_SHORT).show();
    }

    // TODO : Remove This Class.========================================start_http==========================================/
    public class Httpjsontask extends AsyncTask<String, Void, String> {

        String Errormessage;
        boolean IsError = false;
        String msg = "", s_data = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog.show();
        }

        protected String doInBackground(String... arg0) {

            HttpClient htppclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(HttpUrlPath.urlPath + HttpUrlPath.action_payment_request);

            try {
                List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

                nameValuePair.add(new BasicNameValuePair("username", s_Username));
                nameValuePair.add(new BasicNameValuePair("password", s_Password));
                nameValuePair.add(new BasicNameValuePair("mode", s_mode));
                nameValuePair.add(new BasicNameValuePair("amount", s_amount));
                nameValuePair.add(new BasicNameValuePair("date", s_date));
                nameValuePair.add(new BasicNameValuePair("account", s_account));
                nameValuePair.add(new BasicNameValuePair("txnid", s_txnid));
                nameValuePair.add(new BasicNameValuePair("remark", s_remark));

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePair, "UTF-8"));
                HttpResponse response = htppclient.execute(httppost);
                String object = EntityUtils.toString(response.getEntity());
                System.out.println("#####object registration=" + object);
                JSONObject js = new JSONObject(object);

                Errormessage = js.getString("message");
                s_data = js.getString("data");

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

                AlertDialog.Builder builder = new AlertDialog.Builder(Payment_RequiestActivity.this);
                builder.setMessage("" + Errormessage);
                builder.setCancelable(false);
                builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @SuppressLint("NewApi")
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(Payment_RequiestActivity.this, GprsHomeActivity.class);
                        startActivity(intent);
                        finishAffinity();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

            } else {
                Toast.makeText(Payment_RequiestActivity.this, "" + Errormessage, Toast.LENGTH_SHORT).show();
            }
        }

    }


}
