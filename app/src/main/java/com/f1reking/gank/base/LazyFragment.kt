package com.f1reking.gank.base

import android.os.Bundle

/**
 * @author: huangyh
 * @date: 2018/1/18 09:27
 * @desc:
 */
abstract class LazyFragment : BaseFragment() {

    private var isPrepared: Boolean = false
    private var isFirstResume = true
    private var isFirstVisible = true
    private var isFirstInvisible = true

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initPrepare()
    }

    override fun onResume() {
        super.onResume()
        if (isFirstResume) {
            isFirstResume = false
            return
        }
        if (userVisibleHint) {
            onUserVisible()
        }
    }

    override fun onPause() {
        super.onPause()
        if (userVisibleHint) {
            onUserInvisible()
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            if (isFirstVisible) {
                isFirstVisible = false
                initPrepare()
            } else {
                onUserVisible()
            }
        } else {
            if (isFirstInvisible) {
                isFirstInvisible = false
                onFirstUserInvisible()
            } else {
                onUserInvisible()
            }
        }
    }

    private fun initPrepare() {
        if (isPrepared) {
            onFirstUserVisible()
        } else {
            isPrepared = true
        }
    }

    abstract fun onFirstUserVisible()

    private fun onFirstUserInvisible() {
    }

    private fun onUserInvisible() {
    }

    private fun onUserVisible() {
    }
}