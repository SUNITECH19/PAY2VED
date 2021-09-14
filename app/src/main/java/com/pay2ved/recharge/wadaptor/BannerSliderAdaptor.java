package com.pay2ved.recharge.wadaptor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.pay2ved.recharge.R;
import com.pay2ved.recharge.helper.FragmentListener;
import com.pay2ved.recharge.service.callmodel.CallHomeMessage;
import com.pay2ved.recharge.ui.MyImageView;

import java.util.List;

public class BannerSliderAdaptor extends PagerAdapter {

    private FragmentListener fragmentListener;
    private List<CallHomeMessage.ImageInfo> bannerLists;

    public BannerSliderAdaptor(FragmentListener fragmentListener, List<CallHomeMessage.ImageInfo> bannerLists) {
        this.fragmentListener = fragmentListener;
        this.bannerLists = bannerLists;
    }


    @SuppressLint("NewApi")
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {

        View view = LayoutInflater.from( container.getContext() ).inflate( R.layout.layout_slider_item, container, false );

        MyImageView bannerImage = view.findViewById( R.id.banner_slider_image );

//         Set Image Resource...
        Glide.with( view.getContext() ).load( bannerLists.get(position).url ).into( bannerImage );
//                .apply( new RequestOptions().placeholder(  ) ).into( bannerImage );

        bannerImage.setOnClickListener(v -> {
            if (bannerLists.get( position ).url !=null){
//                fragmentListener.showToast( "Path -" + bannerLists.get( position ).getBannerClickLink() );
//                StaticMethods.gotoURL( v.getContext() , URL_CONTACT_US );
                gotoUrl( v.getContext(), bannerLists.get( position ).url );
            }else {
                fragmentListener.showToast( "Content Not Found!" );
            }
        });

        container.addView( view, 0 );
        return view;
    }

    private void gotoUrl(Context context, String url ){
        // TODO : Action...
//        Intent intent = new Intent( context, ActivityShowFragment.class);
//        intent.putExtra(ConstantValue.FRAGMENT_ID, FRAGMENT_OPEN_URL);
//        intent.putExtra(ConstantValue.REQUEST_URL, URL_WEBSITE );
//        context.startActivity(intent);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView( (View)object );
    }

    @Override
    public int getCount() {
        return bannerLists.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }


}
