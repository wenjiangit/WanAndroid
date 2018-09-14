package com.wenjian.wanandroid.ui.search

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.LinearLayout
import co.lujun.androidtagview.TagView
import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.base.BaseActivity
import com.wenjian.wanandroid.extension.apiModelDelegate
import com.wenjian.wanandroid.extension.setupActionBar
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BaseActivity() {

    private lateinit var searchFragment: SearchFragment

    private val mSearchModel: SearchModel by apiModelDelegate(SearchModel::class.java)

    private lateinit var mSearchView: SearchView

    companion object {
        fun start(cxt: Context) {
            Intent(cxt, SearchActivity::class.java).let {
                cxt.startActivity(it)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setupActionBar()
        addFragments()
        initEvents()
        subscribeUi()
        mSearchModel.loadHotWords()
    }

    private fun initEvents() {
        hotwordsContainer.setOnTagClickListener(object : TagView.OnTagClickListener {
            override fun onTagLongClick(position: Int, text: String?) {
            }

            override fun onTagClick(position: Int, text: String?) {
                mSearchView.setQuery(text,true)
            }

            override fun onTagCrossClick(position: Int) {
            }

        })
    }

    private fun subscribeUi() {
        mSearchModel.hotWords.observe(this, Observer {
            it?.data?.let {
                hotwordsContainer.tags = it.map { it.name }
            }
        })
    }

    private fun addFragments() {
        supportFragmentManager.beginTransaction().apply {
            val fragment = SearchFragment()
            add(R.id.layContainer, fragment)
            searchFragment = fragment
        }.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_view, menu)
        menu?.findItem(R.id.search)?.let {
            mSearchView = it.actionView as SearchView
            initSearchView(mSearchView)
        }
        return true
    }

    private fun initSearchView(searchView: SearchView) {
        searchView.queryHint = getString(R.string.hint_search)
        //自动展开
        searchView.onActionViewExpanded()
        //去掉hint icon
        searchView.setIconifiedByDefault(false)

        //去掉下滑线
        val viewById = searchView.findViewById<View>(android.support.v7.appcompat.R.id.search_plate)
        viewById.setBackgroundColor(Color.TRANSPARENT)

        //去掉左边搜索框左边icon
        val magIcon = searchView.findViewById<ImageView>(android.support.v7.appcompat.R.id.search_mag_icon)
        magIcon.layoutParams = LinearLayout.LayoutParams(0, 0)
        magIcon.setImageDrawable(null)

        //点击清除按钮后,隐藏searchFragment,并弹出软键盘
        val searchText = searchView.findViewById<SearchView.SearchAutoComplete>(android.support.v7.appcompat.R.id.search_src_text)
        val closeBt = searchView.findViewById<ImageView>(android.support.v7.appcompat.R.id.search_close_btn)
        closeBt.setOnClickListener {
            searchView.setQuery("", false)
            searchText.requestFocus()
            showSoftKeyboard(searchText)
            hotPanel.visibility = View.VISIBLE
        }

        //添加监听
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    searchFragment.search(query!!)
                    hideSoftKeyboard(searchView)
                    hotPanel.visibility = View.GONE
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

    }

    private fun hideSoftKeyboard(view: View) {
        val methodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        methodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun showSoftKeyboard(view: View) {
        val methodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        methodManager.showSoftInput(view, 0)
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }

}
