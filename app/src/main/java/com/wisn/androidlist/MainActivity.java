package com.wisn.androidlist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<String> data=new ArrayList<>();
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initData() {
        for(int a=0;a<100;a++){
            data.add("data"+a);
        }
    }

    private void initView() {
        mRecyclerView = (RecyclerView) this.findViewById(R.id.base_recyclelistview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
        mRecyclerView.setAdapter(new RecyclerView.Adapter<MyViewHolder>() {
            @Override
            public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                MyViewHolder myviewHodle=new MyViewHolder(View.inflate(MainActivity.this,R.layout.item_textview,null));
                return myviewHodle;
            }

            @Override
            public void onBindViewHolder(MyViewHolder holder, int position) {
                holder.textview.setText(data.get(position));
            }


            @Override
            public int getItemCount() {
                return data.size();
            }
        });
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textview;

        public MyViewHolder(View itemView) {
            super(itemView);
            textview=itemView.findViewById(R.id.textView);
        }
    }
}
