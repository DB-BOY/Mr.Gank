package com.f1reking.gank.module.main.gank

import android.os.Bundle
import android.support.design.widget.TabLayout.OnTabSelectedListener
import android.support.design.widget.TabLayout.Tab
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager.OnPageChangeListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import kotlinx.android.synthetic.main.fragment_gank.view.tab_gank
import kotlinx.android.synthetic.main.fragment_gank.vp_gank

/**
 * @author: huangyh
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
        tab_gank.run {
            tab_gank.setupWithViewPager(vp_gank)
            tab_gank.addOnTabSelectedListener(object : OnTabSelectedListener {
                override fun onTabReselected(tab: Tab?) {
                }

                override fun onTabUnselected(tab: Tab?) {
                }

                override fun onTabSelected(tab: Tab?) {
                    vp_gank.currentItem = tab!!.position
                }
            })
        }
        vp_gank.run {
            vp_gank.adapter = pagerAdapter
            vp_gank.offscreenPageLimit = 7
            vp_gank.addOnPageChangeListener(object : OnPageChangeListener {
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
    }
}








