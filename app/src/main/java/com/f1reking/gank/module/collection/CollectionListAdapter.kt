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

package com.f1reking.gank.module.collection

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import com.f1reking.gank.R
import com.f1reking.gank.entity.CollectionEntity
import com.f1reking.gank.entity.GankEntity
import com.f1reking.gank.module.web.WebActivity
import com.f1reking.gank.util.DateUtils
import kotlinx.android.synthetic.main.item_list_gank.view.tv_author
import kotlinx.android.synthetic.main.item_list_gank.view.tv_time
import kotlinx.android.synthetic.main.item_list_gank.view.tv_title
import kotlinx.android.synthetic.main.item_list_gank.view.tv_type

/**
 * @author: F1ReKing
 * @date: 2018/1/8 15:46
 * @desc:
 */
class CollectionListAdapter(var context: Context,
                            var data: ArrayList<CollectionEntity>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        val EMPTY_VIEW = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup?,
                                    viewType: Int): ViewHolder {
        val view: View
        return if (viewType == EMPTY_VIEW) {
            view = LayoutInflater.from(context).inflate(R.layout.layout_no_data, parent, false)
            EmptyViewHolder(view)
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.item_list_gank, parent, false)
            GankViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return if (data.isNotEmpty()) data.size else 1
    }

    override fun getItemViewType(position: Int): Int {
        if (data.isEmpty()) {
            return EMPTY_VIEW
        }
        return super.getItemViewType(position)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder,
                                  position: Int) {
        when (getItemViewType(position)) {
            EMPTY_VIEW -> {

            }
            else -> {
                holder.itemView.tv_title.text = data[position].desc
                holder.itemView.tv_time.text = data[position].publishedAt?.let {
                    DateUtils.dateFormat(it)
                }
                holder.itemView.tv_author.text = """@${data[position].who}"""
                holder.itemView.tv_type.text = data[position].type
            }
        }
    }

    open fun clear() {
        data.clear()
        notifyDataSetChanged()
    }

    fun addAll(list: List<CollectionEntity>) {
        data.addAll(list)
        notifyDataSetChanged()
    }

    class EmptyViewHolder(view: View) : RecyclerView.ViewHolder(view) {}

    inner class GankViewHolder(view: View) : RecyclerView.ViewHolder(view), OnClickListener {

        override fun onClick(v: View?) {
            val gankEntity = GankEntity("", "", "", "", "", "", "")
            gankEntity._id = data[adapterPosition]._id
            gankEntity.desc = data[adapterPosition].desc
            gankEntity.type = data[adapterPosition].type
            gankEntity.url = data[adapterPosition].url
            gankEntity.who = data[adapterPosition].who
            gankEntity.publishedAt = data[adapterPosition].publishedAt
            WebActivity.newIntent(context, gankEntity)
        }

        init {
            itemView.setOnClickListener(this)
        }
    }
}
