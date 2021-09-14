package com.pay2ved.recharge.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.pay2ved.recharge.R;
import com.pay2ved.recharge.model.ShowFormGetSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class UserArrayAdapter extends ArrayAdapter<String> {
    private Context ctx;
    private List<String> nameArray;
    private ArrayList<String> arraylist;

    public UserArrayAdapter(Context context, int resource, List<String> name) {
        super(context, R.layout.circle_spinner_layout, R.id.txt_name, name);
        this.ctx = context;
        this.nameArray = name;

        this.arraylist = new ArrayList<String>();
        this.arraylist.addAll(nameArray);
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
        View row = inflater.inflate(R.layout.user_spinner_layout, parent, false);

        TextView txt_name = (TextView) row.findViewById(R.id.txt_name);
        txt_name.setText(nameArray.get(position));

        return row;
    }

    @Override
    public Filter getFilter() {

        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                nameArray = (List<String>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults results = new FilterResults();

                // perform your search here using the searchConstraint String.

                constraint = constraint.toString().toLowerCase();
                for (int i = 0; i < arraylist.size(); i++) {
                    String dataNames = arraylist.get(i);
                    if (dataNames.toLowerCase().startsWith(constraint.toString())) {
                        nameArray.add(dataNames);
                    }
                }

                results.count = nameArray.size();
                results.values = nameArray;
                Log.e("VALUES", results.values.toString());

                return results;
            }
        };

        return filter;
    }
}
