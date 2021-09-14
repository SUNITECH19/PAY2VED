package com.pay2ved.recharge.gprs_home_activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pay2ved.recharge.R;
import com.pay2ved.recharge.activity.ContactActivity;
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
import java.util.List;

public class Add_UserActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, Listener.OnObjectQueryListener {

    EditText mobile, business_name, name, email, city, pincode, panno, adhaarno, address;
    Button ok, cancel;
    ImageView img_back, img_contact;
    String s_Password = "", s_Username = "", s_Type = "";
    String s_state = "", s_mobile = "", s_business_name = "", s_name = "", s_email = "", s_city = "", s_pincode = "", s_panno = "", s_adhaarno = "", s_address = "", s_phone = "";
    private ProgressDialog pDialog;
    Spinner spnr_state;
    String[] state = {"Select", "Andaman and Nicobar", "Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chandigarh", "Chhattisgarh"
            , "Dadra and Nagar Haveli"
            , "Daman and Diu", "Delhi", "Goa", "Gujarat", "Haryana", "Himachal Pradesh", "Jammu And Kashmir", "Jharkhand", "Karnataka", "Kerala"
            , "Lakshadweep", "Madhya Pradesh", "Maharashtra", "Manipur", "Meghalaya", "Mizoram", "Nagaland", "Orissa", "Pondicherry", "Punjab"
            , "Rajasthan", "Sikkim", "Tamilnadu", "Tripura", "Uttar Pradesh", "Uttaranchal", " West Bengal"};

    TextView txt_user;
    LinearLayout lnr_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        pDialog = new ProgressDialog(Add_UserActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        s_Username = AppsContants.sharedpreferences.getString(AppsContants.Username, "");
        s_Password = AppsContants.sharedpreferences.getString(AppsContants.Password, "");
        s_Type = AppsContants.sharedpreferences.getString(AppsContants.Type, "");


        txt_user = (TextView) findViewById(R.id.txt_user);
        lnr_view = (LinearLayout) findViewById(R.id.lnr_view);
        if (s_Type.equals("5")) {

            txt_user.setVisibility(View.VISIBLE);
            lnr_view.setVisibility(View.GONE);
        } else {
            lnr_view.setVisibility(View.VISIBLE);
            txt_user.setVisibility(View.GONE);
        }

        spnr_state = (Spinner) findViewById(R.id.spnr_state);
        mobile = (EditText) findViewById(R.id.mobile);
        business_name = (EditText) findViewById(R.id.business_name);
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        city = (EditText) findViewById(R.id.city);
        pincode = (EditText) findViewById(R.id.pincode);
        panno = (EditText) findViewById(R.id.panno);
        adhaarno = (EditText) findViewById(R.id.adhaarno);
        address = (EditText) findViewById(R.id.address);
        cancel = (Button) findViewById(R.id.cancel);
        ok = (Button) findViewById(R.id.ok);
        img_contact = (ImageView) findViewById(R.id.img_contact);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        spnr_state.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, state);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnr_state.setAdapter(aa);

        img_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Add_UserActivity.this, ContactActivity.class);
                startActivity(intent);
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

                s_mobile = mobile.getText().toString().trim();
                s_business_name = business_name.getText().toString().trim();
                s_name = name.getText().toString().trim();
                s_email = email.getText().toString().trim();
                s_city = city.getText().toString().trim();
                s_pincode = pincode.getText().toString().trim();
                s_panno = panno.getText().toString().trim();
                s_adhaarno = adhaarno.getText().toString().trim();
                s_address = address.getText().toString().trim();

                boolean isError = false;

                if (s_mobile.equals("")) {
                    isError = true;
                    mobile.requestFocus();
                    Toast.makeText(Add_UserActivity.this, "Please Enter Mobile Number", Toast.LENGTH_LONG).show();

                } else if (s_mobile.length() < 10) {
                    isError = true;
                    mobile.requestFocus();
                    Toast.makeText(Add_UserActivity.this, "Invalid Number", Toast.LENGTH_LONG).show();

                } else if (s_business_name.equals("")) {
                    isError = true;
                    business_name.requestFocus();
                    Toast.makeText(Add_UserActivity.this, "Please Enter Business Name", Toast.LENGTH_LONG).show();

                } else if (s_name.equals("")) {
                    isError = true;
                    name.requestFocus();
                    Toast.makeText(Add_UserActivity.this, "Please Enter Name", Toast.LENGTH_LONG).show();

                } else if (s_email.equals("")) {
                    isError = true;
                    email.requestFocus();
                    Toast.makeText(Add_UserActivity.this, "Please Enter Email", Toast.LENGTH_LONG).show();

                } else if (s_city.equals("")) {
                    isError = true;
                    city.requestFocus();
                    Toast.makeText(Add_UserActivity.this, "Please Enter City", Toast.LENGTH_LONG).show();

                } else if (s_state.equals("Select")) {
                    isError = true;
                    Toast.makeText(Add_UserActivity.this, "Please Select State", Toast.LENGTH_LONG).show();

                } else if (s_pincode.equals("")) {
                    isError = true;
                    pincode.requestFocus();
                    Toast.makeText(Add_UserActivity.this, "Please Enter Pincode", Toast.LENGTH_LONG).show();

                } else if (s_panno.equals("")) {
                    isError = true;
                    panno.requestFocus();
                    Toast.makeText(Add_UserActivity.this, "Please Enter Pan No", Toast.LENGTH_LONG).show();

                } else if (s_adhaarno.equals("")) {
                    isError = true;
                    adhaarno.requestFocus();
                    Toast.makeText(Add_UserActivity.this, "Please Enter Aadhaar No", Toast.LENGTH_LONG).show();

                } else if (s_address.equals("")) {
                    isError = true;
                    address.requestFocus();
                    Toast.makeText(Add_UserActivity.this, "Please Enter Address", Toast.LENGTH_LONG).show();

                } else {
                    if (!isError) {
                        if (NetConnection.isConnected(Add_UserActivity.this)) {
                            pDialog.show();
                            DBQuery.queryToAddUser(
                                    Add_UserActivity.this, s_Username, s_Password, s_mobile,s_business_name, s_name, s_email, s_city, s_state,
                                    s_pincode, s_panno, s_adhaarno, s_address
                            );

//                            Httpjsontask task = new Httpjsontask();
//                            task.execute();
                        } else {
                            Toast.makeText(Add_UserActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        s_state = state[position].toString().trim();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onResponse(@Nullable CallObject callObject) {
        pDialog.dismiss();
        if ( callObject != null ) {

            AlertDialog.Builder builder = new AlertDialog.Builder(Add_UserActivity.this);
            builder.setMessage("" + callObject.getMessage() );
            builder.setCancelable(false);
            builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @SuppressLint("NewApi")
                public void onClick(DialogInterface dialog, int which) {

                    Intent intent = new Intent(Add_UserActivity.this, GprsHomeActivity.class);
                    startActivity(intent);
                    finishAffinity();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();

        } else {
            Toast.makeText(Add_UserActivity.this, "Something Went Wrong!", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onResume() {

        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        s_phone = AppsContants.sharedpreferences.getString(AppsContants.Phone, "");

        mobile.setText(s_phone);
        super.onResume();
    }

    // TODO: Remove this Class ========================================start_http==========================================/
    public class Httpjsontask extends AsyncTask<String, Void, String> {

        String Errormessage;
        boolean IsError = false;
        String msg = "", s_data = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Add_UserActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... arg0) {

            HttpClient htppclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(HttpUrlPath.urlPath + HttpUrlPath.action_user_add);

            try {
                List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

                nameValuePair.add(new BasicNameValuePair("username", s_Username));
                nameValuePair.add(new BasicNameValuePair("password", s_Password));
                nameValuePair.add(new BasicNameValuePair("mobile", s_mobile));
                nameValuePair.add(new BasicNameValuePair("business_name", s_business_name));
                nameValuePair.add(new BasicNameValuePair("name", s_name));
                nameValuePair.add(new BasicNameValuePair("email", s_email));
                nameValuePair.add(new BasicNameValuePair("city", s_city));
                nameValuePair.add(new BasicNameValuePair("state", s_state));
                nameValuePair.add(new BasicNameValuePair("pincode", s_pincode));
                nameValuePair.add(new BasicNameValuePair("pan", s_panno));
                nameValuePair.add(new BasicNameValuePair("aadhaar", s_adhaarno));
                nameValuePair.add(new BasicNameValuePair("address", s_address));

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

                AlertDialog.Builder builder = new AlertDialog.Builder(Add_UserActivity.this);
                builder.setMessage("" + Errormessage);
                builder.setCancelable(false);
                builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @SuppressLint("NewApi")
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(Add_UserActivity.this, GprsHomeActivity.class);
                        startActivity(intent);
                        finishAffinity();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

            } else {
                Toast.makeText(Add_UserActivity.this, "" + Errormessage, Toast.LENGTH_SHORT).show();
            }
        }

    }

}
