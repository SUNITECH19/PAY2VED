package com.pay2ved.recharge.wactivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.pay2ved.recharge.R;
import com.pay2ved.recharge.activity.GprsHomeActivity;
import com.pay2ved.recharge.databinding.ActivityHomeBinding;
import com.pay2ved.recharge.gprs_home_activity.ReportsActivity;
import com.pay2ved.recharge.helper.Listener;
import com.pay2ved.recharge.other.ConnectionCheck;
import com.pay2ved.recharge.sms_home_activity.SettingsActivity;
import com.pay2ved.recharge.util.NotificationActivity;
import com.pay2ved.recharge.wfragments.FragmentAccount;
import com.pay2ved.recharge.wfragments.FragmentHome;
import com.pay2ved.recharge.wfragments.FragmentReport;

import static androidx.recyclerview.widget.RecyclerView.EdgeEffectFactory.DIRECTION_LEFT;
import static androidx.recyclerview.widget.RecyclerView.EdgeEffectFactory.DIRECTION_RIGHT;

public class ActivityHome extends AppCompatActivity implements
        NavigationBarView.OnItemSelectedListener, Listener.ConnectionListener {
/*
    Task for this activity -
    1. Show No Internet Alert Message..
    2. Notification Counts
    3. Set Different Fragment based on user action...
        - FragmentHome
        - FragmentAccount
        - FragmentReport
     Bottom Nav Action..
     */

    // Variables..
//    private FrameLayout frameLayoutMain;
//    private BottomNavigationView bottomNavigationView;

    // Temp Variables...
    private HomeCrrFragment crrFragment = null;

    private ActivityHomeBinding activityHomeBinding;

    public static Listener.ConnectionListener connectionListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        activityHomeBinding = DataBindingUtil.setContentView( this, R.layout.activity_home );

//        textViewNoInternet = findViewById(R.id.textViewNoInternet);

        setToolbar();

        activityHomeBinding.bottomNavigationView.setOnItemSelectedListener(this);
        activityHomeBinding.bottomNavigationView.setSelectedItemId( R.id.bottom_nav_home );

        ConnectionCheck connectionCheck = new ConnectionCheck(this, this);
        onConnectChange( connectionCheck.isInternetConnected( this ));
    }


    private void setToolbar(){
        activityHomeBinding.include.appToolbar.setTitleMargin( 100, 0, 0, 0);
        setSupportActionBar( activityHomeBinding.include.appToolbar );
        try {
            getSupportActionBar().setTitle("Pay2ved");
//            getSupportActionBar().setLogo(getResources().getDrawable( R.drawable.logo ));
        }catch (NullPointerException ignored){ }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // To Home ToolBar Options
        getMenuInflater().inflate( R.menu.home_menu_items,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if ( id == R.id.menu_notifications ){
            //
            startActivity( new Intent( ActivityHome.this, NotificationActivity.class ));
            return true;
        }else
        if ( id == R.id.menu_settings ){
            Intent intent = new Intent(ActivityHome.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected( item );
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if ( id == R.id.bottom_nav_home ){
            if (crrFragment == null){
                setFrameLayoutMain( FragmentHome.getInstance() );
                crrFragment = HomeCrrFragment.FRAGMENT_HOME;
            }else if (crrFragment == HomeCrrFragment.FRAGMENT_HOME){
                return true;
            }else{
                replaceFragment( FragmentHome.getInstance(), getAnimationDirection( HomeCrrFragment.FRAGMENT_HOME) );
            }
            return true;
        }else
            if( id == R.id.bottom_nav_account ){
                if (crrFragment == HomeCrrFragment.FRAGMENT_ACCOUNT){
                    return true;
                }
                replaceFragment( FragmentAccount.getInstance(), getAnimationDirection( HomeCrrFragment.FRAGMENT_ACCOUNT) );
                /*
                Intent intent = new Intent(ActivityHome.this, My_AccountActivity.class);
                startActivity(intent);
                 */
            return true;
        }else
            if( id == R.id.bottom_nav_reports){
                if (crrFragment == HomeCrrFragment.FRAGMENT_REPORT){
                    return true;
                }
                replaceFragment( FragmentReport.getInstance(), getAnimationDirection( HomeCrrFragment.FRAGMENT_REPORT) );
                return true;
                /*
                Intent intent = new Intent(ActivityHome.this, ReportsActivity.class);
                startActivity(intent);
                 */
        }

        return false;
    }

    private int getAnimationDirection( HomeCrrFragment nextFragment ){
        switch (crrFragment){
            case FRAGMENT_HOME:
                crrFragment = nextFragment;
                return DIRECTION_RIGHT;
            case FRAGMENT_ACCOUNT:
                crrFragment = nextFragment;
                if (nextFragment == HomeCrrFragment.FRAGMENT_REPORT){
                    return DIRECTION_RIGHT;
                }else{
                    return DIRECTION_LEFT;
                }
            case FRAGMENT_REPORT:
                crrFragment = nextFragment;
                return DIRECTION_LEFT;
        }
        return -1;
    }

    private void setFrameLayoutMain(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add( activityHomeBinding.frameLayoutMain.getId(), fragment );
        fragmentTransaction.commit();
    }

    private void replaceFragment( Fragment fragment, int animationDirection ){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (animationDirection == DIRECTION_LEFT){
            fragmentTransaction.setCustomAnimations( R.anim.slide_from_left, R.anim.slide_out_from_right  );
        }else{
            fragmentTransaction.setCustomAnimations( R.anim.slide_from_right, R.anim.slide_out_from_left  );
        }
        fragmentTransaction.replace( activityHomeBinding.frameLayoutMain.getId(), fragment );
        fragmentTransaction.commit();
    }

    @Override
    public void onConnectChange(boolean isConnected) {
        runOnUiThread(() -> {
            if (isConnected){
                activityHomeBinding.textViewNoInternet.setText("Connection is Back...");
                activityHomeBinding.textViewNoInternet.setBackgroundColor(getResources().getColor(R.color.green));
                new CountDownTimer(2500, 1000) {
                    public void onTick(long millisUntilFinished) { }
                    public void onFinish() {
                        activityHomeBinding.textViewNoInternet.setVisibility(View.GONE);
                    }
                }.start();
            }else {
                activityHomeBinding.textViewNoInternet.setVisibility(View.VISIBLE);
                activityHomeBinding.textViewNoInternet.setText("No Internet Connection!");
                activityHomeBinding.textViewNoInternet.setBackgroundColor(getResources().getColor(R.color.red));
            }
        });
    }


    // Constant Val...
    private enum HomeCrrFragment{
        FRAGMENT_HOME,
        FRAGMENT_ACCOUNT,
        FRAGMENT_REPORT
    }


}
