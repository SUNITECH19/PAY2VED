package com.pay2ved.recharge.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.pay2ved.recharge.R;
import com.pay2ved.recharge.helper.NotificationListener;
import com.pay2ved.recharge.service.room.NotificationModel;

import java.util.List;

public class NotificationAdaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private NotificationListener notificationListener;
    private List<NotificationModel> notificationModelList;

    public NotificationAdaptor(NotificationListener notificationListener, List<NotificationModel> notificationModelList) {
        this.notificationListener = notificationListener;
        this.notificationModelList = notificationModelList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate(R.layout.layout_notification_item, parent, false);
        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).setData(position);
    }

    @Override
    public int getItemCount() {
        return notificationModelList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView tvTitle;
        private TextView tvBody;
        private ImageButton deleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.notifyImage);
            tvTitle = itemView.findViewById( R.id.tvTitle );
            tvBody = itemView.findViewById( R.id.tvBody );
            deleteBtn = itemView.findViewById( R.id.imageButtonDelete );

        }

        private void setData( int position ){

            tvTitle.setText( notificationModelList.get( position ).getTitle() );
            tvBody.setText( notificationModelList.get( position ).getBody() );

            // Set Image
            Glide.with( itemView.getContext() ).load( notificationModelList.get(position).getImage() )
                    .apply(RequestOptions.placeholderOf(R.drawable.launcher ))
                    .into( imageView );

            // On Item Click...
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notificationListener.onNotifyClick( notificationModelList.get(position));
                }
            });
            // On Delete Button Click...
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Delete Notification...
                    notificationListener.onDeleteItem( notificationModelList.get(position), position );
                }
            });

        }


    }

}
