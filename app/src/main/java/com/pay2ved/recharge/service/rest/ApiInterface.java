package com.pay2ved.recharge.service.rest;

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

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    String ROOT_URL = "https://pay2ved.com/rest/v1/";

    // URL LINK
    String URL_CONTACT = "contact.php?";
    String URL_BANK = "bank.php";

    // FORM Link
    String URL_ACTION_NOTIFICATION = "notification.php";
    String URL_BALANCE = "balance.php";
    String URL_CHANGE_PASSWORD = "password.php";
    String URL_COMMISSION = "commission.php";
    String URL_RECHARGE_REPORT = "rpt-recharge.php";
    String URL_COMPLAINT = "complaint.php";
    String URL_PAYMENT_REQUEST = "payment-request.php";
    String URL_FUND_TRANSFER = "fund-transfer.php";
    String URL_PLANS = "plans.php";
    String URL_RECHARGE = "recharge.php";
    String URL_USER_ADD = "user-add.php";
    String URL_INFO_DTH = "info-dth.php";
    String URL_DTH_PLANS = "dth-plans.php";

    String URL_R_OFFER = "roffer.php";
    String URL_DTH_R_OFFER = "dth-roffer.php";


    //----------------------------------------------------------------------------------------------
    // Call function --------------

    // Home Alert Message and Banner Info ...
    @POST( URL_ACTION_NOTIFICATION )
    Call<CallHomeMessage> queryHomeMessage( );

    // Home Alert Message and Banner Info ...
    @FormUrlEncoded
    @POST( URL_BALANCE )
    Call<CallBalance> queryBalance(@Field("username") String username,
                                       @Field("password") String password );


    // Change Password ...
    @FormUrlEncoded
    @POST( URL_CHANGE_PASSWORD )
    Call<CallObject> queryToChangePassword( @Field("username") String username,
                                  @Field("password") String password ,
                                  @Field("newpassword") String newPassword );

    // Change Password ...
    @FormUrlEncoded
    @POST( URL_COMMISSION )
    Call<CallCommission> queryToCommission( @Field("username") String username,
                                               @Field("password") String password );

    // Recharge Report ...
    @FormUrlEncoded
    @POST( URL_RECHARGE_REPORT )
    Call<CallRechargeReport> queryToRechargeReport( @Field("username") String username,
                                               @Field("password") String password,
                                               @Field("from") String from,
                                               @Field("to") String to,
                                               @Field("number") String number );
    // Query To Complaint...
    @FormUrlEncoded
    @POST( URL_COMPLAINT )
    Call<CallObject> queryToComplaint( @Field("username") String username,
                                              @Field("password") String password,
                                              @Field("txn_no") String txn_no );

    // Query To Payment Request...
    @FormUrlEncoded
    @POST( URL_PAYMENT_REQUEST )
    Call<CallObject> queryToPayRequest( @Field("username") String username,
                                              @Field("password") String password,
                                              @Field("mode") String mode,
                                              @Field("amount") String amount,
                                              @Field("date") String date,
                                              @Field("account") String account,
                                              @Field("txnid") String txnid,
                                              @Field("remark") String remark );

    // Query To Payment Request...
    @FormUrlEncoded
    @POST( URL_FUND_TRANSFER )
    Call<CallObject> queryToFundTransfer( @Field("username") String username,
                                              @Field("password") String password,
                                              @Field("uid") String uid,
                                              @Field("type") String type,
                                              @Field("amount") String amount,
                                              @Field("remark") String remark );
    // Query To Plans List...
    @FormUrlEncoded
    @POST( URL_PLANS )
    Call<CallPlans> queryToPlans(@Field("username") String username,
                                 @Field("password") String password,
                                 @Field("operator") String operator,
                                 @Field("circle") String circle );

    // Query To Recharge... => DTH And Mobile
    @FormUrlEncoded
    @POST( URL_RECHARGE )
    Call<CallRecharge> queryToRecharge(@Field("username") String username,
                                       @Field("password") String password,
                                       @Field("operator") String operator,
                                       @Field("number") String number,
                                       @Field("service") String service,
                                       @Field("amount") String amount );

    // Query To Recharge => Insurance...
    @FormUrlEncoded
    @POST( URL_RECHARGE )
    Call<CallRecharge> queryToRecharge(@Field("username") String username,
                                       @Field("password") String password,
                                       @Field("operator") String operator,
                                       @Field("number") String number,
                                       @Field("service") String service,
                                       @Field("dob") String dob,
                                       @Field("amount") String amount );

    // Query To Add User....
    @FormUrlEncoded
    @POST( URL_USER_ADD )
    Call<CallObject> queryToAddUser(@Field("username") String username,
                                    @Field("password") String password,
                                    @Field("mobile") String mobile,
                                    @Field("business_name") String business_name,
                                    @Field("name") String name,
                                    @Field("email") String email,
                                    @Field("city") String city,
                                    @Field("state") String state,
                                    @Field("pincode") String pincode,
                                    @Field("pan") String pan,
                                    @Field("aadhaar") String aadhaar,
                                    @Field("address") String address);

    // Query To DTH Info...
    @FormUrlEncoded
    @POST( URL_INFO_DTH )
    Call<CallDTHInfo> queryToDTHInfo(@Field("username") String username,
                                     @Field("password") String password,
                                     @Field("operator") String operator,
                                     @Field("number") String number);

    // Query To DTH Info...
    @FormUrlEncoded
    @POST( URL_DTH_PLANS )
    Call<CallDTHPlans> queryToDTHPlans(@Field("username") String username,
                                       @Field("password") String password,
                                       @Field("operator") String operator );


    // Query To R Offer...
    @FormUrlEncoded
    @POST( URL_R_OFFER )
    Call<CallROffer> queryToROffer(@Field("username") String username,
                                     @Field("password") String password,
                                     @Field("operator") String operator,
                                     @Field("number") String number );
    // Query To DTH R Offer...
    @FormUrlEncoded
    @POST( URL_DTH_R_OFFER )
    Call<CallROffer> queryToDTHROffer(@Field("username") String username,
                                     @Field("password") String password,
                                     @Field("operator") String operator,
                                     @Field("number") String number );


    //----------------------------------------------------------------------------------------------


}
