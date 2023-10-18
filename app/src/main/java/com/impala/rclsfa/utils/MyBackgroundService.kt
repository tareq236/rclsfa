package com.impala.rclsfa.utils

import android.R
import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.impala.rclsfa.MainActivity


class MyBackgroundService : Service() {

    companion object {
        const val CHANNEL_ID = "MyForegroundServiceChannel"
        private const val TAG = "MyBackgroundService"
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "Background service created")
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        val notification = createNotification()
//
//        startForeground(1, notification)

        Log.d(TAG, "Background service started")

        // Perform your background tasks here
        return START_STICKY
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        Log.d(TAG, "Background service destroyed")
//    }

    private fun createNotification(): Notification {
        val notificationIntent = Intent(this, MainActivity::class.java) // Replace with your main activity
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            notificationIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("My Foreground Service")
            .setContentText("Running in the background")
            .setContentIntent(pendingIntent)
            .build()
    }

    private fun createNotificationChannel() {
        // Create a notification channel if you're targeting Android Oreo and above
        // You should call this only once (e.g., in onCreate) when your service starts
        // This is necessary for foreground services on Android Oreo and later
        // You can adjust channel settings as needed
        val name = "My Foreground Service Channel"
        val descriptionText = "Channel for My Foreground Service"
        val importance = NotificationManagerCompat.IMPORTANCE_DEFAULT
        val channel = NotificationChannelCompat.Builder(CHANNEL_ID, importance)
            .setName(name)
            .setDescription(descriptionText)
            .build()

        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.createNotificationChannel(channel)
    }
}
