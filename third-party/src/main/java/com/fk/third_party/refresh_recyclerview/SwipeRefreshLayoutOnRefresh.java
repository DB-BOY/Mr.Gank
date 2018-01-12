package com.fk.third_party.refresh_recyclerview;

import android.support.v4.widget.SwipeRefreshLayout;

/**
 * Created by HuangYH on 2016/5/3.
 */
public class SwipeRefreshLayoutOnRefresh implements SwipeRefreshLayout.OnRefreshListener {

    private RefreshRecyclerView mRefreshRecyclerView;

    public SwipeRefreshLayoutOnRefresh(RefreshRecyclerView refreshRecyclerView) {
        mRefreshRecyclerView = refreshRecyclerView;
    }

    @Override public void onRefresh() {
        if (!mRefreshRecyclerView.isRefresh()){
            mRefreshRecyclerView.setIsRefresh(true);
            mRefreshRecyclerView.refresh();
        }
    }
}
