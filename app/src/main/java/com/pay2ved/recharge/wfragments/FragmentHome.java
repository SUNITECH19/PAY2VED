package com.pay2ved.recharge.wfragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.pay2ved.recharge.R;
import com.pay2ved.recharge.activity.GprsHomeActivity;
import com.pay2ved.recharge.databinding.FragmentHomeBinding;
import com.pay2ved.recharge.gprs_home_activity.ActivityBillPayments;
import com.pay2ved.recharge.gprs_home_activity.Add_UserActivity;
import com.pay2ved.recharge.gprs_home_activity.DthActivity;
import com.pay2ved.recharge.gprs_home_activity.FundActivity;
import com.pay2ved.recharge.gprs_home_activity.MobileActivity;
import com.pay2ved.recharge.gprs_home_activity.Money_TransActivity;
import com.pay2ved.recharge.gprs_home_activity.Payment_RequiestActivity;
import com.pay2ved.recharge.gprs_home_activity.PostPaidActivity;
import com.pay2ved.recharge.gprs_home_activity.UsersActivity;
import com.pay2ved.recharge.helper.FragmentListener;
import com.pay2ved.recharge.helper.Listener;
import com.pay2ved.recharge.other.AppsContants;
import com.pay2ved.recharge.service.callmodel.CallBalance;
import com.pay2ved.recharge.service.callmodel.CallHomeMessage;
import com.pay2ved.recharge.service.query.DBQuery;
import com.pay2ved.recharge.ui.MyViewPagerImageSet;
import com.pay2ved.recharge.wadaptor.BannerSliderAdaptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.pay2ved.recharge.gprs_home_activity.ActivityBillPayments.BILL_CODE_ELECTRICITY;
import static com.pay2ved.recharge.gprs_home_activity.ActivityBillPayments.BILL_CODE_GAS;
import static com.pay2ved.recharge.gprs_home_activity.ActivityBillPayments.BILL_CODE_INSURANCE;
import static com.pay2ved.recharge.gprs_home_activity.ActivityBillPayments.BILL_CODE_LAND_LINE;
import static com.pay2ved.recharge.gprs_home_activity.ActivityBillPayments.BILL_CODE_WATER;
import static com.pay2ved.recharge.gprs_home_activity.ActivityBillPayments.REQUEST_BILL_CODE;

public class FragmentHome extends Fragment implements Listener.OnHomeInfoListener, Listener.OnLoadBalanceListener, FragmentListener {
    private static FragmentHome fragmentHome;

    private FragmentHome() {
        // Required empty public constructor
    }

    public static FragmentHome getInstance(){
        if (fragmentHome == null){
            fragmentHome = new FragmentHome();
        }
        return fragmentHome;
    }

    // Root View --------------------------------------
    private FragmentHomeBinding fragmentHomeBinding;

    // Ad banner List...
    private static List<CallHomeMessage.ImageInfo> bannerLists = new ArrayList<>();
    private static String alertMessage = null;
    private static String homeBalance = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        fragmentHomeBinding = DataBindingUtil.inflate( inflater, R.layout.fragment_home, container, false );

        // Hide while load banner...
        if (bannerLists == null || bannerLists.size()==0){
//            constraintLayoutSlider.setVisibility(View.GONE);
            DBQuery.queryToHomeInfo( this );
        }else{
            setBannerSliderViewPager( bannerLists );
        }

        // hide tvHomeMessage
        if (alertMessage == null){
            fragmentHomeBinding.tvHomeMessage.setVisibility(View.GONE);
        }else{
            fragmentHomeBinding.tvHomeMessage.setSelected(true);
            fragmentHomeBinding.tvHomeMessage.setVisibility(View.VISIBLE);
            fragmentHomeBinding.tvHomeMessage.setText(alertMessage);
        }

//        DBQuery.queryToLoadBalance( this, "", "");

        setButtonAction();

        return fragmentHomeBinding.getRoot();
    }

    private void setButtonAction() {

        // Refresh Balance...
        fragmentHomeBinding.refreshBalanceButton.setOnClickListener(v -> {
            fragmentHomeBinding.refreshBalanceButton.setVisibility(View.GONE);
            fragmentHomeBinding.progressBalance.setVisibility(View.VISIBLE);
            // TODO : Query to load balance...

        });

        // Mobile
        fragmentHomeBinding.layoutMobileBtn.setOnClickListener(v -> {

            AppsContants.sharedpreferences = getActivity().getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
            editor.putString(AppsContants.Phone, "");
            editor.putString(AppsContants.Spinner_list, "MOBILE");
            editor.putString(AppsContants.Title, "Mobile");
            editor.putString(AppsContants.amount, "");
            editor.commit();

            MobileActivity.s_user_name = AppsContants.sharedpreferences.getString(AppsContants.Mob_name, "");
            MobileActivity.s_user_code = AppsContants.sharedpreferences.getString(AppsContants.Mob_code, "");
            MobileActivity.s_user_remark = AppsContants.sharedpreferences.getString(AppsContants.Mob_remark, "");
            MobileActivity.s_user_hint1 = AppsContants.sharedpreferences.getString(AppsContants.Mob_hint1, "");
            MobileActivity.s_user_hint2 = AppsContants.sharedpreferences.getString(AppsContants.Mob_hint2, "");
            MobileActivity.s_user_hint3 = AppsContants.sharedpreferences.getString(AppsContants.Mob_hint3, "");
            MobileActivity.s_user_icon = AppsContants.sharedpreferences.getString(AppsContants.Mob_icon, "");

            Intent intent = new Intent( getContext(), MobileActivity.class);
            startActivity(intent);
        });
        // DTH
        fragmentHomeBinding.layoutDTHBtn.setOnClickListener(v -> {

            AppsContants.sharedpreferences = getContext().getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
            editor.putString(AppsContants.Spinner_list, "DTH");
            editor.putString(AppsContants.Title, "DTH");
            editor.commit();

            DthActivity.s_user_name = AppsContants.sharedpreferences.getString(AppsContants.Dth_name, "");
            DthActivity.s_user_code = AppsContants.sharedpreferences.getString(AppsContants.Dth_code, "");
            DthActivity.s_user_remark = AppsContants.sharedpreferences.getString(AppsContants.Dth_remark, "");
            DthActivity.s_user_hint1 = AppsContants.sharedpreferences.getString(AppsContants.Dth_hint1, "");
            DthActivity.s_user_hint2 = AppsContants.sharedpreferences.getString(AppsContants.Dth_hint2, "");
            DthActivity.s_user_hint3 = AppsContants.sharedpreferences.getString(AppsContants.Dth_hint3, "");
            DthActivity.s_user_icon = AppsContants.sharedpreferences.getString(AppsContants.Dth_icon, "");

            Intent intent = new Intent( getContext(), DthActivity.class);
            startActivity(intent);
        });
        // DataCard
        fragmentHomeBinding.layoutDataCardBtn.setOnClickListener(v -> {

            AppsContants.sharedpreferences = getActivity().getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
            editor.putString(AppsContants.Phone, "");
            editor.putString(AppsContants.Spinner_list, "DATACARD");
            editor.putString(AppsContants.Title, "Datacard");
            editor.putString(AppsContants.amount, "");
            editor.commit();

            MobileActivity.s_user_name = AppsContants.sharedpreferences.getString(AppsContants.Data_name, "");
            MobileActivity.s_user_code = AppsContants.sharedpreferences.getString(AppsContants.Data_code, "");
            MobileActivity.s_user_remark = AppsContants.sharedpreferences.getString(AppsContants.Data_remark, "");
            MobileActivity.s_user_hint1 = AppsContants.sharedpreferences.getString(AppsContants.Data_hint1, "");
            MobileActivity.s_user_hint2 = AppsContants.sharedpreferences.getString(AppsContants.Data_hint2, "");
            MobileActivity.s_user_hint3 = AppsContants.sharedpreferences.getString(AppsContants.Data_hint3, "");
            MobileActivity.s_user_icon = AppsContants.sharedpreferences.getString(AppsContants.Data_icon, "");

            Intent intent = new Intent(getContext(), MobileActivity.class);
            startActivity(intent);
        });
        // PostPaid
        fragmentHomeBinding.layoutPostPaidBtn.setOnClickListener(v -> {
            AppsContants.sharedpreferences = getActivity().getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
            editor.putString(AppsContants.Phone, "");
            editor.putString(AppsContants.Spinner_list, "POSTPAID");
            editor.putString(AppsContants.Title, "Postpaid");
            editor.commit();

            PostPaidActivity.s_user_name = AppsContants.sharedpreferences.getString(AppsContants.Post_name, "");
            PostPaidActivity.s_user_code = AppsContants.sharedpreferences.getString(AppsContants.Post_code, "");
            PostPaidActivity.s_user_remark = AppsContants.sharedpreferences.getString(AppsContants.Post_remark, "");
            PostPaidActivity.s_user_hint1 = AppsContants.sharedpreferences.getString(AppsContants.Post_hint1, "");
            PostPaidActivity.s_user_hint2 = AppsContants.sharedpreferences.getString(AppsContants.Post_hint2, "");
            PostPaidActivity.s_user_hint3 = AppsContants.sharedpreferences.getString(AppsContants.Post_hint3, "");
            PostPaidActivity.s_user_icon = AppsContants.sharedpreferences.getString(AppsContants.Post_icon, "");

            Intent intent = new Intent(getContext(), PostPaidActivity.class);
            startActivity(intent);
        });
        // LandLine
        fragmentHomeBinding.layoutLandLineBtn.setOnClickListener(v -> {

                /*
                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.Spinner_list, "LANDLINE");
                editor.putString(AppsContants.Title, "Landline");
                editor.putString(AppsContants.amount, "");
                editor.commit();

                LandLineActivity.s_user_name = AppsContants.sharedpreferences.getString(AppsContants.Land_name, "");
                LandLineActivity.s_user_code = AppsContants.sharedpreferences.getString(AppsContants.Land_code, "");
                LandLineActivity.s_user_remark = AppsContants.sharedpreferences.getString(AppsContants.Land_remark, "");
                LandLineActivity.s_user_hint1 = AppsContants.sharedpreferences.getString(AppsContants.Land_hint1, "");
                LandLineActivity.s_user_hint2 = AppsContants.sharedpreferences.getString(AppsContants.Land_hint2, "");
                LandLineActivity.s_user_hint3 = AppsContants.sharedpreferences.getString(AppsContants.Land_hint3, "");
                LandLineActivity.s_user_icon = AppsContants.sharedpreferences.getString(AppsContants.Land_icon, "");

                Intent intent = new Intent(GprsHomeActivity.this, LandLineActivity.class);
                startActivity(intent);

                 */

            Intent intent = new Intent( getContext(), ActivityBillPayments.class);
            intent.putExtra( REQUEST_BILL_CODE, BILL_CODE_LAND_LINE );
            startActivity(intent);
        });
        // Electricity
        fragmentHomeBinding.layoutElectricityBtn.setOnClickListener(v -> {
             /*
                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.Spinner_list, "ELECTRICITY");
                editor.putString(AppsContants.Title, "Electricity");
                editor.putString(AppsContants.amount, "");
                editor.commit();

                ElectricityActivity.s_user_name = AppsContants.sharedpreferences.getString(AppsContants.Elec_name, "");
                ElectricityActivity.s_user_code = AppsContants.sharedpreferences.getString(AppsContants.Elec_code, "");
                ElectricityActivity.s_user_remark = AppsContants.sharedpreferences.getString(AppsContants.Elec_remark, "");
                ElectricityActivity.s_user_hint1 = AppsContants.sharedpreferences.getString(AppsContants.Elec_hint1, "");
                ElectricityActivity.s_user_hint2 = AppsContants.sharedpreferences.getString(AppsContants.Elec_hint2, "");
                ElectricityActivity.s_user_hint3 = AppsContants.sharedpreferences.getString(AppsContants.Elec_hint3, "");
                ElectricityActivity.s_user_icon = AppsContants.sharedpreferences.getString(AppsContants.Elec_icon, "");

                 */

            Intent intent = new Intent(getContext(), ActivityBillPayments.class);
            intent.putExtra( REQUEST_BILL_CODE, BILL_CODE_ELECTRICITY );
            startActivity(intent);
        });

        // Water ------------------------------------
        fragmentHomeBinding.layoutWaterBtn.setOnClickListener(v -> {

                /*
                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.Spinner_list, "WATER");
                editor.putString(AppsContants.Title, "Water");
                editor.putString(AppsContants.amount, "");
                editor.commit();

                GasActivity.s_user_name = AppsContants.sharedpreferences.getString(AppsContants.Water_name, "");
                GasActivity.s_user_code = AppsContants.sharedpreferences.getString(AppsContants.Water_code, "");
                GasActivity.s_user_remark = AppsContants.sharedpreferences.getString(AppsContants.Water_remark, "");
                GasActivity.s_user_hint1 = AppsContants.sharedpreferences.getString(AppsContants.Water_hint1, "");
                GasActivity.s_user_hint2 = AppsContants.sharedpreferences.getString(AppsContants.Water_hint2, "");
                GasActivity.s_user_hint3 = AppsContants.sharedpreferences.getString(AppsContants.Water_hint3, "");
                GasActivity.s_user_icon = AppsContants.sharedpreferences.getString(AppsContants.Water_icon, "");

                Intent intent = new Intent(GprsHomeActivity.this, GasActivity.class);
                startActivity(intent);

                 */

            Intent intent = new Intent(getContext(), ActivityBillPayments.class);
            intent.putExtra( REQUEST_BILL_CODE, BILL_CODE_WATER );
            startActivity(intent);
        });
        // Insurance
        fragmentHomeBinding.layoutInsuranceBtn.setOnClickListener(v -> {

                /*
                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.Spinner_list, "INSURANCE");
                editor.putString(AppsContants.Title, "Insurance");
                editor.commit();

                InsuranceActivity.s_user_name = AppsContants.sharedpreferences.getString(AppsContants.Ins_name, "");
                InsuranceActivity.s_user_code = AppsContants.sharedpreferences.getString(AppsContants.Ins_code, "");
                InsuranceActivity.s_user_remark = AppsContants.sharedpreferences.getString(AppsContants.Ins_remark, "");
                InsuranceActivity.s_user_hint1 = AppsContants.sharedpreferences.getString(AppsContants.Ins_hint1, "");
                InsuranceActivity.s_user_hint2 = AppsContants.sharedpreferences.getString(AppsContants.Ins_hint2, "");
                InsuranceActivity.s_user_hint3 = AppsContants.sharedpreferences.getString(AppsContants.Ins_hint3, "");
                InsuranceActivity.s_user_icon = AppsContants.sharedpreferences.getString(AppsContants.Ins_icon, "");

                Intent intent = new Intent(GprsHomeActivity.this, InsuranceActivity.class);
                startActivity(intent);

                 */

            Intent intent = new Intent(getContext(), ActivityBillPayments.class);
            intent.putExtra( REQUEST_BILL_CODE, BILL_CODE_INSURANCE );
            startActivity(intent);
        });
        // Gas Bill
        fragmentHomeBinding.layoutGasBillBtn.setOnClickListener(v -> {

                /*
                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.Spinner_list, "GAS");
                editor.putString(AppsContants.Title, "Gas Bill");
                editor.putString(AppsContants.amount, "");
                editor.commit();

                GasActivity.s_user_name = AppsContants.sharedpreferences.getString(AppsContants.Gas_name, "");
                GasActivity.s_user_code = AppsContants.sharedpreferences.getString(AppsContants.Gas_code, "");
                GasActivity.s_user_remark = AppsContants.sharedpreferences.getString(AppsContants.Gas_remark, "");
                GasActivity.s_user_hint1 = AppsContants.sharedpreferences.getString(AppsContants.Gas_hint1, "");
                GasActivity.s_user_hint2 = AppsContants.sharedpreferences.getString(AppsContants.Gas_hint2, "");
                GasActivity.s_user_hint3 = AppsContants.sharedpreferences.getString(AppsContants.Gas_hint3, "");
                GasActivity.s_user_icon = AppsContants.sharedpreferences.getString(AppsContants.Gas_icon, "");

                Intent intent = new Intent(GprsHomeActivity.this, GasActivity.class);
                startActivity(intent);

                 */

            Intent intent = new Intent(getContext(), ActivityBillPayments.class);
            intent.putExtra( REQUEST_BILL_CODE, BILL_CODE_GAS );
            startActivity(intent);
        });
        // Money Transfer
        fragmentHomeBinding.layoutMoneyTrsBtn.setOnClickListener(v -> {
            AppsContants.sharedpreferences = getActivity().getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
            editor.putString(AppsContants.Phone, "");
            editor.putString(AppsContants.Spinner_list, "MONEY TRANSFER");
            editor.putString(AppsContants.Title, "Money Transfer");
            editor.commit();

            AppsContants.sharedpreferences = getActivity().getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
            String s_Money_Tran = AppsContants.sharedpreferences.getString(AppsContants.Money_Tran, "");
            String s_Money_Valid = AppsContants.sharedpreferences.getString(AppsContants.Money_Valid, "");

            Intent intent = new Intent(getContext(), Money_TransActivity.class);
            startActivity(intent);
        });
        // Payment Transfer
        fragmentHomeBinding.layoutPaymentTrsBtn.setOnClickListener(v -> {

            AppsContants.sharedpreferences = getActivity().getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
            editor.putString(AppsContants.Phone, "");
            editor.putString(AppsContants.U_id, "");
            editor.putString(AppsContants.Balance, "");
            editor.commit();

            Intent intent = new Intent( getContext(), FundActivity.class);
            startActivity(intent);
        });
        // Payment Request
        fragmentHomeBinding.layoutPaymentReqBtn.setOnClickListener(v -> {

            Intent intent = new Intent( getContext(), Payment_RequiestActivity.class);
            startActivity(intent);

        });

        // Users  ------------------------------
        fragmentHomeBinding.layoutUsersBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), UsersActivity.class);
            startActivity(intent);
        });
        // Add Retails
        fragmentHomeBinding.layoutAddRetailersBtn.setOnClickListener(v -> {
            AppsContants.sharedpreferences = getActivity().getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
            editor.putString(AppsContants.Phone, "");
            editor.commit();

            Intent intent = new Intent(getContext(), Add_UserActivity.class);
            startActivity(intent);
        });


    }

    // --- Banner Slider -------------------------------------------------------
    private int BANNER_CURRENT_PAGE;
    private Timer timer;
    @SuppressLint("ClickableViewAccessibility")
    private void setBannerSliderViewPager(final List<CallHomeMessage.ImageInfo> listBannerSliders) {
        if (listBannerSliders.size() > 0){
            fragmentHomeBinding.constraintLayoutSlider.setVisibility( View.VISIBLE );
        }else{
            fragmentHomeBinding.constraintLayoutSlider.setVisibility( View.GONE );
            return;
        }
        BANNER_CURRENT_PAGE = 1;
        if (timer != null){
            timer.cancel();
        }

        BannerSliderAdaptor bannerSliderAdaptor = new BannerSliderAdaptor( this, listBannerSliders );
        fragmentHomeBinding.myViewPagerSlider.setAdapter( bannerSliderAdaptor );
        fragmentHomeBinding.myViewPagerSlider.setClipToPadding( false );
//            bannerSliderViewPager.setPageMargin( 10 );
        fragmentHomeBinding.myViewPagerSlider.setCurrentItem( BANNER_CURRENT_PAGE );
        fragmentHomeBinding.tabLayoutSliderIndicator.setupWithViewPager( fragmentHomeBinding.myViewPagerSlider, true );
        // if Size greater than 2...
        if (listBannerSliders.size()>1){
            ViewPager.OnPageChangeListener viewPagerOnPageChange = new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }
                @Override
                public void onPageSelected(int position) {
                    BANNER_CURRENT_PAGE = position;
                }
                @Override
                public void onPageScrollStateChanged(int state) {
                    if (state == ViewPager.SCROLL_STATE_IDLE) {
                        // To set infinity Looper
                    }
                }
            };
            fragmentHomeBinding.myViewPagerSlider.addOnPageChangeListener( viewPagerOnPageChange );
            startBannerSliderAnim( listBannerSliders );
        }

        fragmentHomeBinding.myViewPagerSlider.setOnTouchListener((view, motionEvent) -> {
            // To set infinity Looper
            if (timer != null){
                timer.cancel();
            }
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                startBannerSliderAnim( listBannerSliders );
            }
            return false;
        });

        fragmentHomeBinding.myViewPagerSlider.setOnClickListener((View.OnClickListener) v -> {
            // TODO : OnClick Banner...
        });

        //------ View Pager for Banner Slider...
    }

    private void startBannerSliderAnim(final List <CallHomeMessage.ImageInfo > bannerSliderModelList) {
        final Handler handler = new Handler();
        final Runnable update = () -> {
            if (fragmentHomeBinding.myViewPagerSlider.getCurrentItem() < bannerSliderModelList.size() - 1) {
                fragmentHomeBinding.myViewPagerSlider.setCurrentItem(fragmentHomeBinding.myViewPagerSlider.getCurrentItem() + 1);
                BANNER_CURRENT_PAGE++;
            } else {
                fragmentHomeBinding.myViewPagerSlider.setCurrentItem(0);
                BANNER_CURRENT_PAGE = 0;
            }
        };
        timer = new Timer();
        long PERIOD_TIME = 1500;
        long DELAY_TIME = 2000;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post( update );
            }
        }, DELAY_TIME, PERIOD_TIME);
    }

    // --- Banner Slider -------------------------------------------------------

    @Override
    public void onLoadHomeInfo(@Nullable CallHomeMessage homeMessage) {
        if (homeMessage != null){
            Log.e("MSG", homeMessage.getText() );
            if (homeMessage.getText() != null && !homeMessage.getText().trim().equals("")){
                alertMessage = homeMessage.getText();
                fragmentHomeBinding.tvHomeMessage.setSelected(true);
                fragmentHomeBinding.tvHomeMessage.setVisibility(View.VISIBLE);
                fragmentHomeBinding.tvHomeMessage.setText(alertMessage);
            }else{
                fragmentHomeBinding.tvHomeMessage.setVisibility(View.GONE);
            }

            if (homeMessage.getData() != null){
                bannerLists = homeMessage.getData();
                fragmentHomeBinding.constraintLayoutSlider.setVisibility(View.VISIBLE);
                setBannerSliderViewPager( bannerLists );
            }else{
                fragmentHomeBinding.constraintLayoutSlider.setVisibility(View.GONE);
                showToast( homeMessage.getText() );
            }
        }
    }

    @Override
    public void onLoadBalance(@Nullable CallBalance callBalance) {
        fragmentHomeBinding.refreshBalanceButton.setVisibility(View.VISIBLE);
        fragmentHomeBinding.progressBalance.setVisibility(View.GONE);

        if (callBalance != null){
            if (callBalance.getData() != null){
                homeBalance = callBalance.getData().getBalance();
                //  Set Balance...
                if (homeBalance.equals("")) {
                    fragmentHomeBinding.tvBalance.setText("₹" + " " + "0.00");
                } else {
                    fragmentHomeBinding.tvBalance.setText("₹" + " " + homeBalance);
                }
            }else{
                // Show Msg
                showToast(callBalance.getMessage());
            }
        }
    }

    @Override
    public void showDialog() {

    }

    @Override
    public void dismissDialog() {

    }

    @Override
    public void showToast(String msg) {

    }


}