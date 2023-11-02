package com.impala.rclsfa.activities.outlet_management

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cn.pedant.SweetAlert.SweetAlertDialog
import com.impala.rclsfa.databinding.ActivityUpdateLocationPageBinding
import com.impala.rclsfa.utils.ApiService
import com.impala.rclsfa.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Field

class UpdateLocationPageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateLocationPageBinding
    private lateinit var loadingDialog: Dialog
    private lateinit var sessionManager: SessionManager

    var retailerId = 0
    var nameBn = ""
    var retailerName = ""
    var outletAddress = ""

    var latitude = ""
    var longitude = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateLocationPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }

        initView()
    }

    private fun initView() {
        sessionManager = SessionManager(this)
        loadingDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
            .setTitleText("Loading")
        retailerId = intent.getIntExtra("retailer_id", 0)!!
        nameBn = intent.getStringExtra("name_bn")!!
        retailerName = intent.getStringExtra("retailer_name")!!
        outletAddress = intent.getStringExtra("outlet_address")!!
        latitude = intent.getStringExtra("latitude")!!
        longitude = intent.getStringExtra("longitude")!!

        binding.retailerId.text = retailerId.toString()
        binding.outletNameBn.text = nameBn
        binding.retailerName.text = retailerName
        binding.outletAddress.text = outletAddress


        binding.updateLocationId.setOnClickListener {
            showLoadingDialog()
            updateGeoLocation(retailerId.toString(),latitude,longitude)
        }
    }


    private fun updateGeoLocation(
        id: String,
        latitude: String,
        longitude: String
    ) {
        val apiService = ApiService.CreateApi2()
        apiService.updateGeoLocation(
            id,
            latitude,
            longitude
        ).enqueue(object :
            Callback<UpdateLocationModel> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<UpdateLocationModel>,
                response: Response<UpdateLocationModel>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        if (data.getSuccess()!!) {
                            showFDialogBox(
                                SweetAlertDialog.SUCCESS_TYPE,
                                "SUCCESS-S5803",
                                "Geo Location Updated successfully"
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

            override fun onFailure(call: Call<UpdateLocationModel>, t: Throwable) {
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