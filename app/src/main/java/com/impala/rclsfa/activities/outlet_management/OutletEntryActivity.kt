package com.impala.rclsfa.activities.outlet_management

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.impala.rclsfa.R
import com.impala.rclsfa.databinding.ActivityOutletEntryBinding
import com.impala.rclsfa.databinding.ActivityOutletManagementMainMenuBinding

class OutletEntryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOutletEntryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOutletEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }

        initView()
    }
    private fun initView() {

        binding.actvRoute.setOnClickListener {
            startActivity(Intent(this,SearchRouteActivity::class.java))
        }
    }
}