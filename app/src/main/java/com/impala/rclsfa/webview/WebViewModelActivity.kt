package com.impala.rclsfa.webview

import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.webkit.SslErrorHandler
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.impala.rclsfa.databinding.ActivityWebviewModelBinding
import com.impala.rclsfa.utils.SessionManager
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class WebViewModelActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebviewModelBinding
    private lateinit var sessionManager: SessionManager

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebviewModelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }

        initView()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initView() {
        var accessUrl = this.intent.getStringExtra("access_url")!!
        val menuName = this.intent.getStringExtra("menu_name")!!
        sessionManager = SessionManager(this)
        val userId = sessionManager.userId
        val designationId = sessionManager.designationId.toString()
        val monthYear = getCurrentMonthYear()
        binding.webView.webViewClient = AppWebViewClient()

        if (accessUrl.contains("{userid}")) {
            accessUrl = userId?.let { accessUrl.replace("{userid}", it) }.toString()
        }
        if (accessUrl.contains("{designationid}")) {
            accessUrl = designationId?.let { accessUrl.replace("{designationid}", it) }.toString()
        }
        if (accessUrl.contains("{monthyear}")) {
            accessUrl = monthYear?.let { accessUrl.replace("{monthyear}", it) }.toString()
        }

        binding.toolbar.title = menuName
        binding.webView.loadUrl(accessUrl)
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.setSupportZoom(true)

        binding.webView.clearCache(true);
        binding.webView.clearHistory();
        binding.webView.settings.allowContentAccess = true;
        binding.webView.settings.domStorageEnabled = true;

        binding.webView.settings.builtInZoomControls = true;
        binding.webView.settings.setSupportZoom(true);
        binding.webView.settings.loadWithOverviewMode = true;
        binding.webView.settings.useWideViewPort = true;
        binding.webView.settings.displayZoomControls = false;
        binding.webView.settings.allowFileAccess = false;
        binding.webView.settings.loadsImagesAutomatically = true;
        binding.webView.settings.databaseEnabled = true;
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentMonthYear(): String {
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM")
        return currentDate.format(formatter)
    }

}
