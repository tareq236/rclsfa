package com.impala.rclsfa.order

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import com.impala.rclsfa.R
import com.impala.rclsfa.databinding.ActivityBasicSummeryBinding
import com.impala.rclsfa.databinding.ActivityDashboardBinding
import com.impala.rclsfa.utils.SessionManager

class BasicSummeryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBasicSummeryBinding
    private lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBasicSummeryBinding.inflate(layoutInflater)
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
        binding.webView.webViewClient = WebViewClient()
        // this will load the url of the website
        binding.webView.loadUrl("http://157.230.195.60:9012/mobile_view/dashboard?user_id=$userId&designation_id=$designationId")

        // this will enable the javascript settings, it can also allow xss vulnerabilities
        binding.webView.settings.javaScriptEnabled = true

        // if you want to enable zoom feature
        binding.webView.settings.setSupportZoom(true)
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