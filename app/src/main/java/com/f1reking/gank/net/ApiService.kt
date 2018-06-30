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

import com.f1reking.gank.entity.HttpEntity
import com.f1reking.gank.entity.JDHttpEntity
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

/**
 * @author: F1ReKing
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

    @GET
    fun getJDGirlList(@Url url: String): Observable<JDHttpEntity>
}