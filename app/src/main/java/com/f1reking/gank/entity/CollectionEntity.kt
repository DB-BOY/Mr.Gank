package com.f1reking.gank.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import com.f1reking.gank.room.converter.Converters

/**
 * @author: huangyh
 * @date: 2018/1/23 10:01
 * @desc:
 */
@Entity(tableName = "collection") @TypeConverters(Converters::class)
data class CollectionEntity constructor(@PrimaryKey var _id: String) {

    constructor() : this("")

    @ColumnInfo(name = "createdAt") var createdAt: String? = null

    @ColumnInfo(name = "desc") var desc: String? = null

    @ColumnInfo(name = "publishedAt") var publishedAt: String? = null

    @ColumnInfo(name = "source") var source: String? = null

    @ColumnInfo(name = "type") var type: String? = null

    @ColumnInfo(name = "url") var url: String? = null

    @ColumnInfo(name = "who") var who: String? = null
    override fun toString(): String {
        return "CollectionEntity(_id='$_id', createdAt=$createdAt, desc=$desc, publishedAt=$publishedAt, source=$source, type=$type, url=$url, who=$who)"
    }
}



