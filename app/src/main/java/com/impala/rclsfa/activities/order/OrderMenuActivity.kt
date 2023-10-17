package com.impala.rclsfa.activities.order

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.impala.rclsfa.R
import com.impala.rclsfa.activities.attendance.MorningAttendanceActivity

class OrderMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_menu)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Enable the Up button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    fun onNewOrderButtonClick(view: View?) {
        val intent = Intent(this, NewOrderActivity::class.java)
        startActivity(intent)
    }

    // Handle the Up button click event
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed() // Navigate back to the previous activity
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
