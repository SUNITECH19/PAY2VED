package com.pay2ved.recharge.fragments;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pay2ved.recharge.R;
import com.pay2ved.recharge.service.room.NotificationModel;

public class NotificationViewDialog extends DialogFragment {

    private NotificationModel notificationModel;

    public NotificationViewDialog(NotificationModel notificationModel) {
        this.notificationModel = notificationModel;
    }

    private ImageView imageView;
    private TextView tvTitle;
    private TextView tvBody;
    private ImageButton deleteBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification_view_dialog, container, false);

        if (getDialog()!=null){
            getDialog().requestWindowFeature( STYLE_NO_TITLE );

            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            getDialog().getWindow().setLayout(width, height);
        }

        imageView = view.findViewById(R.id.notifyImage);
        tvTitle = view.findViewById( R.id.tvTitle );
        tvBody = view.findViewById( R.id.tvBody );
        deleteBtn = view.findViewById( R.id.imageButtonClose );

        tvTitle.setText( notificationModel.getTitle() );
        tvBody.setText( notificationModel.getBody() );

        if (notificationModel.getImage() != null && !notificationModel.getImage().equals("")){
            // Set Image
            Glide.with( getContext() ).load( notificationModel.getImage() )
                    .into( imageView );
        }

        // --- Click Action
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationViewDialog.this.dismiss();
            }
        });

        return view;
    }
}