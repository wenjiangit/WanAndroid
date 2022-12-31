package com.wenjian.wanandroid.ui.web

import android.annotation.SuppressLint
import android.annotation.TargetApi
import androidx.lifecycle.Observer
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.base.VMActivity
import com.wenjian.wanandroid.entity.WebModel
import com.wenjian.wanandroid.extension.*
import com.wenjian.wanandroid.ui.collect.CollectModel
import com.wenjian.wanandroid.utils.NetUtil
import kotlinx.android.synthetic.main.activity_web.*
import org.jetbrains.anko.toast

/**
 * Description: WebActivity
 * Date: 2018/9/9
 *
 * @author jian.wen@ubtrobot.com
 */
class WebActivity : VMActivity<CollectModel>(CollectModel::class.java) {

    private val mWebModel: WebModel? by extraDelegate(EXTRA_MODEL)
    private var mTitle: String? = null
    private var mErrorView: View? = null

    companion object {
        private const val EXTRA_MODEL = "web_model"
        val TAG: String = WebActivity::class.java.simpleName
        fun start(context: Context?, model: WebModel) {
            Intent(context, WebActivity::class.java).apply {
                putExtra(EXTRA_MODEL, model)
            }.let {
                context?.startActivity(it)
            }
        }
    }

    override fun setup() {
        setContentView(R.layout.activity_web)
        setupActionBar(R.drawable.ic_close, "")
        setSystemBarColor(getCompatColor(R.color.light_status_bar))
        initView()
        initWebListener()
        initWebSetting()
        mWebModel?.link?.let { webView.loadUrl(it) }
    }


    private fun initView() {
        val isCollect = mWebModel?.collect ?: false
        if (isCollect) {
            btCollect.setImageResource(R.drawable.ic_favorite)
        } else {
            btCollect.setImageResource(R.drawable.ic_favorite_border)
        }
        btCollect.tag = isCollect

        layRefresh.setColorSchemeColors(getColorAccent())
        layRefresh.setOnRefreshListener {
            //1秒后隐藏
            webView.reload()
            layRefresh.postDelayed({
                layRefresh.isRefreshing = false
            }, 1000)
        }


    }

    @TargetApi(Build.VERSION_CODES.M)
    private fun initWebListener() {
        webView.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            if (scrollY < oldScrollY) {//up
                animateFbt(false)
            }

            if (scrollY > oldScrollY) {//down
                animateFbt(true)
            }
        }

        btCollect.setOnClickListener { _ ->
            val collect = btCollect.tag as Boolean
            if (collect) {
                collectPost()
            } else {
                unCollect()
            }
        }

    }

    private fun unCollect() {
        mViewModel.collect(mWebModel?.id!!)
                .observe(this, Observer {
                    toastSuccess("添加收藏成功")
                    btCollect.setImageResource(R.drawable.ic_favorite)
                    btCollect.tag = true
                })
    }

    private fun collectPost() {
        mViewModel.uncollect(mWebModel?.id!!)
                .observe(this, Observer {
                    toastInfo("已取消收藏")
                    btCollect.setImageResource(R.drawable.ic_favorite_border)
                    btCollect.tag = false
                })
    }


    private var isFbtHide: Boolean = false

    private fun animateFbt(hide: Boolean) {
        if ((isFbtHide && hide) || (!isFbtHide && !hide)) return
        isFbtHide = hide
        val scale = if (hide) 0f else 1f
        btCollect.animate().scaleX(scale).scaleY(scale).setStartDelay(100).start()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.web_share, menu)
        return true
    }


    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_share -> {
                val intent = Intent(Intent.ACTION_SEND).apply {
                    putExtra(Intent.EXTRA_TEXT, mWebModel?.link)
                    type = "text/plain"
                }
                startActivity(Intent.createChooser(intent, "分享给好友"))
                true
            }
            R.id.copy_link -> {
                copy2Clipboard()
                true
            }
            R.id.open_browser -> {
                openBrowser()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun openBrowser() {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            val parse = Uri.parse(mWebModel?.link)
            data = parse
        }
        startActivity(intent)
    }

    private fun copy2Clipboard() {
        val cbm = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        cbm.setPrimaryClip(ClipData.newPlainText(null, mWebModel?.link))
        toast(getString(R.string.copy_success))
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebSetting() {
        webView.settings?.apply {
            loadWithOverviewMode = true
            useWideViewPort = true
            javaScriptEnabled = true
            //允许混合加载
            mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW

            //缓存设置
            domStorageEnabled = true
            databaseEnabled = true

//            val appCacheDir = this@WebActivity.externalCacheDir?.absolutePath + "/webViewCache"
//            setAppCachePath(appCacheDir)
//            setAppCacheEnabled(true)
            cacheMode = if (NetUtil.isNetworkAvailable(this@WebActivity)) {
                WebSettings.LOAD_DEFAULT
            } else {
                WebSettings.LOAD_CACHE_ELSE_NETWORK
            }

        }

        webView.webChromeClient = object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView?, title: String?) {
                toolBar?.title = title
                mTitle = title
            }

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                if (newProgress == 100) {
                    pbLoading.visibility = View.GONE
                } else {
                    pbLoading.progress = newProgress
                }
            }

        }

        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                pbLoading.visibility = View.VISIBLE
                toolBar.title = "载入中..."
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                pbLoading.visibility = View.GONE
                if (mTitle != null) {
                    toolBar.title = mTitle
                }
            }

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                val url = request?.url?.toString()
                webView.loadUrl(url ?: "")
                return true
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)
//                showError()
            }

//            @Suppress("OverridingDeprecatedMember")
//            override fun onReceivedError(view: WebView?, errorCode: Int, description: String?, failingUrl: String?) {
//                Log.i(TAG, "errorCode >>> $errorCode,description >>> $description,failingUrl >>> $failingUrl")
//                showError()
//            }
        }
    }

    /**
     * 显示自定义的错误页面
     */
    private fun showError() {
        if (mErrorView == null) {
            mErrorView = vsError.inflate()
        }

        mErrorView!!.apply {
            visibility = View.VISIBLE
            setOnClickListener {
                webView.reload()
                visibility = View.GONE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val parent = webView.parent as? ViewGroup
        parent?.removeView(webView)
        webView.removeAllViews()
//        webView.destroy()
    }
}
