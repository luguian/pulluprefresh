package com.pinger.swiperefreshdemo;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pinger.swiperefreshdemo.util.SwipeRefresh;
import com.pinger.swiperefreshdemo.view.SwipeRefreshView;

import java.util.ArrayList;
import java.util.List;


/**
 * 使用谷歌提供的SwipeRefreshLayout下拉控件进行下拉刷新
 */
public class MainActivity extends AppCompatActivity {
    private List<String> mList;
    private StringAdapter mAdapter;
    private SwipeRefreshView mSwipeRefreshView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSwipeRefreshView = (SwipeRefreshView) findViewById(R.id.srl);//绑定下拉刷新
        ListView listView = (ListView) findViewById(R.id.lv);//listView

        mList = new ArrayList<>();
        mAdapter = new StringAdapter();
        listView.setAdapter(mAdapter);
        SwipeRefresh.setswiperfresh(mSwipeRefreshView);



        initEvent();
        initData();
    }

    private void initEvent() {

        // 下拉时触发SwipeRefreshLayout的下拉动画，动画完毕之后就会回调这个方法
        mSwipeRefreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });


        // 设置下拉加载更多
        mSwipeRefreshView.setOnLoadMoreListener(new SwipeRefreshView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadMoreData();
            }
        });
//        mSwipeRefreshView.setOnLoadListener(new SwipeRefreshView.OnLoadListener() {
//            @Override
//            public void onLoad() {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        //添加数据
//                        for(int i = 30;i<35;i++){
//                            mList.add("我是天才"+i+"号");
//                            //这里要放在里面刷新，放在外面会导致刷新的进度条卡住
//                            mAdapter.notifyDataSetChanged();
//
//                        }
//                        Toast.makeText(MainActivity.this,"加载了"+5+"条数据",Toast.LENGTH_LONG).show();
//                        //加载完数据设置不加载状态，将加载进度收起来
//                        mSwipeRefreshView.setLoading(false);
//                    }
//                },1200);
//            }
//        });
    }

    private void loadMoreData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                mList.clear();
                mList.addAll(DataResource.getMoreData());
                Toast.makeText(MainActivity.this, "加载了" + 20 + "条数据", Toast.LENGTH_SHORT).show();

                // 加载完数据设置为不加载状态，将加载进度收起来
                mSwipeRefreshView.setLoading(false);
            }
        }, 2000);
    }


    private void initData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mList.clear();
                mList.addAll(DataResource.getData());
                mAdapter.notifyDataSetChanged();

                Toast.makeText(MainActivity.this, "刷新了20条数据", Toast.LENGTH_SHORT).show();

                // 加载完数据设置为不刷新状态，将下拉进度收起来
                if (mSwipeRefreshView.isRefreshing()) {
                    mSwipeRefreshView.setRefreshing(false);
                }
            }
        }, 2000);
    }


    /**
     * 适配器
     */
    private class StringAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(MainActivity.this, android.R.layout.simple_list_item_1, null);
            }

            TextView tv = (TextView) convertView;
            tv.setGravity(Gravity.CENTER);
            tv.setPadding(0, 20, 0, 20);
            tv.setText(mList.get(position));

            return convertView;
        }
    }


    public static class DataResource {
        private static List<String> datas = new ArrayList<>();
        private static int page = 0;

        public static List<String> getData() {
            page = 0;
            datas.clear();
            for (int i = 0; i < 15; i++) {
                datas.add("我是天才" + i + "号");
            }

            return datas;
        }

        public static List<String> getMoreData() {
            page = page + 1;
            for (int i = 20 * page; i < 20 * (page + 1); i++) {
                datas.add("我是天才" + i + "号");
            }

            return datas;
        }
    }
}
