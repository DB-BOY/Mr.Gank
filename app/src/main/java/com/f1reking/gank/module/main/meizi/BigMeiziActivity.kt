package com.f1reking.gank.module.main.meizi

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.f1reking.gank.R
import com.f1reking.gank.R.string
import com.f1reking.gank.base.BaseActivity
import com.f1reking.gank.util.FileUtils
import com.f1reking.gank.util.GlideApp
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_big_meizi.iv_pic
import kotlinx.android.synthetic.main.toolbar.toolbar

/**
 * @author: huangyh
 * @date: 2018/1/9 21:04
 * @desc: 大妹子图片
 */
class BigMeiziActivity : BaseActivity() {

    private val PERMISSIONS = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE)

    companion object {
        val EXTRA_URL = "mImageUrl"
        val TRANSIT_PIC = "picture"
        val EXTRA_TITLE = "title"
        val CODE_PERMISSIONS = 101
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
            alpha = 0.7f
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
                saveImage()
                return true
            }
            R.id.menu_share -> {
                FileUtils.shareImage(this, bitmap)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveImage() {
        val permissions = RxPermissions(this)
        permissions.request(*PERMISSIONS).subscribe { aBoolean ->
            if (aBoolean!!) {
                FileUtils.saveImageToGallery(this, iv_pic, bitmap, title)
            } else {
                showPermissionDialog()
            }
        }
    }

    override fun onActivityResult(requestCode: Int,
                                  resultCode: Int,
                                  data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CODE_PERMISSIONS) {
            saveImage()
        }
    }

    private fun showPermissionDialog() {
        Snackbar.make(iv_pic, getString(string.permission_help),
            Snackbar.LENGTH_LONG).setActionTextColor(
            ContextCompat.getColor(this, R.color.white)).setAction(getString(string.snake_open)) {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.data = Uri.parse("package:" + packageName)
            startActivityForResult(intent, CODE_PERMISSIONS)
        }.show()
    }
}

