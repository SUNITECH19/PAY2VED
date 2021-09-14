package com.pay2ved.recharge.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.pay2ved.recharge.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomPagerAdapter extends PagerAdapter {
    ArrayList<String> mImgarray;
    Context mContext;
    LayoutInflater mLayoutInflater;

    public CustomPagerAdapter(Context context, ArrayList<String> imgarray) {
        this.mContext = context;
        this.mImgarray = imgarray;
        try {
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCount() {
        return mImgarray.size();
    }


    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);

        try {
            Picasso.get().load(mImgarray.get(position).replace(" ", "%20")).into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }

        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {


        return view == object;
    }
}
