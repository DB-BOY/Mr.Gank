package com.f1reking.gank.entity

import java.util.Date

/**
 * @author: huangyh
 * @date: 2018/1/8 14:15
 * @desc:
 */
data class GankEntity(var _id: String,
                      var createdAt: Date,
                      var desc: String,
                      var publishedAt: Date,
                      var source: String,
                      var type: String,
                      var url: String,
                      var user: Boolean,
                      var who: String,
                      var images: MutableList<String>)