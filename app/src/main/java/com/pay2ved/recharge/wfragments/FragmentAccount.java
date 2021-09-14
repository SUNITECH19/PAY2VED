package com.pay2ved.recharge.wfragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pay2ved.recharge.R;
import com.pay2ved.recharge.activity.Bank_DetailsActivity;
import com.pay2ved.recharge.activity.ChangePasswordActivity;
import com.pay2ved.recharge.activity.Contact_UsActivity;
import com.pay2ved.recharge.activity.SplashActivity;
import com.pay2ved.recharge.databinding.FragmentAccountBinding;
import com.pay2ved.recharge.gprs_home_activity.My_AccountActivity;
import com.pay2ved.recharge.gprs_home_activity.My_CommissionActivity;
import com.pay2ved.recharge.other.AppsContants;

public class FragmentAccount extends Fragment {
    private static FragmentAccount fragmentAccount;

    public static FragmentAccount getInstance(){
        if (fragmentAccount == null){
            fragmentAccount = new FragmentAccount();
        }
        return fragmentAccount;
    }

    private FragmentAccountBinding accountBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        accountBinding = DataBindingUtil.inflate( inflater, R.layout.fragment_account, container, false );

        onButtonAction();
        setProfile();

        return accountBinding.getRoot();
    }

    private void onButtonAction(){

        // Change Pin..
        accountBinding.layoutChangePinBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ChangePasswordActivity.class);
            startActivity(intent);
        });
        // Commission..
        accountBinding.layoutCommissionBtn.setOnClickListener(v -> {
            Intent intent = new Intent( getContext(), My_CommissionActivity.class);
            startActivity(intent);
        });
        // Contact Us..
        accountBinding.layoutContactUsBtn.setOnClickListener(v -> {
            Intent intent = new Intent( getContext(), Contact_UsActivity.class);
            startActivity(intent);
        });
        // Bank Details..
        accountBinding.layoutBankDetailsBtn.setOnClickListener(v -> {
            Intent intent = new Intent( getContext(), Bank_DetailsActivity.class);
            startActivity(intent);
        });
        // Log Out..
        accountBinding.layoutLogOutBtn.setOnClickListener(v -> {

            AppsContants.sharedpreferences = getActivity().getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
            editor.putString(AppsContants.Mobile, "");
            editor.putString(AppsContants.Password, "");
            editor.commit();

            Intent intent = new Intent(getContext(), SplashActivity.class);
            startActivity(intent);
            getActivity().finishAffinity();
        });

    }

    private void setProfile(){

        AppsContants.sharedpreferences = getActivity().getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        String s_Username = AppsContants.sharedpreferences.getString(AppsContants.Username, "");
        String s_Password = AppsContants.sharedpreferences.getString(AppsContants.Password, "");
        String s_Mobile = AppsContants.sharedpreferences.getString(AppsContants.Mobile, "");
        String s_Uid = AppsContants.sharedpreferences.getString(AppsContants.Uid, "");
        String s_Type = AppsContants.sharedpreferences.getString(AppsContants.Type, "");
        String s_FullName = AppsContants.sharedpreferences.getString(AppsContants.Fullname, "");
        String s_CompanyName = AppsContants.sharedpreferences.getString(AppsContants.CompanyName, "");
        String s_Email = AppsContants.sharedpreferences.getString(AppsContants.Email, "");
        String s_Address = AppsContants.sharedpreferences.getString(AppsContants.Address, "");
        String s_City = AppsContants.sharedpreferences.getString(AppsContants.City, "");
        String s_State = AppsContants.sharedpreferences.getString(AppsContants.State, "");
        String s_Balance = AppsContants.sharedpreferences.getString(AppsContants.Balance, "");

        accountBinding.tvUserName.setText( s_Username );
        accountBinding.tvMobile.setText( s_Mobile );
        accountBinding.tvBalance.setText( s_Balance );
        accountBinding.tvUID.setText( s_Uid );
//        accountBinding.tvUserName.setText( s_Type );
        accountBinding.tvUserName.setText( s_FullName );
        accountBinding.tvBusinessName.setText( s_CompanyName );
        accountBinding.tvEmail.setText( s_Email );
        accountBinding.tvAddress.setText( s_Address );
        accountBinding.tvCityState.setText( s_City + ", " + s_State);

    }


}