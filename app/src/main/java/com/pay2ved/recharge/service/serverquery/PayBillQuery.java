package com.pay2ved.recharge.service.serverquery;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.pay2ved.recharge.activity.GprsHomeActivity;
import com.pay2ved.recharge.activity.ViewBillActivity;
import com.pay2ved.recharge.helper.Listener;
import com.pay2ved.recharge.other.HttpUrlPath;

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

public class PayBillQuery extends AsyncTask<String, Void, String> {


    private String username;
    private String password;
    private String operator; // Bill payment operator
    private String number; // Mobile/Landline/Customer Account etc.
    private String amount; // Received by API (bill_amount), In case of not received then input manually

    private String consumer_name;
    private String bill_number;
    private String bill_date;
    private String due_date;
    private String due_amount;
    private String mobile; // Compulsory If Insurance payment
    private String dob; // Compulsory If Insurance payment
    private String stdcode; // Compulsory If Landline Payment

    public PayBillQuery(String username, String password, String operator, String number, String amount
            , String consumer_name, String bill_number, String bill_date, String due_date, String due_amount
            , String mobile, String dob, String stdcode ) {
        this.username = username;
        this.password = password;
        this.operator = operator;
        this.number = number;
        this.amount = amount;
        this.consumer_name = consumer_name;
        this.bill_number = bill_number;
        this.bill_date = bill_date;
        this.due_date = due_date;
        this.due_amount = due_amount;
        this.mobile = mobile;
        this.dob = dob;
        this.stdcode = stdcode;
    }

    //========================================start_http==========================================/
    private BillResponse billResponse;
    private Listener.OnPaymentListener listener;

    public void setListener(Listener.OnPaymentListener listener){
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        billResponse = new BillResponse();
    }

    protected String doInBackground(String... arg0) {

        HttpClient htppclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(HttpUrlPath.urlPath + HttpUrlPath.action_pay_bill);

        try {
            List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

            // Compulsory ...
            nameValuePair.add(new BasicNameValuePair("username", username));
            nameValuePair.add(new BasicNameValuePair("password", password));
            nameValuePair.add(new BasicNameValuePair("operator", operator));
            nameValuePair.add(new BasicNameValuePair("number", number));
            nameValuePair.add(new BasicNameValuePair("amount", amount));

            nameValuePair.add(new BasicNameValuePair("consumer_name", consumer_name));
            nameValuePair.add(new BasicNameValuePair("bill_number", bill_number));
            nameValuePair.add(new BasicNameValuePair("bill_date", bill_date));
            nameValuePair.add(new BasicNameValuePair("due_date", due_date));
            nameValuePair.add(new BasicNameValuePair("due_amount", due_amount));
            nameValuePair.add(new BasicNameValuePair("mobile", mobile));
            nameValuePair.add(new BasicNameValuePair("dob", dob));
            nameValuePair.add(new BasicNameValuePair("stdcode", stdcode));


            httppost.setEntity(new UrlEncodedFormEntity(nameValuePair, "UTF-8"));
            HttpResponse response = htppclient.execute(httppost);
            String object = EntityUtils.toString(response.getEntity());
            System.out.println("#####object registration=" + object);

            JSONObject js = new JSONObject(object);

            billResponse.status = js.getInt("status");

            billResponse.msg = js.getString("msg");

            if (js.has("number"))
                billResponse.number = js.getString("number");
            if (js.has("operator"))
                billResponse.operator = js.getString("operator");
            if (js.has("txn_no"))
                billResponse.txn_no = js.getString("txn_no");
            if (js.has("amount"))
                billResponse.amount = js.getString("amount");

            if (js.has("operator_ref_no"))
                billResponse.operator_ref_no = js.getString("operator_ref_no");

        } catch (Exception e) {
            Log.v("PayBillQuery", "Error : " + e.getMessage());
            billResponse.msg = billResponse.msg + ", Error : " + e.getMessage();
            e.printStackTrace();
        }
        return null;
    }


    protected void onPostExecute(String result1) {
        super.onPostExecute(result1);
        listener.onPaymentResponse( billResponse );
    }


    // -------------- Response Object...
    public static class BillResponse {
        public int status;
        public String msg = "";
        public String number;
        public String operator;
        public String txn_no;
        public String amount;
        public String operator_ref_no;
    }

}
