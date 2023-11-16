package com.impala.rclsfa.tgt_setup.kro_outlet_selection

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import cn.pedant.SweetAlert.SweetAlertDialog
import com.impala.rclsfa.tgt_setup.kro_outlet_selection.adapter.KroDetailsAdapter
import com.impala.rclsfa.tgt_setup.kro_outlet_selection.model.KroDetailsModel
import com.impala.rclsfa.tgt_setup.kro_outlet_selection.model.KroRouteListM
import com.impala.rclsfa.databinding.ActivityKroDetailsBinding
import com.impala.rclsfa.utils.ApiService
import com.impala.rclsfa.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Path

class KroDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKroDetailsBinding
    lateinit var adapter: KroDetailsAdapter
    private lateinit var loadingDialog: Dialog
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKroDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }

        initView()
    }

    private fun initView() {
        sessionManager = SessionManager(this)
        adapter = KroDetailsAdapter(this)
        loadingDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
            .setTitleText("Loading")
        val srId = sessionManager.userId!!
        val designationId = sessionManager.designationId!!
        val routeId = this.intent.getIntExtra("route_id",0)

        val linearLayoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = linearLayoutManager
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)


    }

    override fun onResume() {
        super.onResume()
        val srId = sessionManager.userId!!
        val designationId = sessionManager.designationId!!
        val routeId = this.intent.getIntExtra("route_id",0)
        showLoadingDialog()
        retailerListBySrRouteKro(srId, routeId.toString(), designationId.toString())
    }
    private fun retailerListBySrRouteKro(
        sr_id: String,
        route_id: String,
        designation_id: String
    ) {
        val apiService = ApiService.CreateApi2()
        apiService.retailerListBySrRouteKro(
            sr_id,
            route_id,
            designation_id
        ).enqueue(object :
            Callback<KroDetailsModel> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<KroDetailsModel>,
                response: Response<KroDetailsModel>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        if (data.getSuccess()!!) {
                            val dataList = data.getResult()
                            adapter.addData(dataList as MutableList<KroDetailsModel.Result>)
                            // targetAmount = data.getTargetAmount()!!
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

            override fun onFailure(call: Call<KroDetailsModel>, t: Throwable) {
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