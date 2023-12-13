package com.impala.rclsfa.outletManagement

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.impala.rclsfa.R
import com.impala.rclsfa.databinding.ActivityOutletDetailsBinding
import com.squareup.picasso.Picasso

class OutletDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOutletDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOutletDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }

        initView()

    }

    fun initView() {

        val retailerName = this.intent.getStringExtra("retailerName")
        val nameBn = this.intent.getStringExtra("nameBn")
        val proprietorName = this.intent.getStringExtra("proprietorName")
        val mobileNumber = this.intent.getStringExtra("mobileNumber")
        val address = this.intent.getStringExtra("address")
        val birthDay = this.intent.getStringExtra("birthDay")
        val marriageDate = this.intent.getStringExtra("marriageDate")
        val nid = this.intent.getStringExtra("nid")
        val fChildName = this.intent.getStringExtra("fChildName")
        val fChildBirthDay = this.intent.getStringExtra("fChildBirthDay")
        val sChildName = this.intent.getStringExtra("sChildName")
        val sChildBirthDay = this.intent.getStringExtra("sChildBirthDay")
        val image = this.intent.getStringExtra("image")

        Picasso.get().load("http://157.230.195.60:9012/outlet/$image")
            .placeholder(R.drawable.default_image).into(binding.outletImageId)

        binding.retailerNameEn.text = retailerName
        binding.retailerNameBn.text = nameBn
        binding.proprietorName.text = proprietorName
        binding.mobileNumber.text = mobileNumber
        binding.address.text = address
        binding.birthDay.text = birthDay
        binding.marriageDate.text = marriageDate
        binding.nid.text = nid
        binding.firstChildren.text = fChildName
        binding.firstChBirthDay.text = fChildBirthDay
        binding.secondChildren.text = sChildName
        binding.secondChildBirthDay.text = sChildBirthDay
    }
}