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

import android.content.Context
import android.content.Intent
import com.f1reking.gank.Constant

/**
 * @author: F1ReKing
 * @date: 2018/1/18 16:05
 * @desc:
 */
class ShareUtils {

    companion object {

        fun shareText(context: Context,
                      text: CharSequence,
                      title: String) {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.putExtra(Intent.EXTRA_TEXT, text)
            shareIntent.type = Constant.CONTENT_SHARE_TYPE
            context.startActivity(Intent.createChooser(shareIntent, title))
        }
    }
}

