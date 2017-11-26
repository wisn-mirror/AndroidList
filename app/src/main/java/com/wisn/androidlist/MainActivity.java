package com.wisn.androidlist;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<String> data = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mGrid_swipe_refresh;
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerView.Adapter<RecyclerView.ViewHolder>
            mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initData() {
        for (int a = 0; a < 100; a++) {
            data.add("data" + a);
        }
    }

    private void initView() {
        mRecyclerView = (RecyclerView) this.findViewById(R.id.base_recyclelistview);
        mGrid_swipe_refresh = (SwipeRefreshLayout) this.findViewById(R.id.grid_swipe_refresh);
        mGrid_swipe_refresh.setProgressViewOffset(false, 0,
                                                  (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
        mGrid_swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(MainActivity.this,"onRefresh",Toast.LENGTH_SHORT).show();
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this,"onRefresh onsuccess",Toast.LENGTH_SHORT).show();
                        mGrid_swipe_refresh.setRefreshing(false);
                    }
                },3000);
            }
        });
        mLinearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
       /*  GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        gridLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(gridLayoutManager);*/
//        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.HORIZONTAL);
//        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
//        staggeredGridLayoutManager.setReverseLayout(false);
////        staggeredGridLayoutManager.setOrientation(StaggeredGridLayoutManager.HORIZONTAL);
//        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            private int mLastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //0：当前屏幕停止滚动；1时：屏幕在滚动 且 用户仍在触碰或手指还在屏幕上；2时：随用户的操作，屏幕上产生的惯性滑动；
                // 滑动状态停止并且剩余少于两个item时，自动加载下一页
//                if (newState == RecyclerView.SCROLL_STATE_IDLE
//                    && lastVisibleItem +2>=mLayoutManager.getItemCount()) {
//                    new GetData().execute("http://gank.io/api/data/福利/10/"+(++page));
//                }
                if(newState==RecyclerView.SCROLL_STATE_IDLE
                        &&mLastVisibleItem+5>mLinearLayoutManager.getItemCount()){

                    initData();
                    mAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //获取加载的最后一个可见视图在适配器的位置。
                mLastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();
            }
        });
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                if (viewType == 0) {
                    MyViewHolder1
                            myviewHodle =
                            new MyViewHolder1(View.inflate(MainActivity.this, R.layout.item_textview, null));
                    myviewHodle.textview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(MainActivity.this, "textView", Toast.LENGTH_SHORT).show();
                        }
                    });
                    return myviewHodle;
                } else {
                    MyViewHolder2
                            myviewHodle =
                            new MyViewHolder2(View.inflate(MainActivity.this, R.layout.item_imageview, null));
                    myviewHodle.imageview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(MainActivity.this, "imageview", Toast.LENGTH_SHORT).show();
                        }
                    });
                    return myviewHodle;
                }

            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                if (holder instanceof MyViewHolder1) {
                    MyViewHolder1 holder1 = (MyViewHolder1) holder;
                    holder1.textview.setText(data.get(position));
                } else if (holder instanceof MyViewHolder2) {
                    MyViewHolder2 holder2 = (MyViewHolder2) holder;
                    holder2.imageview.setBackgroundResource(R.mipmap.ic_launcher_round);
                }
            }


            @Override
            public int getItemCount() {
                return data.size();
            }

            @Override
            public int getItemViewType(int position) {
                if (position % 2 == 1) {
                    return 0;
                } else {
                    return 1;
                }
            }

        };
        mRecyclerView.setAdapter(mAdapter);
    }

    class MyViewHolder1 extends RecyclerView.ViewHolder {
        public TextView textview;

        public MyViewHolder1(View itemView) {
            super(itemView);
            textview = itemView.findViewById(R.id.textView);
        }
    }
    class MyViewHolder2 extends RecyclerView.ViewHolder {
        public ImageView imageview;

        public MyViewHolder2(View itemView) {
            super(itemView);
            imageview = itemView.findViewById(R.id.imageview);
        }
    }
}
