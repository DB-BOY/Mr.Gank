package com.f1reking.gank.module.main.meizi

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.f1reking.gank.R
import com.f1reking.gank.entity.GankEntity
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import me.f1reking.adapter.RecyclerAdapter
import me.f1reking.adapter.RecyclerViewHolder

/**
 * @author: huangyh
 * @date: 2018/1/9 13:44
 * @desc:
 */
class MeiziListAdapter(var context: Context,
                       data: MutableList<GankEntity>) :
    RecyclerAdapter<GankEntity>(context, R.layout.item_list_meizi, data) {
    override fun convert(holder: RecyclerViewHolder,
                         entity: GankEntity?) {
        Glide.with(context).load(entity?.url).apply(
            RequestOptions().error(R.drawable.pic_no).placeholder(
                R.drawable.pic_loading).centerCrop().transform(
                RoundedCornersTransformation(10, 0))).into(holder.getView(R.id.iv_pic))
    }
}