package com.f1reking.gank.base

import android.app.Application
import android.content.Context
import android.os.StrictMode
import android.support.multidex.MultiDex
import com.f1reking.gank.net.ApiClient
import com.squareup.leakcanary.LeakCanary



/**
 * @author: huangyh
 * @date: 2018/1/5 13:56
 * @desc:
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        ApiClient.instance.init()
        setupLeakCanary()
    }

    override fun attachBaseContext(context: Context) {
        super.attachBaseContext(context)
        MultiDex.install(context)
    }

    protected fun setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(
                this)) { // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        enabledStrictMode()
        LeakCanary.install(this)
    }

    private fun enabledStrictMode() {
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder() //
            .detectAll() //
            .penaltyLog() //
            .penaltyDeath() //
            .build())
    }
}