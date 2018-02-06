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

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.service.quicksettings.TileService
import java.net.URISyntaxException

/**
 * @author: F1ReKing
 * @date: 2018/2/5 17:06
 * @desc:
 */
class AlipayDonateUtils {

  // 支付宝包名
  private val ALIPAY_PACKAGE_NAME = "com.eg.android.AlipayGphone"

  // 旧版支付宝二维码通用 Intent Scheme Url 格式
  private val INTENT_URL_FORMAT = "intent://platformapi/startapp?saId=10000007&" + "clientVersion=3.7.0.0718&qrcode=https%3A%2F%2Fqr.alipay.com%2F{payCode}%3F_s" + "%3Dweb-other&_t=1472443966571#Intent;" + "scheme=alipayqr;package=com.eg.android.AlipayGphone;end"

  private object mHolder {
    val instance = AlipayDonateUtils()
  }

  companion object {
    fun getInstance(): AlipayDonateUtils {
      return mHolder.instance
    }
  }

  /**
   * 打开转账窗口
   * 旧版支付宝二维码方法，需要使用 https://fama.alipay.com/qrcode/index.htm 网站生成的二维码
   * 这个方法最好，但在 2016 年 8 月发现新用户可能无法使用
   *
   * @param activity Parent Activity
   * @param urlCode 手动解析二维码获得地址中的参数，例如 https://qr.alipay.com/aehvyvf4taua18zo6e 最后那段
   * @return 是否成功调用
   */
  fun startAlipayClient(activity: Activity,
                        payCode: String): Boolean {
    return startIntentUrl(activity, INTENT_URL_FORMAT.replace("{payCode}", payCode))
  }

  /**
   * 打开 Intent Scheme Url
   *
   * @param activity Parent Activity
   * @param intentFullUrl Intent 跳转地址
   * @return 是否成功调用
   */
  fun startIntentUrl(activity: Activity,
                     intentFullUrl: String): Boolean {
    try {
      val intent = Intent.parseUri(intentFullUrl, Intent.URI_INTENT_SCHEME)
      activity.startActivity(intent)
      return true
    } catch (e: URISyntaxException) {
      e.printStackTrace()
      return false
    } catch (e: ActivityNotFoundException) {
      e.printStackTrace()
      return false
    }
  }

  /**
   * 判断支付宝客户端是否已安装，建议调用转账前检查
   * @param context Context
   * @return 支付宝客户端是否已安装
   */
  fun hasInstalledAlipayClient(context: Context): Boolean {
    val pm = context.getPackageManager()
    try {
      val info = pm.getPackageInfo(ALIPAY_PACKAGE_NAME, 0)
      return info != null
    } catch (e: PackageManager.NameNotFoundException) {
      e.printStackTrace()
      return false
    }
  }

  /**
   * 获取支付宝客户端版本名称，作用不大
   * @param context Context
   * @return 版本名称
   */
  fun getAlipayClientVersion(context: Context): String? {
    val pm = context.getPackageManager()
    try {
      val info = pm.getPackageInfo(ALIPAY_PACKAGE_NAME, 0)
      return info.versionName
    } catch (e: PackageManager.NameNotFoundException) {
      e.printStackTrace()
      return null
    }
  }

  /**
   * 打开支付宝扫一扫界面
   * @param context Context
   * @return 是否成功打开 Activity
   */
  fun openAlipayScan(context: Context): Boolean {
    try {
      val uri = Uri.parse("alipayqr://platformapi/startapp?saId=10000007")
      val intent = Intent(Intent.ACTION_VIEW, uri)
      if (context is TileService) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
          (context as TileService).startActivityAndCollapse(intent)
        }
      } else {
        context.startActivity(intent)
      }
      return true
    } catch (e: Exception) {
      return false
    }
  }

  /**
   * 打开支付宝付款码
   * @param context Context
   * @return 是否成功打开 Activity
   */
  fun openAlipayBarcode(context: Context): Boolean {
    try {
      val uri = Uri.parse("alipayqr://platformapi/startapp?saId=20000056")
      val intent = Intent(Intent.ACTION_VIEW, uri)
      if (context is TileService) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
          (context as TileService).startActivityAndCollapse(intent)
        }
      } else {
        context.startActivity(intent)
      }
      return true
    } catch (e: Exception) {
      return false
    }
  }
}