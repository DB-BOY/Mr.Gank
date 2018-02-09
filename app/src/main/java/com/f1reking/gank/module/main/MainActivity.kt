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

package com.f1reking.gank.module.main

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.f1reking.gank.Constant
import com.f1reking.gank.R
import com.f1reking.gank.module.about.AboutActivity
import com.f1reking.gank.module.collection.MyCollectionActivity
import com.f1reking.gank.module.main.gank.GankFragment
import com.f1reking.gank.module.main.meizi.MeiziFragment
import com.f1reking.gank.toast
import com.f1reking.gank.util.AlipayDonateUtils
import com.f1reking.gank.util.AppUtils
import kotlinx.android.synthetic.main.activity_main.drawer_layout
import kotlinx.android.synthetic.main.activity_main.nav_view
import kotlinx.android.synthetic.main.fragment_gank.search_view
import kotlinx.android.synthetic.main.toolbar.toolbar

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

  private var mGankFrament: GankFragment? = null
  private var mMeiziFragment: MeiziFragment? = null
  private val fragmentManager by lazy {
    supportFragmentManager
  }
  private var exitTime: Long = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    toolbar.apply {
      title = getString(R.string.app_name)
      setSupportActionBar(this)
    }

    val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar,
        R.string.navigation_drawer_open, R.string.navigation_drawer_close)
    drawer_layout.addDrawerListener(toggle)
    toggle.syncState()

    nav_view.setNavigationItemSelectedListener(this)
    nav_view.setCheckedItem(R.id.nav_gank)
    setFragment(R.id.nav_gank)
  }

  override fun onAttachFragment(fragment: android.support.v4.app.Fragment?) {
    super.onAttachFragment(fragment)
    when (fragment) {
      is GankFragment -> mGankFrament ?: let { mGankFrament = fragment }
      is MeiziFragment -> mMeiziFragment ?: let { mMeiziFragment = fragment }
    }
  }

  private fun setFragment(index: Int) {
    fragmentManager.beginTransaction()
        .apply {
          mGankFrament ?: let {
            GankFragment().let {
              mGankFrament = it
              add(R.id.ll_container, it)
            }
          }
          mMeiziFragment ?: let {
            MeiziFragment().let {
              mMeiziFragment = it
              add(R.id.ll_container, it)
            }
          }
          hideFragment(this)
          when (index) {
            R.id.nav_gank -> {
              mGankFrament?.let {
                this.show(it)
              }
            }
            R.id.nav_meizi -> {
              mMeiziFragment?.let {
                this.show(it)
              }
            }
          }
        }
        .commit()
  }

  private fun hideFragment(transaction: FragmentTransaction) {
    mGankFrament?.let {
      transaction.hide(it)
    }
    mMeiziFragment?.let {
      transaction.hide(it)
    }
  }

  override fun onBackPressed() {
    when {
      drawer_layout.isDrawerOpen(GravityCompat.START) -> drawer_layout.closeDrawer(
          GravityCompat.START)
      search_view.isSearchOpen -> search_view.closeSearch()
      (System.currentTimeMillis() - exitTime) > 2000 -> {
        toast("""再次点击退出 ${AppUtils.getAppName(this)}""")
        exitTime = System.currentTimeMillis()
      }
      else -> {
        finish()
        System.exit(0)
      }
    }
  }

  override fun onNavigationItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.nav_gank -> {
        setFragment(item.itemId)
      }
      R.id.nav_meizi -> {
        setFragment(item.itemId)
      }
      R.id.nav_donate -> {
        val dialog = AlertDialog.Builder(this@MainActivity)
            .create()
        dialog.apply {
          setMessage("如果觉得应用不错，可以请作者喝杯奶茶哦（づ￣3￣）づ╭❤～")
          setButton(DialogInterface.BUTTON_NEGATIVE, "不要") { dialog, which -> dialog!!.dismiss() }
          setButton(DialogInterface.BUTTON_POSITIVE, "支付宝捐助") { dialog, which ->
            dialog!!.dismiss()
            donateAlipay(Constant.PAAYCODE)
          }
        }
        dialog.show()
      }
      R.id.nav_collection -> {
        startActivity(Intent(this@MainActivity, MyCollectionActivity::class.java))
      }
      R.id.nav_about -> {
        startActivity(Intent(this@MainActivity, AboutActivity::class.java))
      }
    }
    drawer_layout.closeDrawer(GravityCompat.START)
    return true
  }

  /**
   * 支付宝捐助
   */
  private fun donateAlipay(payCode: String) {
    val hasInstalledAlipayClient = AlipayDonateUtils.getInstance()
        .hasInstalledAlipayClient(this)
    if (hasInstalledAlipayClient) {
      AlipayDonateUtils.getInstance()
          .startAlipayClient(this, payCode)
    } else {
      toast("未安装支付宝哦 ￣□￣｜｜")
    }
  }
}
