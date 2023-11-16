package com.impala.rclsfa.attendance

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import cn.pedant.SweetAlert.SweetAlertDialog
import com.impala.rclsfa.R
import com.impala.rclsfa.attendance.adapter.LeaveApplicationAdapter
import com.impala.rclsfa.attendance.model.AllLeaveAttendListM
import com.impala.rclsfa.databinding.ActivityIomapplicationBinding
import com.impala.rclsfa.databinding.ActivityLeaveAttendanceApplicationListBinding
import com.impala.rclsfa.utils.ApiService
import com.impala.rclsfa.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IOMApplicationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIomapplicationBinding
    private lateinit var loadingDialog: Dialog
    private lateinit var sessionManager: SessionManager
    lateinit var adapter: LeaveApplicationAdapter
    var firstApproval = ""
    var finalApproval = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIomapplicationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }

        initView()
    }

    private fun initView() {
        adapter = LeaveApplicationAdapter(this)
        sessionManager = SessionManager(this)
        val userId = sessionManager.userId
        val designationId = sessionManager.designationId
        // Initialize the loading dialog
        loadingDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
            .setTitleText("Loading")

        val linearLayoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = linearLayoutManager
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)


        binding.applyId.setOnClickListener {
            startActivity(Intent(this, ApplyIOMApplicationActivity::class.java)
                .putExtra("first_approval",firstApproval)
                .putExtra("final_approval",finalApproval)
            )
        }

        showLoadingDialog()
        iomAttendanceSrList(userId!!)
    }

    private fun iomAttendanceSrList(user_code: String) {
        val apiService = ApiService.CreateApi2()
        apiService.iomAttendanceSrList(user_code).enqueue(object : Callback<AllLeaveAttendListM> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<AllLeaveAttendListM>,
                response: Response<AllLeaveAttendListM>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        if (data.getSuccess()!!) {
                            val dataList = data.getResult()
                            adapter.addData(dataList as MutableList<AllLeaveAttendListM.Result>)
                            firstApproval = data.getFirstApproval()!!.name!!
                            finalApproval = data.getFinalApproval()!!.name!!
                            dismissLoadingDialog()
                        } else {
                            dismissLoadingDialog()
                            showDialogBox(
                                SweetAlertDialog.WARNING_TYPE, "Problem-SF5801",
                                "Failed!!"
                            )
                        }
                    } else {
                        dismissLoadingDialog()
                        showDialogBox(
                            SweetAlertDialog.WARNING_TYPE,
                            "Error-RN5801",
                            "Response NULL value. Try later."
                        )
                    }
                } else {
                    dismissLoadingDialog()
                    showDialogBox(
                        SweetAlertDialog.WARNING_TYPE,
                        "Error-RR5801",
                        "Response failed. Try later."
                    )
                }
            }

            override fun onFailure(call: Call<AllLeaveAttendListM>, t: Throwable) {
                dismissLoadingDialog()
                showDialogBox(SweetAlertDialog.ERROR_TYPE, "Error-NF5801", "Network error")
            }
        })
    }

    private fun showLoadingDialog() {
        loadingDialog.setCancelable(false)
        loadingDialog.show()
    }

    private fun dismissLoadingDialog() {
        loadingDialog.dismiss()
    }

    private fun showDialogBox(
        type: Int,
        title: String,
        message: String,
        callback: (() -> Unit)? = null
    ) {
        val sweetAlertDialog = SweetAlertDialog(this, type)
            .setTitleText(title)
            .setContentText(message)
            .setConfirmClickListener {
                it.dismissWithAnimation()
                callback?.invoke()
            }
        sweetAlertDialog.show()
    }
}
