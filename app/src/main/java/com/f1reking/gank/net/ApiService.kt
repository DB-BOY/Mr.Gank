package com.f1reking.gank.net

import com.f1reking.gank.entity.HttpEntity
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author: huangyh
 * @date: 2018/1/7 15:24
 * @desc:
 */
interface ApiService {

    @GET("data/{type}/{num}/{page}")
    fun getGankList(@Path("type") type: String, @Path("num") num: Int, @Path("page")
    page: Int): Observable<HttpEntity>

    @GET("search/query/{query}/category/all/count/{num}/page/{page}")
    fun queryGankList(@Path("query") query: String, @Path("num") num: Int, @Path("page")
    page: Int): Observable<HttpEntity>
}