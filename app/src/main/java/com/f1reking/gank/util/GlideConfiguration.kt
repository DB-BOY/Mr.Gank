package com.f1reking.gank.util

import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

/**
 * @author: huangyh
 * @date: 2018/1/15 21:56
 * @desc:
 */
@GlideModule class GlideConfiguration : AppGlideModule() {

    /**
     * 禁止解析Manifest文件
     * 主要针对V3升级到v4的用户，可以提升初始化速度，避免一些潜在错误
     * @return
     */
    override fun isManifestParsingEnabled(): Boolean {
        return false
    }
}