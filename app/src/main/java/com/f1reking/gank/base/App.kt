package com.f1reking.gank.base

import android.app.Application
import com.f1reking.gank.net.ApiClient

/**
 * @author: huangyh
 * @date: 2018/1/5 13:56
 * @desc:
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        ApiClient.instance.init()
    }
}