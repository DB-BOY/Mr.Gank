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

package com.f1reking.gank.util

import java.text.ParseException
import java.text.SimpleDateFormat

/**
 * @author: F1ReKing
 * @date: 2018/1/12 14:16
 * @desc:
 */
class DateUtils {

    companion object {

        fun dateFormat(timestamp: String?): String {
            if (timestamp == null) {
                return "unknown"
            }
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS")
            val outputFormat = SimpleDateFormat("yyyy-MM-dd")

            try {
                val date = inputFormat.parse(timestamp)
                return outputFormat.format(date)
            } catch (e: ParseException) {
                return "unknown"
            }
        }
    }

}