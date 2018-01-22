package com.f1reking.gank.module.main.gank.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.f1reking.gank.R
import com.f1reking.gank.base.LazyFragment
import com.f1reking.gank.entity.ApiErrorModel
import com.f1reking.gank.entity.GankEntity
import com.f1reking.gank.entity.HttpEntity
import com.f1reking.gank.module.main.gank.GankListAdapter
import com.f1reking.gank.module.web.WebActivity
import com.f1reking.gank.net.ApiClient
import com.f1reking.gank.net.ApiResponse
import com.f1reking.gank.net.RxScheduler
import com.f1reking.gank.toast
import com.f1reking.gank.widget.GankItemDecoration
import com.fk.third_party.refresh_recyclerview.RefreshRecyclerView.PullLoadMoreListener
import kotlinx.android.synthetic.main.fragment_gank_android.rv_gank
import me.f1reking.adapter.RecyclerAdapter.OnItemClickListener

/**
 * @author: huangyh
 * @date: 2018/1/15 22:32
 * @desc:
 */
class GankResourcesFragment : LazyFragment(), PullLoadMoreListener {

    companion object {
        val TYPE = "拓展资源"
    }

    private var layout: View? = null
    private val datas = mutableListOf<GankEntity>()
    private var page: Int = 1

    private val mGankAdapter: GankListAdapter by lazy {
        GankListAdapter(activity!!, datas)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        layout = inflater.inflate(R.layout.fragment_gank_resources, container, false)
        return layout
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rv_gank.run {
            rv_gank.setColorSchemeResources(R.color.colorPrimary)
            rv_gank.setLinearLayout()
            rv_gank.setOnPullLoadMoreListener(this@GankResourcesFragment)
            rv_gank.setAdapter(mGankAdapter)
        }
        rv_gank.recyclerView.run {
            this!!.addItemDecoration(GankItemDecoration(activity!!))
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
    }

    override fun onFirstUserVisible() {
        rv_gank.setRefreshing(true)
        loadGankList()
    }

    private fun loadGankList() {
        ApiClient.instance.mService.getGankList(TYPE, 10, page).compose(
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