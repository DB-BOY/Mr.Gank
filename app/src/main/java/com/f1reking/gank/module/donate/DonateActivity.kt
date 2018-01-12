package com.f1reking.gank.module.donate

import android.os.Bundle
import com.f1reking.gank.R
import com.f1reking.gank.base.BaseActivity

/**
 * @author: huangyh
 * @date: 2018/1/5 13:43
 * @desc:
 */
class DonateActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donate)
        initView()
    }

    private fun initView() {
        setToolbarTitle("捐赠")
    }
}