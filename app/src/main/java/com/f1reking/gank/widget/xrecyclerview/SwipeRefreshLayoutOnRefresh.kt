package com.f1reking.gank.widget.xrecyclerview

import android.support.v4.widget.SwipeRefreshLayout

/**
 * Created by HuangYH on 2016/5/3.
 */
class SwipeRefreshLayoutOnRefresh(private val mXRecyclerView: XRecyclerView) :
    SwipeRefreshLayout.OnRefreshListener {

    override fun onRefresh() {
        if (!mXRecyclerView.isRefresh) {
            mXRecyclerView.isRefresh = true
            mXRecyclerView.refresh()
        }
    }
}
