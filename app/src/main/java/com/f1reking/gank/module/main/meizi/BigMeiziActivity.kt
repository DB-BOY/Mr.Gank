package com.f1reking.gank.module.main.meizi

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.f1reking.gank.R
import com.f1reking.gank.base.BaseActivity
import com.f1reking.gank.util.FileUtil
import com.f1reking.gank.util.GlideApp
import kotlinx.android.synthetic.main.activity_big_meizi.iv_pic
import kotlinx.android.synthetic.main.toolbar.toolbar

/**
 * @author: huangyh
 * @date: 2018/1/9 21:04
 * @desc: 大妹子图片
 */
class BigMeiziActivity : BaseActivity() {

    companion object {
        val EXTRA_URL = "mImageUrl"
        val TRANSIT_PIC = "picture"
        val EXTRA_TITLE = "title"
    }

    private lateinit var mImageUrl: String
    private var picWidth: Int = 0
    private var picHeight: Int = 0
    private lateinit var bitmap: Bitmap
    private lateinit var title: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_big_meizi)
        initView()
        ViewCompat.setTransitionName(iv_pic, TRANSIT_PIC)
    }

    private fun initView() {
        setToolbarTitle("")
        title = intent.getStringExtra(EXTRA_TITLE)
        toolbar.run {
            toolbar.alpha = 0.7f
        }
        iv_pic.run {
            mImageUrl = intent.getStringExtra(EXTRA_URL)
            val simpleTarget = object : SimpleTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap,
                                             transition: Transition<in Bitmap>?) {
                    picWidth = resource.width
                    picHeight = resource.height
                    bitmap = resource
                    iv_pic.setImageBitmap(resource)
                }
            }
            GlideApp.with(this).asBitmap().load(mImageUrl).into(simpleTarget)
            setOnClickListener { onBackPressed() }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_meizi, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save -> {
                FileUtil.saveImageToGallery(this, iv_pic, bitmap, title)
                return true
            }
            R.id.menu_share -> {

                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

