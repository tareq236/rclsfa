package com.impala.rclsfa.activities.tgt_setup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.impala.rclsfa.R
import com.impala.rclsfa.activities.tgt_setup.kro_outlet_selection.KROOutletActivity
import com.impala.rclsfa.activities.tgt_setup.route_wise_tgt_setup.RouteListActivity
import com.impala.rclsfa.databinding.ActivityOutletManagementMainMenuBinding
import com.impala.rclsfa.databinding.ActivityTgtsetupMainBinding

class TGTSetupMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTgtsetupMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTgtsetupMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }

        binding.routeWiseTgtSetup.setOnClickListener {
            startActivity(Intent(this,RouteListActivity::class.java))
        }

        binding.kroOutletSelection.setOnClickListener {
            startActivity(Intent(this,KROOutletActivity::class.java))
        }

    }


}