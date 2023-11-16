package com.impala.rclsfa.auth


import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.android.material.card.MaterialCardView
import com.google.gson.Gson
import com.impala.rclsfa.auth.model.ProfileDataModel
import com.impala.rclsfa.databinding.ActivityProfileBinding
import com.impala.rclsfa.models.AppButton
import com.impala.rclsfa.models.UserRoles
import com.impala.rclsfa.utils.ApiService
import com.impala.rclsfa.utils.SessionManager
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var loadingDialog: Dialog
    private lateinit var sessionManager: SessionManager
    private val appButtons : List<AppButton> = listOf(
        AppButton("ProfileUpdate", "profileUpdate"),
        AppButton("ChangePassword", "changePassword"),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }

        userRolesCheck()
        initView()
    }

    private fun initView() {

        sessionManager = SessionManager(this)
        // Initialize the loading dialog
        loadingDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
            .setTitleText("Loading")


        binding.profileUpdate.setOnClickListener {
            startActivity(Intent(this, ProfileUpdateActivity::class.java))
        }

        binding.editPhotoId.setOnClickListener {
            startActivity(Intent(this, ProfilePhotoUploadActivity::class.java))
        }

        binding.profileImage.setOnClickListener {
            startActivity(Intent(this, ProfilePhotoUploadActivity::class.java))
        }


        binding.changePassword.setOnClickListener {
            startActivity(Intent(this, ChangePasswordActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        val userId = sessionManager.userId
        showLoadingDialog()
        userDetails(userId!!)

    }
    private fun userDetails(userId: String) {
        val apiService = ApiService.CreateApi1()
        apiService.getUserDetails(userId).enqueue(object :
            Callback<ProfileDataModel> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<ProfileDataModel>,
                response: Response<ProfileDataModel>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        if (data.getSuccess()!!) {
                            val dataList = data.getResult()

                            val name = dataList!!.userInfo!!.name
                            val designation = dataList.userInfo!!.designation
                            val phoneNumber = dataList.userInfo!!.mobileNumber
                            val mailId = dataList.mailId
                            val nid = dataList.nid
                            val bloodGroup = dataList.bloodGroup
                            val fatherName = dataList.fatherName
                            val fatherNid = dataList.fatherNid
                            val motherName = dataList.motherName
                            val motherNid = dataList.motherNid
                            val userId = dataList.userId

                            binding.nameId.text = name
                            binding.userNameId.text = "$name($userId)"
                            binding.designationId.text = designation
                            binding.phoneNumberId.text = phoneNumber
                            binding.mailId.text = mailId
                            binding.nid.text = nid
                            binding.bloodGroup.text = bloodGroup
                            binding.fatherNameId.text = fatherName
                            binding.fatherNid.text = fatherNid
                            binding.motherNameId.text = motherName
                            binding.motherNid.text = motherNid


                            //alter 1
                            val alterName1 = dataList.alternativeNameOne
                            val alterPhone1 = dataList.alternativePhoneNumOne
                            val alterReligion1 = dataList.alternativeRelationOne

                            binding.alterNameId.text = alterName1
                            binding.alterPhoneId.text = alterPhone1
                            binding.alterRelationId.text = alterReligion1

                            //alter 1
                            val alterName2 = dataList.alternativeNameTwo
                            val alterPhone2 = dataList.alternativePhoneNumTwo
                            val alterReligion2 = dataList.alternativeRelationTwo

                            binding.alter2NameId.text = alterName2
                            binding.alter2PhoneId.text = alterPhone2
                            binding.alter2RelationId.text = alterReligion2

                            dismissLoadingDialog()
                        } else {
                            dismissLoadingDialog()
                            showDialogBox(
                                SweetAlertDialog.WARNING_TYPE, "Problem-SF5801",
                                data.getMessage()!!
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

            override fun onFailure(call: Call<ProfileDataModel>, t: Throwable) {
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

    private fun showLoadingDialog() {
        loadingDialog.setCancelable(false)
        loadingDialog.show()
    }

    private fun dismissLoadingDialog() {
        loadingDialog.dismiss()
    }

    private fun userRolesCheck(){
        sessionManager = SessionManager(this)
        val userRoles = sessionManager.userRoles
        val userRolesList = Gson().fromJson(userRoles, Array<UserRoles>::class.java).toList()
        for (button in appButtons) {
            val buttonDetails = userRolesList.firstOrNull { it.access_name == button.access_name }
            if(buttonDetails != null){
                val buttonView: MaterialCardView = findViewById(resources.getIdentifier(button.buttonIdentifier, "id", packageName))
                buttonView.visibility = View.VISIBLE
            }else{
                val buttonView: MaterialCardView = findViewById(resources.getIdentifier(button.buttonIdentifier, "id", packageName))
                buttonView.visibility = View.GONE
            }
        }
    }

}
