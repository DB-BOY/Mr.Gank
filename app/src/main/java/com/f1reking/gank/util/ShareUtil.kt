package com.f1reking.gank.util

import android.content.Context
import android.content.Intent
import com.f1reking.gank.Constant

/**
 * @author: huangyh
 * @date: 2018/1/18 16:05
 * @desc:
 */
class ShareUtil {

    companion object {

        fun shareText(context: Context,
                      text: CharSequence,
                      title: String) {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.putExtra(Intent.EXTRA_TEXT, text)
            shareIntent.type = Constant.CONTENT_SHARE_TYPE
            context.startActivity(Intent.createChooser(shareIntent, title))
        }
    }
}

