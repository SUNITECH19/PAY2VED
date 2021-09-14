package com.pay2ved.recharge.service.query;

import android.util.Log;

import androidx.annotation.Nullable;

import com.pay2ved.recharge.helper.Listener;
import com.pay2ved.recharge.service.callmodel.CallBalance;
import com.pay2ved.recharge.service.callmodel.CallCommission;
import com.pay2ved.recharge.service.callmodel.CallDTHInfo;
import com.pay2ved.recharge.service.callmodel.CallDTHPlans;
import com.pay2ved.recharge.service.callmodel.CallHomeMessage;
import com.pay2ved.recharge.service.callmodel.CallObject;
import com.pay2ved.recharge.service.callmodel.CallPlans;
import com.pay2ved.recharge.service.callmodel.CallROffer;
import com.pay2ved.recharge.service.callmodel.CallRecharge;
import com.pay2ved.recharge.service.callmodel.CallRechargeReport;
import com.pay2ved.recharge.service.rest.Api;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DBQuery {

    // Query To Get Home Page Info...
    public static void queryToHomeInfo( Listener.OnHomeInfoListener listener ){

        Call<CallHomeMessage> call = Api.getApiInterface().queryHomeMessage();

        call.enqueue(new Callback<CallHomeMessage>() {
            @Override
            public void onResponse(Call<CallHomeMessage> call, Response<CallHomeMessage> response) {
                listener.onLoadHomeInfo( response.body() );
            }

            @Override
            public void onFailure(Call<CallHomeMessage> call, Throwable t) {
                listener.onLoadHomeInfo( null );
                Log.e("MSG", t.getMessage() );

            }
        });
    }

    // Query To Load Balance...
    public static void queryToLoadBalance(Listener.OnLoadBalanceListener listener, String userName, String password ){
        Call<CallBalance> call = Api.getApiInterface().queryBalance( userName, password );
        call.enqueue(new Callback<CallBalance>() {
            @Override
            public void onResponse(Call<CallBalance> call, Response<CallBalance> response) {
                listener.onLoadBalance( response.body() );
            }

            @Override
            public void onFailure(Call<CallBalance> call, Throwable t) {
                listener.onLoadBalance( null );
            }
        });
    }

    // Query To Change Password OR Pin
    public static void queryToChangePassword(Listener.OnObjectQueryListener listener, String userName, String password,  String newPassword){
        Call<CallObject> call = Api.getApiInterface().queryToChangePassword( userName, password, newPassword );
        call.enqueue(new Callback<CallObject>() {
            @Override
            public void onResponse(Call<CallObject> call, Response<CallObject> response) {
                listener.onResponse( response.body() );
            }

            @Override
            public void onFailure(Call<CallObject> call, Throwable t) {
                listener.onResponse(null );
            }
        });
    }

    // Query To Get Commission info...
    public static void queryToCommission(Listener.OnCommissionListener listener, String userName, String password ){
        Call<CallCommission> call = Api.getApiInterface().queryToCommission( userName, password );
        call.enqueue(new Callback<CallCommission>() {
            @Override
            public void onResponse(Call<CallCommission> call, Response<CallCommission> response) {
                listener.onLoadCommission( response.body() );
            }

            @Override
            public void onFailure(Call<CallCommission> call, Throwable t) {
                listener.onLoadCommission( null );
            }
        });
    }

    // Query To Recharge Report....
    public static void queryToRechargeReport(Listener.OnRechargeReportListener listener, String userName, String password, String from, String to, String number  ){
        Call<CallRechargeReport> call = Api.getApiInterface().queryToRechargeReport( userName, password, from, to, number );
        call.enqueue(new Callback<CallRechargeReport>() {
            @Override
            public void onResponse(Call<CallRechargeReport> call, Response<CallRechargeReport> response) {
                listener.onLoadRechargeReport( response.body() );
            }

            @Override
            public void onFailure(Call<CallRechargeReport> call, Throwable t) {
                listener.onLoadRechargeReport(null);
            }
        });
    }

    // Query To Complaint...
    public static void queryToComplaint(Listener.OnObjectQueryListener listener, String userName, String password, String txn_no ){
        Call<CallObject> call = Api.getApiInterface().queryToComplaint(userName, password, txn_no );
        call.enqueue(new Callback<CallObject>() {
            @Override
            public void onResponse(Call<CallObject> call, Response<CallObject> response) {
                listener.onResponse( response.body());
            }

            @Override
            public void onFailure(Call<CallObject> call, Throwable t) {
                listener.onResponse(null);
            }
        });
    }

    // Query To Payment Request...
    public static void queryToPayRequest(Listener.OnObjectQueryListener listener, String userName, String password,
                                         String mode,
                                         String amount,
                                         String date,
                                         String account,
                                         String txnid,
                                         String remark ){
        Call<CallObject> call = Api.getApiInterface().queryToPayRequest(userName, password, mode, amount, date, account, txnid, remark );
        call.enqueue(new Callback<CallObject>() {
            @Override
            public void onResponse(Call<CallObject> call, Response<CallObject> response) {
                listener.onResponse( response.body());
            }

            @Override
            public void onFailure(Call<CallObject> call, Throwable t) {
                listener.onResponse(null);
            }
        });
    }
    // Query To Fund Transfer...
    public static void queryToFundTransfer(Listener.OnObjectQueryListener listener, String userName, String password,
                                         String uid,
                                         String type,
                                         String amount,
                                         String remark ){
        Call<CallObject> call = Api.getApiInterface().queryToFundTransfer(userName, password, uid, type, amount, remark );
        call.enqueue(new Callback<CallObject>() {
            @Override
            public void onResponse(Call<CallObject> call, Response<CallObject> response) {
                listener.onResponse( response.body());
            }

            @Override
            public void onFailure(Call<CallObject> call, Throwable t) {
                listener.onResponse(null);
            }
        });
    }

    // Query TO Get Plan List...
    public static void queryToGetPlansList(Listener.OnPlansListener listener, String userName, String password,
                                           String operator,
                                           String circle ){
        Call<CallPlans> call = Api.getApiInterface().queryToPlans( userName, password, operator, circle );
        call.enqueue(new Callback<CallPlans>() {
            @Override
            public void onResponse(Call<CallPlans> call, Response<CallPlans> response) {
                listener.onLoadPlanList(response.body());
            }

            @Override
            public void onFailure(Call<CallPlans> call, Throwable t) {
                listener.onLoadPlanList(null);
            }
        });
    }

    // Query To Recharge : DTH, Mobile, Insurance...
    public static void queryToRecharge( Listener.OnRechargeListener listener,
                                        String userName, String password,
                                        String operator,
                                        String number,
                                        String service,
                                        @Nullable String dob,
                                        String amount ){
        Call<CallRecharge> call;
        if (dob != null){// Insurance
            call = Api.getApiInterface().queryToRecharge( userName, password, operator, number, service, dob, amount );
        }else {
            call = Api.getApiInterface().queryToRecharge( userName, password, operator, number, service, amount );
        }

        call.enqueue(new Callback<CallRecharge>() {
            @Override
            public void onResponse(Call<CallRecharge> call, Response<CallRecharge> response) {
                listener.onRechargeInfo(response.body());
            }

            @Override
            public void onFailure(Call<CallRecharge> call, Throwable t) {
                listener.onRechargeInfo(null);
            }
        });

    }

    // Query To Add User....
    public static void queryToAddUser(Listener.OnObjectQueryListener listener,
                                      String userName, String password,
                                      String mobile,
                                      String businessName,
                                      String name,
                                      String email,
                                      String city,
                                      String state,
                                      String pinCode,
                                      String pan,
                                      String aadhaar,
                                      String address
                                      ){
        Call<CallObject> call = Api.getApiInterface().queryToAddUser( userName, password, mobile, businessName, name, email, city, state, pinCode, pan, aadhaar, address);
        call.enqueue(new Callback<CallObject>() {
            @Override
            public void onResponse(Call<CallObject> call, Response<CallObject> response) {
                listener.onResponse( response.body() );
            }

            @Override
            public void onFailure(Call<CallObject> call, Throwable t) {
                listener.onResponse( null );
            }
        });
    }

    public static void queryToGetCustomerInfo(Listener.OnDTHListener listener, String userName, String password,
                                              String operator,
                                              String number ){
        Call<CallDTHInfo> call = Api.getApiInterface().queryToDTHInfo(
                userName, password, operator, number
        );
        call.enqueue(new Callback<CallDTHInfo>() {
            @Override
            public void onResponse(Call<CallDTHInfo> call, Response<CallDTHInfo> response) {
                listener.onDTHInfoLoad( response.body() );
            }

            @Override
            public void onFailure(Call<CallDTHInfo> call, Throwable t) {
                listener.onDTHInfoLoad( null );
            }
        });
    }

    // Query To Get DTH Plans --------
    public static void queryToGetDTHPlansList(Listener.OnDTHPlansListener listener,  String userName, String password, String operator ){
        Call<CallDTHPlans> call = Api.getApiInterface().queryToDTHPlans( userName, password, operator );
        call.enqueue(new Callback<CallDTHPlans>() {
            @Override
            public void onResponse(Call<CallDTHPlans> call, Response<CallDTHPlans> response) {
                listener.onDTHPlansLoad( response.body() );
            }

            @Override
            public void onFailure(Call<CallDTHPlans> call, Throwable t) {
                listener.onDTHPlansLoad( null );
            }
        });
    }

    // Query To Get R - Offer : DTH And Mobile
    public static void queryToROffer(Listener.OnROfferListener listener, String userName, String password, String number, String operator, boolean isDTH ){
        Call<CallROffer> call;
        if (isDTH){
            call = Api.getApiInterface().queryToDTHROffer( userName, password, operator, number );
        }else{
            call = Api.getApiInterface().queryToROffer( userName, password, operator, number );
        }
        call.enqueue(new Callback<CallROffer>() {
            @Override
            public void onResponse(Call<CallROffer> call, Response<CallROffer> response) {
                listener.onROfferLoad( response.body() );
            }

            @Override
            public void onFailure(Call<CallROffer> call, Throwable t) {
                listener.onROfferLoad(null);
            }
        });
    }




}
