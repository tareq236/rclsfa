package com.impala.rclsfa.webview

import android.os.Build
import android.os.Bundle
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
    var url = ""
    var activityFlag = ""

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
        activityFlag = this.intent.getStringExtra("activity_flag")!!
        sessionManager = SessionManager(this)
        val userId = sessionManager.userId
        val designationId = sessionManager.designationId
        binding.webView.webViewClient = WebViewClient()

        if (activityFlag == "dashboard") {
            binding.toolbar.title = "Dashboard"
            binding.webView.loadUrl("http://157.230.195.60:9012/mobile_view/dashboard?user_id=$userId&designation_id=$designationId")
        } else if (activityFlag == "view_pay_slip") {
            binding.toolbar.title = "View Pay Slip"
            val result = "user_id=$userId&designation_id=$designationId"
            val url = "http://157.230.195.60:9012/mobile_view/pay_slip?$result"
            binding.webView.loadUrl(url)
        } else if (activityFlag == "monthly_attendance") {
            binding.toolbar.title = "Monthly Attendance"
            val userId = sessionManager.userId
            val monthYear = getCurrentMonthYear()
            val result = "sr_id=$userId&month=$monthYear"
            val url = "http://157.230.195.60:9012/mobile_view/monthly_attendance_report/?$result"
            binding.webView.loadUrl(url)
        }else if(activityFlag == "product_wise_summary"){
            binding.toolbar.title = "Product Wise Summery"
            binding.webView.loadUrl("http://157.230.195.60:9012/mobile_view/order_product_wise_summary?user_id=$userId&designation_id=$designationId")
        }else if(activityFlag == "basic_summary"){
            binding.toolbar.title = "Basic Summery"
            binding.webView.loadUrl("http://157.230.195.60:9012/mobile_view/order_basic_summary?user_id=$userId&designation_id=$designationId")
        }else if(activityFlag == "sales_confirmation"){
            binding.toolbar.title = "Sales Confirmation"
            binding.webView.loadUrl("http://157.230.195.60:5011/#/mobile-order/sales-confirmation/$userId")
        }else if(activityFlag == "leader_board"){
            binding.toolbar.title = "Leader Board"
            binding.webView.loadUrl("http://157.230.195.60:5011/#/mobile-order/sales-confirmation/$userId")
        }
        // this will load the url of the website


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

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentMonthYear(): String {
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM")
        return currentDate.format(formatter)
    }

}