package com.pay2ved.recharge.gprs_home_activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import com.pay2ved.recharge.adapter.ImageArrayAdapter;

import com.pay2ved.recharge.helper.Listener;
import com.pay2ved.recharge.other.AppsContants;
import com.pay2ved.recharge.other.HttpUrlPath;
import com.pay2ved.recharge.other.NetConnection;
import com.pay2ved.recharge.service.callmodel.CallRecharge;
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

public class InsuranceActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, Listener.OnRechargeListener {

    private ProgressDialog pDialog;
    EditText amount, edt_customer_number;
    TextView txt_list, txt_remark;
    Spinner spnr_provider;
    Button ok, cancel;
    ImageView img_back;
    String s_customer_number = "", s_amount = "", s_provider = "", s_dob = "", s_remark = "", s_hint1list = "", s_hint2list = "", s_hint3list = "";
    public static String s_user_name = "", s_user_code = "", s_user_remark = "", s_user_hint1 = "", s_user_hint2 = "", s_user_hint3 = "", s_user_icon = "", s_Spinner_list = "", s_Title = "";
    String[] namelists;
    String[] codelist;
    String[] remarklist;
    String[] hint1list;
    String[] hint2list;
    String[] hint3list;
    String[] iconlist;
    Integer[] imageArray;
    ImageArrayAdapter adapter;
    String s_Username = "", s_Password = "";
    RelativeLayout rlt_customer_number, rlt_Date;
    TextView date;
    DatePickerDialog datePickerDialog;
    int startYear, startMonth, startDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance);
        pDialog = new ProgressDialog(InsuranceActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        s_Spinner_list = AppsContants.sharedpreferences.getString(AppsContants.Spinner_list, "");
        s_Username = AppsContants.sharedpreferences.getString(AppsContants.Username, "");
        s_Password = AppsContants.sharedpreferences.getString(AppsContants.Password, "");
        s_Title = AppsContants.sharedpreferences.getString(AppsContants.Title, "");


        txt_list = (TextView) findViewById(R.id.txt_list);
        txt_remark = (TextView) findViewById(R.id.txt_remark);
        txt_list.setText(s_Title);

        date = (TextView) findViewById(R.id.date);
        rlt_customer_number = (RelativeLayout) findViewById(R.id.rlt_customer_number);
        rlt_Date = (RelativeLayout) findViewById(R.id.rlt_Date);

        amount = (EditText) findViewById(R.id.amount);
        edt_customer_number = (EditText) findViewById(R.id.edt_customer_number);
        spnr_provider = (Spinner) findViewById(R.id.spnr_provider);

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

        date.setText(s_dob);

        Calendar c = Calendar.getInstance();
        startYear = c.get(Calendar.YEAR);
        startMonth = c.get(Calendar.MONTH);
        startDay = c.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(InsuranceActivity.this, this, startYear, startMonth, startDay);
        rlt_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });


        codelist = s_user_code.split(",");
        namelists = s_user_name.split(",");
        remarklist = s_user_remark.split(",");
        hint1list = s_user_hint1.split(",");
        hint2list = s_user_hint2.split(",");
        hint3list = s_user_hint3.split(",");
        iconlist = s_user_icon.split(",");


        if (s_Spinner_list.equals("INSURANCE")) {
            imageArray = new Integer[]{R.drawable.default_icon, R.drawable.icici_pru, R.drawable.lic, R.drawable.tata_aia, R.drawable.default_icon, R.drawable.default_icon
                    , R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon
                    , R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon
                    , R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon
                    , R.drawable.default_icon};

        } else {
            imageArray = new Integer[]{R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon,
                    R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon
                    , R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon
                    , R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon
                    , R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon
                    , R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon
                    , R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon};
        }

        adapter = new ImageArrayAdapter(this, R.layout.mobile_spinner_layout, codelist, namelists, remarklist
                , hint1list, hint2list, hint3list, iconlist);
        spnr_provider.setAdapter(adapter);

        spnr_provider.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                s_provider = codelist[position].toString().trim();
                // Toast.makeText(InsuranceActivity.this, "" + position, Toast.LENGTH_SHORT).show();

                s_remark = remarklist[position].toString().trim();
                // Toast.makeText(InsuranceActivity.this, "" + s_remark, Toast.LENGTH_SHORT).show();

                try {
                    s_hint1list = hint1list[position].toString().trim();
                    s_hint2list = hint2list[position].toString().trim();
                    s_hint3list = hint3list[position].toString().trim();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (s_remark.equals("null")) {
                    txt_remark.setText("");
                } else {
                    txt_remark.setText(s_remark);
                }

                if (s_hint1list.equals("null")) {
                    edt_customer_number.setHint("Enter Account Number");
                } else if (s_hint1list.equals("")) {
                    edt_customer_number.setHint("Validator 1");
                } else {
                    edt_customer_number.setHint(s_hint1list);
                }

                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.Operator_code, s_provider);
                editor.commit();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //=======================================================


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                s_customer_number = edt_customer_number.getText().toString().trim();
                s_amount = amount.getText().toString().trim();
                s_dob = date.getText().toString().trim();

                boolean isError = false;

                if (s_provider.equals("null")) {
                    isError = true;
                    Toast.makeText(InsuranceActivity.this, "Please Select Company", Toast.LENGTH_LONG).show();

                } else if (s_customer_number.equals("")) {
                    isError = true;
                    edt_customer_number.requestFocus();
                    Toast.makeText(InsuranceActivity.this, "Please Enter Account Number", Toast.LENGTH_LONG).show();

                } else if (s_dob.equals("")) {
                    isError = true;
                    Toast.makeText(InsuranceActivity.this, "Please Select Date of Birth", Toast.LENGTH_LONG).show();

                } else if (s_amount.equals("")) {
                    isError = true;
                    amount.requestFocus();
                    Toast.makeText(InsuranceActivity.this, "Please Enter Amount", Toast.LENGTH_LONG).show();

                } else {

                    if (!isError) {
                        if (NetConnection.isConnected(InsuranceActivity.this)) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(InsuranceActivity.this);
                            builder.setTitle("Do you want to sure confirm !" + "\n ");
                            builder.setMessage("Account Number :  " + s_customer_number + "\n " + "Company :  " + s_provider + "\n " + "Amount :  " + s_amount);
                            builder.setCancelable(false);
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @SuppressLint("NewApi")
                                public void onClick(DialogInterface dialog, int which) {
                                    // Query To Get Info...
                                    pDialog.show();
                                    DBQuery.queryToRecharge(InsuranceActivity.this,
                                            s_Username, s_Password, s_provider, s_customer_number, s_Spinner_list, s_dob, s_amount );

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
                            Toast.makeText(InsuranceActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
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


     /*   date.setText(new StringBuilder()
                .append(startMonth + 1).append("/").append(startDay).append("/")
                .append(startYear).append(" "));*/

    }

    @Override
    public void onRechargeInfo(@Nullable CallRecharge callRecharge) {
        pDialog.dismiss();

        if (callRecharge!=null && callRecharge.getData() != null){
            AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
            editor.putString(AppsContants.r_Status, callRecharge.getData().getStatus());
            editor.putString(AppsContants.r_Detail, callRecharge.getData().getDetail());
            editor.putString(AppsContants.r_Balance, callRecharge.getData().getBalance());
            editor.commit();
            //============================================
            AlertDialog.Builder builder = new AlertDialog.Builder(InsuranceActivity.this);
            builder.setMessage(callRecharge.getData().getDetail() + "\n ");
            builder.setCancelable(false);
            builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @SuppressLint("NewApi")
                public void onClick(DialogInterface dialog, int which) {
                    //  dialog.cancel();
                    Intent intent = new Intent(InsuranceActivity.this, GprsHomeActivity.class);
                    startActivity(intent);
                    finishAffinity();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();
        }else if (callRecharge !=null){
            Toast.makeText(InsuranceActivity.this, "" + callRecharge.getMessage() , Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(InsuranceActivity.this, "Something Went Wrong!" , Toast.LENGTH_SHORT).show();
        }
    }


    // TODO : Remove this Class ========================================start_http==========================================/
    public class Httpjsontask extends AsyncTask<String, Void, String> {

        String Errormessage;
        boolean IsError = false;
        String msg = "", s_data = "";
        String p_status = "", p_detail = "", p_balance = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(InsuranceActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... arg0) {

            HttpClient htppclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(HttpUrlPath.urlPath + HttpUrlPath.action_recharge);

            try {
                List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

                nameValuePair.add(new BasicNameValuePair("username", s_Username));
                nameValuePair.add(new BasicNameValuePair("password", s_Password));
                nameValuePair.add(new BasicNameValuePair("operator", s_provider));
                nameValuePair.add(new BasicNameValuePair("number", s_customer_number));
                nameValuePair.add(new BasicNameValuePair("service", s_Spinner_list));
                nameValuePair.add(new BasicNameValuePair("dob", s_dob));
                nameValuePair.add(new BasicNameValuePair("amount", s_amount));


                httppost.setEntity(new UrlEncodedFormEntity(nameValuePair, "UTF-8"));
                HttpResponse response = htppclient.execute(httppost);
                String object = EntityUtils.toString(response.getEntity());
                System.out.println("#####object registration=" + object);
                JSONObject js = new JSONObject(object);

                Errormessage = js.getString("message");
                if (js.has("data")) {
                    s_data = js.getString("data");
                    JSONObject data = new JSONObject(s_data);

                    p_status = data.getString("status");
                    p_detail = data.getString("detail");
                    p_balance = data.getString("balance");

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
                editor.putString(AppsContants.r_Status, p_status);
                editor.putString(AppsContants.r_Detail, p_detail);
                editor.putString(AppsContants.r_Balance, p_balance);
                editor.commit();


                //============================================
                AlertDialog.Builder builder = new AlertDialog.Builder(InsuranceActivity.this);
                builder.setMessage(p_detail + "\n ");
                builder.setCancelable(false);
                builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @SuppressLint("NewApi")
                    public void onClick(DialogInterface dialog, int which) {
                        //  dialog.cancel();
                        Intent intent = new Intent(InsuranceActivity.this, GprsHomeActivity.class);
                        startActivity(intent);
                        finishAffinity();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
                //==========================================


            } else {
                Toast.makeText(InsuranceActivity.this, "" + Errormessage, Toast.LENGTH_SHORT).show();
            }
        }

    }

//============================================end ======================================//

}
