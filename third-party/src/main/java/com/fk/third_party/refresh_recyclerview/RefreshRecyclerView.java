package com.fk.third_party.refresh_recyclerview;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.fk.third_party.R;

/**
 * Created by HuangYH on 2016/5/3.
 */
public class RefreshRecyclerView extends LinearLayout {
    private RecyclerView recyclerView;
    private VpSwipeRefreshLayout swipeRefreshLayout;
    private PullLoadMoreListener pullLoadMoreListener;
    private boolean hasMore = true;
    private boolean isRefresh = false;
    private boolean isLoadMore = false;
    private boolean isTop = true;
    private boolean pullRefreshEnable = true;
    private boolean loadMoreEnable = true;
    private View footerView;
    private Context context;
    private TextView loadMoreText;

    public RefreshRecyclerView(Context context) {
        super(context);
        initView(context);
    }

    public RefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.refresh_layout, null);
        swipeRefreshLayout = (VpSwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayoutOnRefresh(this));
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setVerticalScrollBarEnabled(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnScrollListener(new RecyclerViewOnScroll(this));
        recyclerView.setOnTouchListener(new onTouchRecyclerView());
        footerView = view.findViewById(R.id.footerView);
        loadMoreText = (TextView) view.findViewById(R.id.loadMoreText);
        footerView.setVisibility(GONE);
        this.addView(view);
    }

    /**
     * LinearLayoutManager
     */
    public void setLinearLayout() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    /**
     * GridLayoutManager
     */
    public void setGridLayout(int spanCount) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, spanCount);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    /**
     * setStaggeredGridLayout
     */
    public void setStaggeredGridLayout(int spanCount) {
        StaggeredGridLayoutManager staggeredGridLayoutManager =
            new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
    }

    public void setIsTop(boolean top){
        isTop = top;
        if (pullLoadMoreListener != null) {
            pullLoadMoreListener.onBackTop();
        }
    }

    public boolean getIsTop(){
        return isTop;
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return recyclerView.getLayoutManager();
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void scrollToTop() {
        recyclerView.scrollToPosition(0);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        if (adapter != null) {
            recyclerView.setAdapter(adapter);
        }
    }

    /**
     * 下拉刷新开关
     */
    public void setPullRefreshEnable(boolean enable) {
        pullRefreshEnable = enable;
        setSwipeRefreshEnable(enable);
    }

    public boolean getPullRefreshEnable() {
        return pullRefreshEnable;
    }

    public void setSwipeRefreshEnable(boolean enable) {
        swipeRefreshLayout.setEnabled(enable);
    }

    public boolean getSwipeRefreshEnable() {
        return swipeRefreshLayout.isEnabled();
    }

    public void setColorSchemeResources(int... colorResIds) {
        swipeRefreshLayout.setColorSchemeResources(colorResIds);
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
    }

    public void setRefreshing(final boolean isRefreshing) {
        swipeRefreshLayout.post(new Runnable() {
            @Override public void run() {
                if (pullRefreshEnable) swipeRefreshLayout.setRefreshing(isRefreshing);
            }
        });
    }

    public boolean getLoadMoreEnable() {
        return loadMoreEnable;
    }

    /**
     *  上拉加载开关
     */
    public void setLoadMoreEnable(boolean enable) {
        this.loadMoreEnable = enable;
    }

    public void setFooterViewGone(){
        footerView.setVisibility(GONE);
    }


    public void setFooterViewText(CharSequence text) {
        loadMoreText.setText(text);
    }

    public void setFooterViewText(int resId) {
        loadMoreText.setText(resId);
    }

    public void refresh() {
        if (pullLoadMoreListener != null) {
            pullLoadMoreListener.onRefresh();
        }
    }

    public void loadMore() {
        if (pullLoadMoreListener != null && hasMore) {
            footerView.setVisibility(VISIBLE);
            invalidate();
            pullLoadMoreListener.onLoadMore();
        }
    }

    public void setPullLoadMoreCompleted() {
        isRefresh = false;
        swipeRefreshLayout.setRefreshing(false);

        isLoadMore = false;
        footerView.setVisibility(GONE);
    }

    public void setOnPullLoadMoreListener(PullLoadMoreListener listener) {
        pullLoadMoreListener = listener;
    }

    public boolean isLoadMore() {
        return isLoadMore;
    }

    public void setIsLoadMore(boolean isLoadMore) {
        this.isLoadMore = isLoadMore;
    }

    public boolean isRefresh() {
        return isRefresh;
    }

    public void setIsRefresh(boolean isRefresh) {
        this.isRefresh = isRefresh;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    /**
     * Solve IndexOutOfBoundsException exception
     */
    public class onTouchRecyclerView implements OnTouchListener {
        @Override public boolean onTouch(View v, MotionEvent event) {
            return isRefresh || isLoadMore;
        }
    }

    public interface PullLoadMoreListener {
        void onRefresh();
        void onBackTop();
        void onLoadMore();
    }
}
