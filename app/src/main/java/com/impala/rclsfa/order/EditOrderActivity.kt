package com.impala.rclsfa.order

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.impala.rclsfa.databinding.ActivityEditOrderBinding
import com.impala.rclsfa.utils.SessionManager

class EditOrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditOrderBinding
    private lateinit var sessionManager: SessionManager
    private var orderId =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }

        initView()
    }

    private fun initView() {
        binding.progressBar.visibility = View.VISIBLE
        orderId = this.intent.getIntExtra("order_id",0)
        sessionManager = SessionManager(this)

        val userId = sessionManager.userId
        val designationId = sessionManager.designationId
        binding.webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                binding.progressBar.visibility = View.GONE
            }
        }
        // this will load the url of the website
        binding.webView.loadUrl("http://157.230.195.60:5011/#/mobile-order/edit-order/${orderId}")

        // this will enable the javascript settings, it can also allow xss vulnerabilities
        binding.webView.settings.javaScriptEnabled = true


        binding.webView.settings.loadWithOverviewMode = true
        binding.webView.settings.useWideViewPort = true

        binding.webView.settings.setSupportZoom(true)
        binding.webView.settings.builtInZoomControls = true
        binding.webView.settings.displayZoomControls = false



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