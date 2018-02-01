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
import com.f1reking.gank.entity.MessageEvent
import com.f1reking.gank.module.web.WebActivity
import com.f1reking.gank.net.RxScheduler
import com.f1reking.gank.room.AppDatabaseHelper
import com.f1reking.gank.widget.GankItemDecoration
import com.f1reking.gank.widget.xrecyclerview.XRecyclerView.PullLoadMoreListener
import com.f1reking.statuslayout.library.StatusClickListener
import com.f1reking.statuslayout.library.StatusLayout
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_my_collection.rv_collection
import me.f1reking.adapter.RecyclerAdapter.OnItemClickListener
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode.MAIN
import java.util.concurrent.TimeUnit.SECONDS

/**
 * @author: F1ReKing
 * @date: 2018/1/23 16:17
 * @desc:
 */
class MyCollectionActivity : BaseActivity(), PullLoadMoreListener {

    private val datas = ArrayList<CollectionEntity>()

    private val mGankAdapter: CollectionListAdapter by lazy {
        CollectionListAdapter(this, datas)
    }

    private val mStatusLayout: StatusLayout by lazy {
        StatusLayout.Builder(rv_collection)
            .setEmptyText("这里没有文章了哦╮(╯▽╰)╭\n去添加喜欢的文章吧")
            .setStatusClickListener(object : StatusClickListener {
                override fun onEmptyClick(view: View) {
                    mStatusLayout.showLoadingLayout()
                    getCollectionListByLazy()
                }

                override fun onErrorClick(view: View) {
                    mStatusLayout.showLoadingLayout()
                    getCollectionListByLazy()
                }
            })
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_collection)
        initView()
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault()
            .register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault()
            .unregister(this)
    }

    @Subscribe(threadMode = MAIN) fun onMessageEvent(event: MessageEvent) {
        if (event.flag == "refresh") {
            getCollectionList()
        }
    }

    private fun initView() {
        setToolbarTitle("我的收藏")
        rv_collection.apply {
            setColorSchemeResources(R.color.colorPrimary)
            setLinearLayout()
            setOnPullLoadMoreListener(this@MyCollectionActivity)
            setAdapter(mGankAdapter)
            loadMoreEnable = false
        }
            .recyclerView.apply {
            this!!.addItemDecoration(GankItemDecoration(this@MyCollectionActivity))
        }
        mGankAdapter.setOnItemClickListener(object : OnItemClickListener<CollectionEntity> {
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
        mStatusLayout.showLoadingLayout()
        getCollectionListByLazy()
    }

    private fun getCollectionListByLazy() {
        Observable.timer(1, SECONDS)
            .compose(RxScheduler.compose())
            .bindToLifecycle(this@MyCollectionActivity)
            .subscribe({
                getCollectionList()
            })
    }

    private fun getCollectionList() {
        mGankAdapter.clear()
        if (AppDatabaseHelper.getInstance(this).getCollectionList().isNotEmpty()) {
            mStatusLayout.showContentLayout()
            mGankAdapter.addAll(AppDatabaseHelper.getInstance(this).getCollectionList())
        } else {
            mStatusLayout.showEmptyLayout()
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