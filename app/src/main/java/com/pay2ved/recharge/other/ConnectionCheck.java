package com.pay2ved.recharge.other;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

import com.pay2ved.recharge.helper.Listener;

import static com.pay2ved.recharge.wactivity.ActivityHome.connectionListener;

/**
 * ---------
 * Created by Shailendra Lodhi
 * Visit : https://linktr.ee/wackycodes
 * ---------
 */
public class ConnectionCheck extends BroadcastReceiver {

    public static boolean IS_CONNECT = false;
    private static Context context;

    public ConnectionCheck(Listener.ConnectionListener listener, Context context) {
        connectionListener = listener;
        ConnectionCheck.context = context;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            // Register Network to new Android version...
            registerNetworkCallback();
        }
    }

    // Default Constructor : Use in version 10 onwards
    public ConnectionCheck() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (connectionListener != null){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                Log.d("NewApi", "On Internet Connection change, it'll Call Automatically... and Update UI!");
            }else{
                connectionListener.onConnectChange(isInternetConnected(context));
            }
        }
        else {
            Log.e("ERROR", "NOT Found");
        }
    }

    public boolean isInternetConnected(Context ctx) {
        boolean isConnected = false;
        try {
            ConnectivityManager connectivityManager;
            if (ctx != null) {
                connectivityManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
            } else {
                connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            }
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo == null) {
                Log.e("CON", "CON Failed");
                return false;
            }

            if (networkInfo.isConnected() || networkInfo.isConnectedOrConnecting()) {
                Log.e("CON", "CON Back");
                isConnected = true;
            } else {
                Log.e("CON", "CON Lost");
                isConnected = false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IS_CONNECT = isConnected;
            return isConnected;
        }
    }

    // Network Check
    public void registerNetworkCallback() {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkRequest.Builder builder = new NetworkRequest.Builder();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                connectivityManager.registerDefaultNetworkCallback(
                        new ConnectivityManager.NetworkCallback() {
                            @Override
                            public void onAvailable(Network network) {
                                IS_CONNECT = true;
                                if (connectionListener != null){
                                    connectionListener.onConnectChange(true);
                                    // Hint - Use : runOnUiThread(() -> {...} // inside onConnectChange() fun
                                }
                            }

                            @Override
                            public void onLost(Network network) {
                                IS_CONNECT = false;
                                if (connectionListener != null){
                                    connectionListener.onConnectChange(false);
                                    // Hint - Use : runOnUiThread(() -> {...} // inside onConnectChange() fun
                                }
                            }
                        }
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
