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

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.ViewGroup

/**
 * @author: F1ReKing
 * @date: 2018/1/15 22:12
 * @desc:
 */
class TabPagerAdapter(fm: FragmentManager,
                      private val fragmentList: ArrayList<Fragment>?,
                      private val titles: Array<String>) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        return if (fragmentList == null || fragmentList.isEmpty()) null
        else fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList?.size ?: 0
    }

    /**
     * 此方法用来显示tab上的名字
     */
    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }

    /**
     * 重载该方法，防止其它视图被销毁，防止加载视图卡顿
     */
    override fun destroyItem(container: ViewGroup,
                             position: Int,
                             `object`: Any) {
        super.destroyItem(container, position, `object`)
    }
}