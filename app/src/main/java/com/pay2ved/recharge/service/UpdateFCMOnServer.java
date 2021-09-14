package com.pay2ved.recharge.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UpdateFCMOnServer  extends Thread {

    private String fcmID;
    private Context context;
    public UpdateFCMOnServer( String fcmID, Context context ){
        this.fcmID = fcmID;
        this.context = context;
    }

    @Override
    public void run() {

        HttpClient htppclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(HttpUrlPath.urlPath + HttpUrlPath.action_fcm_id_update);

        try {
            List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
            if (context != null){
                SharedPreferences sharedpreferences = AppsContants.getSharedPreferences( context );
                String mobile = sharedpreferences.getString(AppsContants.mobile, "" );
                String password = sharedpreferences.getString( AppsContants.Password, "" );

                nameValuePair.add(new BasicNameValuePair("username", mobile));
                nameValuePair.add(new BasicNameValuePair("password", password));
            }else throw new Exception("User Data Not found!");

            nameValuePair.add(new BasicNameValuePair("device_token", fcmID ));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePair, "UTF-8"));
            HttpResponse response = htppclient.execute(httppost);
            String object = EntityUtils.toString(response.getEntity());
            System.out.println("#####object registration=" + object);

            JSONObject jsonObject = new JSONObject(object);

            String message = jsonObject.getString("message");

            Log.d("RESPONSE_MSG", message );

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            Log.e("MSG", e.getMessage() );
        }

    }

}