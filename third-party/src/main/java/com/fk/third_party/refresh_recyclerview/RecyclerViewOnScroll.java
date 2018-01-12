package com.fk.third_party.refresh_recyclerview;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Created by HuangYH on 2016/5/3.
 */
public class RecyclerViewOnScroll extends RecyclerView.OnScrollListener {

    private RefreshRecyclerView mRefreshRecyclerView;
    public boolean isTop;

    public RecyclerViewOnScroll(RefreshRecyclerView refreshRecyclerView) {
        mRefreshRecyclerView = refreshRecyclerView;
    }

    @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int lastCompletelyVisibleItem = 0;
        int firstVisibleItem = 0;
        LayoutManager layoutManager = recyclerView.getLayoutManager();
        int totalItemCount = layoutManager.getItemCount();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            lastCompletelyVisibleItem = gridLayoutManager.findLastCompletelyVisibleItemPosition();
            firstVisibleItem = gridLayoutManager.findFirstCompletelyVisibleItemPosition();
        } else if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            lastCompletelyVisibleItem = linearLayoutManager.findLastCompletelyVisibleItemPosition();
            firstVisibleItem = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
            int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
            staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(lastPositions);
            lastCompletelyVisibleItem = findMax(lastPositions);
            firstVisibleItem = staggeredGridLayoutManager.findFirstVisibleItemPositions(lastPositions)[0];
        }
        if (firstVisibleItem == 0) {
            if (mRefreshRecyclerView.getPullRefreshEnable()) {
                mRefreshRecyclerView.setSwipeRefreshEnable(true);
            }
            mRefreshRecyclerView.setIsTop(true);
        } else {
            mRefreshRecyclerView.setIsTop(false);
            mRefreshRecyclerView.setSwipeRefreshEnable(false);
        }
        if (mRefreshRecyclerView.getLoadMoreEnable()
            && !mRefreshRecyclerView.isRefresh()
            && mRefreshRecyclerView.isHasMore()
            && (lastCompletelyVisibleItem == totalItemCount - 1)
            && !mRefreshRecyclerView.isLoadMore()
            && (dx > 0 || dy > 0)) {
            mRefreshRecyclerView.setIsLoadMore(true);
            mRefreshRecyclerView.loadMore();
        }
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

}
