package com.f1reking.gank.module.about

import android.os.Bundle
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
        setToolbarTitle(getString(R.string.about))
    }
}