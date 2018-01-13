package com.f1reking.gank.module.main.meizi

import android.os.Bundle
import android.support.v4.view.ViewCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.f1reking.gank.R
import com.f1reking.gank.base.BaseActivity
import kotlinx.android.synthetic.main.activity_big_meizi.iv_pic
import kotlinx.android.synthetic.main.toolbar.toolbar

/**
 * @author: huangyh
 * @date: 2018/1/9 21:04
 * @desc:
 */
class BigMeiziActivity : BaseActivity() {

    companion object {
        val URL = "mImageUrl"

        val TRANSIT_PIC = "picture"
    }

    private lateinit var mImageUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_big_meizi)
        initView()
        ViewCompat.setTransitionName(iv_pic, TRANSIT_PIC)
    }

    private fun initView() {
        setToolbarTitle("")
        toolbar.run {
            toolbar.alpha = 0.7f
        }
        mImageUrl = intent.getStringExtra(URL)
        Glide.with(this).load(mImageUrl).transition(
            DrawableTransitionOptions.withCrossFade()).into(iv_pic)
    }
}

