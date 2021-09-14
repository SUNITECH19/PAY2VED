package com.pay2ved.recharge.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Noti_SharedPreference {

    public static final String PREFS_NAME = "NOTIFICATION_APP";
    public static final String NOTIFICATIONS = "notification";

    public Noti_SharedPreference() {
        super();
    }

    // This four methods are used for maintaining favorites.
    public void saveFavorites(Context context, List<NotificationHub> notificationHubs) {
        SharedPreferences settings;
        Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(notificationHubs);

        editor.putString(NOTIFICATIONS, jsonFavorites);

        editor.commit();
    }

    public void addNotification(Context context, NotificationHub notificationHub) {
        List<NotificationHub> notificationHubs = getFavorites(context);
        if (notificationHubs == null)
            notificationHubs = new ArrayList<NotificationHub>();
        notificationHubs.add(notificationHub);
        saveFavorites(context, notificationHubs);
    }

    public void removeNotification(Context context, NotificationHub notificationHub) {
        ArrayList<NotificationHub> notificationHubs = getFavorites(context);
        if (notificationHubs != null) {
            notificationHubs.remove(notificationHub);
            saveFavorites(context, notificationHubs);
        }
    }

    public void removeAll(Context context) {
        ArrayList<NotificationHub> notificationHubs = getFavorites(context);
        if (notificationHubs != null) {
            notificationHubs.clear();
            saveFavorites(context, notificationHubs);
        }
    }

    public ArrayList<NotificationHub> getFavorites(Context context) {
        SharedPreferences settings;
        List<NotificationHub> notificationHubs;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(NOTIFICATIONS)) {
            String jsonFavorites = settings.getString(NOTIFICATIONS, null);
            Gson gson = new Gson();
            NotificationHub[] favoriteItems = gson.fromJson(jsonFavorites,
                    NotificationHub[].class);

            notificationHubs = Arrays.asList(favoriteItems);
            notificationHubs = new ArrayList<NotificationHub>(notificationHubs);
        } else
            return null;

        return (ArrayList<NotificationHub>) notificationHubs;
    }
}
