package com.pay2ved.recharge.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.Html;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.pay2ved.recharge.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyNotificationManager {
    public static final int ID_BIG_NOTIFICATION = 234;
    public static final int ID_SMALL_NOTIFICATION = 235;

    private Context mCtx;

    Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

    public MyNotificationManager(Context mCtx) {
        this.mCtx = mCtx;
    }

    public void showBigNotification(String title, String message, String url, Intent intent) {
        // Vibrate
        setVibrate( mCtx );

        PendingIntent resultPendingIntent = PendingIntent.getActivity(mCtx, ID_BIG_NOTIFICATION, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
        bigPictureStyle.setBigContentTitle( title );
        bigPictureStyle.setSummaryText(Html.fromHtml(message).toString());
        bigPictureStyle.bigPicture(getBitmapFromURL(url));
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mCtx, mCtx.getString(R.string.app_notification_channel_id));
        Notification notification;
        notification = mBuilder
                .setSmallIcon(R.drawable.launcher)
                .setTicker( title )
                .setContentTitle( title )
                .setContentText( message )
                .setWhen(0)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent)
                .setStyle(bigPictureStyle)
                .setSound( getNotificationRingtone(mCtx), AudioManager.STREAM_NOTIFICATION )
                .setLargeIcon(BitmapFactory.decodeResource(mCtx.getResources(), R.drawable.launcher))
                .setPriority(Notification.PRIORITY_HIGH)
                .build();

        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        NotificationManager notificationManager = (NotificationManager) mCtx.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel(notificationManager);
        }
        notificationManager.notify(ID_BIG_NOTIFICATION, notification);
    }

    public void showSmallNotification(String title, String message, Intent intent) {
        // Vibrate
        setVibrate( mCtx );

        PendingIntent resultPendingIntent = PendingIntent.getActivity(mCtx, ID_SMALL_NOTIFICATION, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mCtx);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mCtx,  mCtx.getString(R.string.app_notification_channel_id));
        Notification notification;
        notification = mBuilder
                .setSmallIcon(R.drawable.launcher)
                .setTicker(title)
                .setWhen(0)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent)
                .setContentTitle(Html.fromHtml(title).toString())
                .setContentText(Html.fromHtml(message).toString())
                .setLargeIcon(BitmapFactory.decodeResource(mCtx.getResources(), R.drawable.launcher))
                .setPriority(Notification.PRIORITY_HIGH)
                .setSound( getNotificationRingtone(mCtx), AudioManager.STREAM_NOTIFICATION )
                .setStyle(new NotificationCompat.BigTextStyle().bigText(Html.fromHtml(message).toString()))
                .build();

        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        NotificationManager notificationManager = (NotificationManager) mCtx.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel(notificationManager);
        }
        notificationManager.notify((int) System.currentTimeMillis(), notification);
    }

    //The method will return Bitmap from an image URL
    private Bitmap getBitmapFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private void createChannel(NotificationManager notificationManager) {
        String name = "notification";
        String description = "Notifications for download status";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel mChannel = new NotificationChannel( mCtx.getString(R.string.app_notification_channel_id), name, importance);
        mChannel.setDescription(description);
        mChannel.enableLights(true);
        mChannel.setShowBadge(true);
        mChannel.setLightColor(Color.BLUE);
        notificationManager.createNotificationChannel(mChannel);
    }

    //////////////////--------------------------------------------------------------------------------------
    private static Uri getNotificationRingtone( Context context ){
        // TODO : Set notification Ring!
        Uri alarmSound = RingtoneManager. getDefaultUri (RingtoneManager.TYPE_NOTIFICATION );
        MediaPlayer mp = MediaPlayer. create ( context, alarmSound );
        mp.start();

        return alarmSound;
    }

    private static void setVibrate( Context context ){
        Vibrator vibrator = (Vibrator) context.getSystemService( Context.VIBRATOR_SERVICE );
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate( VibrationEffect.createOneShot( 200, VibrationEffect.DEFAULT_AMPLITUDE ) );
        }else{
            vibrator.vibrate( 200 );
        }
    }


}
