package com.wenjian.wanandroid.ui.web

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.*
import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.base.BaseActivity
import com.wenjian.wanandroid.extension.extraDelegate
import com.wenjian.wanandroid.extension.setupActionBar
import com.wenjian.wanandroid.utils.NetUtil
import kotlinx.android.synthetic.main.activity_web.*
import org.jetbrains.anko.toast

/**
 * Description: WebActivity
 * Date: 2018/9/9
 *
 * @author jian.wen@ubtrobot.com
 */
class WebActivity : BaseActivity() {

    private val mOriginalUrl: String? by extraDelegate(EXTRA_URL)
    private var mTempUrl: String? = mOriginalUrl
    private var mTitle: String? = null
    private var mErrorView: View? = null

    companion object {
        const val EXTRA_URL = "web_url"
        val TAG: String = WebActivity::class.java.simpleName
        fun start(context: Context?, url: String?) {
            Intent(context, WebActivity::class.java).apply {
                putExtra(WebActivity.EXTRA_URL, url)
            }.let {
                context?.startActivity(it)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        setupActionBar(R.drawable.ic_close, "")

        layRefresh.setOnRefreshListener {
            webView.reload()
            //1秒后自动隐藏
            layRefresh.postDelayed({
                    layRefresh.isRefreshing = false
            }, 1000)
        }

        initWebSetting()
        webView.loadUrl(mOriginalUrl)
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


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_share -> {
                val intent = Intent(Intent.ACTION_SEND).apply {
                    putExtra(Intent.EXTRA_TEXT, mOriginalUrl)
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
            val parse = Uri.parse(mOriginalUrl)
            data = parse
        }
        startActivity(intent)
    }

    private fun copy2Clipboard() {
        val cbm = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        cbm.primaryClip = ClipData.newPlainText(null, mOriginalUrl)
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

            val appCacheDir = this@WebActivity.cacheDir.absolutePath + "/webViewCache"
            setAppCachePath(appCacheDir)
            setAppCacheEnabled(true)
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
                if (layRefresh.isRefreshing) {
                    layRefresh.isRefreshing = false
                }
                if (mTitle != null) {
                    toolBar.title = mTitle
                }
            }

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                val url = request?.url?.toString()
                webView.loadUrl(url)
                mTempUrl = url
                return true
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)
                showError()
            }

            @Suppress("OverridingDeprecatedMember")
            override fun onReceivedError(view: WebView?, errorCode: Int, description: String?, failingUrl: String?) {
                Log.i(TAG, "errorCode >>> $errorCode,description >>> $description,failingUrl >>> $failingUrl")
                showError()
            }
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
        webView.removeAllViews()
        webView.destroy()
    }
}
