package com.f1reking.gank.room.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import com.f1reking.gank.entity.CollectionEntity

/**
 * @author: huangyh
 * @date: 2018/1/23 10:25
 * @desc:
 */
@Dao interface CollectionDao {

    @Insert
    fun insert(collectionEntity: CollectionEntity)

    @Delete()
    fun delete(vararg collectionEntity: CollectionEntity): Int

    @Update
    fun update(vararg collectionEntity: CollectionEntity): Int

    @Query("SELECT * FROM collection ORDER BY id DESC")
    fun getCollectionList(): List<CollectionEntity>

    @Query("SELECT * FROM collection where _id=:id")
    fun queryCollectionById(id: String): List<CollectionEntity>
}