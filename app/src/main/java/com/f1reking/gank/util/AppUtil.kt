package com.f1reking.gank.util

import android.content.Context
import android.content.pm.PackageManager

/**
 * @author: huangyh
 * @date: 2018/1/18 11:36
 * @desc:
 */
class AppUtil {

    companion object {
        fun getVersionName(context: Context): String? = try {
            val packageManager = context.packageManager
            val packageInfo = packageManager.getPackageInfo(context.packageName, 0)
            packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            null
        }

        fun getAppName(context: Context): String? = try {
            val packageManager = context.packageManager
            val packageInfo = packageManager.getPackageInfo(context.packageName, 0)
            val labelRes = packageInfo.applicationInfo.labelRes
            context.resources.getString(labelRes)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            null
        }
    }
}