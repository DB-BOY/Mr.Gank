package com.f1reking.gank.module.main

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.FragmentTransaction
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import com.f1reking.gank.R
import com.f1reking.gank.base.BaseActivity
import com.f1reking.gank.module.about.AboutActivity
import com.f1reking.gank.module.main.gank.GankFragment
import com.f1reking.gank.module.main.meizi.MeiziFragment
import com.f1reking.gank.toast
import com.f1reking.gank.util.AppUtil
import kotlinx.android.synthetic.main.activity_main.bottomNavigation
import kotlinx.android.synthetic.main.toolbar.toolbar

class MainActivity : BaseActivity() {

    private var mGankFrament: GankFragment? = null
    private var mMeiziFragment: MeiziFragment? = null
    private val fragmentManager by lazy {
        supportFragmentManager
    }
    private var exitTime: Long = 0

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
            mMeiziFragment ?: let {
                MeiziFragment().let {
                    mMeiziFragment = it
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
                    mMeiziFragment?.let {
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
        mMeiziFragment?.let {
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
            R.id.menu_about -> {
                startActivity(Intent(this@MainActivity, AboutActivity::class.java))
                return true
            }
            R.id.menu_search -> {

                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onKeyDown(keyCode: Int,
                           event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                toast("再次点击退出 " + AppUtil.getAppName(this))
                exitTime = System.currentTimeMillis()
            } else {
                finish()
                System.exit(0)
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}
