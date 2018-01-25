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

package com.f1reking.gank.room.converter

import android.arch.persistence.room.TypeConverter
import android.text.TextUtils

/**
 * @author: F1ReKing
 * @date: 2018/1/23 11:03
 * @desc:
 */
class Converters {

    @TypeConverter
    fun arrayToString(array: Array<String>): String {
        if (0 === array.size) {
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