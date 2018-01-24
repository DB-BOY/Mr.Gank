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
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.State
import android.view.View
import com.f1reking.gank.R

/**
 * @author: F1ReKing
 * @date: 2018/1/22 13:07
 * @desc:
 */
public class GankItemDecoration(context:Context) : RecyclerView.ItemDecoration() {

    private var dividerHeight: Int

    init {
        dividerHeight = context.resources.getDimensionPixelSize(R.dimen.divider_height)
    }

    override fun getItemOffsets(outRect: Rect?,
                                view: View?,
                                parent: RecyclerView?,
                                state: State?) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect?.bottom = dividerHeight
    }
}


