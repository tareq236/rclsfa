package com.impala.rclsfa.tgt_setup.kro_outlet_selection

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import cn.pedant.SweetAlert.SweetAlertDialog
import com.impala.rclsfa.databinding.ActivityEditKroOutletTargetBinding
import com.impala.rclsfa.tgt_setup.kro_outlet_selection.model.SaveKroTargetModel
import com.impala.rclsfa.utils.ApiService
import com.impala.rclsfa.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditKroOutletTargetActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditKroOutletTargetBinding
    private lateinit var loadingDialog: Dialog
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditKroOutletTargetBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }
        initView()
    }
    private fun initView(){
        sessionManager = SessionManager(this)
        loadingDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
            .setTitleText("Loading")

        val nameEn = this.intent.getStringExtra("name_en")
        val nameBn = this.intent.getStringExtra("name_bn")
        val targetAmount = this.intent.getStringExtra("target_amount")
        val id = this.intent.getIntExtra("id",0)
        val retailerID = this.intent.getIntExtra("retailer_id",0)

        binding.txvNameEn.text = nameEn
        if(!nameBn.isNullOrBlank()){
            binding.txvNameBn.text = nameBn
            binding.txvNameBn.visibility = View.VISIBLE
        }
        binding.edtTargetAmount.editText!!.setText(targetAmount)

        binding.updateId.setOnClickListener {
            val targetAmount = binding.edtTargetAmount.editText!!.text.toString()
            if (targetAmount.isEmpty()) {
                showDialogBoxForValidation(
                    SweetAlertDialog.WARNING_TYPE,
                    "Validation",
                    "Target Amount is required"
                )
                return@setOnClickListener
            }

           // updateKroTgtByRetailer(id.toString(),targetAmount)
            if (retailerID != 0) {
                saveKroTgtByRetailer(retailerID.toString(),targetAmount)
            }
        }
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
//    private fun updateKroTgtByRetailer(
//         id: String,
//         tgt: String
//    ) {
//        val apiService = ApiService.CreateApi2()
//        apiService.updateKroTgtByRetailer(
//            id,
//            tgt
//        ).enqueue(object :
//            Callback<UpdateKroTargetM> {
//            @SuppressLint("SetTextI18n")
//            override fun onResponse(
//                call: Call<UpdateKroTargetM>,
//                response: Response<UpdateKroTargetM>
//            ) {
//                if (response.isSuccessful) {
//                    val data = response.body()
//                    if (data != null) {
//                        if (data.getSuccess()!!) {
//                            val msg = data.getMessage()
//                            showFDialogBox(
//                                SweetAlertDialog.SUCCESS_TYPE,
//                                "SUCCESS-S5803",
//                                msg!!
//                            )
//                            dismissLoadingDialog()
//                        } else {
//                            dismissLoadingDialog()
//                            showDialogBox(
//                                SweetAlertDialog.WARNING_TYPE, "Problem-SF5801",
//                                "Failed!!"
//                            )
//                        }
//                    } else {
//                        dismissLoadingDialog()
//                        showDialogBox(
//                            SweetAlertDialog.WARNING_TYPE,
//                            "Error-RN5801",
//                            "Response NULL value. Try later."
//                        )
//                    }
//                } else {
//                    dismissLoadingDialog()
//                    showDialogBox(
//                        SweetAlertDialog.WARNING_TYPE,
//                        "Error-RR5801",
//                        "Response failed. Try later."
//                    )
//                }
//            }
//
//            override fun onFailure(call: Call<UpdateKroTargetM>, t: Throwable) {
//                dismissLoadingDialog()
//                showDialogBox(SweetAlertDialog.ERROR_TYPE, "Error-NF5801", "Network error")
//            }
//        })
//    }

    private fun saveKroTgtByRetailer(
        retailerID: String,
        tgt: String
    ) {
        showLoadingDialog()
        val apiService = ApiService.CreateApi2()
        apiService.saveKroTgtByRetailer(
            retailerID ,
            tgt
        ).enqueue(object :
            Callback<SaveKroTargetModel> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<SaveKroTargetModel>,
                response: Response<SaveKroTargetModel>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        if (data.getSuccess()!!) {
                            val dataList = data.getResult()
                            showFDialogBox(
                                SweetAlertDialog.SUCCESS_TYPE,
                                "SUCCESS-S5803",
                                "Save Target Done  "
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

            override fun onFailure(call: Call<SaveKroTargetModel>, t: Throwable) {
                dismissLoadingDialog()
                showDialogBox(SweetAlertDialog.ERROR_TYPE, "Error-NF5801", "Network error")
            }
        })
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