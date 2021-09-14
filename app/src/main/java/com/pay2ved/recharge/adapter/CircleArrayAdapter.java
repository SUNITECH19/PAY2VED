package com.pay2ved.recharge.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.pay2ved.recharge.R;

import java.util.List;

public class CircleArrayAdapter extends ArrayAdapter<String> {
    private Context ctx;
    private List<String> nameArray;
    private List<String> codeArray;
    private List<String> prefixArray;

    public CircleArrayAdapter(Context context, int resource, List<String> name, List<String> code,
                              List<String> prefix) {
        super(context, R.layout.circle_spinner_layout, R.id.txt_name, name);
        this.ctx = context;
        this.nameArray = name;
        this.codeArray = code;
        this.prefixArray = prefix;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.circle_spinner_layout, parent, false);

        TextView txt_name = (TextView) row.findViewById(R.id.txt_name);
        txt_name.setText(nameArray.get(position));

        TextView txt_code = (TextView) row.findViewById(R.id.txt_code);
        txt_code.setText(codeArray.get(position));


        TextView txt_prefix = (TextView) row.findViewById(R.id.txt_prefix);
        txt_prefix.setText(prefixArray.get(position));

        return row;
    }
}
