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

package com.f1reking.gank.net

import com.f1reking.gank.BuildConfig
import com.f1reking.gank.Constant
import com.safframework.log.L
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author: F1ReKing
 * @date: 2018/1/8 13:51
 * @desc:
 */
class ApiClient {

    lateinit var mService: ApiService

    private object Holder {
        val INSTANCE = ApiClient()
    }

    companion object {
        val instance by lazy { Holder.INSTANCE }
    }

    fun init() {
        val retrofit = Retrofit.Builder().baseUrl(Constant.BASE_URL).addConverterFactory(
            GsonConverterFactory.create()).addCallAdapterFactory(
            RxJava2CallAdapterFactory.create()).client(getOkHttpClient()).build()
        mService = retrofit.create(ApiService::class.java)
        L.init(this.javaClass)
    }

    private fun getOkHttpClient(): OkHttpClient {
        val httpClientBuilder = OkHttpClient.Builder() //设置日志
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor {
                L.json(it)
            }
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClientBuilder.addInterceptor(logging)
        }

        return httpClientBuilder.build()
    }
}