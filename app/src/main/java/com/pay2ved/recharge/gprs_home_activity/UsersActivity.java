package com.pay2ved.recharge.gprs_home_activity;

import android.content.ContentResolver;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.futuremind.recyclerviewfastscroll.FastScroller;
import com.pay2ved.recharge.R;

import com.pay2ved.recharge.adapter.Reg_UsersAdapter;

import com.pay2ved.recharge.model.ShowFormGetSet;
import com.pay2ved.recharge.other.AppsContants;
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

public class UsersActivity extends AppCompatActivity {

    static JSONArray contactsstate = null;
    ArrayList<ShowFormGetSet> selectUsers;
    ContentResolver resolver;
    SearchView search;
    Reg_UsersAdapter adapter;
    RecyclerView mRecyclerView;
    FastScroller fastScroller;
    String s_Password = "", s_Username = "", s_Type = "";
    TextView txt_user;
    RelativeLayout rlt_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        s_Username = AppsContants.sharedpreferences.getString(AppsContants.Username, "");
        s_Password = AppsContants.sharedpreferences.getString(AppsContants.Password, "");
        s_Type = AppsContants.sharedpreferences.getString(AppsContants.Type, "");


        txt_user = (TextView) findViewById(R.id.txt_user);
        rlt_view = (RelativeLayout) findViewById(R.id.rlt_view);
        if (s_Type.equals("5")) {

            txt_user.setVisibility(View.VISIBLE);
            rlt_view.setVisibility(View.GONE);
        } else {
            rlt_view.setVisibility(View.VISIBLE);
            txt_user.setVisibility(View.GONE);
        }

        fastScroller = (FastScroller) findViewById(R.id.fastscroll);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(UsersActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);
        fastScroller.setRecyclerView(mRecyclerView);

        selectUsers = new ArrayList<ShowFormGetSet>();
        resolver = this.getContentResolver();

        GetContacts_commodity loadContact = new GetContacts_commodity();
        loadContact.execute();

        search = (SearchView) findViewById(R.id.searchView);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search.setIconified(false);
            }
        });

        //*** setOnQueryTextListener ***
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO Auto-generated method stub

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO Auto-generated method stub
                adapter.filter(newText);

                return false;
            }
        });

    }

    //============================================end ======================================//


    private class GetContacts_commodity extends AsyncTask<String, Void, String> {
        String jsonStr;
        String result = "";
        String ss_message = "", ss_data = "";


        protected void onPreExecute() {
            super.onPreExecute();

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
                        String uid = c.getString("uid");
                        String type = c.getString("type");
                        String business_name = c.getString("business_name");
                        String name = c.getString("name");
                        String mobile = c.getString("mobile");
                        String balance = c.getString("balance");
                        String address = c.getString("address");

                        ShowFormGetSet item = new ShowFormGetSet();
                        item.setId(uid);
                        item.setType(type);
                        item.setBusiness_name(business_name);
                        item.setName(name);
                        item.setMobile(mobile);
                        item.setBalance(balance);
                        item.setAddress(address);

                        selectUsers.add(item);
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

            adapter = new Reg_UsersAdapter(UsersActivity.this, selectUsers);
            mRecyclerView.setAdapter(adapter);

        }
    }

}
