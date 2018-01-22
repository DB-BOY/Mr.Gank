package com.f1reking.gank.widget

import android.content.Context
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.State
import android.view.View
import com.f1reking.gank.R

/**
 * @author: huangyh
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


