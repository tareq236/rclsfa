package com.impala.rclsfa.activities.tgt_setup.kro_outlet_selection

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.impala.rclsfa.activities.tgt_setup.kro_outlet_selection.adapter.KroDetailsAdapter
import com.impala.rclsfa.databinding.ActivityKroDetailsBinding
import com.impala.rclsfa.utils.SessionManager

class KroDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKroDetailsBinding
    lateinit var adapter: KroDetailsAdapter
    private lateinit var loadingDialog: Dialog
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKroDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }

        initView()
    }

    private fun initView() {
        sessionManager = SessionManager(this)
        adapter= KroDetailsAdapter(this)

        val linearLayoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = linearLayoutManager
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)
    }
}