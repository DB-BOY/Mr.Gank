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

package com.f1reking.gank.db

import android.content.Context
import com.f1reking.gank.db.dao.CollectionDao

/**
 * @author: F1ReKing
 * @date: 2018/2/1 20:57
 * @desc:
 */
class CollectionDaoOp private constructor() {

  private object mHolder {
    val instance = CollectionDaoOp()
  }

  companion object {
    fun getInstance(): CollectionDaoOp {
      return mHolder.instance
    }
  }

  /**
   * 添加
   */
  fun insertData(context: Context?,
                 collection: Collection) {
    DbManager.getInstance(context!!)
        ?.getDaoSession(context)
        ?.collectionDao?.insert(collection)
  }

  /**
   * 删除
   */
  fun deleteData(context: Context?,
                 collection: Collection) {
    DbManager.getInstance(context!!)
        ?.getDaoSession(context)
        ?.collectionDao?.delete(collection)
  }

  fun deleteById(context: Context?,
                 id: String) {
    DbManager.getInstance(context!!)
        ?.getDaoSession(context)
        ?.collectionDao?.deleteByKey(id)
  }

  fun queryById(context: Context?,
                id: String): MutableList<Collection>? {
    val builder = DbManager.getInstance(context!!)
        ?.getDaoSession(context)
        ?.collectionDao?.queryBuilder()
    return builder?.where(CollectionDao.Properties.Id.eq(id))
        ?.list()
  }

  fun queryAll(context: Context?): MutableList<Collection>? {
    val builder = DbManager.getInstance(context!!)
        ?.getDaoSession(context)
        ?.collectionDao?.queryBuilder()
    return builder?.build()
        ?.list()
  }
}