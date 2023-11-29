package com.impala.rclsfa.auth

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.content.SharedPreferences
import android.widget.Button
import android.widget.EditText
import com.impala.rclsfa.models.LoginRequest
import com.impala.rclsfa.models.LoginResponse
import com.impala.rclsfa.utils.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.gson.Gson
import com.impala.rclsfa.PermissionActivity
import com.impala.rclsfa.R
import com.impala.rclsfa.utils.SessionManager


class LoginActivity : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var loadingDialog: Dialog
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usernameEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)
        sessionManager = SessionManager(this)

        // Initialize the loading dialog
        loadingDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
            .setTitleText("Loading")

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (validateInput(username, password)) {
                // Call the login function here
                performLogin(username, password)
            }
        }
    }

    private fun validateInput(username: String, password: String): Boolean {
        if (username.isEmpty()) {
//            usernameEditText.error = "Username is required"
            showDialogBox(SweetAlertDialog.WARNING_TYPE, "Validation", "Username is required")
            return false
        }

        if (password.isEmpty()) {
//            passwordEditText.error = "Password is required"
            showDialogBox(SweetAlertDialog.WARNING_TYPE, "Validation", "Password is required")
            return false
        }

        return true
    }

    private fun performLogin(username: String, password: String) {
        // Create a Retrofit API service (assuming you have Retrofit set up)
        val apiService = ApiService.CreateApi1()

        // Create a login request
        val loginRequest = LoginRequest(username, password)

        showLoadingDialog()
        // Make the API call
        apiService.login(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    // Handle successful login response
                    val loginResponse = response.body()
                    if (loginResponse != null) {
                        if(loginResponse.success){
                            dismissLoadingDialog()
                            // Save login information (e.g., in SharedPreferences)
                            saveLoginInfoToSharedPreferences(loginResponse)

                            // Navigate to MainActivity
                            val intent = Intent(this@LoginActivity, PermissionActivity::class.java)
                            startActivity(intent)
                            finish()
                        }else{
                            dismissLoadingDialog()
                            showDialogBox(SweetAlertDialog.WARNING_TYPE, "Waring-SF5800", loginResponse.message)
                        }
                    }else{
                        dismissLoadingDialog()
                        showDialogBox(SweetAlertDialog.ERROR_TYPE, "Error-RN5800", "Response NULL value. Try later")
                    }
                }else{
                    dismissLoadingDialog()
                    showDialogBox(SweetAlertDialog.ERROR_TYPE, "Error-RR5800", "Response failed. Try later")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                dismissLoadingDialog()
                showDialogBox(SweetAlertDialog.ERROR_TYPE, "Error-NF5800", "Network error")
            }
        })
    }

    private fun saveLoginInfoToSharedPreferences(result: LoginResponse) {
        val sharedPreferences: SharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        // Save properties from the Result data class to SharedPreferences
        editor.putString("id", result.result.id)
        editor.putString("name", result.result.name)
        editor.putString("mobile_number", result.result.mobile_number)
        editor.putInt("designation_id", result.result.designation_id)
        editor.putString("designation", result.result.designation_details.designation_name)
        editor.putString("password", result.result.password)
        editor.putBoolean("isLoggedIn", true)
        val gson = Gson()
        val jsonStringUserRole = gson.toJson(result.user_roles)
        editor.putString("user_roles", jsonStringUserRole)

        sessionManager.userId = result.result.id
        sessionManager.userName = result.result.name
        sessionManager.designationId = result.result.designation_id
        sessionManager.designationName = result.result.designation_details.designation_name
        sessionManager.userRoles = jsonStringUserRole
        // Add other properties as needed
        //remove save data
        sessionManager.routeName=""
        sessionManager.districtName=""
        sessionManager.divName=""
        sessionManager.categoryName=""
        sessionManager.divId=-1
        sessionManager.districtId=-1
        sessionManager.upzId=-1

        // Apply changes
        editor.apply()
    }

    private fun showDialogBox(type: Int, title: String, message: String, callback: (() -> Unit)? = null) {
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
