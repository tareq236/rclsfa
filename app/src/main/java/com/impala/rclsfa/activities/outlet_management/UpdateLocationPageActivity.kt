package com.impala.rclsfa.activities.outlet_management

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.impala.rclsfa.databinding.ActivityUpdateLocationPageBinding
import com.impala.rclsfa.utils.SessionManager

class UpdateLocationPageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateLocationPageBinding
    private lateinit var loadingDialog: Dialog
    private lateinit var sessionManager: SessionManager

    var retailerId = 0
    var nameBn =""
    var retailerName=""
    var outletAddress=""

    var latitude = ""
    var longitude = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateLocationPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }

        initView()
    }
    private fun initView() {
        sessionManager = SessionManager(this)
        retailerId = intent.getIntExtra("retailer_id",0)!!
        nameBn = intent.getStringExtra("name_bn")!!
        retailerName = intent.getStringExtra("retailer_name")!!
        outletAddress = intent.getStringExtra("outlet_address")!!
        latitude = intent.getStringExtra("latitude")!!
        longitude = intent.getStringExtra("longitude")!!

        binding.retailerId.text = retailerId.toString()
        binding.outletNameBn.text = nameBn
        binding.retailerName.text = retailerName
        binding.outletAddress.text = outletAddress

    }

}