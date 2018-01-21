package com.f1reking.gank.module.main.gank

import android.content.Context
import com.f1reking.gank.R
import com.f1reking.gank.entity.GankEntity
import com.f1reking.gank.util.DateUtil
import com.f1reking.gank.util.GlideApp
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
//        GlideApp.with(context).load(entity?.images?.get(0)).placeholder(
//            R.drawable.pic_loading).error(R.drawable.pic_no).centerCrop().into(holder.getView(R.id.iv_pic))
        holder.setText(R.id.tv_title, entity?.desc)
        holder.setText(R.id.tv_time, entity?.publishedAt?.let { DateUtil.toDate(it) })
    }
}