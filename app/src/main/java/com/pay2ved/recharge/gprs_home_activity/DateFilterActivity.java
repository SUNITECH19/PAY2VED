package com.pay2ved.recharge.gprs_home_activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pay2ved.recharge.R;
import com.pay2ved.recharge.other.AppsContants;

import java.util.Calendar;

public class DateFilterActivity extends Activity {
    ImageView img_back;
    EditText number;
    Button ok, cancel;
    TextView txt_from, txt_to;
    String s_number = "", s_from = "", s_to = "";
    RelativeLayout rlt_from, rlt_to;
    DatePickerDialog.OnDateSetListener datePickerDialog_from, datePickerDialog_to;
    String s_Number = "", s_DATE_FROM = "", s_DATE_TO = "";
    int startYear, startMonth, startDay;
    int Year, Month, Day;

    final int DATE_PICKER_TO = 0;
    final int DATE_PICKER_FROM = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_filter);


        rlt_from = (RelativeLayout) findViewById(R.id.rlt_from);
        rlt_to = (RelativeLayout) findViewById(R.id.rlt_to);

        Calendar c = Calendar.getInstance();
        startYear = c.get(Calendar.YEAR);
        startMonth = c.get(Calendar.MONTH);
        startDay = c.get(Calendar.DAY_OF_MONTH);

        Calendar calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);


        datePickerDialog_from = new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker arg0, int year, int month, int dayOfMonth) {

                startYear = year;
                startMonth = month;
                startDay = dayOfMonth;

                //=========================================
                int Day = startDay;
                int Month = startMonth + 1;

                String s_dayOfMonth = Day + "";
                String s_month = Month + "";

                if (Day < 10) {
                    s_dayOfMonth = "0" + Day;
                }

                if (Month < 10) {
                    s_month = "0" + Month;
                }

                txt_from.setText(startYear + "-" + (s_month) + "-" + s_dayOfMonth);
                //========================================


            }
        };

        datePickerDialog_to = new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker arg0, int year, int month, int dayOfMonth) {

                Year = year;
                Month = month;
                Day = dayOfMonth;

                //=========================================
                int S_day = Day;
                int S_month = Month + 1;

                String s_dayOfMonth = S_day + "";
                String s_month = S_month + "";

                if (S_day < 10) {
                    s_dayOfMonth = "0" + S_day;
                }

                if (S_month < 10) {
                    s_month = "0" + S_month;
                }

                txt_to.setText(Year + "-" + (s_month) + "-" + s_dayOfMonth);
                //========================================



            }
        };


        rlt_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog(DATE_PICKER_FROM);
            }
        });

        rlt_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog(DATE_PICKER_TO);
            }
        });

        txt_from = (TextView) findViewById(R.id.txt_from);
        txt_to = (TextView) findViewById(R.id.txt_to);
        number = (EditText) findViewById(R.id.number);
        cancel = (Button) findViewById(R.id.cancel);
        ok = (Button) findViewById(R.id.ok);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                s_number = number.getText().toString().trim();
                s_from = txt_from.getText().toString().trim();
                s_to = txt_to.getText().toString().trim();

                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.NUMBER, s_number);
                editor.putString(AppsContants.DATE_FROM, s_from);
                editor.putString(AppsContants.DATE_TO, s_to);
                editor.commit();

                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                s_Number = AppsContants.sharedpreferences.getString(AppsContants.NUMBER, "");
                s_DATE_TO = AppsContants.sharedpreferences.getString(AppsContants.DATE_FROM, "");
                s_DATE_FROM = AppsContants.sharedpreferences.getString(AppsContants.DATE_TO, "");

                Intent intent = new Intent(DateFilterActivity.this, Recharge_ReportActivity.class);
                startActivity(intent);

                finish();
            }
        });

    }

    @Override
    protected Dialog onCreateDialog(int id) {

        switch (id) {
            case DATE_PICKER_FROM:
                return new DatePickerDialog(this, datePickerDialog_from, startYear, startMonth, startDay);
            case DATE_PICKER_TO:
                return new DatePickerDialog(this, datePickerDialog_to, Year, Month, Day);
        }
        return null;
    }

}
