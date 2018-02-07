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

package com.f1reking.gank.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.widget.TextView
import com.f1reking.gank.R

/**
 * @author: F1ReKing
 * @date: 2018/1/27 11:45
 * @desc: 自定义书签view
 */

class BookmarkView : TextView {

  private var mWidth: Int = 0
  private var mHeight: Int = 0

  constructor(context: Context) : super(context) {}

  constructor(context: Context,
              attrs: AttributeSet?) : super(context, attrs) {
  }

  constructor(context: Context,
              attrs: AttributeSet?,
              defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
  }

  override fun onMeasure(widthMeasureSpec: Int,
                         heightMeasureSpec: Int) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    mWidth = measuredWidth
    mHeight = measuredHeight
  }

  override fun onDraw(canvas: Canvas?) {
    val mPaint = Paint()
    val path = Path()
    mPaint.color = ContextCompat.getColor(context, R.color.gray)
    mPaint.style = Paint.Style.FILL
    mPaint.isAntiAlias = true
    mPaint.isDither = true
    path.lineTo(mWidth.toFloat(), 0f)
    path.lineTo((mWidth - 15).toFloat(), (mHeight / 2).toFloat())
    path.lineTo(mWidth.toFloat(), mHeight.toFloat())
    path.lineTo(0f, mHeight.toFloat())
    canvas!!.drawPath(path, mPaint)
    super.onDraw(canvas)
  }
}
