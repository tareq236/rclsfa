package com.impala.rclsfa.activities.outlet_management

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.impala.rclsfa.R
import com.impala.rclsfa.databinding.ActivityDashboardBinding
import com.impala.rclsfa.databinding.ActivityOutletManagementMainMenuBinding

class OutletManagementMainMenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOutletManagementMainMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOutletManagementMainMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }

        initView()
    }

    private fun initView() {

        binding.btnOutletEntry.setOnClickListener {
            startActivity(Intent(this, OutletEntryActivity::class.java))
        }

    }
}