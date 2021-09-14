package com.pay2ved.recharge.adapter.searchable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pay2ved.recharge.R;
import com.pay2ved.recharge.helper.Listener;
import com.pay2ved.recharge.model.ModelSearchableItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AdaptorListSearchable  extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;
    private List<ModelSearchableItem> listShorted;
    private final List<ModelSearchableItem> listAllItems;
    private Listener.OnItemSelectListener onItemSelectListener;

    public AdaptorListSearchable(Context context, List<ModelSearchableItem> listShorted, Listener.OnItemSelectListener onItemSelectListener) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        this.onItemSelectListener = onItemSelectListener;
        this.listShorted = listShorted;
        this.listAllItems = new ArrayList<>();
        this.listAllItems.addAll(listShorted);
    }

    public class ViewHolder {
        TextView spinnerTextView;
        ImageView spinnerImages;

        void setData( ModelSearchableItem item){
            spinnerTextView.setText( item.getTitle() );
            try {
                Picasso.get().load(item.getImage()).placeholder(R.drawable.default_icon).into(spinnerImages);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public int getCount() {
        return listShorted.size();
    }

    @Override
    public ModelSearchableItem getItem(int position) {
        return listShorted.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.mobile_spinner_layout, null);
            holder.spinnerTextView = (TextView) view.findViewById(R.id.spinnerTextView);
            holder.spinnerImages = (ImageView) view.findViewById(R.id.spinnerImages);
            view.setTag(holder);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.setData( listShorted.get( position ));
        // On Item Selected...!
        view.setOnClickListener( v -> onItemSelectListener.onSelectItem( listShorted.get( position ) ));
        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        listShorted.clear();
        if (charText.length() == 0) {
            listShorted.addAll(listAllItems);
        } else {
            for (ModelSearchableItem item : listAllItems) {
                if (item.getTitle().toLowerCase(Locale.getDefault()).contains( charText)){
                    listShorted.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void onItemSelected(){

    }

}