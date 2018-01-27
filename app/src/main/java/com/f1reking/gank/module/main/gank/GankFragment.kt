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
import android.support.v4.view.ViewPager.OnPageChangeListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.androidkun.xtablayout.XTabLayout
import com.androidkun.xtablayout.XTabLayout.OnTabSelectedListener
import com.f1reking.gank.R
import com.f1reking.gank.base.BaseFragment
import com.f1reking.gank.module.main.gank.fragment.GankAndroidFragment
import com.f1reking.gank.module.main.gank.fragment.GankAppFragment
import com.f1reking.gank.module.main.gank.fragment.GankRecommendFragment
import com.f1reking.gank.module.main.gank.fragment.GankResourcesFragment
import com.f1reking.gank.module.main.gank.fragment.GankVideoFragment
import com.f1reking.gank.module.main.gank.fragment.GankWebFragment
import com.f1reking.gank.module.main.gank.fragment.GankiOSFragment
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

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        layout = inflater.inflate(R.layout.fragment_gank, container, false)
        return layout
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val mGankVideoFragment = GankVideoFragment()
        val mGankAndroidFragment = GankAndroidFragment()
        val mGankiOSFragment = GankiOSFragment()
        val mGankWebFragment = GankWebFragment()
        val mGankResourcesFragment = GankResourcesFragment()
        val mGankRecommendFragment = GankRecommendFragment()
        val mGankAppFragment = GankAppFragment()
        fragmentList.add(mGankVideoFragment)
        fragmentList.add(mGankAndroidFragment)
        fragmentList.add(mGankiOSFragment)
        fragmentList.add(mGankWebFragment)
        fragmentList.add(mGankResourcesFragment)
        fragmentList.add(mGankRecommendFragment)
        fragmentList.add(mGankAppFragment)
        val pagerAdapter = TabPagerAdapter(childFragmentManager, fragmentList,
            resources.getStringArray(R.array.tab_gank))
        vp_gank.run {
            adapter = pagerAdapter
            offscreenPageLimit = 7
            addOnPageChangeListener(object : OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {
                }

                override fun onPageScrolled(position: Int,
                                            positionOffset: Float,
                                            positionOffsetPixels: Int) {
                }

                override fun onPageSelected(position: Int) {
                }
            })
        }
        tab_gank.run {
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
}








