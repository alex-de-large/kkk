package com.example.morina;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.morina.model.Notification;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity {

    private TextView title;
    private ImageButton mImageButton;
    private RecyclerView mRecyclerView;

    private ArrayList<Notification> notifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        title = findViewById(R.id.title);
        mImageButton = findViewById(R.id.button);

        title.setText(R.string.notification);
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initData();
        initRecyclerView();
    }

    private void initData() {
        notifications = new ArrayList<>();
        notifications.add(new Notification("Message From Taxi App", "Unlimited Premium Class ...", R.drawable.ic_message));
        notifications.add(new Notification("Promotion", "You have just unlock your ...", R.drawable.ic_message));
        notifications.add(new Notification("Promotion", "$5 off everyday this week", R.drawable.ic_message));
    }

    private void initRecyclerView() {
        mRecyclerView = findViewById(R.id.notifications);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),1);
        mRecyclerView.addItemDecoration(mDividerItemDecoration);
        mRecyclerView.setAdapter(new NotificationAdapter());
    }

    private class NotificationHolder extends RecyclerView.ViewHolder {

        private ImageView mImageView;
        private TextView title;
        private TextView text;

        public NotificationHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.notification_image);
            title = itemView.findViewById(R.id.notification_title);
            text = itemView.findViewById(R.id.notification_text);
        }

        public void bind(Notification notification) {
            mImageView.setBackgroundResource(notification.getImage());
            title.setText(notification.getTitle());
            text.setText(notification.getMessage());
        }
    }

    private class NotificationAdapter extends RecyclerView.Adapter<NotificationHolder> {

        @NonNull
        @Override
        public NotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(NotificationActivity.this);
            View view = layoutInflater.inflate(R.layout.item_notification, parent, false);
            return new NotificationHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull NotificationHolder holder, int position) {
            holder.bind(notifications.get(position));
        }

        @Override
        public int getItemCount() {
            return notifications.size();
        }
    }
}