package com.fk.third_party.refresh_recyclerview

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager

/**
 * Created by HuangYH on 2016/5/3.
 */
class RecyclerViewOnScroll(private val mRefreshRecyclerView: RefreshRecyclerView) :
    RecyclerView.OnScrollListener() {
    var isTop: Boolean = false

    override fun onScrolled(recyclerView: RecyclerView,
                            dx: Int,
                            dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        var lastCompletelyVisibleItem = 0
        var firstVisibleItem = 0
        val layoutManager = recyclerView.layoutManager
        val totalItemCount = layoutManager.itemCount
        if (layoutManager is GridLayoutManager) {
            lastCompletelyVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition()
            firstVisibleItem = layoutManager.findFirstCompletelyVisibleItemPosition()
        } else if (layoutManager is LinearLayoutManager) {
            lastCompletelyVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition()
            firstVisibleItem = layoutManager.findFirstCompletelyVisibleItemPosition()
        } else if (layoutManager is StaggeredGridLayoutManager) {
            val lastPositions = IntArray(layoutManager.spanCount)
            layoutManager.findLastCompletelyVisibleItemPositions(lastPositions)
            lastCompletelyVisibleItem = findMax(lastPositions)
            firstVisibleItem = layoutManager.findFirstVisibleItemPositions(lastPositions)[0]
        }
        if (firstVisibleItem == 0) {
            if (mRefreshRecyclerView.pullRefreshEnable) {
                mRefreshRecyclerView.swipeRefreshEnable = true
            }
            mRefreshRecyclerView.isTop = true
        } else {
            mRefreshRecyclerView.isTop = false
            mRefreshRecyclerView.swipeRefreshEnable = false
        }
        if (mRefreshRecyclerView.loadMoreEnable && !mRefreshRecyclerView.isRefresh && mRefreshRecyclerView.isHasMore && lastCompletelyVisibleItem == totalItemCount - 1 && !mRefreshRecyclerView.isLoadMore && (dx > 0 || dy > 0)) {
            mRefreshRecyclerView.isLoadMore = true
            mRefreshRecyclerView.loadMore()
        }
    }

    private fun findMax(lastPositions: IntArray): Int {
        var max = lastPositions[0]
        for (value in lastPositions) {
            if (value > max) {
                max = value
            }
        }
        return max
    }
}
