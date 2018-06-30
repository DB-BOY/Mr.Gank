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
package com.f1reking.gank.module.main.girl

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.androidkun.xtablayout.XTabLayout
import com.f1reking.gank.R
import com.f1reking.gank.base.BaseFragment
import com.f1reking.gank.inflate
import com.f1reking.gank.module.main.gank.TabPagerAdapter
import com.f1reking.gank.module.main.girl.gank.GankGirlFragment
import com.f1reking.gank.module.main.girl.jd.JDGirlFragment
import kotlinx.android.synthetic.main.fragment_girl.tab_girl
import kotlinx.android.synthetic.main.fragment_girl.vp_girl

/**
 * @author: F1ReKing
 * @date: 2018/6/4
 * @desc:
 */
class GirlFragment :BaseFragment(){

    private var layout: View? = null
    private var fragmentList = ArrayList<Fragment>()


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        layout = container!!.inflate(R.layout.fragment_girl)
        return layout
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val mGankGirlFragment = GankGirlFragment()
        val mJDGrilFragment = JDGirlFragment()
        fragmentList.add(mGankGirlFragment)
        fragmentList.add(mJDGrilFragment)
        val pagerAdapter = TabPagerAdapter(childFragmentManager, fragmentList,
                resources.getStringArray(R.array.tab_girl))
        vp_girl.apply {
            adapter = pagerAdapter
            offscreenPageLimit = fragmentList.size
        }
        tab_girl.apply {
            setupWithViewPager(vp_girl)
            setOnTabSelectedListener(object : XTabLayout.OnTabSelectedListener {
                override fun onTabReselected(tab: XTabLayout.Tab?) {
                }

                override fun onTabUnselected(tab: XTabLayout.Tab?) {
                }

                override fun onTabSelected(tab: XTabLayout.Tab?) {
                    vp_girl.currentItem = tab!!.position
                }
            })
        }
    }
}