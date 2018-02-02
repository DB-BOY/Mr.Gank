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

import android.content.Context
import com.f1reking.gank.R
import com.f1reking.gank.db.Collection
import com.f1reking.gank.util.DateUtils
import me.f1reking.adapter.RecyclerAdapter
import me.f1reking.adapter.RecyclerViewHolder

/**
 * @author: F1ReKing
 * @date: 2018/1/8 15:46
 * @desc:
 */
class CollectionListAdapter(var context: Context,
                            var data: List<Collection>) :
    RecyclerAdapter<Collection>(context, R.layout.item_list_gank, data) {
    override fun convert(holder: RecyclerViewHolder,
                         entity: Collection?) {
        holder.setText(R.id.tv_title, entity?.desc)
        holder.setText(R.id.tv_time, entity?.publishedAt?.let { DateUtils.dateFormat(it) })
        holder.setText(R.id.tv_author, """@${entity?.who}""")
        holder.setText(R.id.tv_type, entity?.type)
    }
}