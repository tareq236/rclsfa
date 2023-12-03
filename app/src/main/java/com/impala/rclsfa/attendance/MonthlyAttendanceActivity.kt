package com.impala.rclsfa.attendance

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import com.impala.rclsfa.R
import com.impala.rclsfa.utils.SessionManager
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MonthlyAttendanceActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private lateinit var sessionManager: SessionManager

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_monthly_attendane)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        webView = findViewById(R.id.webView)
        sessionManager = SessionManager(this)
        val userId = sessionManager.userId
        val monthYear = getCurrentMonthYear()
        val result = "sr_id=$userId&month=$monthYear"

        val url = "http://157.230.195.60:9012/mobile_view/monthly_attendance_report/?$result"
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed() // Navigate back to the previous activity
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentMonthYear(): String {
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM")
        return currentDate.format(formatter)
    }
}
