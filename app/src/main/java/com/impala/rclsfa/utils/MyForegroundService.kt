package com.impala.rclsfa.utils

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.impala.rclsfa.R

class MyForegroundService : Service() {

    override fun onCreate() {
        super.onCreate()
        // Initialize and start your background tasks here
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Perform your background tasks here
        val notification = createNotification()
        startForeground(1, notification) // Set the service as foreground

        // Return START_STICKY to ensure the service is restarted if it's killed
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun createNotification(): Notification {
        val channelId = "my_foreground_service_channel"
        val builder = NotificationCompat.Builder(this, channelId)
//            .setSmallIcon(R.drawable.ic_notification_icon) // Replace with your own icon
            .setContentTitle("My App is Running")
            .setContentText("Background tasks are active")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        return builder.build()
    }
}


