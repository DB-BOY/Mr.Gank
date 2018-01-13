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
import com.f1reking.gank.toast
import com.fk.third_party.refresh_recyclerview.RefreshRecyclerView.PullLoadMoreListener
import kotlinx.android.synthetic.main.fragment_gank.rv_gank
import kotlinx.android.synthetic.main.fragment_gank.tab_gank
import kotlinx.android.synthetic.main.fragment_gank.view.tab_gank
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
        GankListAdapter(activity!!, datas)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        layout = inflater.inflate(R.layout.fragment_gank, container, false)
        return layout
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tab_gank.run {
            val tabList = listOf("Android", "iOS", "前端", "拓展资源", "瞎推荐", "App")
            tabList.forEach {
                val tab = tab_gank.newTab()
                tab.text = it
                tab_gank.addTab(tab)
            }
            tab_gank.addOnTabSelectedListener(object : OnTabSelectedListener {
                override fun onTabReselected(tab: Tab?) {
                }

                override fun onTabUnselected(tab: Tab?) {
                }

                override fun onTabSelected(tab: Tab?) {
                    type = tab?.text.toString()
                    page = 1
                    rv_gank.scrollToTop()
                    loadGankList()
                }
            })
        }
        rv_gank.run {
            rv_gank.setColorSchemeResources(R.color.colorPrimary)
            rv_gank.setLinearLayout()
            rv_gank.setOnPullLoadMoreListener(this@GankFragment)
            rv_gank.setAdapter(mGankAdapter)
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

                    WebActivity.newIntent(activity!!, p2!!.url, p2.desc)
                }
            })
        }
        rv_gank.setRefreshing(true)
        type = "Android"
        loadGankList()
    }

    private fun loadGankList() {
        ApiClient.instance.mService.getGankList(type, 10, page).compose(
            RxScheduler.compose()).doOnSubscribe {
            rv_gank.setRefreshing(true)
        }.doAfterTerminate { rv_gank.setPullLoadMoreCompleted() }.subscribe(
            object : ApiResponse<HttpEntity>(activity!!) {
                override fun success(data: HttpEntity) {
                    if (page == 1) {
                        mGankAdapter.clear()
                    }
                    mGankAdapter.addAll(data.results)
                }

                override fun failure(statusCode: Int,
                                     apiErrorModel: ApiErrorModel) {
                    activity!!.toast(apiErrorModel.msg)
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
        rv_gank.setFooterViewGone()
        ++page
        loadGankList()
    }
}







