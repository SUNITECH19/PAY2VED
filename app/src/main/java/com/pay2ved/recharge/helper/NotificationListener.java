package com.pay2ved.recharge.helper;

import com.pay2ved.recharge.service.room.NotificationModel;

public interface NotificationListener {

    void onNotifyClick(NotificationModel notificationModel);

    void onDeleteItem(NotificationModel notificationModel, int position );

}
