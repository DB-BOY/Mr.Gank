package com.f1reking.gank.module.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.f1reking.gank.R
import com.f1reking.gank.base.BaseActivity
import com.f1reking.gank.toast

/**
 * @author: huangyh
 * @date: 2018/1/22 17:29
 * @desc:
 */
class SearchActivity : BaseActivity() {

    companion object {
        const val EXTRA_QUERY = "query"

        fun newIntent(context: Context,
                      query: String) {
            val intent = Intent(context, SearchActivity::class.java)
            intent.putExtra(SearchActivity.EXTRA_QUERY, query)
            context.startActivity(intent)
        }
    }

    private lateinit var query: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initView()
    }

    private fun initView() {
        setToolbarTitle("搜索结果")
        toast(intent.getStringExtra(EXTRA_QUERY))
    }
}