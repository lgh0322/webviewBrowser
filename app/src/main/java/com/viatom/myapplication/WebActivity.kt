package com.viatom.myapplication

import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.provider.SyncStateContract
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.webkit.*
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class WebActivity : AppCompatActivity() {
    lateinit var web: WebView
    lateinit var back:TextView

    val url="http://193.169.0.227:8080"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        initWeb()

    }


    fun initWeb(){
        web = findViewById<WebView>(R.id.web)
        web.settings.setJavaScriptEnabled(true);//启用js
        web.settings.blockNetworkImage = false;//解决图片不显示
        web.settings.useWideViewPort = true
        web.settings.loadWithOverviewMode = true

        web.settings.allowFileAccess = true
        web.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        web.getSettings().domStorageEnabled = true
        web.settings.setAppCacheEnabled(true)

        web.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                super.shouldOverrideUrlLoading(view, request)
                val url = request?.url.toString()
                return !(url.startsWith("http://") || url.startsWith("https://"))

            }
        }
        web.webChromeClient = mWebChromeClient

        web.addJavascriptInterface(object : Any() {
            //定义的方法
            @JavascriptInterface
            fun goBack() {
                this@WebActivity.finish()
            }
        }, "Android")
        web.loadUrl(url)

        back=findViewById(R.id.back)
        back.setOnClickListener {
            web.goBack()
        }
    }

    var fullscreenContainer: FrameLayout? = null
    var customViewCallback: WebChromeClient.CustomViewCallback? = null
    private val mWebChromeClient = object : WebChromeClient() {
        override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
            super.onShowCustomView(view, callback)
            showCustomView(view, callback)
        }

        override fun onHideCustomView() {
            super.onHideCustomView()
            hideCustomView()
        }
    }

    /**
     * 显示自定义控件
     */
    private fun showCustomView(view: View?, callback: WebChromeClient.CustomViewCallback?) {
        if (fullscreenContainer != null) {
            callback?.onCustomViewHidden()
            return
        }

        fullscreenContainer = FrameLayout(this).apply { setBackgroundColor(Color.BLACK) }
        customViewCallback = callback
        fullscreenContainer?.addView(view)
        val decorView = window?.decorView as? FrameLayout
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        decorView?.addView(fullscreenContainer)
    }

    /**
     * 隐藏自定义控件
     */
    private fun hideCustomView() {
        if (fullscreenContainer == null) {
            return
        }

        val decorView = window?.decorView as? FrameLayout
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        fullscreenContainer?.removeAllViews()
        decorView?.removeView(fullscreenContainer)
        fullscreenContainer = null
        customViewCallback?.onCustomViewHidden()
        customViewCallback = null
    }

    override fun onBackPressed() {
        web.goBack()
    }


}