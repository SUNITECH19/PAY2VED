package com.pay2ved.recharge.adapter;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pay2ved.recharge.R;
import com.pay2ved.recharge.model.ItemData;
import com.pay2ved.recharge.sqlite_data.model.Note;

import java.util.ArrayList;
import java.util.List;

public class GatewaySpinnerAdapter extends ArrayAdapter<Note> {
    int groupid;
    Activity context;
    List<Note> list;
    LayoutInflater inflater;

    public GatewaySpinnerAdapter(Activity context, int groupid, int id, List<Note>
            list) {
        super(context, id, list);
        this.list = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.groupid = groupid;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = inflater.inflate(groupid, parent, false);

        Note note = list.get(position);

        TextView textView = (TextView) itemView.findViewById(R.id.txt);
        textView.setText(list.get(position).getNote());

        return itemView;
    }

    public View getDropDownView(int position, View convertView, ViewGroup
            parent) {

        return getView(position, convertView, parent);

    }

    public int getPosition(String s_gateway_no) {

        return 0;
    }
}
