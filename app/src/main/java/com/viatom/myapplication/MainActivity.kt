package com.viatom.myapplication

import android.app.Activity
import android.content.pm.ActivityInfo
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.webkit.*
import android.widget.FrameLayout

class MainActivity : AppCompatActivity() {
    lateinit var web:WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED)

       web=findViewById<WebView>(R.id.aa)
        web.settings.setJavaScriptEnabled(true);//启用js
        web.settings.blockNetworkImage = false;//解决图片不显示
        web.settings.useWideViewPort=true
        web.settings.loadWithOverviewMode=true

        web.settings.allowFileAccess=true
        web.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        web.getSettings().domStorageEnabled=true
        web.settings.setAppCacheEnabled(true)

//        web.webViewClient=object: WebViewClient() {
//            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
//                super.shouldOverrideUrlLoading(view, request)
//                val url=request?.url.toString()
//                return !(url.startsWith("http://") || url.startsWith("https://"))
//
//            }
//        }
        web.webChromeClient=mWebChromeClient
        web.loadUrl("https://mp.weixin.qq.com/s?__biz=Mzg2MjY3MTYwOQ==&mid=2247487860&idx=1&sn=84e45e669d2ef330e48bb576c710efee&chksm=ce0516e2f9729ff48f16b296158c1f1fe34c455dbf5d716cb31717427417af2472c5e261650c#rd")
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

        val decorView =window?.decorView as? FrameLayout
       requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        fullscreenContainer?.removeAllViews()
        decorView?.removeView(fullscreenContainer)
        fullscreenContainer = null
        customViewCallback?.onCustomViewHidden()
        customViewCallback = null
    }

    override fun onBackPressed() {
        web.goBack()
        Log.e("littlePu", "back")
    }



}