package com.f1reking.gank.base

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.f1reking.gank.Constant
import com.f1reking.gank.net.ApiClient
import com.squareup.leakcanary.LeakCanary
import com.tencent.bugly.Bugly

/**
 * @author: huangyh
 * @date: 2018/1/5 13:56
 * @desc:
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        ApiClient.instance.init()
        Bugly.init(applicationContext, Constant.BUGLY_ID, true)
        LeakCanary.install(this)
    }

    override fun attachBaseContext(context: Context) {
        super.attachBaseContext(context)
        MultiDex.install(context)
    }
}