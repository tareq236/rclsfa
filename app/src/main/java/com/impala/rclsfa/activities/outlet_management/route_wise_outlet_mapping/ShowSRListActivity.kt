package com.impala.rclsfa.activities.outlet_management.route_wise_outlet_mapping

import android.annotation.SuppressLint
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import cn.pedant.SweetAlert.SweetAlertDialog
import com.impala.rclsfa.R
import com.impala.rclsfa.activities.outlet_management.route_wise_outlet_mapping.model.RouteListBySRModel
import com.impala.rclsfa.activities.outlet_management.route_wise_outlet_mapping.model.ZoneListModel
import com.impala.rclsfa.databinding.ActivityRouteWiseOutletMappingBinding
import com.impala.rclsfa.databinding.ActivityShowSrlistBinding
import com.impala.rclsfa.utils.ApiService
import com.impala.rclsfa.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Path

class ShowSRListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShowSrlistBinding
    lateinit var adapter: RouteWiseMappingAdapter
    private lateinit var loadingDialog: Dialog
    private lateinit var sessionManager: SessionManager
    var srId = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowSrlistBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }

        initView()
    }

    private fun initView() {
        adapter = RouteWiseMappingAdapter(this)
        sessionManager = SessionManager(this)
        loadingDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
            .setTitleText("Loading")
        srId = intent.getIntExtra("sr_id", 0)
        val designationId = sessionManager.designationId

        val linearLayoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = linearLayoutManager
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)


        showLoadingDialog()
        routeListBySr(srId.toString(), designationId.toString())
    }

    private fun routeListBySr(
        sr_code: String,
        designation_id: String
    ) {
        val apiService = ApiService.CreateApi2()
        apiService.routeListBySr(
            sr_code,
            designation_id
        ).enqueue(object :
            Callback<RouteListBySRModel> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<RouteListBySRModel>,
                response: Response<RouteListBySRModel>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        if (data.getSuccess()!!) {
                            val dataList = data.getResult()
                            adapter.addData(dataList as MutableList<RouteListBySRModel.Result>)
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

            override fun onFailure(call: Call<RouteListBySRModel>, t: Throwable) {
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