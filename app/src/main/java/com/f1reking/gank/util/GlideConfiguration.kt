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

package com.f1reking.gank.util

import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

/**
 * @author: F1ReKing
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