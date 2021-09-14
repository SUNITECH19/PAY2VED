package com.pay2ved.recharge.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class MyViewPagerImageSet  extends ViewPager {
    public MyViewPagerImageSet(@NonNull Context context) {
        super( context );
    }

    public MyViewPagerImageSet(@NonNull Context context, @Nullable AttributeSet attrs) {
        super( context, attrs );
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightSpec = 0;
        if (getLayoutParams().height == AbsListView.LayoutParams.WRAP_CONTENT) {
//            heightSpec = MeasureSpec.makeMeasureSpec(
//                    Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
            heightSpec =  MeasureSpec.makeMeasureSpec( widthMeasureSpec/2, MeasureSpec.AT_MOST );
        }
        else {
            // Any other height should be respected as is.
            heightSpec = heightMeasureSpec;
        }

        super.onMeasure( widthMeasureSpec, heightSpec );
    }

}