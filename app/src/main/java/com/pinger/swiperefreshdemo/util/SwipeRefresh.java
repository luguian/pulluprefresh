package com.pinger.swiperefreshdemo.util;

import com.pinger.swiperefreshdemo.R;
import com.pinger.swiperefreshdemo.view.SwipeRefreshView;

/**
 * Created by lu on 2017/10/16.
 */

public class SwipeRefresh {
    public static void setswiperfresh(SwipeRefreshView mSwipeRefreshView){
        // 不能在onCreate中设置，这个表示当前是刷新状态，如果一进来就是刷新状态，SwipeRefreshLayout会屏蔽掉下拉事件
        //swipeRefreshLayout.setRefreshing(true);

        // 设置颜色属性的时候一定要注意是引用了资源文件还是直接设置16进制的颜色，因为都是int值容易搞混
        // 设置下拉进度的背景颜色，默认就是白色的
        mSwipeRefreshView.setProgressBackgroundColorSchemeResource(android.R.color.white);
        // 设置下拉进度的主题颜色
        mSwipeRefreshView.setColorSchemeResources(R.color.colorAccent,
                android.R.color.holo_blue_bright, R.color.colorPrimaryDark,
                android.R.color.holo_orange_dark, android.R.color.holo_red_dark, android.R.color.holo_purple);

        mSwipeRefreshView.setItemCount(20);

        // 手动调用,通知系统去测量
        mSwipeRefreshView.measure(0, 0);
        mSwipeRefreshView.setRefreshing(true);

    }

}
