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

package com.f1reking.gank.module.collection

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.f1reking.gank.R
import com.f1reking.gank.base.BaseActivity
import com.f1reking.gank.entity.CollectionEntity
import com.f1reking.gank.entity.GankEntity
import com.f1reking.gank.module.web.WebActivity
import com.f1reking.gank.room.AppDatabaseHelper
import com.f1reking.gank.widget.GankItemDecoration
import com.f1reking.gank.widget.xrecyclerview.XRecyclerView.PullLoadMoreListener
import kotlinx.android.synthetic.main.activity_my_collection.rv_collection
import me.f1reking.adapter.RecyclerAdapter.OnItemClickListener

/**
 * @author: F1ReKing
 * @date: 2018/1/23 16:17
 * @desc:
 */
class MyCollectionActivity : BaseActivity(), PullLoadMoreListener {

    private val datas = ArrayList<CollectionEntity>()
    private var page: Int = 1

    private val mGankAdapter: CollectionListAdapter by lazy {
        CollectionListAdapter(this, datas)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_collection)
        initView()
    }

    override fun onResume() {
        super.onResume()
        getCollectionList()
    }

    private fun initView() {
        setToolbarTitle("我的收藏")
        rv_collection.run {
            setColorSchemeResources(R.color.colorPrimary)
            setLinearLayout()
            setOnPullLoadMoreListener(this@MyCollectionActivity)
            setAdapter(mGankAdapter)
            loadMoreEnable = false
        }
        rv_collection.recyclerView.run {
            this!!.addItemDecoration(GankItemDecoration(this@MyCollectionActivity))
        }
        mGankAdapter.run {
            setOnItemClickListener(object : OnItemClickListener<CollectionEntity> {
                override fun onItemLongClick(p0: ViewGroup?,
                                             p1: View?,
                                             p2: CollectionEntity?,
                                             p3: Int): Boolean {
                    return true
                }

                override fun onItemClick(p0: ViewGroup?,
                                         p1: View?,
                                         p2: CollectionEntity,
                                         p3: Int) {
                    val gankEntity = GankEntity("", "", "", "", "", "", "")
                    gankEntity._id = p2._id
                    gankEntity.desc = p2.desc
                    gankEntity.type = p2.type
                    gankEntity.url = p2.url
                    gankEntity.who = p2.who
                    gankEntity.publishedAt = p2.publishedAt
                    WebActivity.newIntent(this@MyCollectionActivity, gankEntity)
                }
            })
        }
    }

    private fun getCollectionList() {
        mGankAdapter.clear()
        if (AppDatabaseHelper.getInstance(this).getCollectionList().isNotEmpty()) {
            mGankAdapter.addAll(AppDatabaseHelper.getInstance(this).getCollectionList())
        }
        rv_collection.setPullLoadMoreCompleted()
    }

    override fun onRefresh() {
       getCollectionList()
    }

    override fun onBackTop() {
    }

    override fun onLoadMore() {

    }
}