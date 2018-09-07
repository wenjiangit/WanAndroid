package com.wenjian.wanandroid.ui.web

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.*
import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.base.BaseActivity
import com.wenjian.wanandroid.extension.extraDelegate
import kotlinx.android.synthetic.main.activity_web.*

class WebActivity : BaseActivity() {

    private val loadUrl: String? by extraDelegate(EXTRA_URL)

    companion object {
        const val EXTRA_URL = "web_url"

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

        setSupportActionBar(toolBar)
        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_close)
            setDisplayHomeAsUpEnabled(true)
            title = ""
        }

        toolBar.setNavigationOnClickListener {
            finish()
        }

        initWebSetting()

        webView.loadUrl(loadUrl)

    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebSetting() {
        webView.settings?.apply {
            loadWithOverviewMode = true
            javaScriptEnabled = true
            useWideViewPort = true

            mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW

        }

        webView.webChromeClient = object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView?, title: String?) {
                toolBar?.title = title
            }

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                pbLoading.progress = newProgress
            }
        }

        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                pbLoading.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                pbLoading.visibility = View.GONE
            }

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                webView.loadUrl(request?.url?.toString())
                return true
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        webView.removeAllViews()
        webView.destroy()
    }
}
