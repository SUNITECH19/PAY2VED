package com.pay2ved.recharge.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pay2ved.recharge.R;
import com.squareup.picasso.Picasso;

public class ImageArrayAdapter extends ArrayAdapter<String> {
    private Context ctx;
    private String[] codeArray;
    private String[] contentArray;
    private String[] remarkArray;
    private String[] hint1Array;
    private String[] hint2Array;
    private String[] hint3Array;
    private String[] imageArray;

    public ImageArrayAdapter(Context context, int resource, String[] code, String[] objects, String[] remark, String[] hint1, String[] hint2, String[] hint3,
                             String[] imageArray) {
        super(context, R.layout.mobile_spinner_layout, R.id.txt_code, code);
        this.ctx = context;
        this.codeArray = code;
        this.contentArray = objects;
        this.remarkArray = remark;
        this.hint1Array = hint1;
        this.hint2Array = hint2;
        this.hint3Array = hint3;
        this.imageArray = imageArray;
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
        View row = inflater.inflate(R.layout.mobile_spinner_layout, parent, false);

        TextView txt_code = (TextView) row.findViewById(R.id.txt_code);
        txt_code.setText(codeArray[position]);

        TextView textView = (TextView) row.findViewById(R.id.spinnerTextView);
        textView.setText(contentArray[position]);

        ImageView imageView = (ImageView) row.findViewById(R.id.spinnerImages);
        try {
            Picasso.get().load(imageArray[position]).placeholder(R.drawable.default_icon).into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return row;

    }
}
