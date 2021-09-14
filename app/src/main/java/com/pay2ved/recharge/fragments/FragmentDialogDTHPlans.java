package com.pay2ved.recharge.fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.pay2ved.recharge.R;
import com.pay2ved.recharge.adapter.FragmentDTHPagerAdapter;
import com.pay2ved.recharge.helper.Listener;
import com.pay2ved.recharge.other.HttpUrlPath;
import com.pay2ved.recharge.service.callmodel.CallDTHPlans;
import com.pay2ved.recharge.service.query.DBQuery;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By Shailendra on 01-06-2021
 * ( http://linktr.ee/wackycodes )
 */
public class FragmentDialogDTHPlans extends Fragment implements Listener.OnItemSelectedListener, Listener.OnDTHPlansListener {

    private Listener.OnItemSelectedListener parentListener;
    private String s_Username;
    private String s_Password;
    private String s_provider;

    public FragmentDialogDTHPlans( String s_Username, String s_Password, String s_provider, Listener.OnItemSelectedListener parentListener ) {
        this.s_Username = s_Username;
        this.s_Password = s_Password;
        this.s_provider = s_provider;
        this.parentListener = parentListener;
    }

    private static String tempProvider = null;
    public static List<CallDTHPlans.DTHPlanItem> dthPlanItemList = new ArrayList<>();

    private FragmentDTHPagerAdapter adapter;

    // Variables...
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private ImageView backImage;

    private ProgressDialog pDialog;

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setStyle(DialogFragment.STYLE_NORMAL, R.style.fullScreenDialog);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dialog_d_t_h_plans, container, false);

        tabLayout = view.findViewById(R.id.tabLayout );
        viewPager = view.findViewById(R.id.viewPager );
        backImage = view.findViewById(R.id.img_back );

        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentListener.onItemSelected( null, null);
            }
        });

        if (tempProvider == null){
            dthPlanItemList.clear();
            tempProvider = s_provider;
        }else if ( !tempProvider.equals( s_provider ) ){
            dthPlanItemList.clear();
            tempProvider = s_provider;
        }

        if (dthPlanItemList != null && dthPlanItemList.size() > 0){
            // Set Data...
            setTabLayout();
        }else{
            // Load Data...
            showDialog();
            DBQuery.queryToGetDTHPlansList( this, s_Username, s_Password, s_provider );
        }
        return view;
    }


    private void setTabLayout( ){
        //  : Create and Set List...
        List<String> tabList = new ArrayList<>();
        List<FragmentDTHTab> fragmentList = new ArrayList<>();

        for (CallDTHPlans.DTHPlanItem item : dthPlanItemList){
            tabList.add( item.getCategory() );
            fragmentList.add( new FragmentDTHTab( this, item.getPlan() ));
        }

        adapter = new FragmentDTHPagerAdapter( getChildFragmentManager(), tabList, fragmentList );

        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(adapter);

        setAction( tabLayout );

    }

    private void setAction(TabLayout tabLayout){
        viewPager.addOnPageChangeListener(
                new TabLayout.TabLayoutOnPageChangeListener(tabLayout) );

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem( tab.getPosition() );
                adapter.setCurrentItem( tab.getPosition() );
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }
            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        } );
    }

    private void showDialog(){
        if (pDialog == null ){
            pDialog = new ProgressDialog( getContext() );
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
        }
        pDialog.show();
    }
    private void dismissDialog(){
        if (pDialog!=null && pDialog.isShowing()){
            pDialog.dismiss();
        }
    }

    @Override
    public void onItemSelected(CallDTHPlans.Amount amount, String planName) {
        parentListener.onItemSelected( amount, planName );
    }

    @Override
    public void onDTHPlansLoad(@Nullable CallDTHPlans callDTHPlans) {
        dismissDialog();
        if (callDTHPlans!= null){
            dthPlanItemList.clear();
            dthPlanItemList.addAll( callDTHPlans.getData() );
            setTabLayout();
        }else {
            Toast.makeText(getContext(), "Something Went Wrong!", Toast.LENGTH_SHORT).show();
        }
    }


}