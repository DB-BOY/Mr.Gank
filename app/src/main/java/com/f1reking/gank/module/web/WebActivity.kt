package com.f1reking.gank.module.web

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.f1reking.gank.Constant
import com.f1reking.gank.R
import com.f1reking.gank.base.BaseActivity
import com.tencent.bugly.crashreport.CrashReport
import kotlinx.android.synthetic.main.activity_web.sr_gank
import kotlinx.android.synthetic.main.activity_web.wv_gank
import kotlinx.android.synthetic.main.toolbar_custom.toolbar_title

/**
 * @author: huangyh
 * @date: 2018/1/8 21:37
 * @desc:
 */
 class WebActivity : BaseActivity() {

    companion object {
        val EXTRA_URL = "webUrl"
        val EXTRA_TITLE = "title"

        fun newIntent(context: Context,
                      url: String,
                      title: String) {
            val intent = Intent(context, WebActivity::class.java)
            intent.putExtra(WebActivity.EXTRA_URL, url)
            intent.putExtra(WebActivity.EXTRA_TITLE, title)
            context.startActivity(intent)
        }
    }

    private var webUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        initView()
    }

    private fun initView() {
        setToolbarTitle("")
        webUrl = intent.getStringExtra(EXTRA_URL)
        toolbar_title.text = intent.getStringExtra(EXTRA_TITLE)
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

    @SuppressLint("SetJavaScriptEnabled") private fun initWebSettings() {
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
        menuInflater.inflate(R.menu.menu_main, menu)
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
                Intent().run {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT,
                        getString(R.string.share_article_url, getString(R.string.app_name),
                            toolbar_title.text, webUrl))
                    type = Constant.CONTENT_SHARE_TYPE
                    startActivity(Intent.createChooser(this, getString(R.string.share_title)))
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
