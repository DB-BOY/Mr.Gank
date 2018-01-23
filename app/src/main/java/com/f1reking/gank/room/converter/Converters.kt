package com.f1reking.gank.room.converter

import android.arch.persistence.room.TypeConverter
import android.text.TextUtils

/**
 * @author: huangyh
 * @date: 2018/1/23 11:03
 * @desc:
 */
class Converters {

    @TypeConverter
    fun arrayToString(array: Array<String>): String {
        if (array == null || array.size === 0) {
            return ""
        }

        val builder = StringBuilder(array[0])
        for (i in 1..array.size - 1) {
            builder.append(",").append(array[i])
        }
        return builder.toString()
    }

    @TypeConverter
    fun StringToArray(value: String): Array<String>? {
        return if (TextUtils.isEmpty(value)) null else value.split(",").toTypedArray()
    }
}