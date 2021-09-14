package com.pay2ved.recharge.gprs_home_activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.pay2ved.recharge.R;
import com.pay2ved.recharge.activity.GprsHomeActivity;
import com.pay2ved.recharge.adapter.ImageArrayAdapter;
import com.pay2ved.recharge.fragments.FragmentDialogCustomerInfo;
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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DthActivity extends AppCompatActivity implements Listener.OnItemSelectedListener, Listener.OnRechargeListener {

    private ProgressDialog pDialog;
    EditText mobile, amount;
    TextView txt_list, txt_remark, plan, rOffer, customer_info;
    Spinner spnr_provider;
    Button ok, cancel;
    ImageView img_back;

    private RelativeLayout headerLayout;
    private LinearLayout mainLayout;
    private FrameLayout frameLayout;

    String s_mobile = "", s_amount = "";
    String s_provider = "", s_Spinner_list = "", s_Title = "", s_remark = "", s_hint1list = "", s_hint2list = "", s_hint3list = "";
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
    public static String s_user_name = "", s_user_code = "", s_user_remark, s_user_hint1 = "", s_user_hint2 = "", s_user_hint3 = "", s_user_icon = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner_gprs);

        pDialog = new ProgressDialog(DthActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        s_Spinner_list = AppsContants.sharedpreferences.getString(AppsContants.Spinner_list, "");
        s_Username = AppsContants.sharedpreferences.getString(AppsContants.Username, "");
        s_Password = AppsContants.sharedpreferences.getString(AppsContants.Password, "");
        s_Title = AppsContants.sharedpreferences.getString(AppsContants.Title, "");

        customer_info = (TextView) findViewById(R.id.customer_info);
        plan = (TextView) findViewById(R.id.plan);
        rOffer = (TextView) findViewById(R.id.r_offer);
        txt_list = (TextView) findViewById(R.id.txt_list);
        txt_remark = (TextView) findViewById(R.id.txt_remark);
        txt_list.setText(s_Title);

        mobile = (EditText) findViewById(R.id.mobile);
        amount = (EditText) findViewById(R.id.amount);
        spnr_provider = (Spinner) findViewById(R.id.spnr_provider);
        cancel = (Button) findViewById(R.id.cancel);
        ok = (Button) findViewById(R.id.ok);
        img_back = (ImageView) findViewById(R.id.img_back);

        headerLayout = findViewById(R.id.header);
        mainLayout = findViewById(R.id.layoutMain);
        frameLayout = findViewById(R.id.frameLayout);

        codelist = s_user_code.split(",");
        namelists = s_user_name.split(",");
        remarklist = s_user_remark.split(",");
        hint1list = s_user_hint1.split(",");
        hint2list = s_user_hint2.split(",");
        hint3list = s_user_hint3.split(",");
        iconlist = s_user_icon.split(",");

        if (s_Spinner_list.equals("DTH")) {
            imageArray = new Integer[]{R.drawable.default_icon, R.drawable.airtel_dth, R.drawable.big_tv, R.drawable.dishtv, R.drawable.sun_tv, R.drawable.tata_sky
                    , R.drawable.videocon_dth, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon
                    , R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon
                    , R.drawable.default_icon};

        } else {
            imageArray = new Integer[]{R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon,
                    R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon
                    , R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon
                    , R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon};
        }

        adapter = new ImageArrayAdapter(this, R.layout.mobile_spinner_layout, codelist, namelists, remarklist
                , hint1list, hint2list, hint3list, iconlist);
        spnr_provider.setAdapter(adapter);
        spnr_provider.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View v, int position, long id) {

                s_provider = codelist[position].toString().trim();

                Log.e("s_provider", s_provider);

                s_remark = remarklist[position].toString().trim();
                // Toast.makeText(DthActivity.this, "" + s_remark, Toast.LENGTH_SHORT).show();

                try {
                    s_hint1list = hint1list[position].toString().trim();
                    s_hint2list = hint2list[position].toString().trim();
                    s_hint3list = hint3list[position].toString().trim();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (s_remark.equals("null") || s_remark.equals("")) {
                    txt_remark.setText("");
                } else {
                    txt_remark.setText(s_remark);
                }

                if (s_hint1list.equals("null")) {
                    mobile.setHint("Enter Customer ID");
                } else if (s_hint1list.equals("")) {
                    mobile.setHint("Validator 1");
                } else {
                    mobile.setHint(s_hint1list);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        onButtonAction();

    }

    private void onButtonAction(){

        // Text Watcher...
        mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ------
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txt_remark.setText( "Number Digit : " + s.length() );
            }

            @Override
            public void afterTextChanged(Editable s) {
                // ------
            }
        });

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

        // Ok : Recharge Button Action........
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                s_mobile = mobile.getText().toString().trim();
                s_amount = amount.getText().toString().trim();

                boolean isError = false;

                if (s_provider.equals("")) {
                    isError = true;
                    Toast.makeText(DthActivity.this, "Please Select Operator", Toast.LENGTH_LONG).show();

                } else if (s_mobile.equals("")) {
                    isError = true;
                    mobile.requestFocus();
                    Toast.makeText(DthActivity.this, "Please Enter Customer ID", Toast.LENGTH_LONG).show();

                } else if (s_amount.equals("")) {
                    isError = true;
                    amount.requestFocus();
                    Toast.makeText(DthActivity.this, "Please Enter Amount", Toast.LENGTH_LONG).show();

                } else {

                    if (!isError) {
                        if (NetConnection.isConnected(DthActivity.this)) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(DthActivity.this);
                            builder.setTitle("Do you want to sure confirm !" + "\n ");
                            builder.setMessage("Customer ID :  " + s_mobile + "\n " + "Operator :  " + s_provider + "\n " + "Amount :  " + s_amount);
                            builder.setCancelable(false);
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @SuppressLint("NewApi")
                                public void onClick(DialogInterface dialog, int which) {
                                    // Query To Get Info...
                                    pDialog.show();
                                    DBQuery.queryToRecharge(DthActivity.this,
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
                            Toast.makeText(DthActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        // Customer Info Button Action...
        customer_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s_mobile = mobile.getText().toString().trim();

                if (s_provider.equals("")) {
                    Toast.makeText(DthActivity.this, "Please Select Operator", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(s_mobile) || s_mobile.length() == 0){
                    mobile.setError("Required field.!");
                    mobile.requestFocus();
                    return;
                }
                showCustomerInfoDialog();
            }
        });

        // Plan Button Click Action...
        plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (s_provider.equals("")) {
                    Toast.makeText(DthActivity.this, "Please Select Operator", Toast.LENGTH_LONG).show();
                    return;
                }

                showDialogDTHPlan();
            }
        });

        // R-Offer Button Click Action...
        rOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s_mobile = mobile.getText().toString().trim();
                if (s_provider.equals("")) {
                    Toast.makeText(DthActivity.this, "Please Select Operator", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(s_mobile) || s_mobile.length() == 0){
                    mobile.setError("Required field.!");
                    mobile.requestFocus();
                    return;
                }
                showOfferDialog();

            }
        });
    }

    // Show Dialog For Customer Info...
    public void showCustomerInfoDialog( ) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment prev = fragmentManager.findFragmentByTag("SHOW_DIALOG");
        if (prev != null) {
            ft.remove(prev);
        }
        //  Assign Required Info...
        FragmentDialogCustomerInfo fragment = new FragmentDialogCustomerInfo( s_Username, s_Password, s_provider, s_mobile );
        fragment.show( fragmentManager, "SHOW_DIALOG");
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
        FragmentDialogROffer fragment = new FragmentDialogROffer( this, s_Username, s_Password, s_provider, s_mobile, false );
        fragment.show( fragmentManager, "SHOW_DIALOG");
    }

    // Show Dialog for DTH Plans....
    boolean isPlanDialogShow = false;
    private void showDialogDTHPlan( ){
        frameLayout.setVisibility(View.VISIBLE);
        headerLayout.setVisibility(View.GONE);
        mainLayout.setVisibility(View.GONE);
        isPlanDialogShow = true;

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add( frameLayout.getId(), new FragmentDialogDTHPlans( s_Username, s_Password,  s_provider, this ) );

        fragmentTransaction.addToBackStack( null );
        fragmentTransaction.commit();
    }

    // On Plan Selected Response....
    @Override
    public void onItemSelected(CallDTHPlans.Amount amountData, String planName) {
        if (amountData != null){
            amount.setText( amountData.getPrice() );
        }
        if (isPlanDialogShow){
            onBackPressed();
        }
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
            AlertDialog.Builder builder = new AlertDialog.Builder(DthActivity.this);
            builder.setMessage(callRecharge.getData().getDetail() + "\n ");
            builder.setCancelable(false);
            builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @SuppressLint("NewApi")
                public void onClick(DialogInterface dialog, int which) {
                    //  dialog.cancel();
                    Intent intent = new Intent(DthActivity.this, GprsHomeActivity.class);
                    startActivity(intent);
                    finishAffinity();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();
        }else if (callRecharge !=null){
            Toast.makeText(DthActivity.this, "" + callRecharge.getMessage() , Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(DthActivity.this, "Something Went Wrong!" , Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        if (isPlanDialogShow){
            frameLayout.setVisibility(View.GONE);
            headerLayout.setVisibility(View.VISIBLE);
            mainLayout.setVisibility(View.VISIBLE);
            isPlanDialogShow = false;
        }
        super.onBackPressed();
    }


    // TODO : Remove This class..========================================start_http==========================================//
    public class Httpjsontask extends AsyncTask<String, Void, String> {

        String Errormessage;
        boolean IsError = false;
        String msg = "", s_data = "";
        String p_status = "", p_detail = "", p_balance = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(DthActivity.this);
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
                Log.v("22", "22" + e.getMessage());
                e.printStackTrace();
            }
            return msg;
        }


        protected void onPostExecute(String result1) {
            super.onPostExecute(result1);
            if (pDialog != null && pDialog.isShowing())
                pDialog.dismiss();

            if (!IsError) {

                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.r_Status, p_status);
                editor.putString(AppsContants.r_Detail, p_detail);
                editor.putString(AppsContants.r_Balance, p_balance);
                editor.commit();

                //============================================
                AlertDialog.Builder builder = new AlertDialog.Builder(DthActivity.this);
                builder.setMessage(p_detail + "\n ");
                builder.setCancelable(false);
                builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @SuppressLint("NewApi")
                    public void onClick(DialogInterface dialog, int which) {
                        //  dialog.cancel();
                        Intent intent = new Intent(DthActivity.this, GprsHomeActivity.class);
                        startActivity(intent);
                        finishAffinity();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

                //==========================================


            } else {
                Toast.makeText(DthActivity.this, "" + Errormessage, Toast.LENGTH_SHORT).show();
            }
        }

    }

    //============================================ end ======================================//


}