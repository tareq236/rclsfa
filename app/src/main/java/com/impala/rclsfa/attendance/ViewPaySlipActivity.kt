package com.impala.rclsfa.attendance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.widget.Toolbar
import com.impala.rclsfa.R
import com.impala.rclsfa.utils.SessionManager

class ViewPaySlipActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pay_slip)

        //webview
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }
        webView = findViewById(R.id.webView)

        sessionManager = SessionManager(this)
        val userId  = sessionManager.userId
        val designationId  = sessionManager.designationId

        val result  = "user_id=$userId&designation_id=$designationId"
        val url = "http://157.230.195.60:9012/mobile_view/pay_slip?$result"
        webView.settings.javaScriptEnabled = true
        webView.settings.userAgentString =
            "Mozilla/5.0 (Linux; Android 11; Pixel 4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Mobile Safari/537.36"
        webView.settings.useWideViewPort = true
        webView.settings.builtInZoomControls = false
        webView.settings.setSupportZoom(false)
        webView.settings.javaScriptCanOpenWindowsAutomatically = true
        webView.settings.allowFileAccess = true
        webView.settings.domStorageEnabled = true

        webView.webViewClient = WebViewClient()

        webView.loadUrl(url)
    }
}