package com.f1reking.gank.module.main

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.NavigationView
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import com.f1reking.gank.R
import com.f1reking.gank.R.id
import com.f1reking.gank.R.string
import com.f1reking.gank.base.BaseActivity
import com.f1reking.gank.module.donate.DonateActivity
import com.f1reking.gank.module.main.gank.GankFragment
import com.f1reking.gank.module.main.meizi.MeiziFragment
import com.f1reking.gank.module.settings.SettingsActivity
import kotlinx.android.synthetic.main.activity_main.drawer_layout
import kotlinx.android.synthetic.main.activity_main.nav_view
import kotlinx.android.synthetic.main.app_bar_main.bottomNavigation
import kotlinx.android.synthetic.main.toolbar.toolbar

class MainActivity : BaseActivity() {

    private var mGankFrament: GankFragment? = null
    private var mHahaFragment: MeiziFragment? = null
    private val fragmentManager by lazy {
        supportFragmentManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        toolbar.run {
            toolbar.title = getString(R.string.app_name)
            setSupportActionBar(this)
        }
        drawer_layout.run {
            val toggle = ActionBarDrawerToggle(this@MainActivity, drawer_layout, toolbar,
                string.navigation_drawer_open, string.navigation_drawer_close)
            drawer_layout.addDrawerListener(toggle)
            toggle.syncState()
        }
        nav_view.run {
            nav_view.setNavigationItemSelectedListener(onDrawerNavigationItemSelectedListener)
        }
        bottomNavigation.run {
            setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
            selectedItemId = R.id.nav_new
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun setFragment(index: Int) {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        }
        fragmentManager.beginTransaction().apply {
            mGankFrament ?: let {
                GankFragment().let {
                    mGankFrament = it
                    add(R.id.ll_container, it)
                }
            }
            mHahaFragment ?: let {
                MeiziFragment().let {
                    mHahaFragment = it
                    add(R.id.ll_container, it)
                }
            }
            hideFragment(this)
            when (index) {
                R.id.nav_new -> {
                    mGankFrament?.let {
                        this.show(it)
                    }
                }
                R.id.nav_haha -> {
                    mHahaFragment?.let {
                        this.show(it)
                    }
                }
            }
        }.commit()
    }

    private fun hideFragment(transaction: FragmentTransaction) {
        mGankFrament?.let {
            transaction.hide(it)
        }
        mHahaFragment?.let {
            transaction.hide(it)
        }
    }

    private val onDrawerNavigationItemSelectedListener = NavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            id.nav_donate -> {
                intent = Intent(this@MainActivity, DonateActivity::class.java)
                startActivity(intent)
            }
            id.nav_settings -> {
                intent = Intent(this@MainActivity, SettingsActivity::class.java)
                startActivity(intent)
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        true
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        return@OnNavigationItemSelectedListener when (item.itemId) {
            R.id.nav_new -> {
                setFragment(item.itemId)
                true
            }
            R.id.nav_haha -> {
                setFragment(item.itemId)
                true
            }
            else -> {
                false
            }
        }
    }
}
