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

package com.f1reking.gank.module.main.gank.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.f1reking.gank.R
import com.f1reking.gank.base.LazyFragment
import com.f1reking.gank.entity.ApiErrorModel
import com.f1reking.gank.entity.GankEntity
import com.f1reking.gank.entity.HttpEntity
import com.f1reking.gank.inflate
import com.f1reking.gank.module.collection.MyCollectionActivity
import com.f1reking.gank.module.main.gank.GankListAdapter
import com.f1reking.gank.module.web.WebActivity
import com.f1reking.gank.net.ApiClient
import com.f1reking.gank.net.ApiResponse
import com.f1reking.gank.net.RxScheduler
import com.f1reking.gank.toast
import com.f1reking.gank.widget.GankItemDecoration
import com.f1reking.gank.widget.xrecyclerview.XRecyclerView.PullLoadMoreListener
import com.f1reking.library.statuslayout.StatusClickListener
import com.f1reking.library.statuslayout.StatusLayout
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import kotlinx.android.synthetic.main.fragment_gank_android.rv_gank
import kotlinx.android.synthetic.main.fragment_gank_resources.*
import me.f1reking.adapter.RecyclerAdapter.OnItemClickListener

/**
 * @author: F1ReKing
 * @date: 2018/1/15 22:32
 * @desc:
 */
class GankResourcesFragment : LazyFragment(), PullLoadMoreListener {

    private val TYPE = "拓展资源"

    private var layout: View? = null
    private val datas = ArrayList<GankEntity>()
    private var page: Int = 1

    private val mGankAdapter: GankListAdapter by lazy {
        GankListAdapter(activity!!, datas)
    }

    private val mStatusLayout: StatusLayout by lazy {
        StatusLayout.Builder(rv_gank)
            .setOnStatusClickListener(object : StatusClickListener {
                override fun onEmptyClick(view: View) {
                    mStatusLayout.showLoadingLayout()
                    page = 1
                    loadGankList()
                }

                override fun onErrorClick(view: View) {
                    mStatusLayout.showLoadingLayout()
                    page = 1
                    loadGankList()
                }
            })
            .build()
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        layout = container!!.inflate(R.layout.fragment_gank_resources)
        return layout
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rv_gank.apply {
            setColorSchemeResources(R.color.colorPrimary)
            setLinearLayout()
            setOnPullLoadMoreListener(this@GankResourcesFragment)
            setAdapter(mGankAdapter)
        }
            .recyclerView.apply {
            this!!.addItemDecoration(GankItemDecoration(activity!!))
        }
        mGankAdapter.apply {
            setOnItemClickListener(object : OnItemClickListener<GankEntity> {
                override fun onItemLongClick(p0: ViewGroup?,
                                             p1: View?,
                                             p2: GankEntity?,
                                             p3: Int): Boolean {
                    return true
                }

                override fun onItemClick(p0: ViewGroup?,
                                         p1: View?,
                                         p2: GankEntity,
                                         p3: Int) {
                    WebActivity.newIntent(activity!!, p2)
                }
            })
        }
        fab.apply {
            attachToRecyclerView(rv_gank.recyclerView!!)
            setOnClickListener{
                startActivity(Intent(activity, MyCollectionActivity::class.java))
            }
        }
    }

    override fun onFirstUserVisible() {
        mStatusLayout.showLoadingLayout()
        loadGankList()
    }

    private fun loadGankList() {
        ApiClient.instance.mService.getGankList(TYPE, 10, page)
            .compose(RxScheduler.compose())
            .bindToLifecycle(this)
            .doAfterTerminate { rv_gank.setPullLoadMoreCompleted() }
            .subscribe(object : ApiResponse<HttpEntity>(activity!!) {
                override fun success(data: HttpEntity) {
                    if (page == 1) {
                        mGankAdapter.clear()
                    }
                    mGankAdapter.addAll(data.results)
                    if (mGankAdapter.data.size > 0) {
                        mStatusLayout.showContentLayout()
                    } else {
                        mStatusLayout.showEmptyLayout()
                    }
                }

                override fun failure(statusCode: Int,
                                     apiErrorModel: ApiErrorModel) {
                    activity!!.toast(apiErrorModel.msg)
                    mStatusLayout.showErrorLayout()
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