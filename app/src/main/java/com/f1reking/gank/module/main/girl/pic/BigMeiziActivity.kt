/*
 *  Copyright (c) 2018 F1ReKing
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.f1reking.gank.module.main.girl.pic

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.crashlytics.android.answers.Answers
import com.crashlytics.android.answers.CustomEvent
import com.f1reking.gank.OOIS
import com.f1reking.gank.R
import com.f1reking.gank.R.string
import com.f1reking.gank.base.BaseActivity
import com.f1reking.gank.toast
import com.f1reking.gank.util.FileUtils
import com.f1reking.gank.util.GlideApp
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_big_meizi.vp_pic
import kotlinx.android.synthetic.main.toolbar.toolbar

/**
 * @author: F1ReKing
 * @date: 2018/1/9 21:04
 * @desc: 大妹子图片
 */
class BigMeiziActivity : BaseActivity() {

  private val PERMISSIONS = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,
      Manifest.permission.READ_EXTERNAL_STORAGE)

  companion object {
    const val EXTRA_PIC_LIST = "pic_list"
    const val EXTRA_ID = "id"
    const val EXTRA_POSITION = "currentPosition"
    const val CODE_PERMISSIONS = 101
  }

  private lateinit var ids: List<String>
  private lateinit var picList: List<String>
  private lateinit var mPagerAdapter: PicPagerAdapter
  private var currentPosition: Int = 0
  private var url: String = ""
  private var picWidth: Int = 0
  private var picHeight: Int = 0
  private var bitmap: Bitmap? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_big_meizi)
    initView()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      val window = window
      window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
      window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
      window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
      window.statusBarColor = Color.TRANSPARENT
    }
  }

  private fun initView() {
    setToolbarTitle("")
    ids = intent.getStringArrayListExtra(EXTRA_ID)
    picList = intent.getStringArrayListExtra(EXTRA_PIC_LIST)
    currentPosition = intent.getIntExtra(EXTRA_POSITION, 0)
    url = picList[currentPosition]
    getBitmap()
    mPagerAdapter = PicPagerAdapter(this, picList)
    vp_pic.apply {
      adapter = mPagerAdapter
      setCurrentItem(currentPosition, false)
      addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
        override fun onPageSelected(position: Int) {
          super.onPageSelected(position)
          currentPosition = position
          url = picList[currentPosition]
          getBitmap()
          toolbar.title = (currentPosition + 1).toString() + "/" + picList.size.toString()
        }
      })
    }

    toolbar.apply {
      alpha = 0.7f
      title = (currentPosition + 1).toString() + "/" + picList.size.toString()
    }
  }

  override fun onDestroy() {
    super.onDestroy()
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_meizi, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
    R.id.menu_save -> OOIS {
      saveImage()
    }
    R.id.menu_share -> OOIS {
      shareImage()
    }
    else -> super.onOptionsItemSelected(item)
  }

  private fun saveImage() {
    val permissions = RxPermissions(this)
    permissions.request(*PERMISSIONS)
        .subscribe { aBoolean ->
          if (aBoolean!!) {
            if (url.endsWith("gif")) {
              toast("动态图不支持保存")
            } else {
              FileUtils.saveImageToGallery(this, vp_pic, bitmap!!, ids[currentPosition])
              Answers.getInstance()
                  .logCustom(CustomEvent("save Meizi").putCustomAttribute("meizi", url))
            }
          } else {
            showPermissionDialog()
          }
        }
  }

  private fun getBitmap() {
    val simpleTarget = object : SimpleTarget<Bitmap>() {
      override fun onResourceReady(resource: Bitmap,
                                   transition: Transition<in Bitmap>?) {
        picWidth = resource.width
        picHeight = resource.height
        bitmap = resource
      }
    }
    GlideApp.with(BigMeiziActivity@ this)
        .asBitmap()
        .load(url)
        .thumbnail(0.1f)
        .into(simpleTarget)
  }

  private fun shareImage() {
    val permissions = RxPermissions(this)
    permissions.request(*PERMISSIONS)
        .subscribe { aBoolean ->
          if (aBoolean!!) {
            if (url.endsWith("gif")) {
              toast("动态图不支持分享")
            } else {
              FileUtils.shareImage(this, bitmap!!)
              Answers.getInstance()
                  .logCustom(CustomEvent("share Meizi").putCustomAttribute("meizi", url))
            }
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
    }
  }

  private fun showPermissionDialog() {
    Snackbar.make(vp_pic, getString(string.permission_help), Snackbar.LENGTH_LONG)
        .setActionTextColor(ContextCompat.getColor(this, R.color.white))
        .setAction(getString(string.snake_open)) {
          val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
          intent.data = Uri.parse("package:" + packageName)
          startActivityForResult(intent, CODE_PERMISSIONS)
        }
        .show()
  }
}

