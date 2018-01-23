package com.f1reking.gank.room

import android.arch.persistence.room.Room
import android.content.Context
import com.f1reking.gank.entity.CollectionEntity

/**
 * @author: huangyh
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

    fun delectCollection(collectionEntity: CollectionEntity) {
        appDatabase.collectionDao().delete(collectionEntity)
    }

    fun updateCollection(collectionEntity: CollectionEntity) {
        appDatabase.collectionDao().update(collectionEntity)
    }

    fun getCollectionList(): List<CollectionEntity> {
        return appDatabase.collectionDao().getCollectionList()
    }

    fun queryCollectionById(id:String):List<CollectionEntity>{
        return appDatabase.collectionDao().queryCollectionById(id)
    }
}