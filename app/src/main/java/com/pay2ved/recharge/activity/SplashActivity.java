package com.pay2ved.recharge.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessaging;
import com.pay2ved.recharge.R;
import com.pay2ved.recharge.other.AppsContants;
import com.pay2ved.recharge.other.HttpUrlPath;
import com.pay2ved.recharge.other.NetConnection;
import com.pay2ved.recharge.util.Config;
import com.pay2ved.recharge.util.NotificationUtils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    private static final int PERMISSIONS_CALL_PHONE = 200;
    private static int SPLASH_TIME_OUT = 3000;
    BroadcastReceiver mRegistrationBroadcastReceiver;
    private static final String TAG = SplashActivity.class.getSimpleName();
    String user_id = "";

    private ArrayList<String> mob_Id = new ArrayList<String>();
    private ArrayList<String> mob_name = new ArrayList<String>();
    private ArrayList<String> mob_code = new ArrayList<String>();
    private ArrayList<String> mob_remark = new ArrayList<String>();
    private ArrayList<String> mob_hint1 = new ArrayList<String>();
    private ArrayList<String> mob_hint2 = new ArrayList<String>();
    private ArrayList<String> mob_hint3 = new ArrayList<String>();
    private ArrayList<String> mob_icon = new ArrayList<String>();

    private ArrayList<String> dth_Id = new ArrayList<String>();
    private ArrayList<String> dth_name = new ArrayList<String>();
    private ArrayList<String> dth_code = new ArrayList<String>();
    private ArrayList<String> dth_remark = new ArrayList<String>();
    private ArrayList<String> dth_hint1 = new ArrayList<String>();
    private ArrayList<String> dth_hint2 = new ArrayList<String>();
    private ArrayList<String> dth_hint3 = new ArrayList<String>();
    private ArrayList<String> dth_icon = new ArrayList<String>();

    private ArrayList<String> data_Id = new ArrayList<String>();
    private ArrayList<String> data_name = new ArrayList<String>();
    private ArrayList<String> data_code = new ArrayList<String>();
    private ArrayList<String> data_remark = new ArrayList<String>();
    private ArrayList<String> data_hint1 = new ArrayList<String>();
    private ArrayList<String> data_hint2 = new ArrayList<String>();
    private ArrayList<String> data_hint3 = new ArrayList<String>();
    private ArrayList<String> data_icon = new ArrayList<String>();

    private ArrayList<String> post_Id = new ArrayList<String>();
    private ArrayList<String> post_name = new ArrayList<String>();
    private ArrayList<String> post_code = new ArrayList<String>();
    private ArrayList<String> post_remark = new ArrayList<String>();
    private ArrayList<String> post_hint1 = new ArrayList<String>();
    private ArrayList<String> post_hint2 = new ArrayList<String>();
    private ArrayList<String> post_hint3 = new ArrayList<String>();
    private ArrayList<String> post_icon = new ArrayList<String>();

    private ArrayList<String> land_Id = new ArrayList<String>();
    private ArrayList<String> land_name = new ArrayList<String>();
    private ArrayList<String> land_code = new ArrayList<String>();
    private ArrayList<String> land_remark = new ArrayList<String>();
    private ArrayList<String> land_hint1 = new ArrayList<String>();
    private ArrayList<String> land_hint2 = new ArrayList<String>();
    private ArrayList<String> land_hint3 = new ArrayList<String>();
    private ArrayList<String> land_icon = new ArrayList<String>();

    private ArrayList<String> elec_Id = new ArrayList<String>();
    private ArrayList<String> elec_name = new ArrayList<String>();
    private ArrayList<String> elec_code = new ArrayList<String>();
    private ArrayList<String> elec_remark = new ArrayList<String>();
    private ArrayList<String> elec_hint1 = new ArrayList<String>();
    private ArrayList<String> elec_hint2 = new ArrayList<String>();
    private ArrayList<String> elec_hint3 = new ArrayList<String>();
    private ArrayList<String> elec_icon = new ArrayList<String>();

    private ArrayList<String> money_Id = new ArrayList<String>();
    private ArrayList<String> money_name = new ArrayList<String>();
    private ArrayList<String> money_code = new ArrayList<String>();
    private ArrayList<String> money_remark = new ArrayList<String>();
    private ArrayList<String> money_hint1 = new ArrayList<String>();
    private ArrayList<String> money_hint2 = new ArrayList<String>();
    private ArrayList<String> money_hint3 = new ArrayList<String>();
    private ArrayList<String> money_icon = new ArrayList<String>();

    private ArrayList<String> ins_Id = new ArrayList<String>();
    private ArrayList<String> ins_name = new ArrayList<String>();
    private ArrayList<String> ins_code = new ArrayList<String>();
    private ArrayList<String> ins_remark = new ArrayList<String>();
    private ArrayList<String> ins_hint1 = new ArrayList<String>();
    private ArrayList<String> ins_hint2 = new ArrayList<String>();
    private ArrayList<String> ins_hint3 = new ArrayList<String>();
    private ArrayList<String> ins_icon = new ArrayList<String>();

    private ArrayList<String> gas_Id = new ArrayList<String>();
    private ArrayList<String> gas_name = new ArrayList<String>();
    private ArrayList<String> gas_code = new ArrayList<String>();
    private ArrayList<String> gas_remark = new ArrayList<String>();
    private ArrayList<String> gas_hint1 = new ArrayList<String>();
    private ArrayList<String> gas_hint2 = new ArrayList<String>();
    private ArrayList<String> gas_hint3 = new ArrayList<String>();
    private ArrayList<String> gas_icon = new ArrayList<String>();

    private ArrayList<String> water_Id = new ArrayList<String>();
    private ArrayList<String> water_name = new ArrayList<String>();
    private ArrayList<String> water_code = new ArrayList<String>();
    private ArrayList<String> water_remark = new ArrayList<String>();
    private ArrayList<String> water_hint1 = new ArrayList<String>();
    private ArrayList<String> water_hint2 = new ArrayList<String>();
    private ArrayList<String> water_hint3 = new ArrayList<String>();
    private ArrayList<String> water_icon = new ArrayList<String>();

    static JSONArray contactsstate = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        user_id = AppsContants.sharedpreferences.getString(AppsContants.USERID, "");

        new Handler().postDelayed(new Runnable() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            public void run() {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_READ_CONTACTS);
                    //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
                } else {

                    //---------------------------------------------------------------------
                    if (user_id.equals("")) {
                        Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(i);
                        finish();

                    } else {
                        Intent i = new Intent(SplashActivity.this, GprsHomeActivity.class);
                        startActivity(i);
                        finish();
                    }
                    //---------------------------------------------------------------
                }
            }

        }, SPLASH_TIME_OUT);


        signProvider("");

        //====================================
        mob_Id.add("0");
        mob_name.add("Select");
        mob_code.add("null");
        mob_remark.add("null");
        mob_hint1.add("null");
        mob_hint2.add("null");
        mob_hint3.add("null");
        mob_icon.add("null");

        dth_Id.add("0");
        dth_name.add("Select");
        dth_code.add("null");
        dth_remark.add("null");
        dth_hint1.add("null");
        dth_hint2.add("null");
        dth_hint3.add("null");
        dth_icon.add("null");

        data_Id.add("0");
        data_name.add("Select");
        data_code.add("null");
        data_remark.add("null");
        data_hint1.add("null");
        data_hint2.add("null");
        data_hint3.add("null");
        data_icon.add("null");

        post_Id.add("0");
        post_name.add("Select");
        post_code.add("null");
        post_remark.add("null");
        post_hint1.add("null");
        post_hint2.add("null");
        post_hint3.add("null");
        post_icon.add("null");

        land_Id.add("0");
        land_name.add("Select");
        land_code.add("null");
        land_remark.add("null");
        land_hint1.add("null");
        land_hint2.add("null");
        land_hint3.add("null");
        land_icon.add("null");

        elec_Id.add("0");
        elec_name.add("Select");
        elec_code.add("null");
        elec_remark.add("null");
        elec_hint1.add("null");
        elec_hint2.add("null");
        elec_hint3.add("null");
        elec_icon.add("null");

        money_Id.add("0");
        money_name.add("Select");
        money_code.add("null");
        money_remark.add("null");
        money_hint1.add("null");
        money_hint2.add("null");
        money_hint3.add("null");
        money_icon.add("null");

        ins_Id.add("0");
        ins_name.add("Select");
        ins_code.add("null");
        ins_remark.add("null");
        ins_hint1.add("null");
        ins_hint2.add("null");
        ins_hint3.add("null");
        ins_icon.add("null");

        gas_Id.add("0");
        gas_name.add("Select");
        gas_code.add("null");
        gas_remark.add("null");
        gas_hint1.add("null");
        gas_hint2.add("null");
        gas_hint3.add("null");
        gas_icon.add("null");

        water_Id.add("0");
        water_name.add("Select");
        water_code.add("null");
        water_remark.add("null");
        water_hint1.add("null");
        water_hint2.add("null");
        water_hint3.add("null");
        water_icon.add("null");

        //===================================

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);
                    displayFirebaseRegId();
                    //  Toast.makeText(getApplicationContext(), "global", Toast.LENGTH_LONG).show();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received
                    String message = intent.getStringExtra("message");
                    // Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();
                }
            }
        };

        FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);
        displayFirebaseRegId();
    }
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        Log.e(TAG, "Firebase reg id: " + regId);
    }
    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.WRITE_CONTACTS, Manifest.permission.READ_CONTACTS}, PERMISSIONS_CALL_PHONE);
                    //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
                } else {
                    //---------------------------------------------------------------------
                    if (user_id.equals("")) {
                        Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(i);
                        finish();

                    } else {
                        Intent i = new Intent(SplashActivity.this, GprsHomeActivity.class);
                        startActivity(i);
                        finish();
                    }
                    //---------------------------------------------------------------

                }


            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == PERMISSIONS_CALL_PHONE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted

                //---------------------------------------------------------------------
                if (user_id.equals("")) {
                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();

                } else {
                    Intent i = new Intent(SplashActivity.this, GprsHomeActivity.class);
                    startActivity(i);
                    finish();
                }
                //---------------------------------------------------------------


            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }

    }

    //----------------------------------provider start---------------------------------
    public void signProvider(String str_id) {

        if (NetConnection.isConnected(SplashActivity.this)) {
            new GetProvider(str_id).execute();

        } else {

            Toast.makeText(SplashActivity.this, "Internet connection error", Toast.LENGTH_LONG).show();
        }
    }

    private class GetProvider extends AsyncTask<String, Void, String> {
        String jsonStr;
        String result = "";
        String jsoprofile, Errormessage = "", s_data = "";
        String id, name = "", code = "", icon = "";

        public GetProvider(String strprofile) {
            this.jsoprofile = strprofile;
        }

        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... voids) {
            // TODO Auto-generated method stub

            HttpClient client = new DefaultHttpClient();
            HttpGet get = new HttpGet(HttpUrlPath.urlPath + HttpUrlPath.action_operators);
            try {
                HttpResponse response = client.execute(get);
                jsonStr = EntityUtils.toString(response.getEntity());
                System.out.println("#####object registration=" + jsonStr);
                JSONObject js = new JSONObject(jsonStr);

                Errormessage = js.getString("message");
                if (js.has("data")) {
                    s_data = js.getString("data");
                    contactsstate = new JSONArray(s_data);
                    for (int i = 0; i < contactsstate.length(); i++) {
                        JSONObject c = contactsstate.getJSONObject(i);

                        id = c.getString("id");
                        name = c.getString("name");
                        code = c.getString("code");
                        String sms_code = c.getString("sms_code");
                        String service = c.getString("service");
                        String service_code = c.getString("service_code");
                        String status = c.getString("status");
                        String hint1 = c.getString("hint1");
                        String hint2 = c.getString("hint2");
                        String hint3 = c.getString("hint3");
                        String remark = c.getString("remark");
                        icon = c.getString("icon");

                        if (code.toString().equals("DMR1")) {
                            AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                            editor.putString(AppsContants.Money_Tran, remark.toString());
                            editor.commit();
                        } else if (code.toString().equals("DMTV")) {
                            AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                            editor.putString(AppsContants.Money_Valid, remark.toString());
                            editor.commit();
                        }

                        if (service.equals("MOBILE")) {

                            mob_Id.add(id);
                            mob_name.add(name);
                            mob_code.add(code);
                            mob_remark.add(remark);
                            mob_hint1.add(hint1);
                            mob_hint2.add(hint2);
                            mob_hint3.add(hint3);
                            mob_icon.add(icon);
                        }

                        if (service.equals("DTH")) {
                            dth_Id.add(id);
                            dth_name.add(name);
                            dth_code.add(code);
                            dth_remark.add(remark);
                            dth_hint1.add(hint1);
                            dth_hint2.add(hint2);
                            dth_hint3.add(hint3);
                            dth_icon.add(icon);
                        }

                        if (service.equals("DATACARD")) {
                            data_Id.add(id);
                            data_name.add(name);
                            data_code.add(code);
                            data_remark.add(remark);
                            data_hint1.add(hint1);
                            data_hint2.add(hint2);
                            data_hint3.add(hint3);
                            data_icon.add(icon);
                        }

                        if (service.equals("POSTPAID")) {
                            post_Id.add(id);
                            post_name.add(name);
                            post_code.add(code);
                            post_remark.add(remark);
                            post_hint1.add(hint1);
                            post_hint2.add(hint2);
                            post_hint3.add(hint3);
                            post_icon.add(icon);
                        }

                        if (service.equals("LANDLINE")) {
                            land_Id.add(id);
                            land_name.add(name);
                            land_code.add(code);
                            land_remark.add(remark);
                            land_hint1.add(hint1);
                            land_hint2.add(hint2);
                            land_hint3.add(hint3);
                            land_icon.add(icon);
                        }

                        if (service.equals("ELECTRICITY")) {
                            elec_Id.add(id);
                            elec_name.add(name);
                            elec_code.add(code);
                            elec_remark.add(remark);
                            elec_hint1.add(hint1);
                            elec_hint2.add(hint2);
                            elec_hint3.add(hint3);
                            elec_icon.add(icon);
                        }

                        if (service.equals("MONEY TRANSFER")) {
                            money_Id.add(id);
                            money_name.add(name);
                            money_code.add(code);
                            money_remark.add(remark);
                            money_hint1.add(hint1);
                            money_hint2.add(hint2);
                            money_hint3.add(hint3);
                            money_icon.add(icon);
                        }

                        if (service.equals("INSURANCE")) {
                            ins_Id.add(id);
                            ins_name.add(name);
                            ins_code.add(code);
                            ins_remark.add(remark);
                            ins_hint1.add(hint1);
                            ins_hint2.add(hint2);
                            ins_hint3.add(hint3);
                            ins_icon.add(icon);
                        }

                        if (service.equals("GAS")) {
                            gas_Id.add(id);
                            gas_name.add(name);
                            gas_code.add(code);
                            gas_remark.add(remark);
                            gas_hint1.add(hint1);
                            gas_hint2.add(hint2);
                            gas_hint3.add(hint3);
                            gas_icon.add(icon);
                        }

                        if (service.equals("WATER")) {
                            water_Id.add(id);
                            water_name.add(name);
                            water_code.add(code);
                            water_remark.add(remark);
                            water_hint1.add(hint1);
                            water_hint2.add(hint2);
                            water_hint3.add(hint3);
                            water_icon.add(icon);
                        }

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

            StringBuilder Mob = new StringBuilder();
            for (int i = 0; i < mob_name.size(); i++) {
                Mob.append(mob_name.get(i)).append(",");
            }

            StringBuilder Mob_code = new StringBuilder();
            for (int i = 0; i < mob_code.size(); i++) {
                Mob_code.append(mob_code.get(i)).append(",");
            }

            StringBuilder Mob_remark = new StringBuilder();
            for (int i = 0; i < mob_remark.size(); i++) {
                Mob_remark.append(mob_remark.get(i)).append(",");
            }
            StringBuilder Mob_hint1 = new StringBuilder();
            for (int i = 0; i < mob_hint1.size(); i++) {
                Mob_hint1.append(mob_hint1.get(i)).append(",");
            }
            StringBuilder Mob_hint2 = new StringBuilder();
            for (int i = 0; i < mob_hint2.size(); i++) {
                Mob_hint2.append(mob_hint2.get(i)).append(",");
            }
            StringBuilder Mob_hint3 = new StringBuilder();
            for (int i = 0; i < mob_hint3.size(); i++) {
                Mob_hint3.append(mob_hint3.get(i)).append(",");
            }
            StringBuilder Mob_icon = new StringBuilder();
            for (int i = 0; i < mob_icon.size(); i++) {
                Mob_icon.append(mob_icon.get(i)).append(",");
            }

            //=========================================


            StringBuilder Dth = new StringBuilder();
            for (int i = 0; i < dth_name.size(); i++) {
                Dth.append(dth_name.get(i)).append(",");
            }

            StringBuilder Dth_code = new StringBuilder();
            for (int i = 0; i < dth_code.size(); i++) {
                Dth_code.append(dth_code.get(i)).append(",");
            }

            StringBuilder Dth_remark = new StringBuilder();
            for (int i = 0; i < dth_remark.size(); i++) {
                Dth_remark.append(dth_remark.get(i)).append(",");
            }
            StringBuilder Dth_hint1 = new StringBuilder();
            for (int i = 0; i < dth_hint1.size(); i++) {
                Dth_hint1.append(dth_hint1.get(i)).append(",");
            }
            StringBuilder Dth_hint2 = new StringBuilder();
            for (int i = 0; i < dth_hint2.size(); i++) {
                Dth_hint2.append(dth_hint2.get(i)).append(",");
            }
            StringBuilder Dth_hint3 = new StringBuilder();
            for (int i = 0; i < dth_hint3.size(); i++) {
                Dth_hint3.append(dth_hint3.get(i)).append(",");
            }
            StringBuilder Dth_icon = new StringBuilder();
            for (int i = 0; i < dth_icon.size(); i++) {
                Dth_icon.append(dth_icon.get(i)).append(",");
            }

            //=========================================


            StringBuilder Data = new StringBuilder();
            for (int i = 0; i < data_name.size(); i++) {
                Data.append(data_name.get(i)).append(",");
            }

            StringBuilder Data_code = new StringBuilder();
            for (int i = 0; i < data_code.size(); i++) {
                Data_code.append(data_code.get(i)).append(",");
            }

            StringBuilder Data_remark = new StringBuilder();
            for (int i = 0; i < data_remark.size(); i++) {
                Data_remark.append(data_remark.get(i)).append(",");
            }
            StringBuilder Data_hint1 = new StringBuilder();
            for (int i = 0; i < data_hint1.size(); i++) {
                Data_hint1.append(data_hint1.get(i)).append(",");
            }
            StringBuilder Data_hint2 = new StringBuilder();
            for (int i = 0; i < data_hint2.size(); i++) {
                Data_hint2.append(data_hint2.get(i)).append(",");
            }
            StringBuilder Data_hint3 = new StringBuilder();
            for (int i = 0; i < data_hint3.size(); i++) {
                Data_hint3.append(data_hint3.get(i)).append(",");
            }
            StringBuilder Data_icon = new StringBuilder();
            for (int i = 0; i < data_icon.size(); i++) {
                Data_icon.append(data_icon.get(i)).append(",");
            }

            //=========================================


            StringBuilder Post = new StringBuilder();
            for (int i = 0; i < post_name.size(); i++) {
                Post.append(post_name.get(i)).append(",");
            }

            StringBuilder Post_code = new StringBuilder();
            for (int i = 0; i < post_code.size(); i++) {
                Post_code.append(post_code.get(i)).append(",");
            }

            StringBuilder Post_remark = new StringBuilder();
            for (int i = 0; i < post_remark.size(); i++) {
                Post_remark.append(post_remark.get(i)).append(",");
            }
            StringBuilder Post_hint1 = new StringBuilder();
            for (int i = 0; i < post_hint1.size(); i++) {
                Post_hint1.append(post_hint1.get(i)).append(",");
            }
            StringBuilder Post_hint2 = new StringBuilder();
            for (int i = 0; i < post_hint2.size(); i++) {
                Post_hint2.append(post_hint2.get(i)).append(",");
            }
            StringBuilder Post_hint3 = new StringBuilder();
            for (int i = 0; i < post_hint3.size(); i++) {
                Post_hint3.append(post_hint3.get(i)).append(",");
            }
            StringBuilder Post_icon = new StringBuilder();
            for (int i = 0; i < post_icon.size(); i++) {
                Post_icon.append(post_icon.get(i)).append(",");
            }
            //=========================================


            StringBuilder Land = new StringBuilder();
            for (int i = 0; i < land_name.size(); i++) {
                Land.append(land_name.get(i)).append(",");
            }

            StringBuilder Land_code = new StringBuilder();
            for (int i = 0; i < land_code.size(); i++) {
                Land_code.append(land_code.get(i)).append(",");
            }

            StringBuilder Land_remark = new StringBuilder();
            for (int i = 0; i < land_remark.size(); i++) {
                Land_remark.append(land_remark.get(i)).append(",");
            }
            StringBuilder Land_hint1 = new StringBuilder();
            for (int i = 0; i < land_hint1.size(); i++) {
                Land_hint1.append(land_hint1.get(i)).append(",");
            }
            StringBuilder Land_hint2 = new StringBuilder();
            for (int i = 0; i < land_hint2.size(); i++) {
                Land_hint2.append(land_hint2.get(i)).append(",");
            }
            StringBuilder Land_hint3 = new StringBuilder();
            for (int i = 0; i < land_hint3.size(); i++) {
                Land_hint3.append(land_hint3.get(i)).append(",");
            }
            StringBuilder Land_icon = new StringBuilder();
            for (int i = 0; i < land_icon.size(); i++) {
                Land_icon.append(land_icon.get(i)).append(",");
            }
            //=========================================


            StringBuilder Elec = new StringBuilder();
            for (int i = 0; i < elec_name.size(); i++) {
                Elec.append(elec_name.get(i)).append(",");
            }

            StringBuilder Elec_code = new StringBuilder();
            for (int i = 0; i < elec_code.size(); i++) {
                Elec_code.append(elec_code.get(i)).append(",");
            }

            StringBuilder Elec_remark = new StringBuilder();
            for (int i = 0; i < elec_remark.size(); i++) {
                Elec_remark.append(elec_remark.get(i)).append(",");
            }
            StringBuilder Elec_hint1 = new StringBuilder();
            for (int i = 0; i < elec_hint1.size(); i++) {
                Elec_hint1.append(elec_hint1.get(i)).append(",");
            }
            StringBuilder Elec_hint2 = new StringBuilder();
            for (int i = 0; i < elec_hint2.size(); i++) {
                Elec_hint2.append(elec_hint2.get(i)).append(",");
            }
            StringBuilder Elec_hint3 = new StringBuilder();
            for (int i = 0; i < elec_hint3.size(); i++) {
                Elec_hint3.append(elec_hint3.get(i)).append(",");
            }
            StringBuilder Elec_icon = new StringBuilder();
            for (int i = 0; i < elec_icon.size(); i++) {
                Elec_icon.append(elec_icon.get(i)).append(",");
            }
            //=========================================


            StringBuilder Money = new StringBuilder();
            for (int i = 0; i < money_name.size(); i++) {
                Money.append(money_name.get(i)).append(",");
            }

            StringBuilder Money_code = new StringBuilder();
            for (int i = 0; i < money_code.size(); i++) {
                Money_code.append(money_code.get(i)).append(",");
            }

            StringBuilder Money_remark = new StringBuilder();
            for (int i = 0; i < money_remark.size(); i++) {
                Money_remark.append(money_remark.get(i)).append(",");
            }
            StringBuilder Money_hint1 = new StringBuilder();
            for (int i = 0; i < money_hint1.size(); i++) {
                Money_hint1.append(money_hint1.get(i)).append(",");
            }
            StringBuilder Money_hint2 = new StringBuilder();
            for (int i = 0; i < money_hint2.size(); i++) {
                Money_hint2.append(money_hint2.get(i)).append(",");
            }
            StringBuilder Money_hint3 = new StringBuilder();
            for (int i = 0; i < money_hint3.size(); i++) {
                Money_hint3.append(money_hint3.get(i)).append(",");
            }
            StringBuilder Money_icon = new StringBuilder();
            for (int i = 0; i < money_icon.size(); i++) {
                Money_icon.append(money_icon.get(i)).append(",");
            }
            //=========================================


            StringBuilder Ins = new StringBuilder();
            for (int i = 0; i < ins_name.size(); i++) {
                Ins.append(ins_name.get(i)).append(",");
            }

            StringBuilder Ins_code = new StringBuilder();
            for (int i = 0; i < ins_code.size(); i++) {
                Ins_code.append(ins_code.get(i)).append(",");
            }

            StringBuilder Ins_remark = new StringBuilder();
            for (int i = 0; i < ins_remark.size(); i++) {
                Ins_remark.append(ins_remark.get(i)).append(",");
            }

            StringBuilder Ins_hint1 = new StringBuilder();
            for (int i = 0; i < ins_hint1.size(); i++) {
                Ins_hint1.append(ins_hint1.get(i)).append(",");
            }
            StringBuilder Ins_hint2 = new StringBuilder();
            for (int i = 0; i < ins_hint2.size(); i++) {
                Ins_hint2.append(ins_hint2.get(i)).append(",");
            }
            StringBuilder Ins_hint3 = new StringBuilder();
            for (int i = 0; i < ins_hint3.size(); i++) {
                Ins_hint3.append(ins_hint3.get(i)).append(",");
            }
            StringBuilder Ins_icon = new StringBuilder();
            for (int i = 0; i < ins_icon.size(); i++) {
                Ins_icon.append(ins_icon.get(i)).append(",");
            }
            //=========================================


            StringBuilder Gas = new StringBuilder();
            for (int i = 0; i < gas_name.size(); i++) {
                Gas.append(gas_name.get(i)).append(",");
            }

            StringBuilder Gas_code = new StringBuilder();
            for (int i = 0; i < gas_code.size(); i++) {
                Gas_code.append(gas_code.get(i)).append(",");
            }

            StringBuilder Gas_remark = new StringBuilder();
            for (int i = 0; i < gas_remark.size(); i++) {
                Gas_remark.append(gas_remark.get(i)).append(",");
            }
            StringBuilder Gas_hint1 = new StringBuilder();
            for (int i = 0; i < gas_hint1.size(); i++) {
                Gas_hint1.append(gas_hint1.get(i)).append(",");
            }
            StringBuilder Gas_hint2 = new StringBuilder();
            for (int i = 0; i < gas_hint2.size(); i++) {
                Gas_hint2.append(gas_hint2.get(i)).append(",");
            }
            StringBuilder Gas_hint3 = new StringBuilder();
            for (int i = 0; i < gas_hint3.size(); i++) {
                Gas_hint3.append(gas_hint3.get(i)).append(",");
            }
            StringBuilder Gas_icon = new StringBuilder();
            for (int i = 0; i < gas_icon.size(); i++) {
                Gas_icon.append(gas_icon.get(i)).append(",");
            }

            //=========================================

            StringBuilder Water = new StringBuilder();
            for (int i = 0; i < water_name.size(); i++) {
                Water.append(water_name.get(i)).append(",");
            }

            StringBuilder Water_code = new StringBuilder();
            for (int i = 0; i < water_code.size(); i++) {
                Water_code.append(water_code.get(i)).append(",");
            }

            StringBuilder Water_remark = new StringBuilder();
            for (int i = 0; i < water_remark.size(); i++) {
                Water_remark.append(water_remark.get(i)).append(",");
            }
            StringBuilder Water_hint1 = new StringBuilder();
            for (int i = 0; i < water_hint1.size(); i++) {
                Water_hint1.append(water_hint1.get(i)).append(",");
            }
            StringBuilder Water_hint2 = new StringBuilder();
            for (int i = 0; i < water_hint2.size(); i++) {
                Water_hint2.append(water_hint2.get(i)).append(",");
            }
            StringBuilder Water_hint3 = new StringBuilder();
            for (int i = 0; i < water_hint3.size(); i++) {
                Water_hint3.append(water_hint3.get(i)).append(",");
            }
            StringBuilder Water_icon = new StringBuilder();
            for (int i = 0; i < water_icon.size(); i++) {
                Water_icon.append(water_icon.get(i)).append(",");
            }


            AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
            editor.putString(AppsContants.Mob_name, Mob.toString());
            editor.putString(AppsContants.Mob_code, Mob_code.toString());
            editor.putString(AppsContants.Mob_remark, Mob_remark.toString());
            editor.putString(AppsContants.Mob_hint1, Mob_hint1.toString());
            editor.putString(AppsContants.Mob_hint2, Mob_hint2.toString());
            editor.putString(AppsContants.Mob_hint3, Mob_hint3.toString());
            editor.putString(AppsContants.Mob_icon, Mob_icon.toString());

            editor.putString(AppsContants.Dth_name, Dth.toString());
            editor.putString(AppsContants.Dth_code, Dth_code.toString());
            editor.putString(AppsContants.Dth_remark, Dth_remark.toString());
            editor.putString(AppsContants.Dth_hint1, Dth_hint1.toString());
            editor.putString(AppsContants.Dth_hint2, Dth_hint2.toString());
            editor.putString(AppsContants.Dth_hint3, Dth_hint3.toString());
            editor.putString(AppsContants.Dth_icon, Dth_icon.toString());

            editor.putString(AppsContants.Data_name, Data.toString());
            editor.putString(AppsContants.Data_code, Data_code.toString());
            editor.putString(AppsContants.Data_remark, Data_remark.toString());
            editor.putString(AppsContants.Data_hint1, Data_hint1.toString());
            editor.putString(AppsContants.Data_hint2, Data_hint2.toString());
            editor.putString(AppsContants.Data_hint3, Data_hint3.toString());
            editor.putString(AppsContants.Data_icon, Data_icon.toString());

            editor.putString(AppsContants.Post_name, Post.toString());
            editor.putString(AppsContants.Post_code, Post_code.toString());
            editor.putString(AppsContants.Post_remark, Post_remark.toString());
            editor.putString(AppsContants.Post_hint1, Post_hint1.toString());
            editor.putString(AppsContants.Post_hint2, Post_hint2.toString());
            editor.putString(AppsContants.Post_hint3, Post_hint3.toString());
            editor.putString(AppsContants.Post_icon, Post_icon.toString());

            editor.putString(AppsContants.Land_name, Land.toString());
            editor.putString(AppsContants.Land_code, Land_code.toString());
            editor.putString(AppsContants.Land_remark, Land_remark.toString());
            editor.putString(AppsContants.Land_hint1, Land_hint1.toString());
            editor.putString(AppsContants.Land_hint2, Land_hint2.toString());
            editor.putString(AppsContants.Land_hint3, Land_hint3.toString());
            editor.putString(AppsContants.Land_icon, Land_icon.toString());

            editor.putString(AppsContants.Elec_name, Elec.toString());
            editor.putString(AppsContants.Elec_code, Elec_code.toString());
            editor.putString(AppsContants.Elec_remark, Elec_remark.toString());
            editor.putString(AppsContants.Elec_hint1, Elec_hint1.toString());
            editor.putString(AppsContants.Elec_hint2, Elec_hint2.toString());
            editor.putString(AppsContants.Elec_hint3, Elec_hint3.toString());
            editor.putString(AppsContants.Elec_icon, Elec_icon.toString());

            editor.putString(AppsContants.Money_name, Money.toString());
            editor.putString(AppsContants.Money_code, Money_code.toString());
            editor.putString(AppsContants.Money_remark, Money_remark.toString());
            editor.putString(AppsContants.Money_hint1, Money_hint1.toString());
            editor.putString(AppsContants.Money_hint2, Money_hint2.toString());
            editor.putString(AppsContants.Money_hint3, Money_hint3.toString());
            editor.putString(AppsContants.Money_icon, Money_icon.toString());

            editor.putString(AppsContants.Ins_name, Ins.toString());
            editor.putString(AppsContants.Ins_code, Ins_code.toString());
            editor.putString(AppsContants.Ins_remark, Ins_remark.toString());
            editor.putString(AppsContants.Ins_hint1, Ins_hint1.toString());
            editor.putString(AppsContants.Ins_hint2, Ins_hint2.toString());
            editor.putString(AppsContants.Ins_hint3, Ins_hint3.toString());
            editor.putString(AppsContants.Ins_icon, Ins_icon.toString());

            editor.putString(AppsContants.Gas_name, Gas.toString());
            editor.putString(AppsContants.Gas_code, Gas_code.toString());
            editor.putString(AppsContants.Gas_remark, Gas_remark.toString());
            editor.putString(AppsContants.Gas_hint1, Gas_hint1.toString());
            editor.putString(AppsContants.Gas_hint2, Gas_hint2.toString());
            editor.putString(AppsContants.Gas_hint3, Gas_hint3.toString());
            editor.putString(AppsContants.Gas_icon, Gas_icon.toString());

            editor.putString(AppsContants.Water_name, Water.toString());
            editor.putString(AppsContants.Water_code, Water_code.toString());
            editor.putString(AppsContants.Water_remark, Water_remark.toString());
            editor.putString(AppsContants.Water_hint1, Water_hint1.toString());
            editor.putString(AppsContants.Water_hint2, Water_hint2.toString());
            editor.putString(AppsContants.Water_hint3, Water_hint3.toString());
            editor.putString(AppsContants.Water_icon, Water_icon.toString());
            editor.commit();

        }
    }
    //---------------------------------------provider end--------------------------------------


}
