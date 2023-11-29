package com.impala.rclsfa.tgt_setup.route_wise_tgt_setup

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import cn.pedant.SweetAlert.SweetAlertDialog
import com.impala.rclsfa.tgt_setup.route_wise_tgt_setup.model.RouteListByTgtModel
import com.impala.rclsfa.tgt_setup.route_wise_tgt_setup.model.TgtRouteDetailsM
import com.impala.rclsfa.databinding.ActivityRouteListDetailsBinding
import com.impala.rclsfa.utils.ApiService
import com.impala.rclsfa.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Path

class RouteListDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRouteListDetailsBinding
    lateinit var adapter: RouteDetailsAdapter
    private lateinit var loadingDialog: Dialog
    private lateinit var sessionManager: SessionManager
    var routeId = ""
    var contribution = ""

    var routeTarget = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRouteListDetailsBinding.inflate(layoutInflater)
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
        routeId = this.intent.getStringExtra("route_id")!!
        contribution = this.intent.getStringExtra("contribution")!!
        //retailerSize = this.intent.getStringExtra("retailerSize")!!
        routeTarget = this.intent.getStringExtra("route_target")!!
        adapter = RouteDetailsAdapter(this, contribution, routeTarget)
        val designationId = sessionManager.designationId
        val srId = sessionManager.userId


        val linearLayoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = linearLayoutManager
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)

        showLoadingDialog()
        retailerListBySrRoute(srId!!, routeId, designationId.toString())

    }

    private fun retailerListBySrRoute(
        sr_id: String,
        route_id: String,
        designation_id: String
    ) {
        val apiService = ApiService.CreateApi2()
        apiService.retailerListBySrRoute(
            sr_id,
            route_id,
            designation_id
        ).enqueue(object :
            Callback<TgtRouteDetailsM> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<TgtRouteDetailsM>,
                response: Response<TgtRouteDetailsM>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        if (data.getSuccess()!!) {
                            val dataList = data.getResult()
                            //retailerSize = dataList!!.size
                            adapter.addData(dataList as MutableList<TgtRouteDetailsM.Result>)
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

            override fun onFailure(call: Call<TgtRouteDetailsM>, t: Throwable) {
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