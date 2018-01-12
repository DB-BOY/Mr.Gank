package com.f1reking.gank.base

import android.support.v4.app.Fragment
import android.widget.Toast

/**
 * @author: huangyh
 * @date: 2018/1/4 15:58
 * @desc:
 */
open class BaseFragment: Fragment() {

    fun Fragment.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(getActivity(), message, duration).show()
    }

}