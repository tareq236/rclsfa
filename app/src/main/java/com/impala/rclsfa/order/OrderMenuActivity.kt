package com.impala.rclsfa.order

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import com.google.gson.Gson
import com.impala.rclsfa.R
import com.impala.rclsfa.databinding.ActivityOrderMenuBinding
import com.impala.rclsfa.models.AppButton
import com.impala.rclsfa.models.UserRoles
import com.impala.rclsfa.utils.SessionManager
import com.impala.rclsfa.webview.WebViewModelActivity

class OrderMenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderMenuBinding
    private lateinit var sessionManager: SessionManager
    private val appButtons : List<AppButton> = listOf(
        AppButton("NewOrder", "morningAttendanceButton"),
        AppButton("OrderList", "orderListButton"),
        AppButton("BasicSummary", "basicSummaryButton"),
        AppButton("ProductWiseSummary", "productWiseSummaryButton"),
        AppButton("SalesConfirmationNightWork", "salesConfirmationNightWorkButton"),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Enable the Up button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        userRolesCheck()
    }

    fun onNewOrderButtonClick(view: View?) {
        val intent = Intent(this, NewOrderActivity::class.java)
        startActivity(intent)
    }

    fun onOrderListButtonClick(view: View?) {
        val intent = Intent(this, OrderListActivity::class.java)
        startActivity(intent)
    }

    fun basicSummaryButton(view: View?) {
        val userRoles = sessionManager.userRoles
        val userRolesList = Gson().fromJson(userRoles, Array<UserRoles>::class.java).toList()
        val buttonDetails = userRolesList.firstOrNull { it.access_name == "BasicSummary" }
        if (buttonDetails != null) {
            if(buttonDetails.access_url != ""){
                val intent = Intent(this, WebViewModelActivity::class.java)
                intent.putExtra("access_url",buttonDetails.access_url)
                intent.putExtra("menu_name",buttonDetails.menu_name)
                startActivity(intent)
            }
        }
    }

    fun productWiseSummaryButton(view: View?) {
        val userRoles = sessionManager.userRoles
        val userRolesList = Gson().fromJson(userRoles, Array<UserRoles>::class.java).toList()
        val buttonDetails = userRolesList.firstOrNull { it.access_name == "ProductWiseSummary" }
        if (buttonDetails != null) {
            if(buttonDetails.access_url != ""){
                val intent = Intent(this, WebViewModelActivity::class.java)
                intent.putExtra("access_url",buttonDetails.access_url)
                intent.putExtra("menu_name",buttonDetails.menu_name)
                startActivity(intent)
            }
        }
    }

    fun salesConfirmationNightWorkButton(view: View?) {
        val userRoles = sessionManager.userRoles
        val userRolesList = Gson().fromJson(userRoles, Array<UserRoles>::class.java).toList()
        val buttonDetails = userRolesList.firstOrNull { it.access_name == "SalesConfirmationNightWork" }
        if (buttonDetails != null) {
            if(buttonDetails.access_url != ""){
                val intent = Intent(this, WebViewModelActivity::class.java)
                intent.putExtra("access_url",buttonDetails.access_url)
                intent.putExtra("menu_name",buttonDetails.menu_name)
                startActivity(intent)
            }
        }
    }

    // Handle the Up button click event
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed() // Navigate back to the previous activity
            return true
        }
        return super.onOptionsItemSelected(item)
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
