package com.pay2ved.recharge.adapter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pay2ved.recharge.R;
import com.pay2ved.recharge.model.ShowFormGetSet;
import com.pay2ved.recharge.other.AppsContants;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ContactAdapter extends BaseAdapter {

    String s_phone = "";
    String phone = "";
    public List<ShowFormGetSet> _data;
    private ArrayList<ShowFormGetSet> arraylist;
    Context _c;
    ViewHolder v;

    public ContactAdapter(List<ShowFormGetSet> selectUsers, Context context) {
        _data = selectUsers;
        _c = context;
        this.arraylist = new ArrayList<ShowFormGetSet>();
        this.arraylist.addAll(_data);
    }

    @Override
    public int getCount() {
        return _data.size();
    }

    @Override
    public Object getItem(int i) {
        return _data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null) {
            LayoutInflater li = (LayoutInflater) _c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = li.inflate(R.layout.contact_info, null);
            Log.e("Inside", "here--------------------------- In view1");
        } else {
            view = convertView;
            Log.e("Inside", "here--------------------------- In view2");
        }

        v = new ViewHolder();
        final ShowFormGetSet data = (ShowFormGetSet) _data.get(i);
        v.rel_contact = (RelativeLayout) view.findViewById(R.id.rel_contact);
        v.title = (TextView) view.findViewById(R.id.name);
        v.phone = (TextView) view.findViewById(R.id.no);

        v.title.setText(data.getTitle());
        v.phone.setText(data.getPhone());
        v.rel_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(_c, ""+data.getPhone(), Toast.LENGTH_SHORT).show();
               // String str_phone = data.getPhone().length() >= 10 ? data.getPhone().substring(data.getPhone().length() - 10) : "";
                if (data.getPhone().length() >= 18) {
                    Toast.makeText(_c, "Invalid Number", Toast.LENGTH_SHORT).show();
                }

                //=========================================
                int i = 0,
                        spaceCount = 0;

                while (i < data.getPhone().length()) {
                    if (data.getPhone().charAt(i) == ' ') {
                        spaceCount++;
                    }
                    i++;
                }
                // Toast.makeText(_c, "" + spaceCount, Toast.LENGTH_SHORT).show();
                //=========================================
                if (spaceCount == 0) {
                    s_phone = data.getPhone().length() >= 10 ? data.getPhone().substring(data.getPhone().length() - 10) : "";
                } else if (spaceCount == 1) {
                    s_phone = data.getPhone().length() >= 10 ? data.getPhone().substring(data.getPhone().length() - 11) : "";
                } else if (spaceCount == 2) {
                    s_phone = data.getPhone().length() >= 10 ? data.getPhone().substring(data.getPhone().length() - 12) : "";
                } else if (spaceCount == 3) {
                    s_phone = data.getPhone().length() >= 10 ? data.getPhone().substring(data.getPhone().length() - 13) : "";
                } else if (spaceCount == 4) {
                    s_phone = data.getPhone().length() >= 10 ? data.getPhone().substring(data.getPhone().length() - 14) : "";
                } else if (spaceCount == 5) {
                    s_phone = data.getPhone().length() >= 10 ? data.getPhone().substring(data.getPhone().length() - 15) : "";
                } else if (spaceCount == 6) {
                    s_phone = data.getPhone().length() >= 10 ? data.getPhone().substring(data.getPhone().length() - 16) : "";
                } else if (spaceCount == 7) {
                    s_phone = data.getPhone().length() >= 10 ? data.getPhone().substring(data.getPhone().length() - 17) : "";
                } else if (spaceCount == 8) {
                    s_phone = data.getPhone().length() >= 10 ? data.getPhone().substring(data.getPhone().length() - 18) : "";
                } else if (spaceCount == 9) {
                    s_phone = data.getPhone().length() >= 10 ? data.getPhone().substring(data.getPhone().length() - 19) : "";
                } else if (spaceCount == 10) {
                    s_phone = data.getPhone().length() >= 10 ? data.getPhone().substring(data.getPhone().length() - 20) : "";
                } else if (spaceCount == 11) {
                    s_phone = data.getPhone().length() >= 10 ? data.getPhone().substring(data.getPhone().length() - 21) : "";
                } else if (spaceCount >= 12) {
                    Toast.makeText(_c, "Invalid Number", Toast.LENGTH_SHORT).show();
                }

                phone = s_phone.replaceAll("\\s+", "");

                AppsContants.sharedpreferences = _c.getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.Phone, phone);
                editor.commit();

                ((Activity) _c).finish();

                notifyDataSetChanged();
            }
        });

        view.setTag(data);
        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        _data.clear();
        if (charText.length() == 0) {
            _data.addAll(arraylist);
        } else {
            for (ShowFormGetSet wp : arraylist) {
                if (wp.getTitle().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    _data.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    static class ViewHolder {
        TextView title, phone;
        RelativeLayout rel_contact;
    }


}
