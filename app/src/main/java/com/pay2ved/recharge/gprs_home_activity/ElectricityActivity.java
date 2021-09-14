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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pay2ved.recharge.R;
import com.pay2ved.recharge.activity.ViewBillActivity;
import com.pay2ved.recharge.adapter.ImageArrayAdapter;
import com.pay2ved.recharge.adapter.MobileArrayAdapter;
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

public class ElectricityActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    EditText edt_customer_number, edt_Service_No, edit_customer_name, edit_Bill_Cycle, edt_Billing_Unit, edt_Sub_Division, edt_Mob_Number;
    TextView txt_list, txt_remark;
    Spinner spnr_provider;
    Button btn_View_Bill, btnPayBill;
    ImageView img_back;
    String s_customer_number = "", s_service_number = "", s_customer_name = "", s_provider = "", s_remark = "", s_hint1list = "", s_hint2list = "", s_hint3list = "", s_bill_unit = "", s_mob_number = "", s_sub_division = "", s_bill_cycle = "";
    public static String s_user_name = "", s_user_code = "", s_user_remark = "", s_user_hint1 = "", s_user_hint2 = "", s_user_hint3 = "", s_user_icon = "", s_Spinner_list = "", s_Title = "";
    String[] namelists;
    String[] remarklist;
    String[] hint1list;
    String[] hint2list;
    String[] hint3list;
    String[] codelist;
    String[] iconlist;
//    Integer[] imageArray;
    ImageArrayAdapter adapter;
    String s_Username = "", s_Password = "", s_Operator_code = "";
    RelativeLayout rlt_Billing_Unit, rlt_Mob_Number, rlt_Sub_Division, rlt_Bill_Cycle, rlt_customer_number, rlt_Service_No;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electricity);

        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        s_Spinner_list = AppsContants.sharedpreferences.getString(AppsContants.Spinner_list, "");
        s_Username = AppsContants.sharedpreferences.getString(AppsContants.Username, "");
        s_Password = AppsContants.sharedpreferences.getString(AppsContants.Password, "");
        s_Title = AppsContants.sharedpreferences.getString(AppsContants.Title, "");

        txt_list = (TextView) findViewById(R.id.txt_list);
        txt_remark = (TextView) findViewById(R.id.txt_remark);
        txt_list.setText(s_Title);

        rlt_Billing_Unit = (RelativeLayout) findViewById(R.id.rlt_Billing_Unit);
        rlt_Mob_Number = (RelativeLayout) findViewById(R.id.rlt_Mob_Number);
        rlt_Sub_Division = (RelativeLayout) findViewById(R.id.rlt_Sub_Division);
        rlt_Bill_Cycle = (RelativeLayout) findViewById(R.id.rlt_Bill_Cycle);
        rlt_customer_number = (RelativeLayout) findViewById(R.id.rlt_customer_number);
        rlt_Service_No = (RelativeLayout) findViewById(R.id.rlt_Service_No);

        edt_customer_number = (EditText) findViewById(R.id.edt_customer_number);
        edt_Service_No = (EditText) findViewById(R.id.edt_Service_No);
        edit_customer_name = (EditText) findViewById(R.id.edit_customer_name);
        edit_Bill_Cycle = (EditText) findViewById(R.id.edit_Bill_Cycle);
        edt_Billing_Unit = (EditText) findViewById(R.id.edt_Billing_Unit);
        edt_Sub_Division = (EditText) findViewById(R.id.edt_Sub_Division);
        edt_Mob_Number = (EditText) findViewById(R.id.edt_Mob_Number);
        spnr_provider = (Spinner) findViewById(R.id.spnr_provider);

        btnPayBill = (Button) findViewById(R.id.btn_pay_bill);
        btn_View_Bill = (Button) findViewById(R.id.btn_View_Bill);
        img_back = (ImageView) findViewById(R.id.img_back);

        codelist = s_user_code.split(",");
        namelists = s_user_name.split(",");
        remarklist = s_user_remark.split(",");
        hint1list = s_user_hint1.split(",");
        hint2list = s_user_hint2.split(",");
        hint3list = s_user_hint3.split(",");
        iconlist = s_user_icon.split(",");

        //=======================================================
        initView();

        onButtonAction();

    }

    private void initView(){
        /**
         if (s_Spinner_list.equals("ELECTRICITY")) {
         imageArray = new Integer[]{R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon
         , R.drawable.bescom, R.drawable.best, R.drawable.bses, R.drawable.bses, R.drawable.default_icon, R.drawable.default_icon
         , R.drawable.default_icon, R.drawable.cesc, R.drawable.cspdcl, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon
         , R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon
         , R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon
         , R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon
         , R.drawable.msedc, R.drawable.mpcz, R.drawable.mppkvv, R.drawable.npcl, R.drawable.northbihar, R.drawable.ndpl, R.drawable.rrvpnl, R.drawable.default_icon
         , R.drawable.default_icon, R.drawable.default_icon, R.drawable.re, R.drawable.southbihar, R.drawable.tsspdcl, R.drawable.apspdcl
         , R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.tata_power, R.drawable.torrent_power
         , R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon
         , R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon
         , R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon
         , R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon
         , R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon
         , R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon
         , R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon
         , R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon
         , R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon
         , R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon
         , R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon
         , R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon
         , R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon
         , R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon
         , R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon
         , R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon
         , R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon
         , R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon};

         } else {
         imageArray = new Integer[]{R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon,
         R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon
         , R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon
         , R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon
         , R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon
         , R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon
         , R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon
         , R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon
         , R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon
         , R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon
         , R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon
         , R.drawable.default_icon, R.drawable.default_icon};
         }

         */

        adapter = new ImageArrayAdapter(this, R.layout.mobile_spinner_layout, codelist, namelists, remarklist
                , hint1list, hint2list, hint3list, iconlist);
        spnr_provider.setAdapter(adapter);

        spnr_provider.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                s_provider = codelist[position].toString().trim();
                s_remark = remarklist[position].toString().trim();

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
                    edt_customer_number.setHint("Enter Consumer/Service/Customer No");
                } else if (s_hint1list.equals("")) {
                    edt_customer_number.setHint("Validator 1");
                } else {
                    edt_customer_number.setHint(s_hint1list);
                }

                if (s_hint2list.equals("null")) {
                    edt_Mob_Number.setHint("Enter Mobile Number");
                    edit_Bill_Cycle.setHint("Enter Bill Cycle");
                    edt_Billing_Unit.setHint("Enter Billing Unit");
                    edt_Sub_Division.setHint("Enter Sub Division");
                    edt_Service_No.setHint("Enter Service Number");
                } else if (s_hint2list.equals("")) {
                    edt_Mob_Number.setHint("Validator 2");
                    edit_Bill_Cycle.setHint("Validator 2");
                    edt_Billing_Unit.setHint("Validator 2");
                    edt_Sub_Division.setHint("Validator 2");
                    edt_Service_No.setHint("Validator 2");
                } else {
                    edt_Mob_Number.setHint(s_hint2list);
                    edit_Bill_Cycle.setHint(s_hint2list);
                    edt_Billing_Unit.setHint(s_hint2list);
                    edt_Sub_Division.setHint(s_hint2list);
                    edt_Service_No.setHint(s_hint2list);
                }

                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.Operator_code, s_provider);
                editor.commit();

                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                s_Operator_code = AppsContants.sharedpreferences.getString(AppsContants.Operator_code, "");

                //======================Electricity===============================

                if (codelist[position].toString().equals("MHSEDCL")) {
                    rlt_Billing_Unit.setVisibility(View.VISIBLE);
                } else {
                    rlt_Billing_Unit.setVisibility(View.GONE);
                }
                if (codelist[position].toString().equals("JBVNL")) {
                    rlt_Sub_Division.setVisibility(View.VISIBLE);
                } else {
                    rlt_Sub_Division.setVisibility(View.GONE);
                }
                if (codelist[position].toString().equals("REEL")) {
                    rlt_Bill_Cycle.setVisibility(View.VISIBLE);
                } else {
                    rlt_Bill_Cycle.setVisibility(View.GONE);
                }
                if (codelist[position].toString().equals("TORRENTP")) {
                    rlt_Service_No.setVisibility(View.VISIBLE);
                } else {
                    rlt_Service_No.setVisibility(View.GONE);
                }
                if (codelist[position].toString().equals("UHBVN")) {
                    rlt_Mob_Number.setVisibility(View.VISIBLE);
                } else if (codelist[position].toString().equals("WBE")) {
                    rlt_Mob_Number.setVisibility(View.VISIBLE);
                } else if (codelist[position].toString().equals("DHBVN")) {
                    rlt_Mob_Number.setVisibility(View.VISIBLE);
                } else {
                    rlt_Mob_Number.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void onButtonAction(){

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_View_Bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s_customer_number = edt_customer_number.getText().toString().trim();
                s_service_number = edt_Service_No.getText().toString().trim();
                s_customer_name = edit_customer_name.getText().toString().trim();
                s_bill_cycle = edit_Bill_Cycle.getText().toString().trim();
                s_bill_unit = edt_Billing_Unit.getText().toString().trim();
                s_sub_division = edt_Sub_Division.getText().toString().trim();
                s_mob_number = edt_Mob_Number.getText().toString().trim();

                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.Operator_code, s_provider);
                editor.putString(AppsContants.Bill_Unit, s_bill_unit);
                editor.putString(AppsContants.Sub_division, s_sub_division);
                editor.putString(AppsContants.Bill_cycle, s_bill_cycle);
                editor.putString(AppsContants.Mobile, s_mob_number);
                editor.putString(AppsContants.Service_No, s_service_number);

                editor.commit();

                boolean isError = false;
                String Error_Text = "";
                if (s_provider.equals("null")) {
                    isError = true;
                    Error_Text = "Please Select Provider";
                } else if (s_customer_number.equals("")) {
                    isError = true;
                    Error_Text = "Please Enter Consumer/Service/Customer Account Number";
                    edt_customer_number.requestFocus();
                } else if (s_customer_name.equals("")) {
                    isError = true;
                    Error_Text = "Please Enter Customer Name";
                    edit_customer_name.requestFocus();
                } else if (s_provider.equals("TORRENTP")) {
                    if (s_service_number.equals("")) {
                        isError = true;
                        edt_Service_No.requestFocus();
                        Error_Text = "Please Enter Service Number";
                    }
                } else if (s_provider.equals("MHSEDCL")) {
                    if (s_bill_unit.equals("")) {
                        isError = true;
                        edt_Billing_Unit.requestFocus();
                        Error_Text = "Please Enter Billing Unit";
                    }
                } else if (s_provider.equals("UHBVN")) {
                    if (s_mob_number.equals("")) {
                        isError = true;
                        edt_Mob_Number.requestFocus();
                        Error_Text = "Please Enter Mobile Number";
                    }
                } else if (s_provider.equals("WBE")) {
                    if (s_mob_number.equals("")) {
                        isError = true;
                        edt_Mob_Number.requestFocus();
                        Error_Text = "Please Enter Mobile Number";
                    }
                } else if (s_provider.equals("DHBVN")) {
                    if (s_mob_number.equals("")) {
                        isError = true;
                        edt_Mob_Number.requestFocus();
                        Error_Text = "Please Enter Mobile Number";
                    }
                } else if (s_provider.equals("JBVNL")) {
                    if (s_sub_division.equals("")) {
                        isError = true;
                        edt_Sub_Division.requestFocus();
                        Error_Text = "Please Enter Sub Division";
                    }
                } else if (s_provider.equals("REEL")) {
                    if (s_bill_cycle.equals("")) {
                        isError = true;
                        Error_Text = "Please Enter Bill Cycle";
                    }
                }

                if (!isError) {
                    if (NetConnection.isConnected(ElectricityActivity.this)) {
                        Httpjsontask task = new Httpjsontask();
                        task.execute();
                    } else {
                        Toast.makeText(ElectricityActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ElectricityActivity.this, "" + Error_Text, Toast.LENGTH_LONG).show();
                }

            }
        });

        btnPayBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Add Action...

            }
        });

    }

    //========================================start_http==========================================/
    public class Httpjsontask extends AsyncTask<String, Void, String> {

        String Errormessage;
        boolean IsError = false;
        String msg = "";
        String RSP_CODE = "", VALID_BILL = "", ACCOUNT = "", CUSTOMER_NAME = "", BILL_NUMBER = "", BILL_DATE = "", BILL_DUE_DATE = "", BILL_AMOUNT = "", PARTIAL_PAY = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ElectricityActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... arg0) {

            HttpClient htppclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(HttpUrlPath.urlPath + HttpUrlPath.action_verify_bill);

            try {
                List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

                nameValuePair.add(new BasicNameValuePair("username", s_Username));
                nameValuePair.add(new BasicNameValuePair("password", s_Password));
                nameValuePair.add(new BasicNameValuePair("operator", s_provider));
                nameValuePair.add(new BasicNameValuePair("account", s_customer_number));
                nameValuePair.add(new BasicNameValuePair("customer_name", s_customer_name));

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePair, "UTF-8"));
                HttpResponse response = htppclient.execute(httppost);
                String object = EntityUtils.toString(response.getEntity());
                System.out.println("#####object registration=" + object);
                JSONObject js = new JSONObject(object);

                RSP_CODE = js.getString("RSP_CODE");
                Errormessage = js.getString("RSP_MSG");
                VALID_BILL = js.getString("VALID_BILL");
                ACCOUNT = js.getString("ACCOUNT");
                CUSTOMER_NAME = js.getString("CUSTOMER_NAME");
                BILL_NUMBER = js.getString("BILL_NUMBER");
                BILL_DATE = js.getString("BILL_DATE");
                BILL_DUE_DATE = js.getString("BILL_DUE_DATE");
                BILL_AMOUNT = js.getString("BILL_AMOUNT");
                PARTIAL_PAY = js.getString("PARTIAL_PAYMENT");

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
                    AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                    editor.putString(AppsContants.Customer_Name, CUSTOMER_NAME);
                    editor.putString(AppsContants.Customer_Number, ACCOUNT);
                    editor.putString(AppsContants.Valid_Bill, VALID_BILL);
                    editor.putString(AppsContants.Bill_Number, BILL_NUMBER);
                    editor.putString(AppsContants.Bill_Date, BILL_DATE);
                    editor.putString(AppsContants.Bill_DueDate, BILL_DUE_DATE);
                    editor.putString(AppsContants.amount, BILL_AMOUNT);
                    editor.putString(AppsContants.Partial_Pay, PARTIAL_PAY);
                    editor.commit();

                    Intent intent = new Intent(ElectricityActivity.this, ViewBillActivity.class);
                    startActivity(intent);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ElectricityActivity.this);
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

            } else {
                Toast.makeText(ElectricityActivity.this, "" + Errormessage, Toast.LENGTH_SHORT).show();
            }
        }

    }

    //============================================end ======================================//
}
