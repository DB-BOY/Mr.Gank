package com.f1reking.gank.module.main.meizi

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.f1reking.gank.R
import com.f1reking.gank.base.BaseFragment
import com.f1reking.gank.entity.ApiErrorModel
import com.f1reking.gank.entity.GankEntity
import com.f1reking.gank.entity.HttpEntity
import com.f1reking.gank.net.ApiClient
import com.f1reking.gank.net.ApiResponse
import com.f1reking.gank.net.RxScheduler
import com.fk.third_party.refresh_recyclerview.RefreshRecyclerView.PullLoadMoreListener
import kotlinx.android.synthetic.main.fragment_meizi.rv_meizi
import me.f1reking.adapter.RecyclerAdapter.OnItemClickListener

/**
 * @author: huangyh
 * @date: 2018/1/5 16:57
 * @desc:
 */
class MeiziFragment : BaseFragment(), PullLoadMoreListener {

    private var layout: View? = null
    private val datas = mutableListOf<GankEntity>()
    private var page: Int = 1

    private val mMeiziAdapter: MeiziListAdapter by lazy {
        MeiziListAdapter(this.activity!!, datas)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        layout = inflater.inflate(R.layout.fragment_meizi, container, false)
        return layout
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rv_meizi.run {
            rv_meizi.setColorSchemeResources(R.color.colorPrimary)
            rv_meizi.setGridLayout(2)
            rv_meizi.setOnPullLoadMoreListener(this@MeiziFragment)
            rv_meizi.setAdapter(mMeiziAdapter)
        }
        mMeiziAdapter.run {
            mMeiziAdapter.setOnItemClickListener(object :OnItemClickListener<GankEntity>{
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
                    val intent = Intent(activity, BigMeiziActivity::class.java)
                    intent.putExtra(BigMeiziActivity.URL, p2?.url)
                    startActivity(intent)
                }
            })
        }

        loadMeiziList()
    }

    private fun loadMeiziList() {
        ApiClient.instance.mService.getGankList("福利", 10, page).compose(
            RxScheduler.compose()).doAfterTerminate { rv_meizi.setPullLoadMoreCompleted() }.subscribe(
            object : ApiResponse<HttpEntity>(this.activity!!) {
                override fun success(data: HttpEntity) {
                    if (page == 1) {
                        mMeiziAdapter.clear()
                    }
                    mMeiziAdapter.addAll(data.results)
                }

                override fun failure(statusCode: Int,
                                     apiErrorModel: ApiErrorModel) {
                    toast(apiErrorModel.msg)
                }
            })
    }

    override fun onRefresh() {
        page = 1
        loadMeiziList()
    }

    override fun onBackTop() {
    }

    override fun onLoadMore() {
        ++page
        loadMeiziList()
    }
}