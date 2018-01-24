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

package com.f1reking.gank.widget.xrecyclerview

import android.content.Context
import android.support.v4.widget.SwipeRefreshLayout
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration

/**
 * @author: F1ReKing
 * @date: 2017/12/19 21:38
 * @desc:
 */

class VpSwipeRefreshLayout(context: Context,
                           attrs: AttributeSet) : SwipeRefreshLayout(context, attrs) {

    private var startY: Float = 0.toFloat()
    private var startX: Float = 0.toFloat()
    // 记录viewPager是否拖拽的标记
    private var mIsVpDragger: Boolean = false
    private val mTouchSlop: Int

    init {
        mTouchSlop = ViewConfiguration.get(context).scaledTouchSlop
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        val action = ev.action
        when (action) {
            MotionEvent.ACTION_DOWN -> { // 记录手指按下的位置
                startY = ev.y
                startX = ev.x // 初始化标记
                mIsVpDragger = false
            }
            MotionEvent.ACTION_MOVE -> { // 如果viewpager正在拖拽中，那么不拦截它的事件，直接return false；
                if (mIsVpDragger) {
                    return false
                } // 获取当前手指位置
                val endY = ev.y
                val endX = ev.x
                val distanceX = Math.abs(endX - startX)
                val distanceY = Math.abs(endY - startY) // 如果X轴位移大于Y轴位移，那么将事件交给viewPager处理。
                if (distanceX > mTouchSlop && distanceX > distanceY) {
                    mIsVpDragger = true
                    return false
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> // 初始化标记
                mIsVpDragger = false
        } // 如果是Y轴位移大于X轴，事件交给swipeRefreshLayout处理。
        return super.onInterceptTouchEvent(ev)
    }
}
