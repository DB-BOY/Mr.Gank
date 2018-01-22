package com.f1reking.gank.util

import java.text.ParseException
import java.text.SimpleDateFormat

/**
 * @author: huangyh
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