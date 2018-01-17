package com.f1reking.gank.module.main.gank

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.ViewGroup

/**
 * @author: huangyh
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