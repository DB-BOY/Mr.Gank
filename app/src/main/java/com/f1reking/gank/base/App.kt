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

package com.f1reking.gank.base

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.f1reking.gank.net.ApiClient
import com.squareup.leakcanary.LeakCanary

/**
 * @author: F1ReKing
 * @date: 2018/1/5 13:56
 * @desc:
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        ApiClient.instance.init()
        LeakCanary.install(this)
    }

    override fun attachBaseContext(context: Context) {
        super.attachBaseContext(context)
        MultiDex.install(context)
    }
}