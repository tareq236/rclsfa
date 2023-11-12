package com.impala.rclsfa.activities.attendance

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.gson.Gson
import com.impala.rclsfa.R
import com.impala.rclsfa.activities.attendance.adapter.LeaveApplicationAdapter
import com.impala.rclsfa.databinding.ActivityAttendanceMenuBinding
import com.impala.rclsfa.models.AppButton
import com.impala.rclsfa.models.UserRoles
import com.impala.rclsfa.utils.SessionManager


class AttendanceMenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAttendanceMenuBinding
    private lateinit var loadingDialog: Dialog
    private lateinit var sessionManager: SessionManager
    lateinit var adapter: LeaveApplicationAdapter
    private val appButtons : List<AppButton> = listOf(
        AppButton("MorningAttendance", "morningAttendanceButton"),
        AppButton("EveningAttendance", "eveningAttendanceButton"),
        AppButton("IOMApplication", "iomApplicationButton"),
        AppButton("LeaveApplication", "leaveApplicationButton"),
        AppButton("ViewPaySlip", "viewPaySlipButton"),
        AppButton("MonthlyAttendance", "monthlyAttendanceButton")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAttendanceMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Enable the Up button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.leaveApplicationButton.setOnClickListener {
            val intent = Intent(this, LeaveAttendanceApplicationListActivity::class.java)
            startActivity(intent)
        }

        binding.iomApplicationButton.setOnClickListener {
            val intent = Intent(this, IOMApplicationActivity::class.java)
            startActivity(intent)
        }

        binding.monthlyAttendanceButton.setOnClickListener {
            val intent = Intent(this, MonthlyAttendaneActivity::class.java)
            startActivity(intent)
        }

        userRolesCheck()

    }

    fun onMorningAttendanceButtonClick(view: View?) {
        val intent = Intent(this, MorningAttendanceActivity::class.java)
        startActivity(intent)
    }

    fun onEveningAttendanceButtonClick(view: View?) {
        val intent = Intent(this, EveningAttendanceActivity::class.java)
        startActivity(intent)
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
