package com.example.morina;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class HelpCenterActivity extends AppCompatActivity {

    private TextView title;
    private ImageButton mImageButton;
    private RecyclerView list;

    private ArrayList<String> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_center);

        title = findViewById(R.id.title);
        mImageButton = findViewById(R.id.button);

        title.setText("Help Center");
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
        dataList = new ArrayList<>();
        dataList.add("Guide to Order");
        dataList.add("Product and Services");
        dataList.add("Tips and Fare");
        dataList.add("Account and Payment");
        dataList.add("Promo and Reward");
    }

    private void initRecyclerView() {
        list = findViewById(R.id.list_view);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(new MyAdapter());
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.text_view);
        }

        public void bind(String data) {
            mTextView.setText(data);
        }
    }

    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(HelpCenterActivity.this);
            View view = layoutInflater.inflate(R.layout.list_item, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.bind(dataList.get(position));
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }
    }


}