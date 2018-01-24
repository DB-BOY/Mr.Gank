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

package com.f1reking.gank.room

import android.arch.persistence.room.Room
import android.content.Context
import com.f1reking.gank.entity.CollectionEntity

/**
 * @author: F1ReKing
 * @date: 2018/1/23 10:45
 * @desc:
 */
class AppDatabaseHelper constructor(context: Context) {

    val appDatabase = Room.databaseBuilder(context, AppDatabase::class.java,
        "gank").allowMainThreadQueries().build()

    companion object {
        @Volatile var INSTANCE: AppDatabaseHelper? = null

        fun getInstance(context: Context): AppDatabaseHelper {
            if (null == INSTANCE) {
                synchronized(AppDatabaseHelper::class) {
                    if (null == INSTANCE) {
                        INSTANCE = AppDatabaseHelper(context.applicationContext)
                    }
                }
            }
            return INSTANCE!!
        }
    }

    /**
     * 插入
     */
    fun insertColletion(collectionEntity: CollectionEntity) {
        appDatabase.collectionDao().insert(collectionEntity)
    }

    /**
     * 删除
     */
    fun delectCollection(collectionEntity: CollectionEntity) {
        appDatabase.collectionDao().delete(collectionEntity)
    }

    /**
     * 更新
     */
    fun updateCollection(collectionEntity: CollectionEntity) {
        appDatabase.collectionDao().update(collectionEntity)
    }

    /**
     * 获取列表
     */
    fun getCollectionList(): List<CollectionEntity> {
        return appDatabase.collectionDao().getCollectionList()
    }

    /**
     * 获取数据
     */
    fun queryCollectionById(id: String): List<CollectionEntity> {
        return appDatabase.collectionDao().queryCollectionById(id)
    }
}