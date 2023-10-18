package com.impala.rclsfa

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionActivity : AppCompatActivity() {

    private val locationPermissionCode = 1
    private val cameraPermissionCode = 2
    private lateinit var permissionStatusTextView: TextView
    private lateinit var requestAllButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)

        permissionStatusTextView = findViewById(R.id.permissionStatusTextView)
        requestAllButton = findViewById(R.id.requestAllButton)

        // Check and update permission statuses
        updatePermissionStatus()

        // Set click listener for the "Request All Permissions" button
        requestAllButton.setOnClickListener {
            requestPermissions()
        }
    }

    private fun updatePermissionStatus() {
        val locationPermission = Manifest.permission.ACCESS_FINE_LOCATION
        val cameraPermission = Manifest.permission.CAMERA

        val locationPermissionStatus = checkPermissionStatus(locationPermission)
        val cameraPermissionStatus = checkPermissionStatus(cameraPermission)

        val locationStatusText = "Location: $locationPermissionStatus\n"
        val cameraStatusText = "Camera: $cameraPermissionStatus\n"

        val combinedStatusText = locationStatusText + cameraStatusText
        permissionStatusTextView.text = combinedStatusText

        if (locationPermissionStatus == PermissionStatus.GRANTED &&
            cameraPermissionStatus == PermissionStatus.GRANTED
        ) {
            // All permissions are granted, proceed to MainActivity
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            requestAllButton.isEnabled = true
        }
    }

    private fun checkPermissionStatus(permission: String): PermissionStatus {
        val permissionStatus = ContextCompat.checkSelfPermission(this, permission)

        return when {
            permissionStatus == PackageManager.PERMISSION_GRANTED -> PermissionStatus.GRANTED
            ActivityCompat.shouldShowRequestPermissionRationale(this, permission) -> PermissionStatus.DENIED
            else -> PermissionStatus.NEVER_ASK_AGAIN
        }
    }

    private fun requestPermissions() {
        val permissionsToRequest = mutableListOf<String>()

        val locationPermission = Manifest.permission.ACCESS_FINE_LOCATION
        val cameraPermission = Manifest.permission.CAMERA

        if (checkPermissionStatus(locationPermission) != PermissionStatus.GRANTED) {
            permissionsToRequest.add(locationPermission)
        }
        if (checkPermissionStatus(cameraPermission) != PermissionStatus.GRANTED) {
            permissionsToRequest.add(cameraPermission)
        }

        if (permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                permissionsToRequest.toTypedArray(),
                locationPermissionCode // Update with your request code
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == locationPermissionCode || requestCode == cameraPermissionCode) {
            // Check if all requested permissions are granted
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                // All permissions granted, proceed to MainActivity
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                // Handle the case when permissions are not granted
                // You can show a message to the user or request permissions again
            }
        }
    }

    enum class PermissionStatus {
        GRANTED,
        DENIED,
        NEVER_ASK_AGAIN
    }
}
