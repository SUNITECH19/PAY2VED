package com.pay2ved.recharge.adapter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pay2ved.recharge.R;
import com.pay2ved.recharge.model.ShowFormGetSet;
import com.pay2ved.recharge.other.AppsContants;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class UserListAdapter extends BaseAdapter {

    public List<ShowFormGetSet> _data;
    private ArrayList<ShowFormGetSet> arraylist;
    Context _c;
    ViewHolder v;

    public UserListAdapter(List<ShowFormGetSet> selectUsers, Context context) {
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater li = (LayoutInflater) _c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = li.inflate(R.layout.user_spinner_layout, null);

        v = new ViewHolder();
        final ShowFormGetSet data = (ShowFormGetSet) _data.get(i);
        v.rel_contact = (RelativeLayout) view.findViewById(R.id.rel_contact);
        v.txt_name = (TextView) view.findViewById(R.id.txt_name);
        v.txt_name.setText(data.getTitle());

        v.rel_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppsContants.sharedpreferences = _c.getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.Phone, data.getTitle());
                editor.putString(AppsContants.U_id, data.getId());
                editor.putString(AppsContants.Balance, data.getBalance());
                editor.commit();

                ((Activity) _c).finish();

            }
        });

        v.txt_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppsContants.sharedpreferences = _c.getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.Phone, data.getTitle());
                editor.putString(AppsContants.U_id, data.getId());
                editor.putString(AppsContants.Balance, data.getBalance());
                editor.commit();

                ((Activity) _c).finish();

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

    class ViewHolder {
        TextView txt_name;
        RelativeLayout rel_contact;
    }


}
