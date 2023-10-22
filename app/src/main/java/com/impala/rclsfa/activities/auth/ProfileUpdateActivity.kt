package com.impala.rclsfa.activities.auth

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cn.pedant.SweetAlert.SweetAlertDialog

import com.impala.rclsfa.activities.auth.model.ProfileDataModel
import com.impala.rclsfa.activities.auth.model.ProfileUpdateModel

import com.impala.rclsfa.databinding.ActivityProfileUpdateBinding
import com.impala.rclsfa.utils.ApiService
import com.impala.rclsfa.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Field

class ProfileUpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileUpdateBinding
    private lateinit var loadingDialog: Dialog
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }

        initView()
    }

    private fun initView() {
        sessionManager = SessionManager(this)
        // Initialize the loading dialog
        loadingDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
            .setTitleText("Loading")


        val userId = sessionManager.userId
        showLoadingDialog()
        userDetails(userId!!)

        binding.profileUpdate.setOnClickListener {
            val mailId = binding.edtMailId.editText!!.text.toString()
            val bloodGroup = binding.edtMailId.editText!!.text.toString()
            val nid = binding.edtNid.editText!!.text.toString()
            val phoneNumber = binding.edtPhone.editText!!.text.toString()
            val fatherName = binding.edtFatherName.editText!!.text.toString()
            val fatherNid = binding.edtFatherNid.editText!!.text.toString()
            val motherName = binding.edtMotherName.editText!!.text.toString()
            val motherNid = binding.edtMotherNid.editText!!.text.toString()
            //alter 1 name
            val alterName1 = binding.edtAlter1Name.editText!!.text.toString()
            val alterPhone1 = binding.edtAlter1Phone.editText!!.text.toString()
            val alterReligion1 = binding.edtAlter1Religion.editText!!.text.toString()
            //alter 2 name
            val alterName2 = binding.edtAlter2Name.editText!!.text.toString()
            val alterPhone2 = binding.edtAlter2Phone.editText!!.text.toString()
            val alterReligion2 = binding.edtAlter2Religion.editText!!.text.toString()

            if(validateInput(mailId,bloodGroup,nid,phoneNumber)){
                showLoadingDialog()
                profileUpdate(
                    userId,
                    mailId,
                    bloodGroup,
                    nid,
                    phoneNumber,
                    alterPhone1,
                    alterName1,
                    alterReligion1,
                    alterPhone2,
                    alterName2,
                    alterReligion2,
                    fatherName,
                    fatherNid,
                    motherName,
                    motherNid
                )
            }

        }

    }
    private fun validateInput(mailId: String, bloodGroup: String, nid: String,phoneNumber:String): Boolean {
        if (mailId.isEmpty()) {
//            usernameEditText.error = "Username is required"
            showDialogBoxForValidation(SweetAlertDialog.WARNING_TYPE, "Validation", "Mail Id is required")
            return false
        }

        if (bloodGroup.isEmpty()) {
//            passwordEditText.error = "Password is required"
            showDialogBoxForValidation(SweetAlertDialog.WARNING_TYPE, "Validation", "Blood Group is required")
            return false
        }
        if (nid.isEmpty()) {
//            passwordEditText.error = "Password is required"
            showDialogBoxForValidation(SweetAlertDialog.WARNING_TYPE, "Validation", "NID is required")
            return false
        }
        if (phoneNumber.isEmpty()) {
//            passwordEditText.error = "Password is required"
            showDialogBoxForValidation(SweetAlertDialog.WARNING_TYPE, "Validation", "Phone Number is required")
            return false
        }
        return true
    }

    private fun profileUpdate(
        userId: String,
        mail_id: String,
        blood_group: String,
        nid: String,
        phone_number: String,
        alternative_phone_num_one: String,
        alternative_name_one: String,
        alternative_relation_one: String,
        alternative_phone_num_two: String,
        alternative_name_two: String,
        alternative_relation_two: String,
        father_name: String,
        father_n_id: String,
        mother_name: String,
        mother_n_id: String
    ) {
        val apiService = ApiService.CreateApi2()
        apiService.updateProfile(
            userId,
            mail_id,
            blood_group,
            nid,
            phone_number,
            alternative_phone_num_one,
            alternative_name_one,
            alternative_relation_one,
            alternative_phone_num_two,
            alternative_name_two,
            alternative_relation_two,
            father_name,
            father_n_id,
            mother_name,
            mother_n_id
        ).enqueue(object :
            Callback<ProfileUpdateModel> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<ProfileUpdateModel>,
                response: Response<ProfileUpdateModel>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        if (data.getSuccess()!!) {
                            val dataList = data.getResult()
                            showDialogBox(
                                SweetAlertDialog.SUCCESS_TYPE,
                                "SUCCESS-S5803",
                                "Profile Updated Successfully "
                            )
                            dismissLoadingDialog()
                        } else {
                            dismissLoadingDialog()
                            showDialogBox(
                                SweetAlertDialog.WARNING_TYPE, "Problem-SF5801",
                                "Profile Updated Successfully Failed"
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

            override fun onFailure(call: Call<ProfileUpdateModel>, t: Throwable) {
                dismissLoadingDialog()
                showDialogBox(SweetAlertDialog.ERROR_TYPE, "Error-NF5801", "Network error")
            }
        })
    }

    private fun userDetails(userId: String) {
        val apiService = ApiService.CreateApi1()
        apiService.getUserDetails(userId!!).enqueue(object :
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
                            //binding.edtName.editText!!.setText(name)
                            val designation = dataList.userInfo!!.designation
                            val phoneNumber = dataList.userInfo!!.mobileNumber
                            binding.edtPhone.editText!!.setText(phoneNumber)
                            val mailId = dataList.mailId
                            binding.edtMailId.editText!!.setText(mailId)
                            val nid = dataList.nid
                            binding.edtNid.editText!!.setText(nid)
                            val bloodGroup = dataList.bloodGroup
                            binding.actvBloodG.setText(bloodGroup)
                            val fatherName = dataList.fatherName
                            binding.edtFatherName.editText!!.setText(fatherName)
                            val fatherNid = dataList.fatherNid
                            binding.edtFatherNid.editText!!.setText(fatherNid)
                            val motherName = dataList.motherName
                            binding.edtMotherName.editText!!.setText(motherName)
                            val motherNid = dataList.motherNid
                            binding.edtMotherNid.editText!!.setText(motherNid)
                            val userId = dataList.userId


                            //alter 1
                            val alterName1 = dataList.alternativeNameOne
                            binding.edtAlter1Name.editText!!.setText(alterName1)
                            val alterPhone1 = dataList.alternativePhoneNumOne
                            binding.edtAlter1Phone.editText!!.setText(alterPhone1)
                            val alterReligion1 = dataList.alternativeRelationOne
                            binding.edtAlter1Religion.editText!!.setText(alterReligion1)

                            //alter 1
                            val alterName2 = dataList.alternativeNameTwo
                            binding.edtAlter2Name.editText!!.setText(alterName2)
                            val alterPhone2 = dataList.alternativePhoneNumTwo
                            binding.edtAlter2Phone.editText!!.setText(alterPhone2)
                            val alterReligion2 = dataList.alternativeRelationTwo
                            binding.edtAlter2Religion.editText!!.setText(alterReligion2)

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

                finish()
            }
        sweetAlertDialog.show()
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
}