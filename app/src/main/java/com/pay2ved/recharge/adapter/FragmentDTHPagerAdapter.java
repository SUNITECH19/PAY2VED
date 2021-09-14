package com.pay2ved.recharge.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.pay2ved.recharge.fragments.FragmentDTHTab;
import com.pay2ved.recharge.fragments.FragmentDialogDTHPlans;
import com.pay2ved.recharge.helper.Listener;

import java.util.ArrayList;
import java.util.List;


/**
 * Created By Shailendra on 01-06-2021
 */
public class FragmentDTHPagerAdapter extends FragmentPagerAdapter {

    private List<String> tabList;
    private List<FragmentDTHTab> fragmentList;

    public FragmentDTHPagerAdapter(@NonNull FragmentManager fm,  List<String> tabList, List<FragmentDTHTab> fragmentList ) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.tabList = tabList;
        this.fragmentList = fragmentList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
//        return  new FragmentDTHTab( parentListener , dthPlanItemList.get( position ).plan );
        return fragmentList.get( position );
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabList.get(position);
    }

    @Override
    public int getCount() {
        return tabList.size();
    }

    // To Lazy Loading....
    public void setCurrentItem( int position ){
//        if( fragmentList.get( position ) instanceof FragmentDTHTab){
//            ((FragmentDTHTab)fragmentList.get( position)).setUIData( );
//        }
    }

}
