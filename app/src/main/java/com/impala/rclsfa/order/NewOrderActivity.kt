package com.impala.rclsfa.order

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.MenuItem
import android.view.MotionEvent
import android.webkit.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnSuccessListener
import com.impala.rclsfa.R

class NewOrderActivity : AppCompatActivity() {
    private val locationPermissionCode = 1
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_order)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Enable the Up button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Check and request location permission
        if (!checkLocationPermission()) {
            requestLocationPermission()
        }
        // Initialize the FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        webView = findViewById(R.id.webView)
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

        try {
            getCurrentLocation(object : OnSuccessListener<Location> {
                override fun onSuccess(location: Location?) {
                    if (location != null) {
                        val latitude = location.latitude
                        val longitude = location.longitude
                        val sharedPreferences =
                            getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
                        val user_id = sharedPreferences.getString("id", "")
//                        val url = "http://sr.radiantcaresales.com/#/mobile-order/new-order/$user_id/$latitude/$longitude"
                        val url = "http://157.230.195.60:5011/#/mobile-order/new-order/$user_id/$latitude/$longitude"
                        webView.loadUrl(url)
                    }
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
            // Handle compression error
            Toast.makeText(applicationContext, "Internal Problem.", Toast.LENGTH_LONG).show()
        }
    }

    private fun getCurrentLocation(callback: OnSuccessListener<Location>) {
        try {
            fusedLocationClient.lastLocation
                .addOnSuccessListener(callback)
        } catch (e: SecurityException) {
            e.printStackTrace()
            // Handle the case where permission is not granted
            Toast.makeText(applicationContext, "Location permission not granted.", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun checkLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            locationPermissionCode
        )
    }

    // Handle the Up button click event
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed() // Navigate back to the previous activity
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        // Disable horizontal scrolling by intercepting horizontal touch events
        return event?.actionMasked == MotionEvent.ACTION_MOVE || super.onTouchEvent(event)
    }
}
