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

package com.f1reking.gank.module.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.f1reking.gank.R
import com.f1reking.gank.base.BaseActivity
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
import com.f1reking.gank.widget.xrecyclerview.XRecyclerView.PullLoadMoreListener
import com.f1reking.statuslayout.library.StatusClickListener
import com.f1reking.statuslayout.library.StatusLayout
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import kotlinx.android.synthetic.main.activity_search.rv_search
import me.f1reking.adapter.RecyclerAdapter.OnItemClickListener

/**
 * @author: F1ReKing
 * @date: 2018/1/22 17:29
 * @desc:
 */
class SearchActivity : BaseActivity(), PullLoadMoreListener {

    companion object {
        const val EXTRA_QUERY = "query"

        fun newIntent(context: Context,
                      query: String) {
            val intent = Intent(context, SearchActivity::class.java)
            intent.putExtra(SearchActivity.EXTRA_QUERY, query)
            context.startActivity(intent)
        }
    }

    private lateinit var query: String
    private val datas = ArrayList<GankEntity>()
    private var page: Int = 1
    private val mGankAdapter: GankListAdapter by lazy {
        GankListAdapter(this, datas)
    }

    private val mStatusLayout: StatusLayout by lazy {
        StatusLayout.Builder(rv_search)
            .setLoadingText("搜索中...")
            .setEmptyText("搜索数据为空\n请重新搜索")
            .setErrorText("搜索出错了\n请重新搜索")
            .setStatusClickListener(object : StatusClickListener {
                override fun onEmptyClick(view: View) {
                    page = 1
                    mStatusLayout.showLoadingLayout()
                    queryGankList()
                }

                override fun onErrorClick(view: View) {
                    page = 1
                    mStatusLayout.showLoadingLayout()
                    queryGankList()
                }
            })
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initView()
    }

    private fun initView() {
        query = intent.getStringExtra(EXTRA_QUERY)
        setToolbarTitle("搜索：" + query)
        rv_search.apply {
            setColorSchemeResources(R.color.colorPrimary)
            setLinearLayout()
            setOnPullLoadMoreListener(this@SearchActivity)
            setAdapter(mGankAdapter)
        }
            .recyclerView.apply {
            this!!.addItemDecoration(GankItemDecoration(this@SearchActivity))
        }
        mGankAdapter.setOnItemClickListener(object : OnItemClickListener<GankEntity> {
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
                WebActivity.newIntent(this@SearchActivity, p2)
            }
        })
        mStatusLayout.showLoadingLayout()
        queryGankList()
    }

    private fun queryGankList() {
        ApiClient.instance.mService.queryGankList(query, 10, page)
            .compose(RxScheduler.compose())
            .bindToLifecycle(this)
            .doAfterTerminate { rv_search.setPullLoadMoreCompleted() }
            .subscribe(object : ApiResponse<HttpEntity>(this@SearchActivity) {
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
                    toast(apiErrorModel.msg)
                    mStatusLayout.showErrorLayout()
                }
            })
    }

    override fun onRefresh() {
        page = 1
        queryGankList()
    }

    override fun onBackTop() {
    }

    override fun onLoadMore() {
        ++page
        queryGankList()
    }
}