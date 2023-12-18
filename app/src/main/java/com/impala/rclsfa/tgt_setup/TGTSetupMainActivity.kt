package com.impala.rclsfa.tgt_setup

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.gson.Gson
import com.impala.rclsfa.databinding.ActivityTgtsetupMainBinding
import com.impala.rclsfa.models.AppButton
import com.impala.rclsfa.models.UserRoles
import com.impala.rclsfa.tgt_setup.kro_outlet_selection.KROOutletActivity
import com.impala.rclsfa.tgt_setup.route_wise_tgt_setup.AssignUserListActivity
import com.impala.rclsfa.tgt_setup.route_wise_tgt_setup.RouteListActivity
import com.impala.rclsfa.utils.SessionManager

class TGTSetupMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTgtsetupMainBinding
    private lateinit var sessionManager: SessionManager
    private val appButtons : List<AppButton> = listOf(
        AppButton("RouteWiseTgtSetup", "routeWiseTgtSetup"),
        AppButton("KROOutletSelection", "kroOutletSelection"),
        AppButton("RouteWiseTgtApprove", "routeWiseTgtApprove")
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTgtsetupMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }

        binding.routeWiseTgtSetup.setOnClickListener {
            startActivity(Intent(this, RouteListActivity::class.java))
        }

        binding.kroOutletSelection.setOnClickListener {
            startActivity(Intent(this, KROOutletActivity::class.java))
        }
        binding.routeWiseTgtApprove.setOnClickListener {
            startActivity(Intent(this, AssignUserListActivity::class.java))
        }
        userRolesCheck()

    }

    private fun userRolesCheck(){
        sessionManager = SessionManager(this)
        val userRoles = sessionManager.userRoles
        val userRolesList = Gson().fromJson(userRoles, Array<UserRoles>::class.java).toList()
        for (button in appButtons) {
            val buttonDetails = userRolesList.firstOrNull { it.access_name == button.access_name }
            if(buttonDetails != null){
                val buttonView: MaterialButton = findViewById(resources.getIdentifier(button.buttonIdentifier, "id", packageName))
                buttonView.visibility = View.VISIBLE
            }else{
                val buttonView: MaterialButton = findViewById(resources.getIdentifier(button.buttonIdentifier, "id", packageName))
                buttonView.visibility = View.GONE
            }
        }
    }

}
