package com.pay2ved.recharge.service;

import android.content.Intent;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.pay2ved.recharge.service.room.DBNotifications;
import com.pay2ved.recharge.service.room.NotificationModel;
import com.pay2ved.recharge.util.NotificationActivity;

import static com.pay2ved.recharge.other.Constant.NOTIFY_TOPIC_ALL;
import static com.pay2ved.recharge.other.Constant.NOTIFY_TYPE;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    public static String FCM_TOKEN = null;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (remoteMessage == null)
            return;

        Log.e(TAG, "From: " + remoteMessage.getFrom());

        //---------- Data Notification -----------
        String title = remoteMessage.getNotification() != null ? remoteMessage.getNotification().getTitle() : "" ;
        String body = remoteMessage.getNotification() != null ? remoteMessage.getNotification().getBody() : "" ;

        Intent intent = new Intent( getApplicationContext(), NotificationActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );

        if ( remoteMessage.getNotification().getImageUrl() != null ){
            new MyNotificationManager( getApplicationContext() ).showBigNotification( title, body,
                    String.valueOf(remoteMessage.getNotification().getImageUrl()!=null ? remoteMessage.getNotification().getImageUrl() : ""),
                    intent );
        }else{
            new MyNotificationManager( getApplicationContext() ).showSmallNotification( title, body, intent );
        }
        try {
            addIntoRoomDatabase( remoteMessage );
            Log.e("Notify", "Message : Title = " +  title
                    + " Body = " + body );
        } catch (Exception e) {
            Log.e("TAG", "Exception: " + e.getMessage());
        }

    }

    // -----------------------------
    // START on_new_token
    @Override
    public void onNewToken(@NonNull String token) {
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
//        if ( user != null &&  SharedPrefManager.getInstance(this).isLoggedIn()){
//            sendFCMToServer(token);
//        }
        Log.e( "NOTIFICATION", "TOKEN : "+ token );
        FCM_TOKEN = token;
        subscribeAll();
        //  Update FCM TOKEN on Server...!
         new UpdateFCMOnServer( FCM_TOKEN, getApplicationContext() ).start();
    }

    private void subscribeAll( ){
        FirebaseMessaging.getInstance().subscribeToTopic( NOTIFY_TOPIC_ALL )
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Log.e( "NOTIFICATION", "Subscriebed : " );
                    }else{
                        Log.e( "NOTIFICATION", "Failed to subscribtion : " );
                    }
                });
    }

    // END on_new_token
    // retrieve the current token
    public static void retrieveCurrentToken(){
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }
                        // Get new FCM registration token
                        FCM_TOKEN = task.getResult();
//                    sendRegistrationToServer( token );
                        Log.e( "NOTIFICATION", "TOKEN : "+ FCM_TOKEN );
                    }
                });

        // Subscribe...
    }

    public static String getFcmToken(){
        if (FCM_TOKEN == null){
            retrieveCurrentToken();
        }
        return FCM_TOKEN ;
    }


    private void addIntoRoomDatabase( RemoteMessage remoteMessage ){
        NotificationModel notificationModel = new NotificationModel(
                remoteMessage.getNotification() != null ? remoteMessage.getNotification().getTitle() : "" ,
                remoteMessage.getNotification() != null ? remoteMessage.getNotification().getBody() : "" ,
                String.valueOf(remoteMessage.getNotification().getImageUrl()!=null ? remoteMessage.getNotification().getImageUrl() : ""),
                remoteMessage.getData().get( NOTIFY_TYPE )!=null? remoteMessage.getData().get( NOTIFY_TYPE ) : ""
        );

        DBNotifications.getInstance( getApplicationContext() )
                .notifyDao().insert( notificationModel );
//        // Add Notification...!
        NotificationActivity.setNotificationItem( notificationModel );

    }




}
