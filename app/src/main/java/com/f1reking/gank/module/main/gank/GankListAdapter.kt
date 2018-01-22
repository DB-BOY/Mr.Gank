package com.f1reking.gank.module.main.gank

import android.content.Context
import com.f1reking.gank.R
import com.f1reking.gank.entity.GankEntity
import com.f1reking.gank.util.DateUtils
import me.f1reking.adapter.RecyclerAdapter
import me.f1reking.adapter.RecyclerViewHolder

/**
 * @author: huangyh
 * @date: 2018/1/8 15:46
 * @desc:
 */
class GankListAdapter(var context: Context,
                      data: MutableList<GankEntity>) :
    RecyclerAdapter<GankEntity>(context, R.layout.item_list_gank, data) {
    override fun convert(holder: RecyclerViewHolder,
                         entity: GankEntity?) {
        holder.setText(R.id.tv_title, entity?.desc)
        holder.setText(R.id.tv_time, entity?.publishedAt?.let { DateUtils.dateFormat(it) })
        holder.setText(R.id.tv_author, """@${entity?.who}""")
        holder.setText(R.id.tv_type, entity?.type)
    }
}