package com.impala.rclsfa.activities.tgt_setup.route_wise_tgt_setup

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import cn.pedant.SweetAlert.SweetAlertDialog

import com.impala.rclsfa.activities.tgt_setup.route_wise_tgt_setup.model.RouteListByTgtModel

import com.impala.rclsfa.databinding.ActivityRouteListBinding
import com.impala.rclsfa.utils.ApiService
import com.impala.rclsfa.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RouteListActivity : AppCompatActivity(), RouteListByTGTAdapter.MainClickManage {
    private lateinit var binding: ActivityRouteListBinding
    lateinit var adapter: RouteListByTGTAdapter
    private lateinit var loadingDialog: Dialog
    private lateinit var sessionManager: SessionManager
    var srId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRouteListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }

        initView()
    }

    private fun initView() {
        sessionManager = SessionManager(this)
        adapter = RouteListByTGTAdapter(
            this,
            sessionManager,
            this
        )

        loadingDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
            .setTitleText("Loading")
        srId = sessionManager.userId!!
        val designationId = sessionManager.designationId

        val linearLayoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = linearLayoutManager
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)

        binding.searchId.setOnClickListener {
            val targetAmount = binding.edtSearch.text.toString()
            if (targetAmount.isEmpty()) {
                showDialogBoxForValidation(
                    SweetAlertDialog.WARNING_TYPE,
                    "Validation",
                    "Route Target is required"
                )
                return@setOnClickListener
            }

            sessionManager.targetAmount = targetAmount
            showLoadingDialog()
            routeListBySr(srId, designationId.toString())
        }


    }


    private fun routeListBySr(
        user_code: String,
        designation_id: String
    ) {
        val apiService = ApiService.CreateApi2()
        apiService.routeListBySrTgt(
            user_code,
            designation_id
        ).enqueue(object :
            Callback<RouteListByTgtModel> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<RouteListByTgtModel>,
                response: Response<RouteListByTgtModel>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        if (data.getSuccess()!!) {
                            val dataList = data.getResult()
                            adapter.addData(dataList as MutableList<RouteListByTgtModel.Result>)
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

            override fun onFailure(call: Call<RouteListByTgtModel>, t: Throwable) {
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

    override fun doClick(
        id: String,
        contribution: String,
        retailerSize: String,
        achAmount: String
    ) {
        startActivity(
            Intent(this, RouteListDetailsActivity::class.java)
                .putExtra("route_id", id)
                .putExtra("contribution", contribution)
                .putExtra("retailerSize", retailerSize)
                .putExtra("route_target", binding.edtSearch.text.toString())
        )
    }

}