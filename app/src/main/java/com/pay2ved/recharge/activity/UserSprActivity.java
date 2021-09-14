package com.pay2ved.recharge.activity;

import android.content.ContentResolver;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import com.pay2ved.recharge.R;
import com.pay2ved.recharge.adapter.UserListAdapter;
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

public class UserSprActivity extends AppCompatActivity {

    static JSONArray contactsstate = null;
    ArrayList<ShowFormGetSet> selectUsers;
    ListView listView;
    ContentResolver resolver;
    SearchView search;
    UserListAdapter adapter;
    String s_Password = "", s_Username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        s_Username = AppsContants.sharedpreferences.getString(AppsContants.Username, "");
        s_Password = AppsContants.sharedpreferences.getString(AppsContants.Password, "");

        selectUsers = new ArrayList<ShowFormGetSet>();
        resolver = this.getContentResolver();
        listView = (ListView) findViewById(R.id.contacts_list);

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
        String ss_message = "", ss_data = "", S_balance = "", S_business_name = "", S_uid = "";


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
                        S_uid = c.getString("uid");
                        String a2 = c.getString("type");
                        S_business_name = c.getString("business_name");
                        String a4 = c.getString("name");
                        String a5 = c.getString("mobile");
                        S_balance = c.getString("balance");

                        ShowFormGetSet item = new ShowFormGetSet();
                        item.setId(S_uid);
                        item.setTitle(S_business_name);
                        item.setBalance(S_balance);

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

            adapter = new UserListAdapter(selectUsers, UserSprActivity.this);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    ShowFormGetSet data = selectUsers.get(i);
                }
            });

            listView.setFastScrollEnabled(true);

        }
    }

}
