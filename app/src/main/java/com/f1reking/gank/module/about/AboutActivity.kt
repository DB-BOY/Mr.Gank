package com.f1reking.gank.module.about

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.f1reking.gank.R
import com.f1reking.gank.base.BaseActivity
import com.f1reking.gank.util.AppUtils
import com.f1reking.gank.util.ShareUtils
import kotlinx.android.synthetic.main.activity_about.tv_version

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
        tv_version.run {
            tv_version.text = String.format(getString(R.string.app_version),
                AppUtils.getVersionName(this@AboutActivity))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_about, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_share -> {
                ShareUtils.shareText(this, getString(R.string.share_app), "推荐给好友")
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}