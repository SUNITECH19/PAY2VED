package com.pay2ved.recharge.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pay2ved.recharge.R;
import com.pay2ved.recharge.adapter.CustomPagerAdapter;
import com.pay2ved.recharge.gprs_home_activity.ActivityBillPayments;
import com.pay2ved.recharge.gprs_home_activity.Add_UserActivity;
import com.pay2ved.recharge.gprs_home_activity.FundActivity;
import com.pay2ved.recharge.gprs_home_activity.GasActivity;
import com.pay2ved.recharge.gprs_home_activity.InsuranceActivity;
import com.pay2ved.recharge.gprs_home_activity.LandLineActivity;
import com.pay2ved.recharge.gprs_home_activity.MobileActivity;
import com.pay2ved.recharge.gprs_home_activity.Money_TransActivity;
import com.pay2ved.recharge.gprs_home_activity.My_AccountActivity;
import com.pay2ved.recharge.gprs_home_activity.PostPaidActivity;
import com.pay2ved.recharge.gprs_home_activity.ReportsActivity;
import com.pay2ved.recharge.gprs_home_activity.DthActivity;
import com.pay2ved.recharge.gprs_home_activity.Payment_RequiestActivity;
import com.pay2ved.recharge.gprs_home_activity.UsersActivity;
import com.pay2ved.recharge.other.AppsContants;
import com.pay2ved.recharge.other.AutoScrollViewPager;
import com.pay2ved.recharge.other.ConnectionDetector;
import com.pay2ved.recharge.other.HttpUrlPath;
import com.pay2ved.recharge.sms_home_activity.SettingsActivity;
import com.pay2ved.recharge.util.NotificationActivity;

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

import static com.pay2ved.recharge.gprs_home_activity.ActivityBillPayments.BILL_CODE_ELECTRICITY;
import static com.pay2ved.recharge.gprs_home_activity.ActivityBillPayments.BILL_CODE_GAS;
import static com.pay2ved.recharge.gprs_home_activity.ActivityBillPayments.BILL_CODE_INSURANCE;
import static com.pay2ved.recharge.gprs_home_activity.ActivityBillPayments.BILL_CODE_LAND_LINE;
import static com.pay2ved.recharge.gprs_home_activity.ActivityBillPayments.BILL_CODE_WATER;
import static com.pay2ved.recharge.gprs_home_activity.ActivityBillPayments.REQUEST_BILL_CODE;

public class GprsHomeActivity extends AppCompatActivity {

    // TODO : Remove this Activity...

    ImageView img_back, img_refresh, img_notification;
    TextView txt_balance;
    RelativeLayout rlt_mobile, rlt_dth, rlt_datacard, rlt_postpaid, rlt_landline, rlt_electricity, rlt_money_transfer,
            rlt_last_insurance, rlt_gas_bill, rlt_fund_transfer, rlt_payment_req, rlt_setting, rlt_add_user, rlt_register_user, rlt_water;

    RelativeLayout rlt_home, rlt_my_account, rlt_reports, txt_header;
    String s_Balance = "";
    TextView text_view;
    String s_Username = "", s_Password = "";
    AutoScrollViewPager viewPager;
    private TextView[] dots;
    ArrayList<String> imagesName;
    ConnectionDetector cd;
    private LinearLayout ll_dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gprs_home);

        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        s_Balance = AppsContants.sharedpreferences.getString(AppsContants.Balance, "");
        s_Username = AppsContants.sharedpreferences.getString(AppsContants.Username, "");
        s_Password = AppsContants.sharedpreferences.getString(AppsContants.Password, "");

        ll_dots = (LinearLayout) findViewById(R.id.ll_dots);
        viewPager = (AutoScrollViewPager) findViewById(R.id.autoscrollviewpager);
        text_view = (TextView) findViewById(R.id.text_view);
        text_view.setSelected(true);

        txt_balance = (TextView) findViewById(R.id.txt_balance);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_refresh = (ImageView) findViewById(R.id.img_refresh);
        img_notification = (ImageView) findViewById(R.id.img_notification);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (s_Balance.equals("")) {
            txt_balance.setText("₹" + " " + "0.00");
        } else {
            txt_balance.setText("₹" + " " + s_Balance);
        }

        img_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetContacts_Balance().execute();
            }
        });

        img_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( GprsHomeActivity.this, NotificationActivity.class ));
            }
        });

        rlt_mobile = (RelativeLayout) findViewById(R.id.rlt_mobile);
        rlt_dth = (RelativeLayout) findViewById(R.id.rlt_dth);
        rlt_datacard = (RelativeLayout) findViewById(R.id.rlt_datacard);
        rlt_postpaid = (RelativeLayout) findViewById(R.id.rlt_postpaid);
        rlt_landline = (RelativeLayout) findViewById(R.id.rlt_landline);
        rlt_electricity = (RelativeLayout) findViewById(R.id.rlt_electricity);
        rlt_money_transfer = (RelativeLayout) findViewById(R.id.rlt_money_transfer);
        rlt_last_insurance = (RelativeLayout) findViewById(R.id.rlt_last_insurance);
        rlt_gas_bill = (RelativeLayout) findViewById(R.id.rlt_gas_bill);
        rlt_fund_transfer = (RelativeLayout) findViewById(R.id.rlt_fund_transfer);
        rlt_payment_req = (RelativeLayout) findViewById(R.id.rlt_payment_req);
        rlt_setting = (RelativeLayout) findViewById(R.id.rlt_setting);
        rlt_add_user = (RelativeLayout) findViewById(R.id.rlt_add_user);
        rlt_register_user = (RelativeLayout) findViewById(R.id.rlt_register_user);
        rlt_water = (RelativeLayout) findViewById(R.id.rlt_water);


        //========================================================
        txt_header = (RelativeLayout) findViewById(R.id.txt_header);
        rlt_home = (RelativeLayout) findViewById(R.id.rlt_home);
        rlt_my_account = (RelativeLayout) findViewById(R.id.rlt_my_account);
        rlt_reports = (RelativeLayout) findViewById(R.id.rlt_reports);

        rlt_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent(GprsHomeActivity.this, GprsHomeActivity.class);
                startActivity(intent);
                finish();*/
            }
        });

        rlt_my_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(GprsHomeActivity.this, My_AccountActivity.class);
                startActivity(intent);

            }
        });

        rlt_reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(GprsHomeActivity.this, ReportsActivity.class);
                startActivity(intent);
            }
        });


        //========================================================================

        rlt_mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.Phone, "");
                editor.putString(AppsContants.Spinner_list, "MOBILE");
                editor.putString(AppsContants.Title, "Mobile");
                editor.putString(AppsContants.amount, "");
                editor.commit();

                MobileActivity.s_user_name = AppsContants.sharedpreferences.getString(AppsContants.Mob_name, "");
                MobileActivity.s_user_code = AppsContants.sharedpreferences.getString(AppsContants.Mob_code, "");
                MobileActivity.s_user_remark = AppsContants.sharedpreferences.getString(AppsContants.Mob_remark, "");
                MobileActivity.s_user_hint1 = AppsContants.sharedpreferences.getString(AppsContants.Mob_hint1, "");
                MobileActivity.s_user_hint2 = AppsContants.sharedpreferences.getString(AppsContants.Mob_hint2, "");
                MobileActivity.s_user_hint3 = AppsContants.sharedpreferences.getString(AppsContants.Mob_hint3, "");
                MobileActivity.s_user_icon = AppsContants.sharedpreferences.getString(AppsContants.Mob_icon, "");

                Intent intent = new Intent(GprsHomeActivity.this, MobileActivity.class);
                startActivity(intent);
            }
        });

        rlt_dth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.Spinner_list, "DTH");
                editor.putString(AppsContants.Title, "DTH");
                editor.commit();

                DthActivity.s_user_name = AppsContants.sharedpreferences.getString(AppsContants.Dth_name, "");
                DthActivity.s_user_code = AppsContants.sharedpreferences.getString(AppsContants.Dth_code, "");
                DthActivity.s_user_remark = AppsContants.sharedpreferences.getString(AppsContants.Dth_remark, "");
                DthActivity.s_user_hint1 = AppsContants.sharedpreferences.getString(AppsContants.Dth_hint1, "");
                DthActivity.s_user_hint2 = AppsContants.sharedpreferences.getString(AppsContants.Dth_hint2, "");
                DthActivity.s_user_hint3 = AppsContants.sharedpreferences.getString(AppsContants.Dth_hint3, "");
                DthActivity.s_user_icon = AppsContants.sharedpreferences.getString(AppsContants.Dth_icon, "");

                Intent intent = new Intent(GprsHomeActivity.this, DthActivity.class);
                startActivity(intent);
            }
        });

        rlt_datacard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.Phone, "");
                editor.putString(AppsContants.Spinner_list, "DATACARD");
                editor.putString(AppsContants.Title, "Datacard");
                editor.putString(AppsContants.amount, "");
                editor.commit();

                MobileActivity.s_user_name = AppsContants.sharedpreferences.getString(AppsContants.Data_name, "");
                MobileActivity.s_user_code = AppsContants.sharedpreferences.getString(AppsContants.Data_code, "");
                MobileActivity.s_user_remark = AppsContants.sharedpreferences.getString(AppsContants.Data_remark, "");
                MobileActivity.s_user_hint1 = AppsContants.sharedpreferences.getString(AppsContants.Data_hint1, "");
                MobileActivity.s_user_hint2 = AppsContants.sharedpreferences.getString(AppsContants.Data_hint2, "");
                MobileActivity.s_user_hint3 = AppsContants.sharedpreferences.getString(AppsContants.Data_hint3, "");
                MobileActivity.s_user_icon = AppsContants.sharedpreferences.getString(AppsContants.Data_icon, "");

                Intent intent = new Intent(GprsHomeActivity.this, MobileActivity.class);
                startActivity(intent);
            }
        });

        rlt_postpaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.Phone, "");
                editor.putString(AppsContants.Spinner_list, "POSTPAID");
                editor.putString(AppsContants.Title, "Postpaid");
                editor.commit();

                PostPaidActivity.s_user_name = AppsContants.sharedpreferences.getString(AppsContants.Post_name, "");
                PostPaidActivity.s_user_code = AppsContants.sharedpreferences.getString(AppsContants.Post_code, "");
                PostPaidActivity.s_user_remark = AppsContants.sharedpreferences.getString(AppsContants.Post_remark, "");
                PostPaidActivity.s_user_hint1 = AppsContants.sharedpreferences.getString(AppsContants.Post_hint1, "");
                PostPaidActivity.s_user_hint2 = AppsContants.sharedpreferences.getString(AppsContants.Post_hint2, "");
                PostPaidActivity.s_user_hint3 = AppsContants.sharedpreferences.getString(AppsContants.Post_hint3, "");
                PostPaidActivity.s_user_icon = AppsContants.sharedpreferences.getString(AppsContants.Post_icon, "");

                Intent intent = new Intent(GprsHomeActivity.this, PostPaidActivity.class);
                startActivity(intent);
            }
        });
        rlt_landline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.Spinner_list, "LANDLINE");
                editor.putString(AppsContants.Title, "Landline");
                editor.putString(AppsContants.amount, "");
                editor.commit();

                LandLineActivity.s_user_name = AppsContants.sharedpreferences.getString(AppsContants.Land_name, "");
                LandLineActivity.s_user_code = AppsContants.sharedpreferences.getString(AppsContants.Land_code, "");
                LandLineActivity.s_user_remark = AppsContants.sharedpreferences.getString(AppsContants.Land_remark, "");
                LandLineActivity.s_user_hint1 = AppsContants.sharedpreferences.getString(AppsContants.Land_hint1, "");
                LandLineActivity.s_user_hint2 = AppsContants.sharedpreferences.getString(AppsContants.Land_hint2, "");
                LandLineActivity.s_user_hint3 = AppsContants.sharedpreferences.getString(AppsContants.Land_hint3, "");
                LandLineActivity.s_user_icon = AppsContants.sharedpreferences.getString(AppsContants.Land_icon, "");

                Intent intent = new Intent(GprsHomeActivity.this, LandLineActivity.class);
                startActivity(intent);

                 */

                Intent intent = new Intent(GprsHomeActivity.this, ActivityBillPayments.class);
                intent.putExtra( REQUEST_BILL_CODE, BILL_CODE_LAND_LINE );
                startActivity(intent);
            }
        });

        rlt_electricity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.Spinner_list, "ELECTRICITY");
                editor.putString(AppsContants.Title, "Electricity");
                editor.putString(AppsContants.amount, "");
                editor.commit();

                ElectricityActivity.s_user_name = AppsContants.sharedpreferences.getString(AppsContants.Elec_name, "");
                ElectricityActivity.s_user_code = AppsContants.sharedpreferences.getString(AppsContants.Elec_code, "");
                ElectricityActivity.s_user_remark = AppsContants.sharedpreferences.getString(AppsContants.Elec_remark, "");
                ElectricityActivity.s_user_hint1 = AppsContants.sharedpreferences.getString(AppsContants.Elec_hint1, "");
                ElectricityActivity.s_user_hint2 = AppsContants.sharedpreferences.getString(AppsContants.Elec_hint2, "");
                ElectricityActivity.s_user_hint3 = AppsContants.sharedpreferences.getString(AppsContants.Elec_hint3, "");
                ElectricityActivity.s_user_icon = AppsContants.sharedpreferences.getString(AppsContants.Elec_icon, "");

                 */

                Intent intent = new Intent(GprsHomeActivity.this, ActivityBillPayments.class);
                intent.putExtra( REQUEST_BILL_CODE, BILL_CODE_ELECTRICITY );
                startActivity(intent);
            }
        });

        rlt_money_transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.Phone, "");
                editor.putString(AppsContants.Spinner_list, "MONEY TRANSFER");
                editor.putString(AppsContants.Title, "Money Transfer");
                editor.commit();

                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                String s_Money_Tran = AppsContants.sharedpreferences.getString(AppsContants.Money_Tran, "");
                String s_Money_Valid = AppsContants.sharedpreferences.getString(AppsContants.Money_Valid, "");

                Intent intent = new Intent(GprsHomeActivity.this, Money_TransActivity.class);
                startActivity(intent);
            }
        });

        rlt_last_insurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.Spinner_list, "INSURANCE");
                editor.putString(AppsContants.Title, "Insurance");
                editor.commit();

                InsuranceActivity.s_user_name = AppsContants.sharedpreferences.getString(AppsContants.Ins_name, "");
                InsuranceActivity.s_user_code = AppsContants.sharedpreferences.getString(AppsContants.Ins_code, "");
                InsuranceActivity.s_user_remark = AppsContants.sharedpreferences.getString(AppsContants.Ins_remark, "");
                InsuranceActivity.s_user_hint1 = AppsContants.sharedpreferences.getString(AppsContants.Ins_hint1, "");
                InsuranceActivity.s_user_hint2 = AppsContants.sharedpreferences.getString(AppsContants.Ins_hint2, "");
                InsuranceActivity.s_user_hint3 = AppsContants.sharedpreferences.getString(AppsContants.Ins_hint3, "");
                InsuranceActivity.s_user_icon = AppsContants.sharedpreferences.getString(AppsContants.Ins_icon, "");

                Intent intent = new Intent(GprsHomeActivity.this, InsuranceActivity.class);
                startActivity(intent);

                 */

                Intent intent = new Intent(GprsHomeActivity.this, ActivityBillPayments.class);
                intent.putExtra( REQUEST_BILL_CODE, BILL_CODE_INSURANCE );
                startActivity(intent);
            }
        });
        rlt_gas_bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.Spinner_list, "GAS");
                editor.putString(AppsContants.Title, "Gas Bill");
                editor.putString(AppsContants.amount, "");
                editor.commit();

                GasActivity.s_user_name = AppsContants.sharedpreferences.getString(AppsContants.Gas_name, "");
                GasActivity.s_user_code = AppsContants.sharedpreferences.getString(AppsContants.Gas_code, "");
                GasActivity.s_user_remark = AppsContants.sharedpreferences.getString(AppsContants.Gas_remark, "");
                GasActivity.s_user_hint1 = AppsContants.sharedpreferences.getString(AppsContants.Gas_hint1, "");
                GasActivity.s_user_hint2 = AppsContants.sharedpreferences.getString(AppsContants.Gas_hint2, "");
                GasActivity.s_user_hint3 = AppsContants.sharedpreferences.getString(AppsContants.Gas_hint3, "");
                GasActivity.s_user_icon = AppsContants.sharedpreferences.getString(AppsContants.Gas_icon, "");

                Intent intent = new Intent(GprsHomeActivity.this, GasActivity.class);
                startActivity(intent);

                 */

                Intent intent = new Intent(GprsHomeActivity.this, ActivityBillPayments.class);
                intent.putExtra( REQUEST_BILL_CODE, BILL_CODE_GAS );
                startActivity(intent);
            }
        });

        rlt_fund_transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.Phone, "");
                editor.putString(AppsContants.U_id, "");
                editor.putString(AppsContants.Balance, "");
                editor.commit();

                Intent intent = new Intent(GprsHomeActivity.this, FundActivity.class);
                startActivity(intent);
            }
        });

        rlt_payment_req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(GprsHomeActivity.this, Payment_RequiestActivity.class);
                startActivity(intent);

            }
        });


        rlt_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(GprsHomeActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        rlt_add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.Phone, "");
                editor.commit();

                Intent intent = new Intent(GprsHomeActivity.this, Add_UserActivity.class);
                startActivity(intent);
            }
        });

        rlt_register_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(GprsHomeActivity.this, UsersActivity.class);
                startActivity(intent);
            }
        });

        rlt_water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.Spinner_list, "WATER");
                editor.putString(AppsContants.Title, "Water");
                editor.putString(AppsContants.amount, "");
                editor.commit();

                GasActivity.s_user_name = AppsContants.sharedpreferences.getString(AppsContants.Water_name, "");
                GasActivity.s_user_code = AppsContants.sharedpreferences.getString(AppsContants.Water_code, "");
                GasActivity.s_user_remark = AppsContants.sharedpreferences.getString(AppsContants.Water_remark, "");
                GasActivity.s_user_hint1 = AppsContants.sharedpreferences.getString(AppsContants.Water_hint1, "");
                GasActivity.s_user_hint2 = AppsContants.sharedpreferences.getString(AppsContants.Water_hint2, "");
                GasActivity.s_user_hint3 = AppsContants.sharedpreferences.getString(AppsContants.Water_hint3, "");
                GasActivity.s_user_icon = AppsContants.sharedpreferences.getString(AppsContants.Water_icon, "");

                Intent intent = new Intent(GprsHomeActivity.this, GasActivity.class);
                startActivity(intent);

                 */

                Intent intent = new Intent(GprsHomeActivity.this, ActivityBillPayments.class);
                intent.putExtra( REQUEST_BILL_CODE, BILL_CODE_WATER );
                startActivity(intent);
            }
        });

        //=====================================================
        imagesName = new ArrayList<>();
        signUpClick("");

        //===================================================

    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[imagesName.size()];

        ll_dots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(GprsHomeActivity.this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(Color.parseColor("#000000"));
            ll_dots.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(Color.parseColor("#FFFFFF"));
    }

    //==============================ads=========================================================

    public void signUpClick(String str_com_id) {
        cd = new ConnectionDetector(GprsHomeActivity.this);
        if (!cd.isConnectingToInternet()) {
            Toast.makeText(GprsHomeActivity.this, "No Network Connection", Toast.LENGTH_LONG).show();
        } else {
            new GetContacts_commodity(str_com_id).execute();
        }

    }

    private class GetContacts_commodity extends AsyncTask<String, Void, String> {
        String jsonStr;
        String result = "";
        String jsoprofile;
        String s_text = "";


        public GetContacts_commodity(String strprofile) {
            this.jsoprofile = strprofile;
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected String doInBackground(String... voids) {
            // TODO Auto-generated method stub
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(HttpUrlPath.urlPath + HttpUrlPath.action_notification);

            try {
                HttpResponse response = client.execute(post);
                jsonStr = EntityUtils.toString(response.getEntity());
                System.out.println("#####object registration=" + jsonStr);
                JSONObject js = new JSONObject(jsonStr);

                s_text = js.getString("text");
                if (js.has("image")) {
                    String s_image = js.getString("image");
                    JSONArray url = new JSONArray(s_image);
                    for (int i = 0; i < url.length(); i++) {
                        JSONObject image = url.getJSONObject(i);

                        String S_url = image.getString("url");
                        String[] img = S_url.split(",");

                        for (int i1 = 0; i1 < img.length; i1++) {
                            imagesName.add(img[i1]);
                        }

                        Log.e("images", imagesName.toString());
                    }

                }


            }
            catch (IOException e) {
                e.printStackTrace();
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
            return result;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (!s_text.equals(" ")) {
                text_view.setText(s_text);
                txt_header.setVisibility(View.VISIBLE);
            } else {
                txt_header.setVisibility(View.GONE);
            }

            viewPager.startAutoScroll();
            viewPager.setInterval(4000);
            viewPager.setCycle(true);
            viewPager.setStopScrollWhenTouch(true);
            viewPager.setAdapter(new CustomPagerAdapter(GprsHomeActivity.this, imagesName));
            addBottomDots(0);


        }
    }


    //==========================================================

    private class GetContacts_Balance extends AsyncTask<String, Void, String> {
        String jsonStr;
        String result = "";
        String s_data = "", s_message = "", s_balance = "";

        public GetContacts_Balance() {

        }

        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected String doInBackground(String... voids) {
            // TODO Auto-generated method stub
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(HttpUrlPath.urlPath + HttpUrlPath.action_balance);

            try {
                List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
                nameValuePair.add(new BasicNameValuePair("username", s_Username));
                nameValuePair.add(new BasicNameValuePair("password", s_Password));

                post.setEntity(new UrlEncodedFormEntity(nameValuePair, "UTF-8"));
                HttpResponse response = client.execute(post);
                jsonStr = EntityUtils.toString(response.getEntity());
                System.out.println("#####object registration=" + jsonStr);
                JSONObject js = new JSONObject(jsonStr);

                s_message = js.getString("message");

                if (js.has("data")) {
                    s_data = js.getString("data");
                    JSONObject data = new JSONObject(s_data);
                    s_balance = data.getString("balance");
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

            if (s_balance.equals("")) {
                txt_balance.setText("₹" + " " + "0.00");
            } else {
                txt_balance.setText("₹" + " " + s_balance);
            }

            AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
            editor.putString(AppsContants.Balance, s_balance);
            editor.commit();

            AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
            s_Balance = AppsContants.sharedpreferences.getString(AppsContants.Balance, "");

            Toast.makeText(GprsHomeActivity.this, "" + s_message, Toast.LENGTH_SHORT).show();
        }
    }

}
