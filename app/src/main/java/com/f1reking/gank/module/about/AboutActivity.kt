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

package com.f1reking.gank.module.about

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.f1reking.gank.OOIS
import com.f1reking.gank.R
import com.f1reking.gank.base.BaseActivity
import com.f1reking.gank.util.AppUtils
import com.f1reking.gank.util.ShareUtils
import kotlinx.android.synthetic.main.activity_about.tv_version

/**
 * @author: F1ReKing
 * @date: 2018/1/5 13:43
 * @desc:
 */
class AboutActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        initView()
    }

    private fun initView() {
        setToolbarTitle("")
        tv_version.apply {
            text = String.format(getString(R.string.app_version),
                AppUtils.getVersionName(this@AboutActivity))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_about, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.menu_share -> OOIS {
            ShareUtils.shareText(this, getString(R.string.share_app),
                getString(R.string.share_to_friend))
        }
        else            -> super.onOptionsItemSelected(item)
    }
}