package com.impala.rclsfa.activities.attendance

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.pedant.SweetAlert.SweetAlertDialog
import com.impala.rclsfa.activities.attendance.model.SaveLeaveAttendM
import com.impala.rclsfa.databinding.ActivityApplyIomapplicationBinding
import com.impala.rclsfa.utils.ApiService
import com.impala.rclsfa.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ApplyIOMApplicationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityApplyIomapplicationBinding
    private lateinit var loadingDialog: Dialog
    private lateinit var sessionManager: SessionManager
    var cal = Calendar.getInstance()
    var firstApproval = ""
    var finalApproval = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApplyIomapplicationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }
        initView()
    }

    private fun initView() {
        firstApproval = this.intent.getStringExtra("first_approval")!!
        finalApproval = this.intent.getStringExtra("final_approval")!!
        binding.firstApproveData.text = firstApproval
        binding.finalApproveData.text = finalApproval
        loadingDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
            .setTitleText("Loading")
        sessionManager = SessionManager(this)
        val srCode = sessionManager.userId

        val dateSetListener1 =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val myFormat = "yyyy-MM-dd" // mention the format you need
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                binding.fromDate.text = sdf.format(cal.time)
            }
        val dateSetListener2 =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val myFormat = "yyyy-MM-dd" // mention the format you need
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                binding.toDate.text = sdf.format(cal.time)
            }

        binding.fromDate.setOnClickListener {
            DatePickerDialog(
                this,
                dateSetListener1,
                // set DatePickerDialog to point to today's date when it loads up
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding.toDate.setOnClickListener {
            DatePickerDialog(
                this,
                dateSetListener2,
                // set DatePickerDialog to point to today's date when it loads up
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding.applyId.setOnClickListener {
            val fromDate = binding.fromDate.text.toString()
            val toDate = binding.toDate.text.toString()
            val comments = binding.edtComments.editText!!.text.toString()

            if(fromDate.isEmpty()){
                showDialogBoxForValidation(
                    SweetAlertDialog.WARNING_TYPE,
                    "Validation",
                    "From Date is required"
                )
                return@setOnClickListener
            }
            if(toDate.isEmpty()){
                showDialogBoxForValidation(
                    SweetAlertDialog.WARNING_TYPE,
                    "Validation",
                    "To Date is required"
                )
                return@setOnClickListener
            }
            showLoadingDialog()
            saveLeaveAttendanceBySr(srCode!!, fromDate, toDate, comments, "0")
        }

    }


    private fun saveLeaveAttendanceBySr(
        sr_code: String,
        absent_from_date: String,
        absent_to_date: String,
        comments: String,
        status: String
    ) {
        val apiService = ApiService.CreateApi2()
        apiService.saveLeaveAttendanceBySr(
            sr_code,
            absent_from_date,
            absent_to_date,
            comments,
            status
        ).enqueue(object :
            Callback<SaveLeaveAttendM> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<SaveLeaveAttendM>,
                response: Response<SaveLeaveAttendM>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        if (data.getSuccess()!!) {
                            val dataList = data.getResult()
                            showFDialogBox(
                                SweetAlertDialog.SUCCESS_TYPE,
                                "SUCCESS-S5803",
                                "Leave Attendance Done  "
                            )
                            dismissLoadingDialog()
                        } else {
                            dismissLoadingDialog()
                            showDialogBox(
                                SweetAlertDialog.WARNING_TYPE, "Problem-SF5801",
                                " Failed"
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

            override fun onFailure(call: Call<SaveLeaveAttendM>, t: Throwable) {
                dismissLoadingDialog()
                showDialogBox(SweetAlertDialog.ERROR_TYPE, "Error-NF5801", "Network error")
            }
        })
    }

    private fun showDialogBoxForValidation(
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

    private fun showFDialogBox(
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

                finish()
            }
        sweetAlertDialog.show()
    }

}