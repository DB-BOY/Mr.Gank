package com.f1reking.gank.util

import android.content.Context
import android.content.Intent
import android.net.Uri

/**
 * @author: huangyh
 * @date: 2018/1/9 23:06
 * @desc:
 */
class ShareUtil {

    public fun shareImage(context: Context,
                          uri: Uri,
                          title: String) {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
        shareIntent.type = "image/*"
        context.startActivity(Intent.createChooser(shareIntent, title))
    }
}