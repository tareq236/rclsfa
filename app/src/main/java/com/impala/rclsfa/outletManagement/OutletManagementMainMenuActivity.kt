package com.impala.rclsfa.outletManagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.google.gson.Gson
import com.impala.rclsfa.outletManagement.outlet_entry.OutletEntryActivity
import com.impala.rclsfa.outletManagement.route_wise_outlet_mapping.RouteWiseOutletMappingActivity
import com.impala.rclsfa.databinding.ActivityOutletManagementMainMenuBinding
import com.impala.rclsfa.models.AppButton
import com.impala.rclsfa.models.UserRoles
import com.impala.rclsfa.utils.SessionManager

class OutletManagementMainMenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOutletManagementMainMenuBinding
    lateinit var sessionManager:SessionManager
    private val appButtons : List<AppButton> = listOf(
        AppButton("RouteWiseOutletMapping", "routeWiseOutMapping"),
        AppButton("OutletEntry", "btnOutletEntry"),
        AppButton("OutletSearching", "outletSearching"),
        AppButton("LocationUpdate", "locationUpdate"),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOutletManagementMainMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }

        userRolesCheck()
        initView()
    }

    private fun initView() {
        sessionManager = SessionManager(this)

        binding.routeWiseOutMapping.setOnClickListener {
            startActivity(Intent(this, RouteWiseOutletMappingActivity::class.java))
        }

        binding.outletSearching.setOnClickListener {
            startActivity(Intent(this, OutletSearchingActivity::class.java))
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


    private fun userRolesCheck(){
        sessionManager = SessionManager(this)
        val userRoles = sessionManager.userRoles
        val userRolesList = Gson().fromJson(userRoles, Array<UserRoles>::class.java).toList()
        for (button in appButtons) {
            val buttonDetails = userRolesList.firstOrNull { it.access_name == button.access_name }
            if(buttonDetails != null){
                val buttonView: Button = findViewById(resources.getIdentifier(button.buttonIdentifier, "id", packageName))
                buttonView.visibility = View.VISIBLE
            }else{
                val buttonView: Button = findViewById(resources.getIdentifier(button.buttonIdentifier, "id", packageName))
                buttonView.visibility = View.GONE
            }
        }
    }
}
