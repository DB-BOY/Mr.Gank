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

package com.f1reking.gank.module.main.meizi

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.GridLayoutManager.SpanSizeLookup
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import com.f1reking.gank.R
import com.f1reking.gank.entity.GankEntity
import com.f1reking.gank.module.main.gank.GankListAdapter
import com.f1reking.gank.util.GlideApp
import kotlinx.android.synthetic.main.item_list_meizi.view.iv_pic
import kotlinx.android.synthetic.main.layout_no_data.view.tv_title

/**
 * @author: F1ReKing
 * @date: 2018/1/9 13:44
 * @desc:
 */
class MeiziListAdapter(var context: Context,
                       var data: ArrayList<GankEntity>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val EMPTY_VIEW = 1
    }

    fun setLayoutManage(gridLayoutManager: GridLayoutManager) {
        gridLayoutManager.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (data.isEmpty()) gridLayoutManager.spanCount else 1
            }
        }
    }

    private lateinit var onIntentListener: OnIntentListener

    override fun onCreateViewHolder(parent: ViewGroup?,
                                    viewType: Int): ViewHolder {
        val view: View
        return if (viewType == EMPTY_VIEW) {
            view = LayoutInflater.from(context).inflate(R.layout.layout_no_data, parent, false)
            EmptyViewHolder(view)
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.item_list_meizi, parent, false)
            MeiziViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return if (data.isNotEmpty()) data.size else 1
    }

    override fun getItemViewType(position: Int): Int {
        if (data.isEmpty()) {
            return GankListAdapter.EMPTY_VIEW
        }
        return super.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: ViewHolder,
                                  position: Int) {

        when (getItemViewType(position)) {
            EMPTY_VIEW -> {
                holder.itemView.tv_title.text = "没有妹子"
            }
            else -> {
                GlideApp.with(context).load(data[position].url).placeholder(
                    R.drawable.pic_loading).error(R.drawable.pic_no).centerCrop().into(holder.itemView.iv_pic)
            }
        }
    }

    fun clear() {
        data.clear()
        notifyDataSetChanged()
    }

    fun addAll(list: List<GankEntity>) {
        data.addAll(list)
        notifyDataSetChanged()
    }

    class EmptyViewHolder(view: View) : RecyclerView.ViewHolder(view)

    inner class MeiziViewHolder(view: View) : RecyclerView.ViewHolder(view), OnClickListener {

        override fun onClick(v: View) {
            onIntentListener.onClick(v, data[adapterPosition])
        }

        init {
            itemView.setOnClickListener(this)
        }
    }

    fun setOnIntentListener(onIntentListener: OnIntentListener) {
        this.onIntentListener = onIntentListener
    }

    interface OnIntentListener {
        fun onClick(view: View,
                    gankEntity: GankEntity)
    }
}
