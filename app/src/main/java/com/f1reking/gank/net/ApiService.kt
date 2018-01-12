package com.f1reking.gank.net

import com.f1reking.gank.entity.HttpEntity
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

/**
 * @author: huangyh
 * @date: 2018/1/7 15:24
 * @desc:
 */
interface ApiService {

    @GET("{type}/{num}/{page}")
    fun getGankList(@Path("type") type: String, @Path("num") num: Int, @Path("page")
    page: Int): Observable<HttpEntity>

    @GET
    fun downloadPic(@Url fileUrl: String): Observable<ResponseBody>
}