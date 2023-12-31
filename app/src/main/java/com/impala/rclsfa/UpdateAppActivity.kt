package com.impala.rclsfa

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.impala.rclsfa.databinding.ActivityTgtsetupMainBinding
import com.impala.rclsfa.databinding.ActivityUpdateAppBinding

class UpdateAppActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateAppBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateAppBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)


        binding.btnUpdate.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://157.230.39.154/update_apk/rcl_sfa.apk"))
            startActivity(browserIntent)

        }
    }
}