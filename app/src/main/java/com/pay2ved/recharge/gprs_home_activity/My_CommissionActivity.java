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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.futuremind.recyclerviewfastscroll.FastScroller;
import com.pay2ved.recharge.R;
import com.pay2ved.recharge.activity.GprsHomeActivity;
import com.pay2ved.recharge.adapter.My_CommissionAdapter;

import com.pay2ved.recharge.helper.Listener;
import com.pay2ved.recharge.model.ShowFormGetSet;
import com.pay2ved.recharge.other.AppsContants;
import com.pay2ved.recharge.other.ConnectionDetector;
import com.pay2ved.recharge.other.HttpUrlPath;
import com.pay2ved.recharge.service.callmodel.CallCommission;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class My_CommissionActivity extends AppCompatActivity implements Listener.OnCommissionListener {

    ImageView img_back;
    RelativeLayout rlt_home, rlt_my_account, rlt_reports;
    TextView txt_title;
    List<ShowFormGetSet> products;
    RecyclerView mRecyclerView;
    ConnectionDetector cd;
    static JSONArray contactsstate = null;
    private ProgressDialog pDialog;
    My_CommissionAdapter mAdapter;
    String s_Username = "", s_Password = "";
    FastScroller fastScroller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commission__report);

        // -- Dialog
        pDialog = new ProgressDialog(My_CommissionActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);


        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        s_Username = AppsContants.sharedpreferences.getString(AppsContants.Username, "");
        s_Password = AppsContants.sharedpreferences.getString(AppsContants.Password, "");

        txt_title = (TextView) findViewById(R.id.txt_title);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //========================================================
        rlt_home = (RelativeLayout) findViewById(R.id.rlt_home);
        rlt_my_account = (RelativeLayout) findViewById(R.id.rlt_my_account);
        rlt_reports = (RelativeLayout) findViewById(R.id.rlt_reports);

        rlt_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(My_CommissionActivity.this, GprsHomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        rlt_my_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(My_CommissionActivity.this, My_AccountActivity.class);
                startActivity(intent);
                finish();
            }
        });

        rlt_reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(My_CommissionActivity.this, ReportsActivity.class);
                startActivity(intent);
                finish();
            }
        });


        //========================================================================

        fastScroller = (FastScroller) findViewById(R.id.fastscroll);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(My_CommissionActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);
        fastScroller.setRecyclerView(mRecyclerView);

        products = new ArrayList<ShowFormGetSet>();
        signUpClick();

    }

    public void signUpClick() {
        cd = new ConnectionDetector(My_CommissionActivity.this);
        if (!cd.isConnectingToInternet()) {
            Toast.makeText(My_CommissionActivity.this, "noNetworkConnection", Toast.LENGTH_LONG).show();
        } else {
//            new GetContacts_commodity("").execute();
            pDialog.show();
            DBQuery.queryToCommission( this, s_Username, s_Password );
        }

    }

    @Override
    public void onLoadCommission(@Nullable CallCommission callCommission) {
        pDialog.dismiss();
        if (callCommission != null){
            if (callCommission.getData() != null ){
                mAdapter = new My_CommissionAdapter(My_CommissionActivity.this, callCommission.getData());
                mRecyclerView.setAdapter(mAdapter);
            }else{
                Toast.makeText(this, callCommission.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }else
        {
            Toast.makeText(this, "Something Went Wrong!", Toast.LENGTH_SHORT).show();
        }
    }


    // TODO : Remove ---------------------------------------------------------------------------------
    private class GetContacts_commodity extends AsyncTask<String, Void, String> {
        String jsonStr;
        String result = "";
        String jsoprofile;
        String ss_message = "", ss_data = "";


        public GetContacts_commodity(String strprofile) {
            this.jsoprofile = strprofile;

        }

        protected void onPreExecute() {
            super.onPreExecute();

            pDialog.show();

        }


        @Override
        protected String doInBackground(String... voids) {
            // TODO Auto-generated method stub

            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(HttpUrlPath.urlPath + HttpUrlPath.action_commission);

            try {

                List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

                nameValuePair.add(new BasicNameValuePair("username", s_Username));
                nameValuePair.add(new BasicNameValuePair("password", s_Password));

                post.setEntity(new UrlEncodedFormEntity(nameValuePair));
                HttpResponse response = client.execute(post);
                jsonStr = EntityUtils.toString(response.getEntity());
                System.out.println("#####object registration=" + jsonStr);

                JSONObject js = new JSONObject(jsonStr);
                ss_message = js.getString("message");

                if (js.has("data")) {

                    ss_data = js.getString("data");
                    contactsstate = new JSONArray(ss_data);

                    //contactsstate = new JSONArray(jsonStr);
                    for (int i = 0; i < contactsstate.length(); i++) {
                        JSONObject c = contactsstate.getJSONObject(i);
                        String a1 = c.getString("id");
                        String a2 = c.getString("name");
                        String a3 = c.getString("service");
                        String a4 = c.getString("commission");
                        String a5 = c.getString("surcharge");

                        ShowFormGetSet item = new ShowFormGetSet();
                        item.setId(a1);
                        item.setTitle(a2);
                        item.setService(a3);
                        item.setCommission(a4);
                        item.setSurcharge(a5);

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

        }
    }

}
