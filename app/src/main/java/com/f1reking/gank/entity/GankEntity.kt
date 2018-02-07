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

package com.f1reking.gank.entity

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author: F1ReKing
 * @date: 2018/1/8 14:15
 * @desc:
 */
@SuppressLint("ParcelCreator") @Parcelize data class GankEntity(var _id: String?,
                                                                var ganhuo_id: String?,
                                                                var desc: String?,
                                                                var publishedAt: String?,
                                                                var type: String?,
                                                                var url: String?,
                                                                var who: String?,
                                                                var images: MutableList<String>?) :
    Parcelable