package com.pay2ved.recharge.util;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.pay2ved.recharge.R;
import com.pay2ved.recharge.adapter.NotificationAdaptor;
import com.pay2ved.recharge.fragments.NotificationViewDialog;
import com.pay2ved.recharge.helper.NotificationListener;
import com.pay2ved.recharge.service.room.DBNotifications;
import com.pay2ved.recharge.service.room.NotificationModel;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity implements NotificationListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        initView();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private static NotificationAdaptor notificationAdaptor;
    private static List<NotificationModel> notificationModelList = new ArrayList<>();

    private ImageButton backBtn;
    private RecyclerView recyclerView;
    private TextView noDataText;

    private void initView(){
        backBtn = findViewById( R.id.backBtn );
        recyclerView = findViewById( R.id.recyclerViewNotifications );
        noDataText = findViewById( R.id.textViewNoNotifications );


        notificationModelList.clear();
        notificationModelList.addAll( DBNotifications.getInstance( this ).notifyDao().getList() );

        notificationAdaptor = new NotificationAdaptor( this, notificationModelList );
        recyclerView.setAdapter( notificationAdaptor );
        notificationAdaptor.notifyDataSetChanged();

        setNoNotificationText();
    }

    public static void setNotificationItem(NotificationModel notificationItem){
        notificationModelList.add( 0, notificationItem );
        if (notificationAdaptor != null)
            notificationAdaptor.notifyDataSetChanged();
    }

    private void setNoNotificationText(){
        if (noDataText != null)
            if (notificationModelList.size() == 0){
                noDataText.setVisibility(View.VISIBLE);
            }else{
                noDataText.setVisibility(View.GONE);
            }
    }

    @Override
    public void onNotifyClick(NotificationModel notificationModel) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment prev = fragmentManager.findFragmentByTag("SHOW_NOTIFY");
        if (prev != null) {
            ft.remove(prev);
        }
        NotificationViewDialog fragment = new NotificationViewDialog( notificationModel );
        fragment.show( fragmentManager, "SHOW_NOTIFY");
    }

    @Override
    public void onDeleteItem(NotificationModel modelNotification, int position) {
        DBNotifications.getInstance( this ).notifyDao().delete( modelNotification );

        if (notificationModelList.contains( modelNotification )){
            notificationModelList.remove( modelNotification );
        }
        if (notificationAdaptor != null)
            notificationAdaptor.notifyItemRemoved( position );

        setNoNotificationText();
    }


}
