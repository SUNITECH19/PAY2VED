package com.pay2ved.recharge.service.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NotifyDao {

    //---------------------

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert( NotificationModel notification );

    @Delete()
    void delete(NotificationModel notification);

    @Query("DELETE FROM NOTIFICATIONS WHERE id = :id")
    void delete(String id);
    //
    @Query("SELECT * FROM NOTIFICATIONS ORDER BY id DESC")
    List<NotificationModel> getList( );

}