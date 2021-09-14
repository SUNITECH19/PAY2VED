package com.pay2ved.recharge.sms_home_activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.pay2ved.recharge.R;
import com.pay2ved.recharge.adapter.GatewaySpinnerAdapter;
import com.pay2ved.recharge.other.AppsContants;
import com.pay2ved.recharge.sqlite_data.model.DatabaseHelper;
import com.pay2ved.recharge.sqlite_data.model.Note;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    String s_mobile = "", s_Password = "", s_gateway_no = "";
    TextView txt_mobile, txt_pin;
    String s_mob = "", s_pin_no = "", s_gateway = "";
    ImageView img_back;
    Spinner spnr_gateway;
    String Id = "", s_position_id = "";
    private DatabaseHelper db;
    GatewaySpinnerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        txt_mobile = (TextView) findViewById(R.id.txt_mobile);
        txt_pin = (TextView) findViewById(R.id.txt_pin);
        spnr_gateway = (Spinner) findViewById(R.id.spnr_gateway);


        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txt_mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
                final View view = layoutInflaterAndroid.inflate(R.layout.dialog, null);

                AlertDialog.Builder alert = new AlertDialog.Builder(SettingsActivity.this);
                alert.setView(view);
                alert.setTitle("Enter Username or Mobile No");
                alert.setCancelable(false);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        final EditText edt_mobile = view.findViewById(R.id.edt_mobile);
                        s_mob = edt_mobile.getText().toString().trim();

                        if (s_mob.equals("")) {
                            edt_mobile.requestFocus();
                            Toast.makeText(SettingsActivity.this, "Please Enter Username or Mobile No", Toast.LENGTH_LONG).show();

                        } else {
                            AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                            editor.putString(AppsContants.Mobile, s_mob);
                            editor.commit();

                          /*  Intent intent = getIntent();
                            finish();
                            startActivity(intent);
*/
                            finish();
                            //dialog.dismiss();
                        }

                    }
                });


                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        //dialog.dismiss();
                    }
                });

                alert.show();

            }
        });

        txt_pin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
                final View view = layoutInflaterAndroid.inflate(R.layout.pin_dialog, null);

                AlertDialog.Builder alert = new AlertDialog.Builder(SettingsActivity.this);
                alert.setView(view);
                alert.setTitle("Enter Pin");
                alert.setCancelable(false);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        final EditText edt_pin = view.findViewById(R.id.edt_pin);
                        s_pin_no = edt_pin.getText().toString().trim();

                        if (s_pin_no.equals("")) {
                            edt_pin.requestFocus();
                            Toast.makeText(SettingsActivity.this, "Please Enter Pin", Toast.LENGTH_LONG).show();
                        } else {
                            AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                            editor.putString(AppsContants.Password, s_pin_no);
                            editor.commit();

                          /*  Intent intent = getIntent();
                            finish();
                            startActivity(intent);
*/
                            finish();
                           // dialog.dismiss();
                        }

                    }
                });


                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                       // dialog.dismiss();
                    }
                });

                alert.show();

            }
        });

        List<Note> gateway_list = new ArrayList<>();
        db = new DatabaseHelper(this);
        gateway_list.addAll(db.getAllNotes());

        adapter = new GatewaySpinnerAdapter(this,
                R.layout.gateway_spinner_layout, R.id.txt, gateway_list);
        spnr_gateway.setAdapter(adapter);

        spnr_gateway.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View v, int position, long id) {

                String name = ((TextView) v.findViewById(R.id.txt)).getText().toString();

                Id = String.valueOf(position);
                s_gateway = "" + name;

                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.GateWay_No, s_gateway);
                editor.putString(AppsContants.position_id, Id);
                editor.commit();

                //spnr_gateway.setSelection(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

    }

    @Override
    protected void onResume() {

        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        s_mobile = AppsContants.sharedpreferences.getString(AppsContants.Mobile, "");
        s_Password = AppsContants.sharedpreferences.getString(AppsContants.Password, "");
        s_gateway_no = AppsContants.sharedpreferences.getString(AppsContants.GateWay_No, "");
        s_position_id = AppsContants.sharedpreferences.getString(AppsContants.position_id, "");

        // Toast.makeText(SettingsActivity.this, "" + s_gateway_no, Toast.LENGTH_SHORT).show();

        try {
            if (s_gateway_no != null) {
                int spinnerPosition = adapter.getPosition(s_gateway_no);
                spnr_gateway.setSelection(Integer.parseInt(s_position_id));

                // Toast.makeText(SettingsActivity.this, "" + Integer.parseInt(s_position_id), Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }


        txt_mobile.setText(s_mobile);
        txt_pin.setText(s_Password);

        super.onResume();
    }
}
