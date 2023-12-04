package com.impala.rclsfa.order

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.print.PrintAttributes
import android.print.PrintJob
import android.print.PrintManager
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.impala.rclsfa.R
import com.impala.rclsfa.databinding.ActivityPrintAllBinding
import com.impala.rclsfa.databinding.ActivitySinglePrintBinding
import com.impala.rclsfa.utils.SessionManager

class SinglePrintActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySinglePrintBinding
    private lateinit var sessionManager: SessionManager
    lateinit var printJob: PrintJob

    // on below line we are initializing
    // button pressed variable as false
    private var printBtnPressed = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySinglePrintBinding.inflate(layoutInflater)
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
        binding.webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                printWebPage(view!!)
            }
        }
        // this will load the url of the website
        binding.webView.loadUrl("http://157.230.195.60:9012/mobile_view/sr_order_preview/?back_option=false&id=77283&preview=false")

        // this will enable the javascript settings, it can also allow xss vulnerabilities
        binding.webView.settings.javaScriptEnabled = true

        // if you want to enable zoom feature
        binding.webView.settings.setSupportZoom(true)

        binding.webView.settings.loadWithOverviewMode = true
        binding.webView.settings.useWideViewPort = true
        binding.webView.settings.setSupportZoom(true)
        binding.webView.settings.builtInZoomControls = true
        binding.webView.settings.displayZoomControls = false


        binding.printId.setOnClickListener {
            printWebPage(binding.webView)
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

    @SuppressLint("ServiceCast")
    fun printWebPage(webView: WebView) {
        // on below line we are initializing
        // print button pressed variable to true.
        printBtnPressed = true
        // on below line we are initializing
        // our print manager variable.
        val printManager =
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                this.getSystemService(Context.PRINT_SERVICE) as PrintManager
            } else {
                TODO("VERSION.SDK_INT < KITKAT")
            }

        // on below line we are creating a variable for job name
        val jobName = " webpage" + webView.url
        // on below line we are initializing our print adapter.
        val printAdapter =
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                // on below line we are creating
                // our print document adapter.
                webView.createPrintDocumentAdapter(jobName)
            } else {
                TODO("VERSION.SDK_INT < LOLLIPOP")
            }
        // on below line we are checking id
        // print manager is not null
        assert(printManager != null)

        // on below line we are initializing
        // our print job with print manager
        printJob = printManager.print(
            jobName, printAdapter,
            // on below line we are calling
            // build method for print attributes.
            PrintAttributes.Builder().build()
        )
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onResume() {
        super.onResume()

        // on below line we are checking
        // if print button is pressed.
        if (printBtnPressed) {
            // in this case we are simply checking
            // if the print job is completed.
            if (printJob.isCompleted) {
                // in this case we are simply displaying a completed toast message
                Toast.makeText(this, "Completed..", Toast.LENGTH_SHORT).show()
            }

            // below method is called if the print job has started.
            else if (printJob.isStarted) {
                Toast.makeText(this, "Started..", Toast.LENGTH_SHORT).show()
            }

            // below method is called if print job has blocked.
            else if (printJob.isBlocked) {
                Toast.makeText(this, "Blocked..", Toast.LENGTH_SHORT).show()
            }

            // below method is called if print job has cancelled.
            else if (printJob.isCancelled) {
                Toast.makeText(this, "Cancelled..", Toast.LENGTH_SHORT).show()
            }

            // below method is called is print job is failed.
            else if (printJob.isFailed) {
                Toast.makeText(this, "Failed..", Toast.LENGTH_SHORT).show()
            }

            // below method is called if print job is queued.
            else if (printJob.isQueued) {
                Toast.makeText(this, "Jon Queued..", Toast.LENGTH_SHORT).show()
            }

            // on below line we are simply initializing
            // our print button pressed as false
            printBtnPressed = false
        }

        binding.webView.setInitialScale(80);
    }
}