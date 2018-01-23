package com.f1reking.gank.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.f1reking.gank.entity.CollectionEntity
import com.f1reking.gank.room.dao.CollectionDao

/**
 * @author: huangyh
 * @date: 2018/1/23 10:22
 * @desc:
 */
@Database(entities = arrayOf(CollectionEntity::class), version = 1) abstract class AppDatabase :
    RoomDatabase() {

    abstract fun collectionDao(): CollectionDao
}