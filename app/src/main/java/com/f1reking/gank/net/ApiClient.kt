package com.f1reking.gank.net

import com.f1reking.gank.BuildConfig
import com.f1reking.gank.Constant
import com.f1reking.gank.util.FLog
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author: huangyh
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
    }

    private fun getOkHttpClient(): OkHttpClient {
        val httpClientBuilder = OkHttpClient.Builder() //设置日志
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor { FLog.json(it) }
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClientBuilder.addInterceptor(logging)
        }

        return httpClientBuilder.build()
    }
}