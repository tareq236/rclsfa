package com.impala.rclsfa.activities.outlet_management

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.impala.rclsfa.activities.outlet_management.outlet_entry.adapter.RouteAdapter
import com.impala.rclsfa.databinding.ActivityOutletSearchingBinding
import com.impala.rclsfa.utils.SessionManager

class OutletSearchingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOutletSearchingBinding
    private lateinit var loadingDialog: Dialog
    private lateinit var sessionManager: SessionManager
    lateinit var adapter: OutletListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOutletSearchingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }

        initView()
    }
    private fun initView() {
        adapter = OutletListAdapter(this)
        sessionManager = SessionManager(this)


        val linearLayoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = linearLayoutManager
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)


    }
}