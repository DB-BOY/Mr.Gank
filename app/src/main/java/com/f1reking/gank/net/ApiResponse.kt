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

import android.content.Context
import com.f1reking.gank.entity.ApiErrorModel
import com.google.gson.Gson
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * @author: F1ReKing
 * @date: 2018/1/8 14:21
 * @desc:
 */
abstract class ApiResponse<T>(private val context: Context) : Observer<T> {

    override fun onNext(t: T) {
        success(t)
    }

    override fun onComplete() {
    }

    override fun onSubscribe(d: Disposable) {
    }

    override fun onError(e: Throwable) {
        if (e is HttpException) {
            val apiErrorModel: ApiErrorModel = when (e.code()) {
                ApiErrorType.INTERNAL_SERVER_ERROR.code -> ApiErrorType.INTERNAL_SERVER_ERROR.getApiErrorModel(
                    context)
                ApiErrorType.BAD_GATEWAY.code -> ApiErrorType.BAD_GATEWAY.getApiErrorModel(context)
                ApiErrorType.NOT_FOUND.code -> ApiErrorType.NOT_FOUND.getApiErrorModel(context)
                else -> otherError(e)
            }
            failure(e.code(), apiErrorModel)
            return
        }

        val apiErrorType: ApiErrorType = when (e) {
            is UnknownHostException -> ApiErrorType.NETWORK_NOT_CONNECT
            is ConnectException -> ApiErrorType.NETWORK_NOT_CONNECT
            is SocketTimeoutException -> ApiErrorType.CONNECTION_TIMEOUT
            else -> ApiErrorType.UNEXPECTED_ERROR
        }
        failure(apiErrorType.code, apiErrorType.getApiErrorModel(context))
    }

    private fun otherError(e: HttpException) = Gson().fromJson(
        e.response().errorBody()?.charStream(), ApiErrorModel::class.java)

    abstract fun success(data: T)
    abstract fun failure(statusCode: Int,
                         apiErrorModel: ApiErrorModel)
}