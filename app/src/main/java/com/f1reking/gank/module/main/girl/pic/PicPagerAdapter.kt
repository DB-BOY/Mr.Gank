/*
 * Copyright (c) 2018 F1ReKing
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.f1reking.gank.module.main.girl.pic

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import com.f1reking.gank.util.GlideApp
import com.github.chrisbanes.photoview.PhotoView

/**
 * @author: F1ReKing
 * @date: 2018/6/10 16:50
 * @desc:
 */
class PicPagerAdapter : PagerAdapter {

  private lateinit var imageUrls: List<String>
  private lateinit var mContext: Context
  private var url: String = ""

  constructor(mContext: Context,
              imageUrls: List<String>) : super() {
    this.imageUrls = imageUrls
    this.mContext = mContext
  }

  override fun isViewFromObject(view: View,
                                `object`: Any): Boolean {
    return view == `object`
  }

  override fun getCount(): Int {
    return if (imageUrls != null) imageUrls.size else 0
  }

  override fun destroyItem(container: ViewGroup,
                           position: Int,
                           `object`: Any) {
    container.removeView(`object` as View?)
  }

  override fun instantiateItem(container: ViewGroup,
                               position: Int): Any {
    url = imageUrls[position]
    val photoView = PhotoView(mContext)
    GlideApp.with(mContext)
        .load(url)
        .thumbnail(0.1f)
        .into(photoView)
    container.addView(photoView)
    return photoView
  }
}