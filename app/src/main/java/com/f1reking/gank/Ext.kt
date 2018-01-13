package com.f1reking.gank

import android.content.Context
import android.support.annotation.StringRes
import android.widget.Toast

/**
 * @author: huangyh
 * @date: 2018/1/13 08:47
 * @desc:
 */
fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.toast(@StringRes id: Int) {
    toast(getString(id))
}

