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
import android.database.sqlite.SQLiteDatabase
import com.f1reking.gank.db.dao.DaoMaster
import com.f1reking.gank.db.dao.DaoSession

/**
 * @author: F1ReKing
 * @date: 2018/2/1 20:45
 * @desc:
 */
class DbManager private constructor(context: Context) {

  private val DB_NAME = "gank.db"
  private var mDevOpenHelper: DaoMaster.DevOpenHelper? = null
  private var mDaoMaster: DaoMaster? = null
  private var mDaoSession: DaoSession? = null

  init {
    mDevOpenHelper = DBOpenHelper(context, DB_NAME, null)
    getDaoMaster(context)
    getDaoSession(context)
  }

  companion object {
    @Volatile var instance: DbManager? = null

    fun getInstance(context: Context): DbManager? {
      if (null == instance) {
        synchronized(DbManager::class) {
          if (null == instance) {
            instance = DbManager(context)
          }
        }
      }
      return instance
    }
  }

  fun getDaoMaster(context: Context): DaoMaster? {
    if (null == mDaoMaster) {
      synchronized(DbManager::class.java) {
        if (null == mDaoMaster) {
          mDaoMaster = DaoMaster(getWritableDatabase(context))
        }
      }
    }
    return mDaoMaster
  }

  fun getDaoSession(context: Context): DaoSession? {
    if (null == mDaoSession) {
      synchronized(DbManager::class.java) {
        mDaoSession = getDaoMaster(context)?.newSession()
      }
    }
    return mDaoSession
  }

  fun getWritableDatabase(context: Context): SQLiteDatabase? {
    if (null == mDevOpenHelper) {
      getInstance(context)
    }
    return mDevOpenHelper?.writableDatabase
  }

  fun getReadableDatabase(context: Context): SQLiteDatabase? {
    if (null == mDevOpenHelper) {
      getInstance(context)
    }
    return mDevOpenHelper?.readableDatabase
  }
}