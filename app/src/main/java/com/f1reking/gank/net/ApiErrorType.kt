package com.f1reking.gank.net

import android.content.Context
import android.support.annotation.StringRes
import com.f1reking.gank.R
import com.f1reking.gank.entity.ApiErrorModel

/**
 * @author: huangyh
 * @date: 2018/1/8 14:27
 * @desc:
 */
enum class ApiErrorType(val code:Int,@param:StringRes private val messageInt: Int)  {

    INTERNAL_SERVER_ERROR(500, R.string.service_error),
    BAD_GATEWAY(502, R.string.service_error),
    NOT_FOUND(404, R.string.not_found),
    CONNECTION_TIMEOUT(408, R.string.timeout),
    NETWORK_NOT_CONNECT(499, R.string.network_wrong),
    UNEXPECTED_ERROR(700, R.string.unexpected_error);

    private val DEFAULT_CODE = 1

    fun getApiErrorModel(context:Context):ApiErrorModel{
        return ApiErrorModel(DEFAULT_CODE,context.getString(messageInt))
    }

}
