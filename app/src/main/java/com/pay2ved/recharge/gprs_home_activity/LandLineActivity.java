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

public class LandLineActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    EditText edt_customer_number, edt_Landline_number, edt_tid_number, edit_customer_name;
    TextView txt_list, txt_remark;
    Spinner spnr_provider, spnr_SERVICE_TYPE;
    Button btn_View_Bill, cancel;
    ImageView img_back;
    String s_customer_number = "", s_customer_name = "", s_provider = "", s_Landline_number = "", s_tid_number = "", s_Service_Type = "", s_remark = "", s_hint1list = "", s_hint2list = "", s_hint3list = "";
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
    String s_Username = "", s_Password = "", s_Operator_code = "";
    RelativeLayout rlt_customer_number, rlt_Landline_number, rlt_tid_number, rlt_SERVICE_TYPE;
    String[] SERVICE_TYPE = {"Select", "LLI - Landline Individual", "LLC - Landline Corporate"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landline);

        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        s_Spinner_list = AppsContants.sharedpreferences.getString(AppsContants.Spinner_list, "");
        s_Username = AppsContants.sharedpreferences.getString(AppsContants.Username, "");
        s_Password = AppsContants.sharedpreferences.getString(AppsContants.Password, "");
        s_Title = AppsContants.sharedpreferences.getString(AppsContants.Title, "");

        txt_list = (TextView) findViewById(R.id.txt_list);
        txt_remark = (TextView) findViewById(R.id.txt_remark);
        txt_list.setText(s_Title);

        rlt_customer_number = (RelativeLayout) findViewById(R.id.rlt_customer_number);
        rlt_Landline_number = (RelativeLayout) findViewById(R.id.rlt_Landline_number);
        rlt_tid_number = (RelativeLayout) findViewById(R.id.rlt_tid_number);
        rlt_SERVICE_TYPE = (RelativeLayout) findViewById(R.id.rlt_SERVICE_TYPE);

        edt_customer_number = (EditText) findViewById(R.id.edt_customer_number);
        edit_customer_name = (EditText) findViewById(R.id.edit_customer_name);
        edt_Landline_number = (EditText) findViewById(R.id.edt_Landline_number);
        edt_tid_number = (EditText) findViewById(R.id.edt_tid_number);

        spnr_provider = (Spinner) findViewById(R.id.spnr_provider);
        spnr_SERVICE_TYPE = (Spinner) findViewById(R.id.spnr_SERVICE_TYPE);

        cancel = (Button) findViewById(R.id.cancel);
        btn_View_Bill = (Button) findViewById(R.id.btn_View_Bill);
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

        btn_View_Bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                s_customer_number = edt_customer_number.getText().toString().trim();
                s_customer_name = edit_customer_name.getText().toString().trim();
                s_Landline_number = edt_Landline_number.getText().toString().trim();
                s_tid_number = edt_tid_number.getText().toString().trim();

                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.Operator_code, s_provider);
                editor.putString(AppsContants.TID_Number, s_tid_number);
                editor.putString(AppsContants.Landline_Number, s_Landline_number);
                editor.commit();


                boolean isError = false;
                String Error_Text = "";
                if (s_provider.equals("null")) {
                    isError = true;
                    Error_Text = "Please Select Company";
                } else if (s_customer_number.equals("")) {
                    isError = true;
                    Error_Text = "Please Enter Account Number";
                    edt_customer_number.requestFocus();
                } else if (s_customer_name.equals("")) {
                    isError = true;
                    Error_Text = "Please Enter Customer Name";
                    edit_customer_name.requestFocus();
                } else if (s_provider.equals("BSNLPH")) {
                    if (s_Landline_number.equals("")) {
                        isError = true;
                        edt_Landline_number.requestFocus();
                        Error_Text = "Please Enter Landline Number";
                    } else if (s_Service_Type.equals("")) {
                        isError = true;
                        Error_Text = "Please Select Service Type";
                    }
                } else if (s_provider.equals("MTNLMB")) {
                    if (s_Landline_number.equals("")) {
                        isError = true;
                        edt_Landline_number.requestFocus();
                        Error_Text = "Please Enter Landline Number";
                    } else if (s_tid_number.equals("")) {
                        isError = true;
                        edt_tid_number.requestFocus();
                        Error_Text = "Please Enter TID Number";
                    }
                } else if (s_provider.equals("MTNLDPH")) {
                    if (s_Landline_number.equals("")) {
                        isError = true;
                        edt_Landline_number.requestFocus();
                        Error_Text = "Please Enter Landline Number";
                    }
                }

                if (!isError) {
                    if (NetConnection.isConnected(LandLineActivity.this)) {

                        Httpjsontask task = new Httpjsontask();
                        task.execute();

                      /*  AlertDialog.Builder builder = new AlertDialog.Builder(LandLineActivity.this);
                        builder.setTitle("Do you want to sure confirm !" + "\n ");
                        builder.setMessage("Account Number :  " + s_customer_number + "\n " + "Company :  " + s_provider + "\n " + "Customer Name :  " + s_customer_name);
                        builder.setCancelable(false);
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @SuppressLint("NewApi")
                            public void onClick(DialogInterface dialog, int which) {
                                Httpjsontask task = new Httpjsontask();
                                task.execute();
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
*/
                        //====================================================
                    } else {
                        Toast.makeText(LandLineActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LandLineActivity.this, "" + Error_Text, Toast.LENGTH_LONG).show();
                }

            }
        });


        spnr_SERVICE_TYPE.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                s_Service_Type = SERVICE_TYPE[position].toString().trim();

                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.Service_Type, s_Service_Type);
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter type = new ArrayAdapter(this, android.R.layout.simple_spinner_item, SERVICE_TYPE);
        type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnr_SERVICE_TYPE.setAdapter(type);


        codelist = s_user_code.split(",");
        namelists = s_user_name.split(",");
        remarklist = s_user_remark.split(",");
        hint1list = s_user_hint1.split(",");
        hint2list = s_user_hint2.split(",");
        hint3list = s_user_hint3.split(",");
        iconlist = s_user_icon.split(",");


        if (s_Spinner_list.equals("LANDLINE")) {
            imageArray = new Integer[]{R.drawable.default_icon, R.drawable.airtel, R.drawable.bsnl, R.drawable.mtnl, R.drawable.mtnl, R.drawable.tikona,
                    R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon
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

                s_remark = remarklist[position].toString().trim();
                // Toast.makeText(LandLineActivity.this, "" + s_remark, Toast.LENGTH_SHORT).show();

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
                    edt_Landline_number.setHint("Enter Landline Number With STD CODE");
                } else if (s_hint1list.equals("")) {
                    edt_Landline_number.setHint("Validator 1");
                } else {
                    edt_Landline_number.setHint(s_hint1list);
                }

                if (s_hint2list.equals("null")) {
                    edt_customer_number.setHint("Enter Account Number");
                } else if (s_hint2list.equals("")) {
                    edt_customer_number.setHint("Enter Account Number");
                } else {
                    edt_customer_number.setHint(s_hint2list);
                }

                if (s_hint3list.equals("null")) {
                    edt_tid_number.setHint("Enter TID Number");
                } else if (s_hint3list.equals("")) {
                    edt_tid_number.setHint("Validator 3");
                } else {
                    edt_tid_number.setHint(s_hint3list);
                }

                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.Operator_code, s_provider);
                editor.commit();

                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                s_Operator_code = AppsContants.sharedpreferences.getString(AppsContants.Operator_code, "");

                //======================LandLine===============================

                if (codelist[position].toString().equals("BSNLPH")) {
                    rlt_Landline_number.setVisibility(View.VISIBLE);
                    rlt_SERVICE_TYPE.setVisibility(View.VISIBLE);
                    rlt_tid_number.setVisibility(View.GONE);
                } else if (codelist[position].toString().equals("MTNLMB")) {
                    rlt_Landline_number.setVisibility(View.VISIBLE);
                    rlt_tid_number.setVisibility(View.VISIBLE);
                    rlt_SERVICE_TYPE.setVisibility(View.GONE);
                } else if (codelist[position].toString().equals("MTNLDPH")) {
                    rlt_Landline_number.setVisibility(View.VISIBLE);
                    rlt_tid_number.setVisibility(View.GONE);
                    rlt_SERVICE_TYPE.setVisibility(View.GONE);
                } else {
                    rlt_SERVICE_TYPE.setVisibility(View.GONE);
                    rlt_Landline_number.setVisibility(View.GONE);
                    rlt_tid_number.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //=======================================================
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
            pDialog = new ProgressDialog(LandLineActivity.this);
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

            if (s_provider.equals("BSNLPH")) {
                if (s_Landline_number.equals("")) {
                    IsError = true;
                    edt_Landline_number.requestFocus();
                    Toast.makeText(LandLineActivity.this, "Please Enter Landline Number", Toast.LENGTH_LONG).show();
                } else if (s_Service_Type.equals("Select")) {
                    IsError = true;
                    Toast.makeText(LandLineActivity.this, "Please Select Service Type", Toast.LENGTH_LONG).show();
                }
            } else if (s_provider.equals("MTNLDPH")) {
                if (s_Landline_number.equals("")) {
                    IsError = true;
                    edt_Landline_number.requestFocus();
                    Toast.makeText(LandLineActivity.this, "Please Enter Landline Number", Toast.LENGTH_LONG).show();
                }
            } else if (s_provider.equals("MTNLMB")) {
                if (s_Landline_number.equals("")) {
                    IsError = true;
                    edt_Landline_number.requestFocus();
                    Toast.makeText(LandLineActivity.this, "Please Enter Landline Number", Toast.LENGTH_LONG).show();
                } else if (s_tid_number.equals("")) {
                    IsError = true;
                    edt_tid_number.requestFocus();
                    Toast.makeText(LandLineActivity.this, "Please Enter TID Number", Toast.LENGTH_LONG).show();
                }
            }

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

                    Intent intent = new Intent(LandLineActivity.this, ViewBillActivity.class);
                    startActivity(intent);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LandLineActivity.this);
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
                Toast.makeText(LandLineActivity.this, "" + Errormessage, Toast.LENGTH_SHORT).show();
            }
        }

    }

    //============================================end ======================================//
}
