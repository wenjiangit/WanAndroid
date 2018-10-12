package com.wenjian.wanandroid.ui.theme

import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.base.BaseSkinActivity
import com.wenjian.wanandroid.entity.Skin
import com.wenjian.wanandroid.extension.addCustomDecoration
import com.wenjian.wanandroid.extension.getCompatColor
import com.wenjian.wanandroid.extension.setSystemBarColor
import com.wenjian.wanandroid.extension.setupActionBar
import com.wenjian.wanandroid.helper.QMUIStatusBarHelper
import com.wenjian.wanandroid.helper.ThemeHelper
import com.wenjian.wanandroid.model.RxBus
import com.wenjian.wanandroid.model.SkinChangeEvent
import com.wenjian.wanandroid.ui.adapter.ThemeAdapter
import kotlinx.android.synthetic.main.activity_theme.*
import kotlinx.android.synthetic.main.fix_title_bar.*

class ThemeActivity : BaseSkinActivity() {

    private val mAdapter: ThemeAdapter by lazy { ThemeAdapter() }

    override fun setup() {
        setContentView(R.layout.activity_theme)
        setupActionBar(title = "主题风格")
        initRecycler()
    }

    private fun initRecycler() {
        themeRecycler.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@ThemeActivity)
            addCustomDecoration()
            adapter = mAdapter
        }
        mAdapter.setNewData(ThemeHelper.loadSkin())

        mAdapter.setOnItemClickListener { _, _, position ->
            mAdapter.data.forEach {
                it.select = false
            }
            val skin = mAdapter.data[position].apply {
                select = true
            }
            mAdapter.notifyDataSetChanged()
            applySkin(skin)
        }
    }

    private fun applySkin(skin: Skin) {
        val skinColor: Int = if (ThemeHelper.isDefault(skin)) R.color.white else skin.color
        val colorInt = getCompatColor(skinColor)
        toolBar.setBackgroundColor(colorInt)
        if (ThemeHelper.isDefault(skin)) {
            toolBar.setTitleTextColor(Color.BLACK)
            toolBar.navigationIcon?.setTint(Color.BLACK)
            setSystemBarColor(getCompatColor(R.color.white))
            QMUIStatusBarHelper.setStatusBarLightMode(this)
        } else {
            setSystemBarColor(colorInt)
            toolBar.setTitleTextColor(Color.WHITE)
            toolBar.navigationIcon?.setTint(Color.WHITE)
            QMUIStatusBarHelper.setStatusBarDarkMode(this)
        }
        //这里不能使用recreate,会使界面黑屏
//        recreate()
        ThemeHelper.setSkin(skin.id)
    }

    override fun onPause() {
        super.onPause()
        //通知主题发生改变,主界面重新启动
        if (ThemeHelper.hasChanged()) {
            RxBus.post(SkinChangeEvent())
        }
    }

}
