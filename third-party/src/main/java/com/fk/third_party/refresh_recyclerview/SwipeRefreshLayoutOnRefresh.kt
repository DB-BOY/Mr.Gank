package com.fk.third_party.refresh_recyclerview

import android.support.v4.widget.SwipeRefreshLayout

/**
 * Created by HuangYH on 2016/5/3.
 */
class SwipeRefreshLayoutOnRefresh(private val mRefreshRecyclerView: RefreshRecyclerView) :
    SwipeRefreshLayout.OnRefreshListener {

    override fun onRefresh() {
        if (!mRefreshRecyclerView.isRefresh) {
            mRefreshRecyclerView.isRefresh = true
            mRefreshRecyclerView.refresh()
        }
    }
}
