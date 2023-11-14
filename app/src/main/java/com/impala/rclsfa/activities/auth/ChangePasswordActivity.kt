package com.impala.rclsfa.activities.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.impala.rclsfa.R
import com.impala.rclsfa.databinding.ActivityChangePasswordBinding
import com.impala.rclsfa.databinding.ActivityNotificationBinding

class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }
    }
}