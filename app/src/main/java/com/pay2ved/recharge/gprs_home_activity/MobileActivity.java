package com.pay2ved.recharge.gprs_home_activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.os.Bundle;
import android.text.Editable;

import android.text.TextWatcher;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.pay2ved.recharge.R;
import com.pay2ved.recharge.activity.ContactActivity;
import com.pay2ved.recharge.activity.GprsHomeActivity;
import com.pay2ved.recharge.activity.PlansActivity;
import com.pay2ved.recharge.adapter.CircleArrayAdapter;
import com.pay2ved.recharge.adapter.ImageArrayAdapter;
import com.pay2ved.recharge.fragments.FragmentDialogDTHPlans;
import com.pay2ved.recharge.fragments.FragmentDialogROffer;
import com.pay2ved.recharge.helper.Listener;
import com.pay2ved.recharge.other.AppsContants;
import com.pay2ved.recharge.other.HttpUrlPath;
import com.pay2ved.recharge.other.NetConnection;
import com.pay2ved.recharge.service.callmodel.CallDTHPlans;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MobileActivity extends AppCompatActivity implements Listener.OnItemSelectedListener, Listener.OnRechargeListener {

    private ProgressDialog pDialog;
    EditText amount, mobile;
    TextView txt_list, txt_remark, plan, rOffer;
    Spinner spnr_provider, spnr_circle;
    Button ok;
    ImageView img_back, img_contact;
    String s_mobile = "", s_circle = "", s_amount = "", s_provider = "", s_phone = "", s_remark = "", s_hint1list = "", s_hint2list = "", s_hint3list = "";
    public static String s_user_name = "", s_user_code = "", s_user_remark = "", s_user_hint1 = "", s_user_hint2 = "", s_user_hint3 = "", s_user_icon = "", s_Spinner_list = "", s_Title = "";
    String[] namelists;
    String[] codelist;
    Integer[] imageArray;
    List<String> circle_name;
    List<String> circle_code;
    String[] remarklist;
    String[] hint1list;
    String[] hint2list;
    String[] hint3list;
    String[] iconlist;
    List<String> circle_id;
    CircleArrayAdapter c_adapter;
    ImageArrayAdapter adapter;
    String s_Username = "", s_Password = "";
    String s_id = "", s_operator_code = "", s_circle_code = "", ss_amount = "";
    private static final int RESULT_PICK_CONTACT = 0;
    String MobileFirst4Char = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile__dth);

        pDialog = new ProgressDialog(MobileActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        s_Spinner_list = AppsContants.sharedpreferences.getString(AppsContants.Spinner_list, "");
        s_Username = AppsContants.sharedpreferences.getString(AppsContants.Username, "");
        s_Password = AppsContants.sharedpreferences.getString(AppsContants.Password, "");
        s_Title = AppsContants.sharedpreferences.getString(AppsContants.Title, "");


        System.out.println( "s_Username : " + s_Username );
        System.out.println( "s_Password : " + s_Password );
//        System.out.println( "s_Operator_code : " + s_Operator_code );
//        System.out.println( "s_Circle_code : " + s_Circle_code );

        plan = (TextView) findViewById(R.id.plan);
        rOffer = (TextView) findViewById(R.id.r_offer);
        txt_list = (TextView) findViewById(R.id.txt_list);
        txt_remark = (TextView) findViewById(R.id.txt_remark);
        txt_list.setText(s_Title);

        spnr_circle = (Spinner) findViewById(R.id.spnr_circle);
        mobile = (EditText) findViewById(R.id.mobile);
        amount = (EditText) findViewById(R.id.amount);
        spnr_provider = (Spinner) findViewById(R.id.spnr_provider);

        ok = (Button) findViewById(R.id.ok);

        img_contact = (ImageView) findViewById(R.id.img_contact);
        img_back = (ImageView) findViewById(R.id.img_back);


        codelist = s_user_code.split(",");
        namelists = s_user_name.split(",");
        remarklist = s_user_remark.split(",");
        hint1list = s_user_hint1.split(",");
        hint2list = s_user_hint2.split(",");
        hint3list = s_user_hint3.split(",");
        iconlist = s_user_icon.split(",");

        //=======================================================
        initData();

        onButtonAction();

    }

    private void initData(){

        if (s_Spinner_list.equals("MOBILE")) {

            imageArray = new Integer[]{R.drawable.default_icon, R.drawable.aircel, R.drawable.airtel,
                    R.drawable.bsnl, R.drawable.bsnl, R.drawable.idea, R.drawable.jio, R.drawable.jio, R.drawable.mtnl, R.drawable.mtnl
                    , R.drawable.mts, R.drawable.reliance, R.drawable.tata_docomo, R.drawable.tata_docomo, R.drawable.tata_indicom
                    , R.drawable.uninor, R.drawable.uninor, R.drawable.videocon, R.drawable.videocon, R.drawable.virgin, R.drawable.virgin
                    , R.drawable.vodafone, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon
                    , R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon
                    , R.drawable.default_icon};

        } else if (s_Spinner_list.equals("DATACARD")) {
            imageArray = new Integer[]{R.drawable.default_icon, R.drawable.mts, R.drawable.reliance, R.drawable.tata_docomo,
                    R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon
                    , R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon
                    , R.drawable.default_icon};

        } else {
            imageArray = new Integer[]{R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon,
                    R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon
                    , R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon
                    , R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon
                    , R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon};
        }

        adapter = new ImageArrayAdapter(this, R.layout.mobile_spinner_layout, codelist, namelists, remarklist
                , hint1list, hint2list, hint3list, iconlist);
        spnr_provider.setAdapter(adapter);

        spnr_provider.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                s_provider = codelist[position].toString().trim();
                // Toast.makeText(MobileActivity.this, "" + position, Toast.LENGTH_SHORT).show();

                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.Operator_code, s_provider);
                editor.commit();

                s_remark = remarklist[position].toString().trim();
                // Toast.makeText(DthActivity.this, "" + s_remark, Toast.LENGTH_SHORT).show();

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
                    mobile.setHint("Enter Mobile Number");
                } else if (s_hint1list.equals("")) {
                    mobile.setHint("Validator 1");
                } else {
                    mobile.setHint(s_hint1list);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnr_circle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                s_circle = circle_code.get(position).toString().trim();
                // Toast.makeText(Mobile_DataCardActivity.this, "" + s_circle, Toast.LENGTH_SHORT).show();

                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.Circle_code, s_circle);
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //=======================================================
        circle_name = new ArrayList();
        circle_code = new ArrayList();
        circle_id = new ArrayList();
        // Reading json file from assets folder
        final StringBuffer sb = new StringBuffer();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(getAssets().open(
                    "circle.json")));
            String temp;
            while ((temp = br.readLine()) != null)
                sb.append(temp);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close(); // stop reading
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String jsonstring = sb.toString();
        try {
            JSONObject jsonObjMain = new JSONObject(jsonstring);
            JSONArray jsonArray = jsonObjMain.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                String c_id = jsonObj.getString("id");
                String c_name = jsonObj.getString("name");
                String c_circle_code = jsonObj.getString("code");

                circle_name.add(c_name);
                circle_code.add(c_circle_code);
                circle_id.add(c_id);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (circle_name.size() > 0) {

            c_adapter = new CircleArrayAdapter(this, R.layout.circle_spinner_layout, circle_code, circle_id, circle_name);
            spnr_circle.setAdapter(c_adapter);

        }

        mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txt_remark.setText( "Number Digit : " + s.length() );
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // Toast.makeText(Mobile_DataCardActivity.this, "" + mobile.getText(), Toast.LENGTH_SHORT).show();

                //=================================================


                StringBuffer sb_pre = new StringBuffer();
                BufferedReader bufer = null;
                try {
                    bufer = new BufferedReader(new InputStreamReader(getAssets().open(
                            "prefix.json")));
                    String temp;
                    while ((temp = bufer.readLine()) != null)
                        sb_pre.append(temp);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        bufer.close(); // stop reading
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


                String json_pr = sb_pre.toString();
                try {
                    JSONObject jsonObjMain = new JSONObject(json_pr);
                    JSONArray jsonArray = jsonObjMain.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObj = jsonArray.getJSONObject(i);
                        s_id = jsonObj.getString("prefix");
                        s_operator_code = jsonObj.getString("operator_code");
                        s_circle_code = jsonObj.getString("circle_code");

                        //===================================================

                        if (mobile.getText().toString().equals(s_id)) {

                            if (s_operator_code != null) {
                                int O_Position = adapter.getPosition(s_operator_code);
                                spnr_provider.setSelection(O_Position);
                                // Toast.makeText(Mobile_DataCardActivity.this, "" + O_Position, Toast.LENGTH_SHORT).show();
                            }

                            if (s_circle_code != null) {
                                int C_Position = c_adapter.getPosition(s_circle_code);
                                spnr_circle.setSelection(C_Position);
                                // Toast.makeText(Mobile_DataCardActivity.this, "" + C_Position, Toast.LENGTH_SHORT).show();
                            }
                        }

                        //==================================================

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

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

        img_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MobileActivity.this, ContactActivity.class);
                startActivity(intent);

               /* Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(contactPickerIntent, RESULT_PICK_CONTACT);*/
            }
        });

        plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s_mobile = mobile.getText().toString().trim();
                if (s_mobile.equals("")) {
                    mobile.requestFocus();
                    Toast.makeText(MobileActivity.this, "Please Enter Mobile No", Toast.LENGTH_LONG).show();

                } else if (s_provider.equals("")) {
                    Toast.makeText(MobileActivity.this, "Please Select Operator", Toast.LENGTH_LONG).show();

                } else if (s_circle.equals("")) {
                    Toast.makeText(MobileActivity.this, "Please Select Circle", Toast.LENGTH_LONG).show();

                } else {
                    AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                    editor.putString(AppsContants.Phone, s_mobile);
                    editor.commit();

                    Intent intent = new Intent(MobileActivity.this, PlansActivity.class);
                    startActivity(intent);
                }
            }
        });

        rOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO : Request to Get Data...
                s_mobile = mobile.getText().toString().trim();
                if (s_mobile.equals("")) {
                    mobile.requestFocus();
                    Toast.makeText(MobileActivity.this, "Please Enter Mobile No", Toast.LENGTH_LONG).show();

                } else if (s_provider.equals("")) {
                    Toast.makeText(MobileActivity.this, "Please Select Operator", Toast.LENGTH_LONG).show();

                } else if (s_circle.equals("")) {
                    Toast.makeText(MobileActivity.this, "Please Select Circle", Toast.LENGTH_LONG).show();

                }else{
                    showOfferDialog();
                }
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                s_mobile = mobile.getText().toString().trim();
                s_amount = amount.getText().toString().trim();

                boolean isError = false;

                if (s_mobile.equals("")) {
                    isError = true;
                    mobile.requestFocus();
                    Toast.makeText(MobileActivity.this, "Please Enter Mobile No", Toast.LENGTH_LONG).show();

                } else if (s_mobile.length() < 10) {
                    isError = true;
                    mobile.requestFocus();
                    Toast.makeText(MobileActivity.this, "Invalid Number", Toast.LENGTH_LONG).show();

                } else if (s_mobile.length() > 10) {
                    isError = true;
                    mobile.requestFocus();
                    Toast.makeText(MobileActivity.this, "Invalid Number", Toast.LENGTH_LONG).show();

                } else if (s_provider.equals("null")) {
                    isError = true;
                    Toast.makeText(MobileActivity.this, "Please Select Operator", Toast.LENGTH_LONG).show();

                } else if (s_circle.equals("null")) {
                    isError = true;
                    Toast.makeText(MobileActivity.this, "Please Select Circle", Toast.LENGTH_LONG).show();

                } else if (s_amount.equals("")) {
                    isError = true;
                    amount.requestFocus();
                    Toast.makeText(MobileActivity.this, "Please Enter Amount", Toast.LENGTH_LONG).show();

                } else {
                    if (!isError) {
                        if (NetConnection.isConnected(MobileActivity.this)) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(MobileActivity.this);
                            builder.setTitle("Do you want to sure confirm !" + "\n ");
                            builder.setMessage("Mobile No. :  " + s_mobile + "\n " + "Operator :  " + s_provider + "\n " + "Amount :  " + s_amount);
                            builder.setCancelable(false);
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @SuppressLint("NewApi")
                                public void onClick(DialogInterface dialog, int which) {
                                    // Query To Get Info...
                                    pDialog.show();
                                    DBQuery.queryToRecharge(MobileActivity.this,
                                            s_Username, s_Password, s_provider, s_mobile, s_Spinner_list, null, s_amount );

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
                            Toast.makeText(MobileActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        }

                    }

                }
            }
        });

    }

    // Show Dialog For Show ROffer Info...
    public void showOfferDialog( ) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment prev = fragmentManager.findFragmentByTag("SHOW_DIALOG");
        if (prev != null) {
            ft.remove(prev);
        }
        //  Assign Required Info...
        FragmentDialogROffer fragment = new FragmentDialogROffer( this, s_Username, s_Password, s_provider, s_mobile, true );
        fragment.show( fragmentManager, "SHOW_DIALOG");
    }

    @Override
    public void onItemSelected(CallDTHPlans.Amount amountData, String planName) {
        if (amountData != null)
            amount.setText( amountData.getPrice() );
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


            AlertDialog.Builder builder = new AlertDialog.Builder(MobileActivity.this);
            builder.setMessage(callRecharge.getData().getDetail() + "\n ");
            // builder.setMessage("Mobile No. " + s_mobile + "\n " + "Operator " + s_provider + "\n " + "Amount " + s_amount);
            builder.setCancelable(false);
            builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @SuppressLint("NewApi")
                public void onClick(DialogInterface dialog, int which) {
                    //  dialog.cancel();
                    Intent intent = new Intent(MobileActivity.this, GprsHomeActivity.class);
                    startActivity(intent);
                    finishAffinity();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();
        }else if (callRecharge !=null){
            Toast.makeText(MobileActivity.this, "" + callRecharge.getMessage() , Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MobileActivity.this, "Something Went Wrong!" , Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RESULT_PICK_CONTACT:
                    contactPicked(data);
                    break;
            }
        } else {
            Log.e("Activity", "Failed to pick contact");
        }
    }

    private void contactPicked(Intent data) {
        Cursor cursor = null;
        try {
            String phoneNo = null;
            String name = null;

            Uri uri = data.getData();
            cursor = getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();
            int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            phoneNo = cursor.getString(phoneIndex);
            name = cursor.getString(nameIndex);

            //  mobile.setText(phoneNo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {

        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        ss_amount = AppsContants.sharedpreferences.getString(AppsContants.amount, "");
        s_phone = AppsContants.sharedpreferences.getString(AppsContants.Phone, "");

        amount.setText(ss_amount);
        mobile.setText(s_phone);

        //=================================================
        if (!s_phone.equals("")) {
            String first4char = s_phone.substring(0, 4);
            MobileFirst4Char = String.valueOf(Integer.parseInt(first4char));
            //Toast.makeText(this, "" + MobileFirst4Char, Toast.LENGTH_SHORT).show();
        }

        StringBuffer sb_pre = new StringBuffer();
        BufferedReader bufer = null;
        try {
            bufer = new BufferedReader(new InputStreamReader(getAssets().open(
                    "prefix.json")));
            String temp;
            while ((temp = bufer.readLine()) != null)
                sb_pre.append(temp);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufer.close(); // stop reading
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        String json_pr = sb_pre.toString();
        try {
            JSONObject jsonObjMain = new JSONObject(json_pr);
            JSONArray jsonArray = jsonObjMain.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                s_id = jsonObj.getString("prefix");
                s_operator_code = jsonObj.getString("operator_code");
                s_circle_code = jsonObj.getString("circle_code");

                //===================================================

                if (!MobileFirst4Char.equals("")) {

                    if (MobileFirst4Char.equals(s_id)) {

                        if (s_operator_code != null) {
                            int O_Position = adapter.getPosition(s_operator_code);
                            spnr_provider.setSelection(O_Position);
                            // Toast.makeText(Mobile_DataCardActivity.this, "" + O_Position, Toast.LENGTH_SHORT).show();
                        }

                        if (s_circle_code != null) {
                            int C_Position = c_adapter.getPosition(s_circle_code);
                            spnr_circle.setSelection(C_Position);
                            // Toast.makeText(Mobile_DataCardActivity.this, "" + C_Position, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        //=====================================================

        super.onResume();
    }


    /////// Update Code : On 30-05-2021 ------------------------------------------------------------------------------

// TODO : Remove this Class========================================start_http==========================================/
    public class Httpjsontask extends AsyncTask<String, Void, String> {

        String Errormessage;
        boolean IsError = false;
        String msg = "", s_data = "";
        String p_status = "", p_detail = "", p_balance = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MobileActivity.this);
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
                nameValuePair.add(new BasicNameValuePair("number", s_mobile));
                nameValuePair.add(new BasicNameValuePair("service", s_Spinner_list));
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
                Log.v("MobileActivity", "22" + e.getMessage());
                e.printStackTrace();
            }
            return msg;
        }


        @SuppressLint("ResourceAsColor")
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


                AlertDialog.Builder builder = new AlertDialog.Builder(MobileActivity.this);
                builder.setMessage(p_detail + "\n ");
                // builder.setMessage("Mobile No. " + s_mobile + "\n " + "Operator " + s_provider + "\n " + "Amount " + s_amount);
                builder.setCancelable(false);
                builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @SuppressLint("NewApi")
                    public void onClick(DialogInterface dialog, int which) {
                        //  dialog.cancel();
                        Intent intent = new Intent(MobileActivity.this, GprsHomeActivity.class);
                        startActivity(intent);
                        finishAffinity();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

            } else {
                Toast.makeText(MobileActivity.this, "" + Errormessage, Toast.LENGTH_SHORT).show();
            }
        }

    }

//============================================end ======================================//


}