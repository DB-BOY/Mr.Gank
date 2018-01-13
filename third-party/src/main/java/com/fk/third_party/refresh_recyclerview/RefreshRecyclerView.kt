package com.fk.third_party.refresh_recyclerview

import android.content.Context
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.fk.third_party.R

/**
 * Created by HuangYH on 2016/5/3.
 */
class RefreshRecyclerView : LinearLayout {
    var recyclerView: RecyclerView? = null
        private set
    private var swipeRefreshLayout: VpSwipeRefreshLayout? = null
    private var pullLoadMoreListener: PullLoadMoreListener? = null
    var isHasMore = true
    var isRefresh = false
    var isLoadMore = false
    var isTop = true
        set(top) {
            field = top
            if (pullLoadMoreListener != null) {
                pullLoadMoreListener!!.onBackTop()
            }
        }
    /**
     * 下拉刷新开关
     */
    var pullRefreshEnable = true
        set(enable) {
            field = enable
            swipeRefreshEnable = enable
        }
    /**
     * 上拉加载开关
     */
    var loadMoreEnable = true
    private var footerView: View? = null
    private var mContext:Context?= null
    private var loadMoreText: TextView? = null

    val layoutManager: RecyclerView.LayoutManager
        get() = recyclerView!!.layoutManager

    var swipeRefreshEnable: Boolean
        get() = swipeRefreshLayout!!.isEnabled
        set(enable) {
            swipeRefreshLayout!!.isEnabled = enable
        }

    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context,
                attrs: AttributeSet) : super(context, attrs) {
        initView(context)
    }

    private fun initView(context: Context) {
        mContext = context
        val view = LayoutInflater.from(context).inflate(R.layout.refresh_layout, null)
        swipeRefreshLayout = view.findViewById<View>(
            R.id.swipeRefreshLayout) as VpSwipeRefreshLayout
        swipeRefreshLayout!!.setColorSchemeResources(android.R.color.holo_red_light)
        swipeRefreshLayout!!.setOnRefreshListener(SwipeRefreshLayoutOnRefresh(this))
        recyclerView = view.findViewById<View>(R.id.recycler_view) as RecyclerView
        recyclerView!!.isVerticalScrollBarEnabled = true
        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.itemAnimator = DefaultItemAnimator()
        recyclerView!!.addOnScrollListener(RecyclerViewOnScroll(this))
        recyclerView!!.setOnTouchListener(onTouchRecyclerView())
        footerView = view.findViewById(R.id.footerView)
        loadMoreText = view.findViewById<View>(R.id.loadMoreText) as TextView
        footerView!!.visibility = View.GONE
        this.addView(view)
    }

    /**
     * LinearLayoutManager
     */
    fun setLinearLayout() {
        val linearLayoutManager = LinearLayoutManager(mContext)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView!!.layoutManager = linearLayoutManager
    }

    /**
     * GridLayoutManager
     */
    fun setGridLayout(spanCount: Int) {
        val gridLayoutManager = GridLayoutManager(mContext, spanCount)
        gridLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView!!.layoutManager = gridLayoutManager
    }

    /**
     * setStaggeredGridLayout
     */
    fun setStaggeredGridLayout(spanCount: Int) {
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(spanCount,
            StaggeredGridLayoutManager.VERTICAL)
        recyclerView!!.layoutManager = staggeredGridLayoutManager
    }

    fun scrollToTop() {
        recyclerView!!.scrollToPosition(0)
    }

    fun setAdapter(adapter: RecyclerView.Adapter<*>?) {
        if (adapter != null) {
            recyclerView!!.adapter = adapter
        }
    }

    fun setColorSchemeResources(vararg colorResIds: Int) {
        swipeRefreshLayout!!.setColorSchemeResources(*colorResIds)
    }

    fun getSwipeRefreshLayout(): SwipeRefreshLayout? {
        return swipeRefreshLayout
    }

    fun setRefreshing(isRefreshing: Boolean) {
        swipeRefreshLayout!!.post(object : Runnable {
            override fun run() {
                if (pullRefreshEnable) swipeRefreshLayout!!.isRefreshing = isRefreshing
            }
        })
    }

    fun setFooterViewGone() {
        footerView!!.visibility = View.GONE
    }

    fun setFooterViewText(text: CharSequence) {
        loadMoreText!!.text = text
    }

    fun setFooterViewText(resId: Int) {
        loadMoreText!!.setText(resId)
    }

    fun refresh() {
        if (pullLoadMoreListener != null) {
            pullLoadMoreListener!!.onRefresh()
        }
    }

    fun loadMore() {
        if (pullLoadMoreListener != null && isHasMore) {
            footerView!!.visibility = View.VISIBLE
            invalidate()
            pullLoadMoreListener!!.onLoadMore()
        }
    }

    fun setPullLoadMoreCompleted() {
        isRefresh = false
        swipeRefreshLayout!!.isRefreshing = false

        isLoadMore = false
        footerView!!.visibility = View.GONE
    }

    fun setOnPullLoadMoreListener(listener: PullLoadMoreListener) {
        pullLoadMoreListener = listener
    }

    /**
     * Solve IndexOutOfBoundsException exception
     */
    inner class onTouchRecyclerView : View.OnTouchListener {
        override fun onTouch(v: View,
                             event: MotionEvent): Boolean {
            return isRefresh || isLoadMore
        }
    }

    interface PullLoadMoreListener {
        fun onRefresh()
        fun onBackTop()
        fun onLoadMore()
    }
}
