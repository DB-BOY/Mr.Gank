/*
 *  Copyright (c) 2018 F1ReKing
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.f1reking.gank.widget.xrecyclerview

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager

/**
* @author: F1ReKing
* @date: 2016/5/3 17:15
* @desc:
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
