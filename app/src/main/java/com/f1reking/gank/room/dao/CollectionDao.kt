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

package com.f1reking.gank.room.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import com.f1reking.gank.entity.CollectionEntity

/**
 * @author: F1ReKing
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