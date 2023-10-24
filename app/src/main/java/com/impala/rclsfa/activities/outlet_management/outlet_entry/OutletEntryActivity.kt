package com.impala.rclsfa.activities.outlet_management.outlet_entry

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.impala.rclsfa.databinding.ActivityOutletEntryBinding
import com.impala.rclsfa.utils.SessionManager

class OutletEntryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOutletEntryBinding
    lateinit var sessionManager: SessionManager

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
        sessionManager = SessionManager(this)

        binding.actvRoute.setOnClickListener {
            startActivity(Intent(this, SearchRouteActivity::class.java))
        }

        binding.actvCategory.setOnClickListener {
            startActivity(Intent(this, SearchCategoryActivity::class.java))
        }

        binding.actvDivision.setOnClickListener {
            startActivity(Intent(this, SearchDivisionActivity::class.java))
        }
    }


    override fun onResume() {
        super.onResume()

        val routeName = sessionManager.routeName
        binding.actvRoute.setText(routeName)

        val cateName = sessionManager.categoryName
        binding.actvCategory.setText(cateName)

        val divName = sessionManager.divName
        binding.actvDivision.setText(divName)

    }
}