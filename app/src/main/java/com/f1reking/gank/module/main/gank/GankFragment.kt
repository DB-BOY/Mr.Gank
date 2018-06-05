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

package com.f1reking.gank.module.main.gank

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.androidkun.xtablayout.XTabLayout
import com.androidkun.xtablayout.XTabLayout.OnTabSelectedListener
import com.f1reking.gank.R
import com.f1reking.gank.base.BaseFragment
import com.f1reking.gank.inflate
import com.f1reking.gank.module.main.gank.fragment.GankAndroidFragment
import com.f1reking.gank.module.main.gank.fragment.GankAppFragment
import com.f1reking.gank.module.main.gank.fragment.GankRecommendFragment
import com.f1reking.gank.module.main.gank.fragment.GankResourcesFragment
import com.f1reking.gank.module.main.gank.fragment.GankWebFragment
import com.f1reking.gank.module.main.gank.fragment.GankiOSFragment
import com.f1reking.gank.module.search.SearchActivity
import com.miguelcatalan.materialsearchview.MaterialSearchView
import kotlinx.android.synthetic.main.fragment_gank.search_view
import kotlinx.android.synthetic.main.fragment_gank.tab_gank
import kotlinx.android.synthetic.main.fragment_gank.vp_gank

/**
 * @author: F1ReKing
 * @date: 2018/1/4 15:57
 * @desc:
 */
class GankFragment : BaseFragment() {

  private var layout: View? = null
  private var fragmentList = ArrayList<Fragment>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)
  }

  override fun onCreateView(inflater: LayoutInflater,
                            container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    layout = container!!.inflate(R.layout.fragment_gank)
    return layout
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    val mGankAndroidFragment = GankAndroidFragment()
    val mGankiOSFragment = GankiOSFragment()
    val mGankWebFragment = GankWebFragment()
    val mGankResourcesFragment = GankResourcesFragment()
    val mGankRecommendFragment = GankRecommendFragment()
    val mGankAppFragment = GankAppFragment()
    fragmentList.add(mGankAndroidFragment)
    fragmentList.add(mGankiOSFragment)
    fragmentList.add(mGankWebFragment)
    fragmentList.add(mGankResourcesFragment)
    fragmentList.add(mGankRecommendFragment)
    fragmentList.add(mGankAppFragment)
    val pagerAdapter = TabPagerAdapter(childFragmentManager, fragmentList,
        resources.getStringArray(R.array.tab_gank))
    vp_gank.apply {
      adapter = pagerAdapter
      offscreenPageLimit = fragmentList.size
    }
    tab_gank.apply {
      setupWithViewPager(vp_gank)
      setOnTabSelectedListener(object : OnTabSelectedListener {
        override fun onTabReselected(tab: XTabLayout.Tab?) {
        }

        override fun onTabUnselected(tab: XTabLayout.Tab?) {
        }

        override fun onTabSelected(tab: XTabLayout.Tab?) {
          vp_gank.currentItem = tab!!.position
        }
      })
    }
  }

  override fun onCreateOptionsMenu(menu: Menu,
                                   inflater: MenuInflater?) {
    inflater!!.inflate(R.menu.menu_main, menu)
    val searchItem = menu.findItem(R.id.menu_search)
    search_view.apply {
      setMenuItem(searchItem)
      setHint("输入搜索内容")
      setSuggestions(resources.getStringArray(R.array.query_suggestions))
      setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
          SearchActivity.newIntent(activity!!, query!!)
          closeSearch()
          return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
          return false
        }
      })
    }
    return super.onCreateOptionsMenu(menu, inflater)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    return super.onOptionsItemSelected(item)
  }
}








