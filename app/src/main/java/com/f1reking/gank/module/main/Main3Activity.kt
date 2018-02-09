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

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.FragmentTransaction
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import com.f1reking.gank.OOIS
import com.f1reking.gank.R
import com.f1reking.gank.base.BaseActivity
import com.f1reking.gank.module.collection.MyCollectionActivity
import com.f1reking.gank.module.main.gank.GankFragment
import com.f1reking.gank.module.main.meizi.MeiziFragment
import com.f1reking.gank.module.search.SearchActivity
import com.f1reking.gank.toast
import com.f1reking.gank.util.AlipayDonateUtils
import com.f1reking.gank.util.AppUtils
import com.miguelcatalan.materialsearchview.MaterialSearchView
import kotlinx.android.synthetic.main.activity_main3.bottomNavigation
import kotlinx.android.synthetic.main.activity_main3.search_view
import kotlinx.android.synthetic.main.toolbar.toolbar

class Main3Activity : BaseActivity() {

  private var mGankFrament: GankFragment? = null
  private var mMeiziFragment: MeiziFragment? = null
  private val fragmentManager by lazy {
    supportFragmentManager
  }
  private var exitTime: Long = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main3)
    initView()
  }

  private fun initView() {
    toolbar.apply {
      title = getString(R.string.app_name)
      setSupportActionBar(this)
    }
    bottomNavigation.apply {
      setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
      selectedItemId = R.id.nav_new
    }
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
            R.id.nav_new -> {
              mGankFrament?.let {
                this.show(it)
              }
            }
            R.id.nav_haha -> {
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

  private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
    return@OnNavigationItemSelectedListener when (item.itemId) {
      R.id.nav_new -> {
        setFragment(item.itemId)
        true
      }
      R.id.nav_haha -> {
        setFragment(item.itemId)
        true
      }
      else -> {
        false
      }
    }
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    val searchItem = menu.findItem(R.id.menu_search)
    search_view.apply {
      setMenuItem(searchItem)
      setHint("输入搜索内容")
      setSuggestions(resources.getStringArray(R.array.query_suggestions))
      setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
          SearchActivity.newIntent(this@Main3Activity, query!!)
          closeSearch()
          return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
          return false
        }
      })
    }
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
//    R.id.menu_about -> OOIS {
//      startActivity(Intent(this@Main3Activity, AboutActivity::class.java))
//    }
    R.id.menu_search -> OOIS {}
    R.id.menu_collection -> OOIS {
      startActivity(Intent(this@Main3Activity, MyCollectionActivity::class.java))
    }
//    R.id.menu_donate -> OOIS {
//      val dialog = AlertDialog.Builder(this@Main3Activity)
//          .create()
//      dialog.apply {
//        setMessage("如果觉得应用不错，可以请作者喝杯奶茶哦（づ￣3￣）づ╭❤～")
//        setButton(BUTTON_NEGATIVE, "不要") { dialog, which -> dialog!!.dismiss() }
//        setButton(BUTTON_POSITIVE, "支付宝捐助") { dialog, which ->
//          dialog!!.dismiss()
//          donateAlipay(Constant.PAAYCODE)
//        }
//      }
//      dialog.show()
//
//    }
    else -> super.onOptionsItemSelected(item)

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
    }else{
      toast("未安装支付宝哦 ￣□￣｜｜")
    }
  }

  override fun onKeyDown(keyCode: Int,
                         event: KeyEvent): Boolean {
    if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
      if (search_view.isSearchOpen) {
        search_view.closeSearch()
      } else if ((System.currentTimeMillis() - exitTime) > 2000) {
        toast("""再次点击退出 ${AppUtils.getAppName(this)}""")
        exitTime = System.currentTimeMillis()
      } else {
        finish()
        System.exit(0)
      }
      return true
    }
    return super.onKeyDown(keyCode, event)
  }
}
