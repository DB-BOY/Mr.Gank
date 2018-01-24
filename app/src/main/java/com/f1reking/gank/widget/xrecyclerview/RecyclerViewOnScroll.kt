package com.f1reking.gank.widget.xrecyclerview

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager

/**
 * Created by HuangYH on 2016/5/3.
 */
class RecyclerViewOnScroll(private val mXRecyclerView: XRecyclerView) :
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
            if (mXRecyclerView.pullRefreshEnable) {
                mXRecyclerView.swipeRefreshEnable = true
            }
            mXRecyclerView.isTop = true
        } else {
            mXRecyclerView.isTop = false
            mXRecyclerView.swipeRefreshEnable = false
        }
        if (mXRecyclerView.loadMoreEnable && !mXRecyclerView.isRefresh && mXRecyclerView.isHasMore && lastCompletelyVisibleItem == totalItemCount - 1 && !mXRecyclerView.isLoadMore && (dx > 0 || dy > 0)) {
            mXRecyclerView.isLoadMore = true
            mXRecyclerView.loadMore()
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
