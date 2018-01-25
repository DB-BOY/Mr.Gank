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
import com.f1reking.gank.net.ApiClient
import com.f1reking.gank.net.ApiResponse
import com.f1reking.gank.net.RxScheduler
import com.f1reking.gank.toast
import com.f1reking.gank.widget.GankItemDecoration
import com.f1reking.gank.widget.xrecyclerview.XRecyclerView.PullLoadMoreListener
import kotlinx.android.synthetic.main.fragment_gank_android.rv_gank

/**
 * @author: F1ReKing
 * @date: 2018/1/15 22:31
 * @desc:
 */
class GankiOSFragment : LazyFragment(), PullLoadMoreListener {

    companion object {
        val TYPE = "iOS"
    }

    private var layout: View? = null
    private val datas = ArrayList<GankEntity>()
    private var page: Int = 1

    private val mGankAdapter: GankListAdapter by lazy {
        GankListAdapter(activity!!, datas)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        layout = inflater.inflate(R.layout.fragment_gank_ios, container, false)
        return layout
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rv_gank.run {
            setColorSchemeResources(R.color.colorPrimary)
            setLinearLayout()
            setOnPullLoadMoreListener(this@GankiOSFragment)
            setAdapter(mGankAdapter)
        }
        rv_gank.recyclerView.run {
            this!!.addItemDecoration(GankItemDecoration(activity!!))
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