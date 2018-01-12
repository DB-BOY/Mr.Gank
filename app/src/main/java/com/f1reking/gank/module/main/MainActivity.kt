package com.f1reking.gank.module.main

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.FragmentTransaction
import android.view.Menu
import android.view.MenuItem
import com.f1reking.gank.R
import com.f1reking.gank.base.BaseActivity
import com.f1reking.gank.module.about.AboutActivity
import com.f1reking.gank.module.main.gank.GankFragment
import com.f1reking.gank.module.main.meizi.MeiziFragment
import com.f1reking.gank.module.settings.SettingsActivity
import kotlinx.android.synthetic.main.activity_main.bottomNavigation
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
        bottomNavigation.run {
            setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
            selectedItemId = R.id.nav_new
        }
    }

    private fun setFragment(index: Int) {
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_setting -> {
                startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
                return true
            }
            R.id.menu_about -> {
                startActivity(Intent(this@MainActivity, AboutActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
