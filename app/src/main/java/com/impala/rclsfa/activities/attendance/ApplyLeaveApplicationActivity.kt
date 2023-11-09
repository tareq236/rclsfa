package com.impala.rclsfa.activities.attendance

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.impala.rclsfa.R
import com.impala.rclsfa.databinding.ActivityApplyLeaveApplicationBinding
import com.impala.rclsfa.databinding.ActivityLeaveAttendanceApplicationListBinding
import com.impala.rclsfa.utils.SessionManager

class ApplyLeaveApplicationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityApplyLeaveApplicationBinding
    private lateinit var loadingDialog: Dialog
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApplyLeaveApplicationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }
    }
}