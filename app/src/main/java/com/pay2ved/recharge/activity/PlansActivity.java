package com.pay2ved.recharge.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.pay2ved.recharge.R;
import com.pay2ved.recharge.helper.Listener;
import com.pay2ved.recharge.other.AppsContants;
import com.pay2ved.recharge.other.ConnectionDetector;
import com.pay2ved.recharge.other.HttpUrlPath;
import com.pay2ved.recharge.service.callmodel.CallPlans;
import com.pay2ved.recharge.service.query.DBQuery;
import com.pay2ved.recharge.wfragments.FragmentPlanList;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PlansActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, Listener.OnPlansListener {

    ImageView img_back;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ConnectionDetector cd;
    private ProgressDialog pDialog;
    String s_Username = "", s_Password = "", s_Operator_code = "", s_Circle_code = "", ss_position = "", ss_tab = "";
//    ViewPagerAdapter adapter;
    int selectedTabPosition;

    // ----- Variable.
    private List<CallPlans.ModelPlanCategory> planCategoryList = new ArrayList<>();
    private TabAdaptor tabAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plans);

        pDialog = new ProgressDialog(PlansActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        s_Username = AppsContants.sharedpreferences.getString(AppsContants.Username, "");
        s_Password = AppsContants.sharedpreferences.getString(AppsContants.Password, "");
        s_Operator_code = AppsContants.sharedpreferences.getString(AppsContants.Operator_code, "");
        s_Circle_code = AppsContants.sharedpreferences.getString(AppsContants.Circle_code, "");

//        System.out.println( "s_Username : " + s_Username );
//        System.out.println( "s_Password : " + s_Password );
//        System.out.println( "s_Operator_code : " + s_Operator_code );
//        System.out.println( "s_Circle_code : " + s_Circle_code );

        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(this);

        signUpClick();

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                viewPager.setCurrentItem(tab.getPosition());
                selectedTabPosition = viewPager.getCurrentItem();
                Log.d("Selected", "Selected " + tab.getPosition());


                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.Pager_Position, String.valueOf(position));
                editor.commit();

                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                ss_position = AppsContants.sharedpreferences.getString(AppsContants.Pager_Position, "");

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.d("Unselected", "Unselected " + tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    //=============================================================

    public void signUpClick() {
        cd = new ConnectionDetector(PlansActivity.this);
        if (!cd.isConnectingToInternet()) {

            Toast.makeText(PlansActivity.this, "noNetworkConnection", Toast.LENGTH_LONG).show();
        } else {
            pDialog.show();
            DBQuery.queryToGetPlansList( this, s_Username, s_Password, s_Operator_code, s_Circle_code );
//            new GetContacts_commodity("").execute();
        }

    }

    @Override
    public void onLoadPlanList(@Nullable CallPlans callPlans) {
        pDialog.dismiss();
        if (callPlans!=null){
            if (callPlans.getData()!=null){
                planCategoryList.addAll( callPlans.getData() );
                // : Update Adaptor..
                tabAdaptor = new TabAdaptor( getSupportFragmentManager(), planCategoryList );
                viewPager.setAdapter(tabAdaptor);
                viewPager.setOffscreenPageLimit(planCategoryList.size());

            }else{
                Toast.makeText(this, callPlans.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }else Toast.makeText(this, "Failed! Something Went wrong!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public class TabAdaptor extends FragmentStatePagerAdapter {
        private List<CallPlans.ModelPlanCategory> tabCounts;
        private List<Fragment> fragmentList = new ArrayList<>();

        public TabAdaptor(@NonNull FragmentManager fm, List<CallPlans.ModelPlanCategory> tabCounts ) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            this.tabCounts = tabCounts;
            fragmentList.clear();
            for (CallPlans.ModelPlanCategory item :tabCounts){
                fragmentList.add( FragmentPlanList.getInstance( item.getPlan() ));
            }
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return tabCounts.get(position).getCategory();
        }

        @Override
        public Fragment getItem(int position) {
//            return FragmentPlanList.getInstance( tabCounts.get(position).getPlan() );
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return tabCounts.size();
        }
    }

    // TODO : Remove..------------------------------
    private class GetContacts_commodity extends AsyncTask<String, Void, String> {
        String jsonStr;
        String result = "";
        String jsoprofile;
        String ss_message = "", ss_data = "", category = "", ss_plan = "";
        String S_amount = "", S_validity = "", S_description = "", value = "";
        JSONObject dataJSONObject = null, jsonObject = null;
        JSONArray j_plan = null;

        public GetContacts_commodity(String strprofile) {
            this.jsoprofile = strprofile;

        }

        protected void onPreExecute() {
            super.onPreExecute();

            pDialog.show();

        }


        @Override
        protected String doInBackground(String... voids) {
            // TODO Auto-generated method stub

            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(HttpUrlPath.urlPath + HttpUrlPath.action_plans);
           /*
            try {

                List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

                nameValuePair.add(new BasicNameValuePair("username", s_Username));
                nameValuePair.add(new BasicNameValuePair("password", s_Password));
                nameValuePair.add(new BasicNameValuePair("operator", s_Operator_code));
                nameValuePair.add(new BasicNameValuePair("circle", s_Circle_code));

                post.setEntity(new UrlEncodedFormEntity(nameValuePair));
                HttpResponse response = client.execute(post);
                jsonStr = EntityUtils.toString(response.getEntity());
                System.out.println("#####object registration=" + jsonStr);
                JSONObject js = new JSONObject(jsonStr);

                ss_message = js.getString("message");
                ss_data = js.getString("data");
                JSONArray data = new JSONArray(ss_data);
                for (int i = 0; i < data.length(); i++) {
                    dataJSONObject = data.getJSONObject(i);
                    category = dataJSONObject.getString("category");
                    cat_list.add(category);

                    if (dataJSONObject.has("plan")) {
                        ss_plan = dataJSONObject.getString("plan");
                        j_plan = new JSONArray(ss_plan);

                        for (int ii = 0; ii < j_plan.length(); ii++) {
                            jsonObject = j_plan.getJSONObject(ii);

                            S_amount = jsonObject.getString("amount");
                            S_validity = jsonObject.getString("validity");
                            S_description = jsonObject.getString("description");

                            ShowFormGetSet item = new ShowFormGetSet();
                            item.setAmount(S_amount);
                            item.setValidity(S_validity);
                            item.setDescription(S_description);

                            if (i == 0) {
                                reco.add(item);
                            } else if (i == 1) {
                                full.add(item);
                            } else if (i == 2) {
                                local.add(item);
                            } else if (i == 3) {
                                topup.add(item);
                            } else if (i == 4) {
                                threeg.add(item);
                            } else if (i == 5) {
                                unlit.add(item);
                            } else if (i == 6) {
                                other.add(item);
                            } else if (i == 7) {
                                extra.add(item);
                            } else if (i == 8) {
                                extra_0.add(item);
                            } else if (i == 9) {
                                extra_1.add(item);
                            } else if (i == 10) {
                                extra_2.add(item);
                            } else if (i == 11) {
                                extra_3.add(item);
                            } else if (i == 12) {
                                extra_4.add(item);
                            } else if (i == 13) {
                                extra_5.add(item);
                            } else {
                                Plan.add(item);
                            }

                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            */
            return result;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();

//            setupViewPager(viewPager);
        }
    }

    /**
     *
     public class ViewPagerAdapter extends FragmentStatePagerAdapter {
     List<Fragment> fragments;
     List<String> tab;
     private Context context;

     public ViewPagerAdapter(Context c, FragmentManager manager, List<Fragment> fragments, List<String> tab) {
     super(manager);
     this.fragments = fragments;
     this.tab = tab;
     this.context = c;
     }

     @Override
     public Fragment getItem(int position) {
     this.tab.get(position);

     if (!ss_position.equals("")) {

     AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
     SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
     editor.putString(AppsContants.Tab, tab.get(Integer.parseInt(ss_position)));
     editor.commit();

     AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
     ss_tab = AppsContants.sharedpreferences.getString(AppsContants.Tab, "");
     }

     // return RecommendedFragment.newInstance(tab.get(position));
     // return fragments.get(position);
     return RecommendedFragment.getInstance(position);
     }

     @Override
     public int getCount() {
     return tab.size();
     }

     @Override
     public CharSequence getPageTitle(int position) {
     return tab.get(position);
     }


     }
     */

}