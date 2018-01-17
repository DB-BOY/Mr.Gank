package com.f1reking.gank.module.about

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.f1reking.gank.R
import com.f1reking.gank.base.BaseActivity

/**
 * @author: huangyh
 * @date: 2018/1/5 13:43
 * @desc:
 */
class AboutActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        initView()
    }

    private fun initView() {
        setToolbarTitle("")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_about, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_share -> {

                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}