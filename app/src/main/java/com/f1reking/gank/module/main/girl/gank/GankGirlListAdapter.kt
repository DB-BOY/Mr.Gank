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

package com.f1reking.gank.module.main.girl.gank

import android.content.Context
import com.f1reking.gank.R
import com.f1reking.gank.entity.GankEntity
import com.f1reking.gank.util.GlideApp
import me.f1reking.adapter.RecyclerAdapter
import me.f1reking.adapter.RecyclerViewHolder

/**
 * @author: F1ReKing
 * @date: 2018/1/9 13:44
 * @desc:
 */
class GankGirlListAdapter(var context: Context,
                          var data: ArrayList<GankEntity>) :
    RecyclerAdapter<GankEntity>(context, R.layout.item_list_meizi, data) {
    override fun convert(holder: RecyclerViewHolder,
                         entity: GankEntity?) {
        GlideApp.with(context)
            .load(entity?.url)
            .placeholder(R.drawable.pic_loading)
            .error(R.drawable.pic_no)
            .centerCrop()
            .into(holder.getView(R.id.iv_pic))
    }
}