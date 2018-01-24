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

package com.f1reking.gank.module.main.meizi

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
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
import com.f1reking.gank.toast
import com.f1reking.gank.widget.xrecyclerview.XRecyclerView.PullLoadMoreListener
import kotlinx.android.synthetic.main.fragment_meizi.rv_meizi
import me.f1reking.adapter.RecyclerAdapter.OnItemClickListener

/**
 * @author: F1ReKing
 * @date: 2018/1/5 16:57
 * @desc:
 */
class MeiziFragment : BaseFragment(), PullLoadMoreListener {

    private var layout: View? = null
    private val datas = mutableListOf<GankEntity>()
    private var page: Int = 1

    private val mMeiziAdapter: MeiziListAdapter by lazy {
        MeiziListAdapter(activity!!, datas)
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
            setColorSchemeResources(R.color.colorPrimary)
            setStaggeredGridLayout(2)
            setOnPullLoadMoreListener(this@MeiziFragment)
            setAdapter(mMeiziAdapter)
        }
        mMeiziAdapter.run {
            mMeiziAdapter.setOnItemClickListener(object : OnItemClickListener<GankEntity> {
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
                    val intent = Intent(activity!!, BigMeiziActivity::class.java)
                    intent.putExtra(BigMeiziActivity.EXTRA_URL, p2?.url)
                    intent.putExtra(BigMeiziActivity.EXTRA_TITLE, p2?._id)
                    val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        activity!!, p1!!, BigMeiziActivity.TRANSIT_PIC)
                    try {
                        ActivityCompat.startActivity(activity!!, intent, optionsCompat.toBundle())
                    } catch (e: IllegalArgumentException) {
                        e.printStackTrace()
                        startActivity(intent)
                    }
                }
            })
        }

        loadMeiziList()
    }

    private fun loadMeiziList() {
        ApiClient.instance.mService.getGankList("福利", 10, page).compose(
            RxScheduler.compose()).doOnSubscribe {
            rv_meizi.setRefreshing(true)
        }.doAfterTerminate { rv_meizi.setPullLoadMoreCompleted() }.subscribe(
            object : ApiResponse<HttpEntity>(activity!!) {
                override fun success(data: HttpEntity) {
                    if (page == 1) {
                        mMeiziAdapter.clear()
                    }
                    mMeiziAdapter.addAll(data.results)
                }

                override fun failure(statusCode: Int,
                                     apiErrorModel: ApiErrorModel) {
                    activity!!.toast(apiErrorModel.msg)
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
        rv_meizi.setFooterViewGone()
        ++page
        loadMeiziList()
    }
}