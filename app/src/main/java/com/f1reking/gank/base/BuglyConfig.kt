package com.f1reking.gank.base

import android.content.Context
import com.f1reking.gank.Constant
import com.f1reking.gank.module.main.MainActivity
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta

/**
 * @author: huangyh
 * @date: 2018/1/19 15:43
 * @desc:
 */
class BuglyConfig {

    companion object {

        fun init(context: Context) {
            Beta.canShowUpgradeActs.add(MainActivity::class.java)
            Bugly.init(context, Constant.BUGLY_ID, true)
        }
    }
}