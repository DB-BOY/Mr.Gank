package com.f1reking.gank.entity

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author: huangyh
 * @date: 2018/1/8 14:15
 * @desc:
 */
@SuppressLint("ParcelCreator") @Parcelize data class GankEntity(var _id: String?,
                                                                var ganhuo_id: String?,
                                                                var createdAt: String?,
                                                                var desc: String?,
                                                                var publishedAt: String?,
                                                                var source: String?,
                                                                var type: String?,
                                                                var url: String?,
                                                                var user: Boolean?,
                                                                var who: String?,
                                                                var images: MutableList<String>?) :
    Parcelable {}