package com.f1reking.gank.module.main.gank

import android.os.Bundle
import android.support.design.widget.TabLayout.OnTabSelectedListener
import android.support.design.widget.TabLayout.Tab
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.f1reking.gank.R
import com.f1reking.gank.base.BaseFragment
import com.f1reking.gank.entity.ApiErrorModel
import com.f1reking.gank.entity.GankEntity
import com.f1reking.gank.entity.HttpEntity
import com.f1reking.gank.module.web.WebActivity
import com.f1reking.gank.net.ApiClient
import com.f1reking.gank.net.ApiResponse
import com.f1reking.gank.net.RxScheduler
import com.fk.third_party.refresh_recyclerview.RefreshRecyclerView.PullLoadMoreListener
import kotlinx.android.synthetic.main.fragment_news.rv_news
import kotlinx.android.synthetic.main.fragment_news.tab_news
import kotlinx.android.synthetic.main.fragment_news.view.tab_news
import me.f1reking.adapter.RecyclerAdapter.OnItemClickListener

/**
 * @author: huangyh
 * @date: 2018/1/4 15:57
 * @desc:
 */
class GankFragment : BaseFragment(), PullLoadMoreListener {

    private var layout: View? = null
    private val datas = mutableListOf<GankEntity>()
    private var page: Int = 1
    private lateinit var type: String

    private val mGankAdapter: GankListAdapter by lazy {
        GankListAdapter(this.activity!!, datas)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        layout = inflater.inflate(R.layout.fragment_news, container, false)
        return layout
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tab_news.run {
            val tabList = listOf("Android", "iOS", "前端", "拓展资源", "瞎推荐", "App")
            tabList.forEach {
                val tab = tab_news.newTab()
                tab.text = it
                tab_news.addTab(tab)
            }
            tab_news.addOnTabSelectedListener(object : OnTabSelectedListener {
                override fun onTabReselected(tab: Tab?) {
                }

                override fun onTabUnselected(tab: Tab?) {
                }

                override fun onTabSelected(tab: Tab?) {
                    type = tab?.text.toString()
                    page = 1
                    rv_news.scrollToTop()
                    loadGankList()
                }
            })
        }
        rv_news.run {
            rv_news.setColorSchemeResources(R.color.colorPrimary)
            rv_news.setLinearLayout()
            rv_news.setOnPullLoadMoreListener(this@GankFragment)
            rv_news.setAdapter(mGankAdapter)
        }
        mGankAdapter.run {
            mGankAdapter.setOnItemClickListener(object : OnItemClickListener<GankEntity> {
                override fun onItemLongClick(p0: ViewGroup?,
                                             p1: View?,
                                             p2: GankEntity?,
                                             p3: Int): Boolean {
                    return true
                }

                override fun onItemClick(p0: ViewGroup?,
                                         p1: View?,
                                         p2: GankEntity?,
                                         p3: Int) {

                    WebActivity.newIntent(p1!!.context, p2!!.url, p2.desc)
                }
            })
        }
        rv_news.setRefreshing(true)
        type = "Android"
        loadGankList()
    }

    private fun loadGankList() {
        ApiClient.instance.mService.getGankList(type, 10, page).compose(
            RxScheduler.compose()).doAfterTerminate { rv_news.setPullLoadMoreCompleted() }.subscribe(
            object : ApiResponse<HttpEntity>(this.activity!!) {
                override fun success(data: HttpEntity) {
                    if (page == 1) {
                        mGankAdapter.clear()
                    }
                    mGankAdapter.addAll(data.results)
                }

                override fun failure(statusCode: Int,
                                     apiErrorModel: ApiErrorModel) {
                    toast(apiErrorModel.msg)
                }
            })
    }

    override fun onRefresh() {
        page = 1
        loadGankList()
    }

    override fun onBackTop() {
    }

    override fun onLoadMore() {
        ++page
        loadGankList()
    }
}







