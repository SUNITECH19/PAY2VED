package com.pay2ved.recharge.service.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import static com.pay2ved.recharge.other.Constant.DB_NOTIFICATIONS;

@Database(entities = {NotificationModel.class}, version = 1, exportSchema = false)
public abstract class DBNotifications extends RoomDatabase {

    public abstract NotifyDao notifyDao();

    public static DBNotifications dbNotifications;

    public static DBNotifications getInstance(Context context) {
        if (dbNotifications == null) {
            dbNotifications = Room.databaseBuilder(context, DBNotifications.class, DB_NOTIFICATIONS)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return dbNotifications;
    }

}