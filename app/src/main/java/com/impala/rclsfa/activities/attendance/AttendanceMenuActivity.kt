package com.impala.rclsfa.activities.attendance

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.impala.rclsfa.R
import com.impala.rclsfa.activities.attendance.adapter.LeaveApplicationAdapter
import com.impala.rclsfa.databinding.ActivityAttendanceMenuBinding
import com.impala.rclsfa.utils.SessionManager


class AttendanceMenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAttendanceMenuBinding
    private lateinit var loadingDialog: Dialog
    private lateinit var sessionManager: SessionManager
    lateinit var adapter: LeaveApplicationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAttendanceMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }



        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Enable the Up button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.leaveApplication.setOnClickListener {
            val intent = Intent(this, LeaveAttendancesApplicationActivity::class.java)
            startActivity(intent)
        }

    }


    fun onMorningAttendanceButtonClick(view: View?) {
        val intent = Intent(this, MorningAttendanceActivity::class.java)
        startActivity(intent)
    }

    fun onEveningAttendanceButtonClick(view: View?) {
        val intent = Intent(this, EveningAttendanceActivity::class.java)
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
