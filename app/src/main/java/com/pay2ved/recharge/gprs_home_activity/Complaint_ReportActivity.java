package com.pay2ved.recharge.gprs_home_activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.futuremind.recyclerviewfastscroll.FastScroller;
import com.pay2ved.recharge.R;
import com.pay2ved.recharge.activity.GprsHomeActivity;
import com.pay2ved.recharge.adapter.Complaint_ReportAdapter;
import com.pay2ved.recharge.model.ShowFormGetSet;
import com.pay2ved.recharge.other.AppsContants;
import com.pay2ved.recharge.other.ConnectionDetector;
import com.pay2ved.recharge.other.HttpUrlPath;

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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Complaint_ReportActivity extends AppCompatActivity {

    ImageView img_back, img_filter;
    RelativeLayout rlt_home, rlt_my_account, rlt_reports;
    TextView txt_title;
    TextView txt_data;
    List<ShowFormGetSet> products;
    RecyclerView mRecyclerView;
    ConnectionDetector cd;
    static JSONArray contactsstate = null;
    private ProgressDialog pDialog;
    Complaint_ReportAdapter mAdapter;
    String s_DATE_FROM = "", s_DATE_TO = "";
    String s_Username = "", s_Password = "", s_Action = "", s_Title = "";
    FastScroller fastScroller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction__report);

        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        s_DATE_FROM = AppsContants.sharedpreferences.getString(AppsContants.DATE_FROM, "");
        s_DATE_TO = AppsContants.sharedpreferences.getString(AppsContants.DATE_TO, "");
        s_Username = AppsContants.sharedpreferences.getString(AppsContants.Username, "");
        s_Password = AppsContants.sharedpreferences.getString(AppsContants.Password, "");
        s_Action = AppsContants.sharedpreferences.getString(AppsContants.Action, "");
        s_Title = AppsContants.sharedpreferences.getString(AppsContants.Title, "");

        txt_title = (TextView) findViewById(R.id.txt_title);
        txt_title.setText(s_Title);
        txt_data = (TextView) findViewById(R.id.txt_data);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_filter = (ImageView) findViewById(R.id.img_filter);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        img_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Complaint_ReportActivity.this, Filter_transActivity.class);
                startActivity(intent);

            }
        });

        //========================================================
        rlt_home = (RelativeLayout) findViewById(R.id.rlt_home);
        rlt_my_account = (RelativeLayout) findViewById(R.id.rlt_my_account);
        rlt_reports = (RelativeLayout) findViewById(R.id.rlt_reports);

        rlt_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Complaint_ReportActivity.this, GprsHomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        rlt_my_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Complaint_ReportActivity.this, My_AccountActivity.class);
                startActivity(intent);
                finish();
            }
        });

        rlt_reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Complaint_ReportActivity.this, ReportsActivity.class);
                startActivity(intent);
                finish();
            }
        });


        //========================================================================

        fastScroller = (FastScroller) findViewById(R.id.fastscroll);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(Complaint_ReportActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);
        fastScroller.setRecyclerView(mRecyclerView);

        products = new ArrayList<ShowFormGetSet>();
        signUpClick();

    }

    public void signUpClick() {
        cd = new ConnectionDetector(Complaint_ReportActivity.this);
        if (!cd.isConnectingToInternet()) {

            Toast.makeText(Complaint_ReportActivity.this, "noNetworkConnection", Toast.LENGTH_LONG).show();
        } else {
            new GetContacts_commodity("").execute();

        }

    }


    private class GetContacts_commodity extends AsyncTask<String, Void, String> {
        String jsonStr;
        String result = "";
        String jsoprofile;
        String ss_message = "", ss_error = "", ss_data = "";

        public GetContacts_commodity(String strprofile) {
            this.jsoprofile = strprofile;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Complaint_ReportActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }


        @Override
        protected String doInBackground(String... voids) {
            // TODO Auto-generated method stub
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(HttpUrlPath.urlPath + s_Action);

            try {

                List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
                nameValuePair.add(new BasicNameValuePair("username", s_Username));
                nameValuePair.add(new BasicNameValuePair("password", s_Password));
                nameValuePair.add(new BasicNameValuePair("from", s_DATE_FROM));
                nameValuePair.add(new BasicNameValuePair("to", s_DATE_TO));

                post.setEntity(new UrlEncodedFormEntity(nameValuePair));
                HttpResponse response = client.execute(post);
                jsonStr = EntityUtils.toString(response.getEntity());
                System.out.println("#####object registration=" + jsonStr);

                JSONObject js = new JSONObject(jsonStr);
                ss_error = js.getString("error");
                ss_message = js.getString("message");
                if (js.has("data")) {
                    ss_data = js.getString("data");
                    contactsstate = new JSONArray(ss_data);
                    for (int i = 0; i < contactsstate.length(); i++) {
                        JSONObject c = contactsstate.getJSONObject(i);
                        String a1 = c.getString("id");
                        String a2 = c.getString("name");
                        String a3 = c.getString("amount");
                        String a4 = c.getString("type");
                        String a5 = c.getString("date");
                        String a6 = c.getString("ref_no");
                        String a7 = c.getString("remark");

                        ShowFormGetSet item = new ShowFormGetSet();
                        item.setId(a1);
                        item.setTitle(a2);
                        item.setAmount(a3);
                        item.setType(a4);
                        item.setDate(a5);
                        item.setRef_no(a6);
                        item.setRemark(a7);

                        products.add(item);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return result;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();

            if (ss_error.equals("2")) {
                txt_data.setText(ss_message);
                txt_data.setVisibility(View.VISIBLE);
            } else {
                txt_data.setVisibility(View.GONE);
            }
            mAdapter = new Complaint_ReportAdapter(Complaint_ReportActivity.this, products);
            mRecyclerView.setAdapter(mAdapter);

        }
    }


    @Override
    public void onResume() {

        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        s_DATE_FROM = AppsContants.sharedpreferences.getString(AppsContants.DATE_FROM, "");
        s_DATE_TO = AppsContants.sharedpreferences.getString(AppsContants.DATE_TO, "");

        super.onResume();
    }

}
