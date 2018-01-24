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

package com.f1reking.gank.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import com.f1reking.gank.room.converter.Converters

/**
 * @author: F1ReKing
 * @date: 2018/1/23 10:01
 * @desc:
 */
@Entity(tableName = "collection") @TypeConverters(Converters::class)
data class CollectionEntity constructor(@PrimaryKey(autoGenerate = true) var id: Int) {

    constructor() : this(0)

    @ColumnInfo(name = "_id") var _id: String? = null

    @ColumnInfo(name = "desc") var desc: String? = null

    @ColumnInfo(name = "publishedAt") var publishedAt: String? = null

    @ColumnInfo(name = "type") var type: String? = null

    @ColumnInfo(name = "url") var url: String? = null

    @ColumnInfo(name = "who") var who: String? = null

    override fun toString(): String {
        return "CollectionEntity(id=$id, _id=$_id, desc=$desc, publishedAt=$publishedAt, type=$type, url=$url, who=$who)"
    }
}



