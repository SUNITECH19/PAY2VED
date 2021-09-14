package com.pay2ved.recharge.gprs_home_activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.pay2ved.recharge.adapter.BeneficiaryAdapter;
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

public class BeneficiaryActivity extends AppCompatActivity {

    ImageView img_back;
    RelativeLayout rlt_home, rlt_my_account, rlt_reports;
    TextView txt_title;
    List<ShowFormGetSet> products;
    RecyclerView mRecyclerView;
    ConnectionDetector cd;
    private ProgressDialog pDialog;
    BeneficiaryAdapter mAdapter;
    String s_Username = "", s_Password = "", s_Mobile = "";
    FastScroller fastScroller;
    Button btn_BeneficiaryAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beneficiary);

        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        s_Username = AppsContants.sharedpreferences.getString(AppsContants.Username, "");
        s_Password = AppsContants.sharedpreferences.getString(AppsContants.Password, "");
        s_Mobile = AppsContants.sharedpreferences.getString(AppsContants.Phone, "");

        btn_BeneficiaryAdd = (Button) findViewById(R.id.btn_BeneficiaryAdd);
        txt_title = (TextView) findViewById(R.id.txt_title);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_BeneficiaryAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BeneficiaryActivity.this, AddBeneficiaryActivity.class);
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
                Intent intent = new Intent(BeneficiaryActivity.this, GprsHomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        rlt_my_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(BeneficiaryActivity.this, My_AccountActivity.class);
                startActivity(intent);
                finish();
            }
        });

        rlt_reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(BeneficiaryActivity.this, ReportsActivity.class);
                startActivity(intent);
                finish();
            }
        });


        //========================================================================

        fastScroller = (FastScroller) findViewById(R.id.fastscroll);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(BeneficiaryActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);
        fastScroller.setRecyclerView(mRecyclerView);

        products = new ArrayList<ShowFormGetSet>();
        DataFetch();

    }

    public void DataFetch() {
        cd = new ConnectionDetector(BeneficiaryActivity.this);
        if (!cd.isConnectingToInternet()) {
            Toast.makeText(BeneficiaryActivity.this, "noNetworkConnection", Toast.LENGTH_LONG).show();
        } else {
            new GetBeneficiary("").execute();
        }
    }

    private class GetBeneficiary extends AsyncTask<String, Void, String> {
        String result = "";
        String jsoprofile, jsonStr;
        String S_message = "", S_RSP_CODE = "", S_SEND_MOB = "";
        JSONObject jsonObject;

        public GetBeneficiary(String strprofile) {
            this.jsoprofile = strprofile;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(BeneficiaryActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... voids) {
            // TODO Auto-generated method stub


            HttpClient htppclient = new DefaultHttpClient();
            HttpPost post = new HttpPost(HttpUrlPath.urlPath + HttpUrlPath.action_money_transfer);

            try {
                List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
                nameValuePair.add(new BasicNameValuePair("username", s_Username));
                nameValuePair.add(new BasicNameValuePair("password", s_Password));
                nameValuePair.add(new BasicNameValuePair("request", "beneficiaryList"));
                nameValuePair.add(new BasicNameValuePair("mobile", s_Mobile));

                post.setEntity(new UrlEncodedFormEntity(nameValuePair, "UTF-8"));
                HttpResponse response = htppclient.execute(post);
                jsonStr = EntityUtils.toString(response.getEntity());
                System.out.println("#####object registration=" + jsonStr);
                jsonObject = new JSONObject(jsonStr);

                S_RSP_CODE = jsonObject.getString("RSP_CODE");
                S_message = jsonObject.getString("RSP_MSG");
                S_SEND_MOB = jsonObject.getString("SEND_MOB");

                if (jsonObject.has("BEN_DATA")) {
                    String ss_data = jsonObject.getString("BEN_DATA");
                    JSONArray jsonArray = new JSONArray(ss_data);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject c = jsonArray.getJSONObject(i);
                        String code = c.getString("BeneficiaryCode");
                        String name = c.getString("BeneficiaryName");
                        String acc_no = c.getString("AccountNumber");
                        String acc_type = c.getString("AccountType");
                        String ifsc = c.getString("IFSC");

                        ShowFormGetSet item = new ShowFormGetSet();
                        item.setId(code);
                        item.setName(name);
                        item.setAccount(acc_no);
                        item.setType(acc_type);
                        item.setIfsc(ifsc);

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

            mAdapter = new BeneficiaryAdapter(BeneficiaryActivity.this, products);
            mRecyclerView.setAdapter(mAdapter);
        }
    }
}
