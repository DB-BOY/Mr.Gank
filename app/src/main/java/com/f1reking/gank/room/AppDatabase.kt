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

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.f1reking.gank.entity.CollectionEntity
import com.f1reking.gank.room.dao.CollectionDao

/**
 * @author: F1ReKing
 * @date: 2018/1/23 10:22
 * @desc:
 */
@Database(entities = arrayOf(CollectionEntity::class), version = 1) abstract class AppDatabase :
    RoomDatabase() {

    abstract fun collectionDao(): CollectionDao
}