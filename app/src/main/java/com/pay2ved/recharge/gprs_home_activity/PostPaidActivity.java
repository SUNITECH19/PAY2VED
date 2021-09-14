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
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.pay2ved.recharge.R;
import com.pay2ved.recharge.activity.ContactActivity;
import com.pay2ved.recharge.activity.GprsHomeActivity;
import com.pay2ved.recharge.adapter.ImageArrayAdapter;
import com.pay2ved.recharge.helper.Listener;
import com.pay2ved.recharge.other.AppsContants;
import com.pay2ved.recharge.other.HttpUrlPath;
import com.pay2ved.recharge.other.NetConnection;
import com.pay2ved.recharge.service.serverquery.PayBillQuery;

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

public class PostPaidActivity extends AppCompatActivity implements Listener.OnPaymentListener{

    private ProgressDialog pDialog;
    EditText mobile, amount;
    TextView txt_list, txt_remark;
    Spinner spnr_provider;
    Button btn_View_Bill, btn_pay_bill;
    ImageView img_back, img_contact;

    // Check Details...
    private LinearLayout layoutCustomerDetails;
    private TextView textCustomerName, textBillNumber, textBillDate, textDueDate, textBillAmount, textDueAmount;
    private LinearLayout layoutCustomerDetails2;
    private TextView textCustomerDetail;

    String s_mobile = "", s_amount = "", s_remark = "", s_hint1list = "", s_hint2list = "", s_hint3list = "";
    String s_provider = "", s_phone = "", s_Spinner_list = "", s_Title = "";
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
    public static String s_user_name = "", s_user_code = "", s_user_remark = "", s_user_hint1 = "", s_user_hint2 = "", s_user_hint3 = "", s_user_icon = "";
    String s_id = "", s_operator_code = "", s_circle_code = "";
    String MobileFirst4Char = "";

    // Response Model...
    private ActivityBillPayments.ResponseModel responseModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postpaid);

        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        s_Spinner_list = AppsContants.sharedpreferences.getString(AppsContants.Spinner_list, "");
        s_Username = AppsContants.sharedpreferences.getString(AppsContants.Username, "");
        s_Password = AppsContants.sharedpreferences.getString(AppsContants.Password, "");
        s_Title = AppsContants.sharedpreferences.getString(AppsContants.Title, "");

        txt_list = (TextView) findViewById(R.id.txt_list);
        txt_remark = (TextView) findViewById(R.id.txt_remark);
        txt_list.setText(s_Title);

        mobile = (EditText) findViewById(R.id.mobile);
        amount = (EditText) findViewById(R.id.amount);
        spnr_provider = (Spinner) findViewById(R.id.spnr_provider);
        btn_pay_bill = (Button) findViewById(R.id.btn_pay_bill);
        btn_View_Bill = (Button) findViewById(R.id.btn_View_Bill);
        img_contact = (ImageView) findViewById(R.id.img_contact);
        img_back = (ImageView) findViewById(R.id.img_back);

        layoutCustomerDetails = findViewById(R.id.layoutCustomerDetails);
        layoutCustomerDetails2 = findViewById(R.id.layoutCustomerDetails2);
        textCustomerDetail = findViewById(R.id.textCustomerDetail);
        textCustomerName = findViewById(R.id.textCustomerName);
        textBillNumber = findViewById(R.id.textBillNumber);
        textBillDate = findViewById(R.id.textBillDate);
        textDueDate = findViewById(R.id.textDueDate);
        textBillAmount = findViewById(R.id.textBillAmount);
        textDueAmount = findViewById(R.id.textDueAmount);

        codelist = s_user_code.split(",");
        namelists = s_user_name.split(",");
        remarklist = s_user_remark.split(",");
        hint1list = s_user_hint1.split(",");
        hint2list = s_user_hint2.split(",");
        hint3list = s_user_hint3.split(",");
        iconlist = s_user_icon.split(",");

        if (s_Spinner_list.equals("POSTPAID")) {
            imageArray = new Integer[]{R.drawable.default_icon, R.drawable.aircel, R.drawable.airtel, R.drawable.bsnl, R.drawable.idea, R.drawable.jio,
                    R.drawable.reliance, R.drawable.reliance, R.drawable.tata_docomo, R.drawable.tata_indicom, R.drawable.vodafone,
                    R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon
                    , R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon
                    , R.drawable.default_icon};

        } else {
            imageArray = new Integer[]{R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon,
                    R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon
                    , R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon
                    , R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon, R.drawable.default_icon};
        }

        adapter = new ImageArrayAdapter(this, R.layout.mobile_spinner_layout, codelist, namelists, remarklist
                , hint1list, hint2list, hint3list, iconlist);
        spnr_provider.setAdapter(adapter);

        spnr_provider.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View v, int position, long id) {

                s_provider = codelist[position].toString().trim();


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
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
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
                            }
                        }

                        //==================================================

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        //=======================================================


        onButtonAction();

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
                Intent intent = new Intent(PostPaidActivity.this, ContactActivity.class);
                startActivity(intent);
            }
        });
        btn_pay_bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s_amount = amount.getText().toString().trim();
                if (s_amount.equals("")) {
                    amount.setError("Required field.!");
                    amount.requestFocus();
                    return;
                }

                showDialog();
                hideKeyboard();

                // Query To Process...
                PayBillQuery billQuery;
                billQuery = new PayBillQuery(
                        s_Username, s_Password, s_provider,  s_mobile, s_amount,
                        responseModel.customer_name, responseModel.bill_number, responseModel.bill_date, responseModel.due_date, responseModel.due_amount,
                        "", "", ""
                );

                billQuery.setListener( PostPaidActivity.this );

                billQuery.execute();
            }
        });

        btn_View_Bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                s_mobile = mobile.getText().toString().trim();

                if (s_mobile.equals("")) {
                    mobile.requestFocus();
                    mobile.setError("Please Enter Mobile No");
                    return;
                } else if (s_mobile.length() < 10) {
                    mobile.requestFocus();
                    mobile.setError("Invalid Number");
                    return;
                } else if (s_provider.equals("") || s_provider.equals("null")) {
                    Toast.makeText(PostPaidActivity.this, "Please Select Operator", Toast.LENGTH_LONG).show();
                    return;
                }

                if (NetConnection.isConnected(PostPaidActivity.this)) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(PostPaidActivity.this);
                    builder.setTitle("Do you want to sure confirm !" + "\n ");
                    builder.setMessage("Mobile No. :  " + s_mobile + "\n " + "Operator :  " + s_provider + "\n " + "Amount :  " + s_amount);
                    builder.setCancelable(false);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @SuppressLint("NewApi")
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            showDialog();
                            Httpjsontask task = new Httpjsontask();
                            task.execute();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @SuppressLint("NewApi")
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();

                    //====================================================
                } else {
                    Toast.makeText(PostPaidActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void showDialog(){
        if (pDialog == null ){
            pDialog = new ProgressDialog( this );
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
        }
        pDialog.show();
    }
    private void dismissDialog(){
        if (pDialog!=null && pDialog.isShowing()){
            pDialog.dismiss();
        }
    }

    private void hideKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager)this.getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null && getCurrentFocus().getApplicationWindowToken() != null){
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(),0);
        }
    }

    private void setData(){
        // Change UI...
        btn_View_Bill.setVisibility(View.GONE);
        btn_pay_bill.setVisibility( View.VISIBLE );
        amount.setVisibility( View.VISIBLE );

        layoutCustomerDetails.setVisibility(View.VISIBLE);

        textCustomerName.setText( responseModel.customer_name );
        textBillNumber.setText( responseModel.bill_number );
        textBillDate.setText( responseModel.bill_date );
        textDueDate.setText( responseModel.due_date );
        textBillAmount.setText(  "₹" +responseModel.bill_amount );
        textDueAmount.setText(  "₹" +responseModel.due_amount );

        if (responseModel.partial_payment.equals("n")){
            amount.setClickable( false );
            amount.setFocusable( false );
            amount.clearFocus();
        }else{
            amount.setClickable( true );
            amount.setFocusable( true );
        }

        amount.setText( responseModel.bill_amount );

    }

    private void allowToNextStep(){
        btn_View_Bill.setVisibility(View.GONE);
        btn_pay_bill.setVisibility( View.VISIBLE );
        amount.setVisibility( View.VISIBLE );

        // Set Window If Response Code = 2...
        layoutCustomerDetails2.setVisibility(View.VISIBLE);
        textCustomerDetail.setText( responseModel.msg );

        amount.requestFocus();
    }

    @Override
    public void onPaymentResponse(PayBillQuery.BillResponse billResponse) {
        dismissDialog();

        String responseMsg;
        if ( billResponse != null){
            responseMsg = billResponse.msg;

            AlertDialog.Builder builder = new AlertDialog.Builder(PostPaidActivity.this);
            builder.setTitle( responseMsg + "\n ");

            builder.setMessage("Number/ID :  " + billResponse.number + "\n " + "Operator :  " + billResponse.operator + "\n "
                    + "Amount :  " + billResponse.amount + "\n "
                    + "TXT NO. :  " + billResponse.txn_no + "\n "
                    + "Ref. Number :  " + billResponse.operator_ref_no + "\n "
            );

            builder.setCancelable(false);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @SuppressLint("NewApi")
                public void onClick(DialogInterface dialog, int which) {
                    if (billResponse.status == 0){
                        // Success..
                        dialog.dismiss();
                        PostPaidActivity.this.finish();
                    }else{
                        dialog.dismiss();
                    }
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    //========================================start_http==========================================/
    public class Httpjsontask extends AsyncTask<String, Void, String> {

        int resCode = -1;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            responseModel = new ActivityBillPayments.ResponseModel();
        }

        protected String doInBackground(String... arg0) {
            String errorMsg = null;

            HttpClient htppclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(HttpUrlPath.urlPath + HttpUrlPath.action_info_postpaid );

            try {
                List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

                nameValuePair.add(new BasicNameValuePair("username", s_Username));
                nameValuePair.add(new BasicNameValuePair("password", s_Password));
                nameValuePair.add(new BasicNameValuePair("operator", s_provider));
                nameValuePair.add(new BasicNameValuePair("number", s_mobile));

//                nameValuePair.add(new BasicNameValuePair("service", s_Spinner_list));
//                nameValuePair.add(new BasicNameValuePair("amount", s_amount));

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePair, "UTF-8"));
                HttpResponse response = htppclient.execute(httppost);
                String object = EntityUtils.toString(response.getEntity());
                System.out.println("#####object registration=" + object);

                JSONObject js = new JSONObject(object);

                resCode = js.getInt( "status");
                responseModel.msg = js.getString( "msg" );
                responseModel.operator = js.getString( "operator" );

                if (resCode == 0 && js.has("info")){
                    // Success...
                    JSONObject infoData = js.getJSONObject( "info" );
                    responseModel.customer_name = infoData.getString("customer_name");
                    responseModel.bill_number = infoData.getString("bill_number");
                    responseModel.bill_date = infoData.getString("bill_date");
                    responseModel.due_date = infoData.getString("due_date");
                    responseModel.bill_amount = infoData.getString("bill_amount");
                    responseModel.due_amount = infoData.getString("due_amount");
                    responseModel.partial_payment = infoData.getString("partial_payment");

                    responseModel.customer_mobile = infoData.has("customer_mobile") ? infoData.getString("customer_mobile") : "";

                }else{
                    // Failed...

                }
            } catch (Exception e) {
                Log.d("PostPaidActivity", "Error: " + e.getMessage());
                errorMsg = e.getMessage();
                e.printStackTrace();
            }
            return errorMsg;
        }

        protected void onPostExecute(String result1) {
            super.onPostExecute(result1);
            dismissDialog();
            if (resCode != 0){
                // Show Error...
                if (resCode == 2){ /// Allow User To Input Amount And  Proceed To Payment...
                    allowToNextStep();
                }else {
                    Snackbar.make( mobile.getRootView(), responseModel.msg + ( result1 !=null? " Error : " +result1 :  "" ), Snackbar.LENGTH_SHORT ).show();
                }
            }else{
                // Set UI Data..
                setData();
            }
        }

    }

//============================================end ======================================//

    @Override
    protected void onResume() {

        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        s_phone = AppsContants.sharedpreferences.getString(AppsContants.Phone, "");

        mobile.setText(s_phone);

        //=================================================
        if (!s_phone.equals("")) {
            String first4char = s_phone.substring(0, 4);
            MobileFirst4Char = String.valueOf(Integer.parseInt(first4char));
            // Toast.makeText(this, "" + MobileFirst4Char, Toast.LENGTH_SHORT).show();
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

}