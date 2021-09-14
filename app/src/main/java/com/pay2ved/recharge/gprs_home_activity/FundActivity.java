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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pay2ved.recharge.R;
import com.pay2ved.recharge.activity.GprsHomeActivity;
import com.pay2ved.recharge.activity.UserSprActivity;
import com.pay2ved.recharge.adapter.UserArrayAdapter;
import com.pay2ved.recharge.helper.Listener;
import com.pay2ved.recharge.other.AppsContants;
import com.pay2ved.recharge.other.ConnectionDetector;
import com.pay2ved.recharge.other.HttpUrlPath;
import com.pay2ved.recharge.other.NetConnection;
import com.pay2ved.recharge.service.callmodel.CallObject;
import com.pay2ved.recharge.service.query.DBQuery;
import com.reginald.editspinner.EditSpinner;

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

public class FundActivity extends AppCompatActivity implements Listener.OnObjectQueryListener {

    ConnectionDetector cd;
    static JSONArray contactsstate = null;
    ArrayList<String> user_Name;
    ArrayList<String> user_bal;
    ArrayList<String> user_Id;
    RadioGroup group_type;
    RadioButton type;
    EditText amount, remark;
    Button ok, cancel;
    ImageView img_back, img_search;
    String s_Password = "", s_Username = "", s_Type = "", s_id = "", s_user = "", s_U_id = "";
    String s_type = "", s_name = "", s_amount = "", s_remark = "", s_balance = "";
    private ProgressDialog pDialog;
    EditSpinner edit_spinner;
    TextView balance, txt_user;
    LinearLayout lnr_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fund);

        pDialog = new ProgressDialog(FundActivity.this);
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
        balance = (TextView) findViewById(R.id.balance);
        group_type = (RadioGroup) findViewById(R.id.group_type);
        edit_spinner = (EditSpinner) findViewById(R.id.edit_spinner);
        amount = (EditText) findViewById(R.id.amount);
        remark = (EditText) findViewById(R.id.remark);
        cancel = (Button) findViewById(R.id.cancel);
        ok = (Button) findViewById(R.id.ok);
        img_search = (ImageView) findViewById(R.id.img_search);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        edit_spinner.setEditable(false);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        user_Name = new ArrayList<String>();
        user_bal = new ArrayList<String>();
        user_Id = new ArrayList<String>();
        signUpClick();

        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FundActivity.this, UserSprActivity.class);
                startActivity(intent);
            }
        });


        group_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                int selectedId = group_type.getCheckedRadioButtonId();
                type = (RadioButton) findViewById(selectedId);
                if (selectedId == -1) {
                    Toast.makeText(FundActivity.this, "Nothing selected", Toast.LENGTH_SHORT).show();
                } else {
                    // Toast.makeText(FundActivity.this, type.getText(), Toast.LENGTH_SHORT).show();
                    s_type = type.getText().toString().trim();
                }

            }
        });


        int selectedId = group_type.getCheckedRadioButtonId();
        type = (RadioButton) findViewById(selectedId);
        if (selectedId == -1) {
            Toast.makeText(FundActivity.this, "Nothing selected", Toast.LENGTH_SHORT).show();
        } else {
            // Toast.makeText(FundActivity.this, type.getText(), Toast.LENGTH_SHORT).show();
            s_type = type.getText().toString().trim();
        }


        edit_spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                s_id = user_Id.get(position);
                s_name = user_Name.get(position);
                s_balance = user_bal.get(position);


                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.U_id, s_id);
                editor.commit();

                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                s_U_id = AppsContants.sharedpreferences.getString(AppsContants.U_id, "");

                // Toast.makeText(FundActivity.this, "" + s_balance, Toast.LENGTH_SHORT).show();
                if (s_balance.equals("")) {
                    balance.setText("0.00");
                } else {
                    balance.setText(s_balance);
                }

            }
        });


    }

    @Override
    public void onResponse(@Nullable CallObject callObject) {
        pDialog.dismiss();
        if (callObject!= null){
            AlertDialog.Builder builder = new AlertDialog.Builder(FundActivity.this);
            builder.setMessage("" + callObject.getMessage());
            builder.setCancelable(false);
            builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @SuppressLint("NewApi")
                public void onClick(DialogInterface dialog, int which) {

                    Intent intent = new Intent(FundActivity.this, GprsHomeActivity.class);
                    startActivity(intent);
                    finishAffinity();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();

        } else {
            Toast.makeText(FundActivity.this, "Something Went wrong!", Toast.LENGTH_SHORT).show();
        }
    }


    //============================================end ======================================//

    public void signUpClick() {
        cd = new ConnectionDetector(FundActivity.this);
        if (!cd.isConnectingToInternet()) {

            Toast.makeText(FundActivity.this, "noNetworkConnection", Toast.LENGTH_LONG).show();
        } else {
            new GetContacts_commodity("").execute();

        }

    }

    @Override
    protected void onResume() {

        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        s_user = AppsContants.sharedpreferences.getString(AppsContants.Phone, "");
        s_U_id = AppsContants.sharedpreferences.getString(AppsContants.U_id, "");
        s_balance = AppsContants.sharedpreferences.getString(AppsContants.Balance, "");

        edit_spinner.setText(s_user);
        if (s_balance.equals("")) {
            balance.setText("0.00");
        } else {
            balance.setText(s_balance);
        }

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                s_amount = amount.getText().toString().trim();
                s_remark = remark.getText().toString().trim();

                //Toast.makeText(FundActivity.this, "" + s_U_id, Toast.LENGTH_SHORT).show();

                boolean isError = false;

                if (s_U_id.equals("")) {
                    isError = true;
                    Toast.makeText(FundActivity.this, "Please Select User", Toast.LENGTH_LONG).show();
                }
                if (s_type.equals("")) {
                    isError = true;
                    Toast.makeText(FundActivity.this, "Please Select Transfer Type", Toast.LENGTH_LONG).show();
                } else if (s_amount.equals("")) {
                    isError = true;
                    amount.requestFocus();
                    Toast.makeText(FundActivity.this, "Please Enter Amount", Toast.LENGTH_LONG).show();

                } else {

                    if (!isError) {
                        if (NetConnection.isConnected(FundActivity.this)) {
                            pDialog.show();
                            DBQuery.queryToFundTransfer( FundActivity.this, s_Username, s_Password, s_id, s_Type, s_amount, s_remark );
//                            Httpjsontask task = new Httpjsontask();
//                            task.execute();
                        } else {
                            Toast.makeText(FundActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        }

                    }
                }

            }
        });

        super.onResume();
    }


    private class GetContacts_commodity extends AsyncTask<String, Void, String> {
        String jsonStr;
        String result = "";
        String jsoprofile;
        String ss_message = "", ss_data = "", S_balance = "", S_business_name = "", S_uid = "";


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
            HttpPost post = new HttpPost(HttpUrlPath.urlPath + HttpUrlPath.action_users);

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

                    for (int i = 0; i < contactsstate.length(); i++) {
                        JSONObject c = contactsstate.getJSONObject(i);
                        S_uid = c.getString("uid");
                        String a2 = c.getString("type");
                        S_business_name = c.getString("business_name");
                        String a4 = c.getString("name");
                        String a5 = c.getString("mobile");
                        S_balance = c.getString("balance");

                        user_Id.add(S_uid);
                        user_Name.add(S_business_name);
                        user_bal.add(S_balance);
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

            //  final ListAdapter adapter = new ArrayAdapter<String>(FundActivity.this, android.R.layout.simple_spinner_dropdown_item, user_Name);
            // edit_spinner.setAdapter(adapter);

            final UserArrayAdapter c_adapter = new UserArrayAdapter(FundActivity.this, R.layout.circle_spinner_layout, user_Name);
            edit_spinner.setAdapter(c_adapter);

            edit_spinner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CharSequence constraint = edit_spinner.getText().toString();
                    c_adapter.getFilter().filter(constraint);

                    edit_spinner.showDropDown();
                }
            });

        }
    }


    // TODO : Remove this Class========================================start_http==========================================/
    public class Httpjsontask extends AsyncTask<String, Void, String> {

        String Errormessage;
        boolean IsError = false;
        String msg = "", s_data = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(FundActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... arg0) {

            HttpClient htppclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(HttpUrlPath.urlPath + HttpUrlPath.action_fund_transfer);

            try {
                List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

                nameValuePair.add(new BasicNameValuePair("username", s_Username));
                nameValuePair.add(new BasicNameValuePair("password", s_Password));
                nameValuePair.add(new BasicNameValuePair("uid", s_U_id));
                nameValuePair.add(new BasicNameValuePair("type", s_type));
                nameValuePair.add(new BasicNameValuePair("amount", s_amount));
                nameValuePair.add(new BasicNameValuePair("remark", s_remark));

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

                AlertDialog.Builder builder = new AlertDialog.Builder(FundActivity.this);
                builder.setMessage("" + Errormessage);
                builder.setCancelable(false);
                builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @SuppressLint("NewApi")
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(FundActivity.this, GprsHomeActivity.class);
                        startActivity(intent);
                        finishAffinity();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

            } else {
                Toast.makeText(FundActivity.this, "" + Errormessage, Toast.LENGTH_SHORT).show();
            }
        }

    }
}
