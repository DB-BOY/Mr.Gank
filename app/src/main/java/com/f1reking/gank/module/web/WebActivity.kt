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

package com.f1reking.gank.module.web

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.f1reking.gank.R
import com.f1reking.gank.base.BaseActivity
import com.f1reking.gank.entity.CollectionEntity
import com.f1reking.gank.entity.GankEntity
import com.f1reking.gank.room.AppDatabaseHelper
import com.f1reking.gank.toast
import com.f1reking.gank.util.ShareUtils
import com.tencent.bugly.crashreport.CrashReport
import kotlinx.android.synthetic.main.activity_web.sr_gank
import kotlinx.android.synthetic.main.activity_web.wv_gank
import kotlinx.android.synthetic.main.toolbar_custom.toolbar_title

/**
 * @author: F1ReKing
 * @date: 2018/1/8 21:37
 * @desc:
 */
class WebActivity : BaseActivity() {

    companion object {
        val EXTRA_GANK = "gank"

        fun newIntent(context: Context,
                      gankEntity: GankEntity) {
            val intent = Intent(context, WebActivity::class.java)
            intent.putExtra(WebActivity.EXTRA_GANK, gankEntity)
            context.startActivity(intent)
        }
    }

    private var webUrl: String? = null
    private lateinit var gankEntity: GankEntity
    private var id: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        initView()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (wv_gank != null) {
            wv_gank.removeAllViews()
            wv_gank.destroy()
            null === wv_gank
        }
    }

    private fun initView() {
        setToolbarTitle("")
        gankEntity = intent.getParcelableExtra(EXTRA_GANK)
        id = if (!TextUtils.isEmpty(gankEntity._id)) {
            gankEntity._id
        } else {
            gankEntity.ganhuo_id
        }
        webUrl = gankEntity.url
        toolbar_title.text = "加载中..."
        sr_gank.run {
            sr_gank.isRefreshing = true
            sr_gank.setColorSchemeResources(R.color.colorPrimary)
            sr_gank.setOnRefreshListener {
                wv_gank.loadUrl(webUrl)
            }
        }
        wv_gank.run {
            wv_gank.loadUrl(webUrl)
            initWebSettings()
            initWebChromeClient()
            initWebViewClient()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebSettings() {
        val webSettings: WebSettings = wv_gank.settings
        webSettings.javaScriptEnabled = true
    }

    private fun initWebChromeClient() {
        wv_gank.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView,
                                           newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                CrashReport.setJavascriptMonitor(view, true)
                sr_gank.isRefreshing = newProgress != 100
            }

            override fun onReceivedTitle(view: WebView?,
                                         title: String?) {
                super.onReceivedTitle(view, title)
                toolbar_title.text = title
            }
        }
    }

    private fun initWebViewClient() {
        wv_gank.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView?,
                                                  request: WebResourceRequest?): Boolean {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view?.loadUrl(request?.url.toString())
                } else {
                    view?.loadUrl(request.toString())
                }
                return true
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_web, menu)
        if (AppDatabaseHelper.getInstance(this).queryCollectionById(id!!).isNotEmpty()) {
            menu.findItem(R.id.menu_collection).setIcon(R.drawable.ic_menu_star)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_browser -> {
                Intent().run {
                    action = "android.intent.action.VIEW"
                    data = Uri.parse(webUrl)
                    startActivity(this)
                }
                return true
            }
            R.id.menu_share -> {
                ShareUtils.shareText(this,
                    getString(R.string.share_article_url, getString(R.string.app_name),
                        toolbar_title.text, webUrl), getString(R.string.share_title))
                return true
            }
            R.id.menu_copy -> {
                val cm = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clipData = ClipData.newRawUri("Mr.gank", Uri.parse(wv_gank.url))
                cm.primaryClip = clipData
                toast("复制成功，可以发给好友")
                return true
            }
            R.id.menu_collection -> {
                if (AppDatabaseHelper.getInstance(this).queryCollectionById(id!!).isNotEmpty()) {
                    AppDatabaseHelper.getInstance(this).delectCollection(
                        AppDatabaseHelper.getInstance(this).queryCollectionById(id!!)[0])
                    item.icon = ContextCompat.getDrawable(this, R.drawable.ic_munu_star_block)
                    toast("取消收藏")
                } else {
                    val collectionEntity = CollectionEntity()
                    collectionEntity._id = id
                    collectionEntity.desc = gankEntity.desc!!
                    collectionEntity.publishedAt = gankEntity.publishedAt!!
                    collectionEntity.type = gankEntity.type!!
                    collectionEntity.url = gankEntity.url!!
                    collectionEntity.who = gankEntity.who!!
                    AppDatabaseHelper.getInstance(this).insertColletion(collectionEntity)
                    item.icon = ContextCompat.getDrawable(this, R.drawable.ic_menu_star)
                    toast("已收藏")
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onKeyDown(keyCode: Int,
                           event: KeyEvent?): Boolean {
        return if ((keyCode == KeyEvent.KEYCODE_BACK) && wv_gank.canGoBack()) {
            wv_gank.goBack()
            true
        } else {
            finish()
            false
        }
    }
}
