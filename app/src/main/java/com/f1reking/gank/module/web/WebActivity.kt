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
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.f1reking.gank.OOIS
import com.f1reking.gank.R
import com.f1reking.gank.db.Collection
import com.f1reking.gank.db.CollectionDaoOp
import com.f1reking.gank.entity.GankEntity
import com.f1reking.gank.entity.MessageEvent
import com.f1reking.gank.toast
import com.f1reking.gank.util.ShareUtils
import com.f1reking.library.statuslayout.StatusClickListener
import com.f1reking.library.statuslayout.StatusLayout
import kotlinx.android.synthetic.main.activity_web.sr_gank
import kotlinx.android.synthetic.main.activity_web.wv_gank
import kotlinx.android.synthetic.main.toolbar_custom.toolbar
import kotlinx.android.synthetic.main.toolbar_custom.toolbar_title
import me.imid.swipebacklayout.lib.SwipeBackLayout
import me.imid.swipebacklayout.lib.app.SwipeBackActivity
import org.greenrobot.eventbus.EventBus

/**
 * @author: F1ReKing
 * @date: 2018/1/8 21:37
 * @desc:
 */
class WebActivity : SwipeBackActivity() {

    companion object {
        const val EXTRA_GANK = "gank"

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
    private val mSwipeBackLayout: SwipeBackLayout by lazy {
        swipeBackLayout
    }

    private val mStatusLayout: StatusLayout by lazy {
        StatusLayout.Builder(sr_gank)
            .setErrorText("加载出错了\n请重试")
            .setStatusClickListener(object : StatusClickListener {
                override fun onEmptyClick(view: View) {
                }

                override fun onErrorClick(view: View) {
                    mStatusLayout.showContentLayout()
                    wv_gank.reload()
                }
            })
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        initView()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (wv_gank != null) {
            val parent = wv_gank.parent as ViewParent
            (parent as ViewGroup).removeView(wv_gank)
            wv_gank.stopLoading()
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            wv_gank.settings.javaScriptEnabled = false
            wv_gank.clearHistory()
            wv_gank.clearView()
            wv_gank.removeAllViews()

            try {
                wv_gank.destroy()
            } catch (ex: Throwable) {
                ex.printStackTrace()
            }
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
        sr_gank.apply {
            isRefreshing = true
            setColorSchemeResources(R.color.colorPrimary)
            setOnRefreshListener {
                wv_gank.reload()
            }
        }
        wv_gank.apply {
            loadUrl(webUrl)
            initWebSettings()
            initWebChromeClient()
            initWebViewClient()
        }
        mSwipeBackLayout.apply {
            this.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT)
        }
    }

    private fun setToolbarTitle(title: String) {
        toolbar.title = title
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    @SuppressLint("SetJavaScriptEnabled") private fun initWebSettings() {
        val webSettings: WebSettings = wv_gank.settings
        webSettings.apply {
            javaScriptEnabled = true
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            }
        }
    }

    private fun initWebChromeClient() {
        wv_gank.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView,
                                           newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                sr_gank.isRefreshing = newProgress != 100
            }

            override fun onReceivedTitle(view: WebView?,
                                         title: String) {
                super.onReceivedTitle(view, title)
                toolbar_title.text = title
                val pnotfound = "404"
                if (title.contains(pnotfound)) {
                    mStatusLayout.showErrorLayout()
                }
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

            override fun onReceivedError(view: WebView?,
                                         request: WebResourceRequest?,
                                         error: WebResourceError?) {
                super.onReceivedError(view, request, error)
                mStatusLayout.showErrorLayout()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_web, menu)
        if (CollectionDaoOp.getInstance().queryById(this, id!!)!!.isNotEmpty()) {
            menu.findItem(R.id.menu_collection)
                .setIcon(R.drawable.ic_menu_star)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.menu_browser    -> OOIS {
            Intent().apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse(webUrl)
                startActivity(this)
            }
        }
        R.id.menu_share      -> OOIS {
            ShareUtils.shareText(this,
                getString(R.string.share_article_url, getString(R.string.app_name),
                    toolbar_title.text, webUrl), getString(R.string.share_title))
        }
        R.id.menu_copy       -> OOIS {
            val cm = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newRawUri("Mr.gank", Uri.parse(wv_gank.url))
            cm.primaryClip = clipData
            toast(getString(R.string.share_copy))
        }
        R.id.menu_collection -> OOIS {
            if (CollectionDaoOp.getInstance().queryById(this, id!!)!!.isNotEmpty()) {
                CollectionDaoOp.getInstance().deleteById(this, id!!)
                item.icon = ContextCompat.getDrawable(this, R.drawable.ic_munu_star_block)
                toast(getString(R.string.fav_cancel))
            } else {
                val collection = Collection()
                collection.id = id
                collection.desc = gankEntity.desc
                collection.publishedAt = gankEntity.publishedAt
                collection.type = gankEntity.type
                collection.url = gankEntity.url
                collection.who = gankEntity.who
                CollectionDaoOp.getInstance()
                    .insertData(this, collection)
                item.icon = ContextCompat.getDrawable(this, R.drawable.ic_menu_star)
                toast(getString(R.string.fav_submit))
            }
            EventBus.getDefault()
                .post(MessageEvent("refresh"))
        }
        else                 -> super.onOptionsItemSelected(item)
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
