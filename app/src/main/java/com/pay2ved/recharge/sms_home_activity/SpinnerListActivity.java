package com.pay2ved.recharge.sms_home_activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pay2ved.recharge.R;
import com.pay2ved.recharge.activity.ContactActivity;
import com.pay2ved.recharge.adapter.SpinnerAdapter;
import com.pay2ved.recharge.model.ItemData;
import com.pay2ved.recharge.other.AppsContants;

import java.util.ArrayList;

public class SpinnerListActivity extends AppCompatActivity {

    EditText mobile, amount;
    TextView txt_list;
    Spinner spnr_provider;
    Button ok, cancel;
    ImageView img_back, img_contact;
    String s_mobile = "", s_amount = "";
    String Id = "", Name = "", s_phone = "", s_Spinner_list = "", s_Title = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);

        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        s_Spinner_list = AppsContants.sharedpreferences.getString(AppsContants.Spinner_list, "");
        s_Title = AppsContants.sharedpreferences.getString(AppsContants.Title, "");


        txt_list = (TextView) findViewById(R.id.txt_list);
        txt_list.setText(s_Title);

        mobile = (EditText) findViewById(R.id.mobile);
        amount = (EditText) findViewById(R.id.amount);
        spnr_provider = (Spinner) findViewById(R.id.spnr_provider);
        cancel = (Button) findViewById(R.id.cancel);
        ok = (Button) findViewById(R.id.ok);
        img_contact = (ImageView) findViewById(R.id.img_contact);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        img_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SpinnerListActivity.this, ContactActivity.class);
                startActivity(intent);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ArrayList<ItemData> list_mobile = new ArrayList<>();
        list_mobile.add(new ItemData("Select", "null", R.drawable.default_icon));
        list_mobile.add(new ItemData("Aircel", "AC", R.drawable.aircel));
        list_mobile.add(new ItemData("Airtel", "AT", R.drawable.airtel));
        list_mobile.add(new ItemData("BSNL Special validity", "BV", R.drawable.bsnl));
        list_mobile.add(new ItemData("BSNL Topup", "BT", R.drawable.bsnl));
        list_mobile.add(new ItemData("Idea Topup", "ID", R.drawable.idea));
        list_mobile.add(new ItemData("Jio", "LM", R.drawable.jio));
        list_mobile.add(new ItemData("Jio Topup", "JIOT", R.drawable.jio));
        list_mobile.add(new ItemData("MTNL Special", "MV", R.drawable.mtnl));
        list_mobile.add(new ItemData("MTNL Topup", "MT", R.drawable.mtnl));
        list_mobile.add(new ItemData("MTS Topup", "MS", R.drawable.mts));
        list_mobile.add(new ItemData("Reliance", "RG", R.drawable.reliance));
        list_mobile.add(new ItemData("Tata Docomo Spl", "TS", R.drawable.tata_docomo));
        list_mobile.add(new ItemData("Tata Docomo Topup", "TD", R.drawable.tata_docomo));
        list_mobile.add(new ItemData("Tata Indicom(CDMA)", "PT", R.drawable.tata_indicom));
        list_mobile.add(new ItemData("Uninor Special", "US", R.drawable.uninor));
        list_mobile.add(new ItemData("Uninor Topup", "UN", R.drawable.uninor));
        list_mobile.add(new ItemData("Videocon Mob Spl", "VS", R.drawable.videocon));
        list_mobile.add(new ItemData("Videocon Mob Topup", "VM", R.drawable.videocon));
        list_mobile.add(new ItemData("Virgin CDMA", "VC", R.drawable.virgin));
        list_mobile.add(new ItemData("Virgin GSM", "VG", R.drawable.virgin));
        list_mobile.add(new ItemData("Vodafone Prepaid", "VD", R.drawable.vodafone));


        ArrayList<ItemData> list_dth = new ArrayList<>();
        list_dth.add(new ItemData("Select", "null", R.drawable.default_icon));
        list_dth.add(new ItemData("Airtel Digital TV", "DA", R.drawable.airtel_dth));
        list_dth.add(new ItemData("Big TV", "DB", R.drawable.big_tv));
        list_dth.add(new ItemData("Dish TV", "DD", R.drawable.dishtv));
        list_dth.add(new ItemData("Sun Direct TV", "DS", R.drawable.sun_tv));
        list_dth.add(new ItemData("Tata Sky", "DT", R.drawable.tata_sky));
        list_dth.add(new ItemData("Videocon D2H", "DV", R.drawable.videocon_dth));


        ArrayList<ItemData> list_datacard = new ArrayList<>();
        list_datacard.add(new ItemData("Select", "null", R.drawable.default_icon));
        list_datacard.add(new ItemData("MTS Data Card", "MD", R.drawable.mts));
        list_datacard.add(new ItemData("Reliance Data Card", "RD", R.drawable.reliance));
        list_datacard.add(new ItemData("Tata Data Card", "TP", R.drawable.tata_docomo));


        ArrayList<ItemData> list_postpaid = new ArrayList<>();
        list_postpaid.add(new ItemData("Select", "null", R.drawable.default_icon));
        list_postpaid.add(new ItemData("Aircel Postpaid", "PAA", R.drawable.aircel));
        list_postpaid.add(new ItemData("Airtel Postpaid", "PA", R.drawable.airtel));
        list_postpaid.add(new ItemData("BSNL Postpaid", "PB", R.drawable.bsnl));
        list_postpaid.add(new ItemData("Idea Postpaid", "PI", R.drawable.idea));
        list_postpaid.add(new ItemData("Jio Postpaid", "PL", R.drawable.jio));
        list_postpaid.add(new ItemData("Reliance CDMA Postpaid", "PR", R.drawable.reliance));
        list_postpaid.add(new ItemData("Reliance GSM Postpaid", "PG", R.drawable.reliance));
        list_postpaid.add(new ItemData("Tata Docomo GSM Postpaid", "PD", R.drawable.tata_docomo));
        list_postpaid.add(new ItemData("Tata Indicaom CDMA Postpaid", "tp2", R.drawable.tata_indicom));
        list_postpaid.add(new ItemData("Vodafone Postpaid", "PV", R.drawable.vodafone));
        list_postpaid.add(new ItemData("Tikona Broadband Postpaid", "TB", R.drawable.tikona));

        ArrayList<ItemData> list_landline = new ArrayList<>();
        list_landline.add(new ItemData("Select", "null", R.drawable.default_icon));
        list_landline.add(new ItemData("Airtel Landline", "LA", R.drawable.airtel));
        list_landline.add(new ItemData("BSNL Landline", "LB", R.drawable.bsnl));
        list_landline.add(new ItemData("MTNL Delhi Landline", "LD", R.drawable.mtnl));
        list_landline.add(new ItemData("MTNL Landline", "LT", R.drawable.mtnl));

        //=====================================================

        if (s_Spinner_list.equals("MOBILE")) {

            SpinnerAdapter adapter = new SpinnerAdapter(this,
                    R.layout.spinner_layout, R.id.txt, list_mobile);
            spnr_provider.setAdapter(adapter);

        } else if (s_Spinner_list.equals("DTH")) {

            SpinnerAdapter adapter = new SpinnerAdapter(this,
                    R.layout.spinner_layout, R.id.txt, list_dth);
            spnr_provider.setAdapter(adapter);
        } else if (s_Spinner_list.equals("DATACARD")) {

            SpinnerAdapter adapter = new SpinnerAdapter(this,
                    R.layout.spinner_layout, R.id.txt, list_datacard);
            spnr_provider.setAdapter(adapter);
        } else if (s_Spinner_list.equals("POSTPAID")) {

            SpinnerAdapter adapter = new SpinnerAdapter(this,
                    R.layout.spinner_layout, R.id.txt, list_postpaid);
            spnr_provider.setAdapter(adapter);
        } else if (s_Spinner_list.equals("LANDLINE")) {

            SpinnerAdapter adapter = new SpinnerAdapter(this,
                    R.layout.spinner_layout, R.id.txt, list_landline);
            spnr_provider.setAdapter(adapter);
        }


        //====================================================

        spnr_provider.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View v, int position, long id) {

                String name = ((TextView) v.findViewById(R.id.txt_id)).getText().toString();

                Id = String.valueOf(position);
                Name = "" + name;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                s_mobile = mobile.getText().toString().trim();
                s_amount = amount.getText().toString().trim();


                if (s_mobile.equals("")) {
                    mobile.requestFocus();
                    Toast.makeText(SpinnerListActivity.this, "Please Enter Mobile No", Toast.LENGTH_LONG).show();

                } else if (Name.equals("null")) {
                    Toast.makeText(SpinnerListActivity.this, "Please Select Operator", Toast.LENGTH_LONG).show();

                } else if (s_amount.equals("")) {
                    amount.requestFocus();
                    Toast.makeText(SpinnerListActivity.this, "Please Enter Amount", Toast.LENGTH_LONG).show();

                } else {

                    AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                    editor.putString(AppsContants.mobile, s_mobile);
                    editor.putString(AppsContants.amount, s_amount);
                    editor.putString(AppsContants.code, Name);
                    editor.commit();

                    Intent intent = new Intent(SpinnerListActivity.this, SendSmsActivity.class);
                    startActivity(intent);

                    finish();

                }
            }
        });
    }

    @Override
    protected void onResume() {

        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        s_phone = AppsContants.sharedpreferences.getString(AppsContants.Phone, "");

        mobile.setText(s_phone);

        super.onResume();
    }
}
