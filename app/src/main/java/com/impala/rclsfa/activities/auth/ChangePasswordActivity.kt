package com.impala.rclsfa.activities.auth

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cn.pedant.SweetAlert.SweetAlertDialog
import com.impala.rclsfa.MainActivity
import com.impala.rclsfa.R
import com.impala.rclsfa.activities.attendance.model.SaveLeaveAttendM
import com.impala.rclsfa.activities.auth.model.ChangePasswordM
import com.impala.rclsfa.databinding.ActivityChangePasswordBinding
import com.impala.rclsfa.databinding.ActivityNotificationBinding
import com.impala.rclsfa.utils.ApiService
import com.impala.rclsfa.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Query

class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangePasswordBinding
    private lateinit var loadingDialog: Dialog
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }

        sessionManager = SessionManager(this)
        loadingDialog =
            SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE).setTitleText("Loading")
        val userId = sessionManager.userId



        binding.changePassword.setOnClickListener {
            val oldPassword = binding.edtOldPassword.editText!!.text.toString()
            val newPassword = binding.edtNewPassword.editText!!.text.toString()
            val confPassword = binding.edtConfPassword.editText!!.text.toString()

            if (validateInput(oldPassword, newPassword, confPassword)) {
                showLoadingDialog()
                changePassword(userId!!, newPassword)
            }

        }


    }

    private fun validateInput(
        oldPassword: String,
        newPassword: String,
        confPassword: String,
    ): Boolean {
        if (oldPassword.isEmpty()) {
            showDialogBoxForValidation(
                SweetAlertDialog.WARNING_TYPE,
                "Validation",
                "Old password is required"
            )
            return false
        } else if (newPassword.isEmpty()) {
            showDialogBoxForValidation(
                SweetAlertDialog.WARNING_TYPE,
                "Validation",
                "New password is required"
            )
            return false
        } else if (confPassword.isEmpty()) {
            showDialogBoxForValidation(
                SweetAlertDialog.WARNING_TYPE,
                "Validation",
                "Confirm password is required"
            )
            return false
        } else {
            if (newPassword != confPassword) {
                showDialogBoxForValidation(
                    SweetAlertDialog.WARNING_TYPE,
                    "Validation",
                    "Password not match"
                )
            }
            return false
        }
        return true
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

    private fun changePassword(
        user_id: String,
        password: String
    ) {
        val apiService = ApiService.CreateApi2()
        apiService.changePassword(
            user_id,
            password
        ).enqueue(object :
            Callback<ChangePasswordM> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<ChangePasswordM>,
                response: Response<ChangePasswordM>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        if (data.getSuccess()!!) {
                            val dataList = data.getResult()
                            showFDialogBox(
                                SweetAlertDialog.SUCCESS_TYPE,
                                "SUCCESS-S5803",
                                "Change password successfully done "
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

            override fun onFailure(call: Call<ChangePasswordM>, t: Throwable) {
                dismissLoadingDialog()
                showDialogBox(SweetAlertDialog.ERROR_TYPE, "Error-NF5801", "Network error")
            }
        })
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
                startActivity(Intent(this, MainActivity::class.java))
            }
        sweetAlertDialog.show()
    }
}
