package com.f1reking.gank.module.collection

import android.content.Context
import com.f1reking.gank.R
import com.f1reking.gank.entity.CollectionEntity
import com.f1reking.gank.util.DateUtils
import me.f1reking.adapter.RecyclerAdapter
import me.f1reking.adapter.RecyclerViewHolder

/**
 * @author: huangyh
 * @date: 2018/1/8 15:46
 * @desc:
 */
class CollectionListAdapter(var context: Context,
                            data: List<CollectionEntity>) :
    RecyclerAdapter<CollectionEntity>(context, R.layout.item_list_gank, data) {
    override fun convert(holder: RecyclerViewHolder,
                         entity: CollectionEntity?) {
        holder.setText(R.id.tv_title, entity?.desc)
        holder.setText(R.id.tv_time, entity?.publishedAt?.let { DateUtils.dateFormat(it) })
        holder.setText(R.id.tv_author, """@${entity?.who}""")
        holder.setText(R.id.tv_type, entity?.type)
    }
}