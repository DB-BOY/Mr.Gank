package com.f1reking.gank.module.settings

import android.os.Bundle
import com.f1reking.gank.R
import com.f1reking.gank.base.BaseActivity

/**
 * @author: huangyh
 * @date: 2018/1/4 16:03
 * @desc:
 */
class SettingsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setttings)
        initView()
    }

    private fun initView() {
        setToolbarTitle("设置")
    }

}