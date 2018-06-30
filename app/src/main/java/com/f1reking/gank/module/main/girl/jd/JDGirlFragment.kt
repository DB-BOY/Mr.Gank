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
package com.f1reking.gank.module.main.girl.jd

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.f1reking.gank.Constant
import com.f1reking.gank.R
import com.f1reking.gank.base.LazyFragment
import com.f1reking.gank.entity.ApiErrorModel
import com.f1reking.gank.entity.JDGirlEntity
import com.f1reking.gank.entity.JDHttpEntity
import com.f1reking.gank.inflate
import com.f1reking.gank.module.main.girl.pic.BigMeiziActivity
import com.f1reking.gank.net.ApiClient
import com.f1reking.gank.net.ApiResponse
import com.f1reking.gank.net.RxScheduler
import com.f1reking.gank.toast
import com.f1reking.gank.widget.xrecyclerview.XRecyclerView
import com.f1reking.library.statuslayout.StatusClickListener
import com.f1reking.library.statuslayout.StatusLayout
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import kotlinx.android.synthetic.main.fragment_jd_girl.rv_jd_girl
import me.f1reking.adapter.RecyclerAdapter

/**
 * @author: F1ReKing
 * @date: 2018/6/4
 * @desc:
 */
class JDGirlFragment : LazyFragment(), XRecyclerView.PullLoadMoreListener {

  private var layout: View? = null
  private val datas = ArrayList<JDGirlEntity>()
  private var page: Int = 1
  private var isMore: Boolean = false

  private val mJDGirlListAdapter: JDGirlListAdapter by lazy {
    JDGirlListAdapter(activity!!, datas)
  }

  private val mStatusLayout: StatusLayout by lazy {
    StatusLayout.Builder(rv_jd_girl)
        .setOnEmptyText("咦，妹子都不见了\n\n 重新找看看吧")
        .setOnErrorText("出错啦 ")
        .setOnStatusClickListener(object : StatusClickListener {
          override fun onEmptyClick(view: View) {
            mStatusLayout.showLoadingLayout()
            page = 1
            loadJDGirlList()
          }

          override fun onErrorClick(view: View) {
            mStatusLayout.showLoadingLayout()
            page = 1
            loadJDGirlList()
          }
        })
        .build()
  }

  override fun onCreateView(inflater: LayoutInflater,
                            container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    layout = container!!.inflate(R.layout.fragment_jd_girl)
    return layout
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    initView()
  }

  private fun initView() {
    rv_jd_girl.apply {
      setColorSchemeResources(R.color.colorPrimary)
      setGridLayout(2)
      setOnPullLoadMoreListener(this@JDGirlFragment)
      setAdapter(mJDGirlListAdapter)
    }
    mJDGirlListAdapter.apply {
      setOnItemClickListener(object : RecyclerAdapter.OnItemClickListener<JDGirlEntity> {
        override fun onItemLongClick(p0: ViewGroup?,
                                     p1: View?,
                                     p2: JDGirlEntity?,
                                     position: Int): Boolean {
          return true
        }

        override fun onItemClick(p0: ViewGroup?,
                                 view: View,
                                 jdGirlEntity: JDGirlEntity,
                                 p3: Int) {
          val intent = Intent(activity!!, BigMeiziActivity::class.java)
          val picList = ArrayList<String>()
          val ids = ArrayList<String>()
          mJDGirlListAdapter.data.forEach {
            picList.add(it.pics[0])
            ids.add(it.comment_ID)
          }
          intent.putExtra(BigMeiziActivity.EXTRA_PIC_LIST, picList)
          intent.putExtra(BigMeiziActivity.EXTRA_ID, ids)
          intent.putExtra(BigMeiziActivity.EXTRA_POSITION, p3)
          startActivity(intent)
        }
      })
    }
  }

  override fun onFirstUserVisible() {
    mStatusLayout.showLoadingLayout()
    loadJDGirlList()
  }

  private fun loadJDGirlList() {
    ApiClient.instance.mService.getJDGirlList(Constant.JD_URL + page)
        .compose(RxScheduler.compose())
        .bindToLifecycle(this)
        .doAfterTerminate { rv_jd_girl.setPullLoadMoreCompleted() }
        .subscribe(object : ApiResponse<JDHttpEntity>(activity!!) {
          override fun success(data: JDHttpEntity) {
            if (page == 1) {
              mJDGirlListAdapter.clear()
            }
            mJDGirlListAdapter.addAll(data.comments)
            if (mJDGirlListAdapter.data.size > 0) {
              mStatusLayout.showContentLayout()
            } else {
              mStatusLayout.showEmptyLayout()
            }
            if (isMore) {
              if (data.comments.isEmpty()) {
                --page
              }
            }
          }

          override fun failure(statusCode: Int,
                               apiErrorModel: ApiErrorModel) {
            activity!!.toast(apiErrorModel.msg)
            if (mJDGirlListAdapter.data.size == 0) {
              mStatusLayout.showErrorLayout()
            }
            if (isMore) {
              --page
            }
          }
        })
  }

  override fun onRefresh() {
    isMore = false
    page = 1
    loadJDGirlList()
  }

  override fun onBackTop() {
  }

  override fun onLoadMore() {
    isMore = true
    ++page
    loadJDGirlList()
  }
}