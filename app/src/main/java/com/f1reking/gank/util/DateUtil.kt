package com.f1reking.gank.util

import java.text.SimpleDateFormat
import java.util.Date

/**
 * @author: huangyh
 * @date: 2018/1/12 14:16
 * @desc:
 */
class DateUtil {

    companion object {

        fun toDate(date: Date): String {
            val dateFormat = SimpleDateFormat("yyyy/MM/dd H:m:s")
            return dateFormat.format(date)
        }
    }

}