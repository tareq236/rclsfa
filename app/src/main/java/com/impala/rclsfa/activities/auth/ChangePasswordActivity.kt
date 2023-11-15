package com.impala.rclsfa.activities.auth

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cn.pedant.SweetAlert.SweetAlertDialog
import com.impala.rclsfa.R
import com.impala.rclsfa.databinding.ActivityChangePasswordBinding
import com.impala.rclsfa.databinding.ActivityNotificationBinding
import com.impala.rclsfa.utils.SessionManager

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
        loadingDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE).setTitleText("Loading")
        val userId = sessionManager.userId

        val oldPassword = binding.edtOldPassword.editText!!.text.toString()
        val newPassword = binding.edtNewPassword.editText!!.text.toString()
        val confPassword = binding.edtConfPassword.editText!!.text.toString()

        if (validateInput(oldPassword, newPassword, confPassword)) {
            showLoadingDialog()

        }


    }

    private fun validateInput(oldPassword: String, newPassword: String, confPassword: String, ): Boolean {
        if (oldPassword.isEmpty()) {
            showDialogBoxForValidation(
                SweetAlertDialog.WARNING_TYPE,
                "Validation",
                "Old password is required"
            )
            return false
        }else if (newPassword.isEmpty()) {
            showDialogBoxForValidation(
                SweetAlertDialog.WARNING_TYPE,
                "Validation",
                "New password is required"
            )
            return false
        }else if (confPassword.isEmpty()) {
            showDialogBoxForValidation(
                SweetAlertDialog.WARNING_TYPE,
                "Validation",
                "Confirm password is required"
            )
            return false
        }else{
            if(newPassword != confPassword){
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

    private fun showDialogBoxForValidation(type: Int, title: String, message: String, callback: (() -> Unit)? = null) {
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
}
