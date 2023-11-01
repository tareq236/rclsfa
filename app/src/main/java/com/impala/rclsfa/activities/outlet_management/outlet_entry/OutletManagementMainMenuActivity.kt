package com.impala.rclsfa.activities.outlet_management.outlet_entry

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.impala.rclsfa.activities.outlet_management.OutletSearchingActivity
import com.impala.rclsfa.activities.outlet_management.LocationUpdateActivity
import com.impala.rclsfa.activities.outlet_management.route_wise_outlet_mapping.RouteWiseOutletMappingActivity
import com.impala.rclsfa.databinding.ActivityOutletManagementMainMenuBinding
import com.impala.rclsfa.utils.SessionManager

class OutletManagementMainMenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOutletManagementMainMenuBinding
    lateinit var sessionManager:SessionManager
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
        sessionManager = SessionManager(this)

        binding.routeWiseOutMapping.setOnClickListener {
            startActivity(Intent(this, RouteWiseOutletMappingActivity::class.java))
        }

        binding.outletSearching.setOnClickListener {
            startActivity(Intent(this,OutletSearchingActivity::class.java))
        }

        binding.locationUpdate.setOnClickListener {
            startActivity(Intent(this, LocationUpdateActivity::class.java))
        }

        binding.btnOutletEntry.setOnClickListener {
            sessionManager.routeName=""
            sessionManager.districtName=""
            sessionManager.upzName=""
            sessionManager.divName=""
            sessionManager.categoryName=""
            sessionManager.divId=-1
            sessionManager.districtId=-1
            sessionManager.upzId=-1
            startActivity(Intent(this, OutletEntryActivity::class.java))
        }

    }
}