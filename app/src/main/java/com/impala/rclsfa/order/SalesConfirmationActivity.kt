package com.impala.rclsfa.order

import android.net.http.SslError
import android.os.Bundle
import android.webkit.SslErrorHandler
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.impala.rclsfa.databinding.ActivitySalesConfirmationBinding
import com.impala.rclsfa.utils.SessionManager

class SalesConfirmationActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySalesConfirmationBinding
    private lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySalesConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }

        initView()
    }

    private fun initView() {

        sessionManager = SessionManager(this)

        val userId = sessionManager.userId
        val designationId = sessionManager.designationId
        binding.webView.webViewClient = AppWebViewClient()
        // this will load the url of the website
        binding.webView.loadUrl("http://157.230.195.60:5011/#/mobile-order/sales-confirmation/$userId")

        // this will enable the javascript settings, it can also allow xss vulnerabilities
        binding.webView.settings.javaScriptEnabled = true

        // if you want to enable zoom feature
        binding.webView.settings.setSupportZoom(true)
    }

    class AppWebViewClient() : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {

            view.loadUrl(url)
            return true
        }


        override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
            var message = "SSL Certificate error."
            when (error.primaryError) {
                SslError.SSL_UNTRUSTED ->
                    message = "The certificate authority is not trusted."
                SslError.SSL_EXPIRED ->
                    message = "The certificate has expired.";
                SslError.SSL_IDMISMATCH ->
                    message = "The certificate Hostname mismatch.";
                SslError.SSL_NOTYETVALID ->
                    message = "The certificate is not yet valid.";
            }
            message += "\"SSL Certificate Error\" Do you want to continue anyway?";
            //Log your message
            handler.proceed()

        }
    }
    // if you press Back button this code will work
    override fun onBackPressed() {
        // if your webview can go back it will go back
        if (binding.webView.canGoBack())
            binding.webView.goBack()
        // if your webview cannot go back
        // it will exit the application
        else
            super.onBackPressed()
    }
}
