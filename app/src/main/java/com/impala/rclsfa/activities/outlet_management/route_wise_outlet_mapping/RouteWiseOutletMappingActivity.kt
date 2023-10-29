package com.impala.rclsfa.activities.outlet_management.route_wise_outlet_mapping

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import cn.pedant.SweetAlert.SweetAlertDialog
import com.impala.rclsfa.R
import com.impala.rclsfa.activities.outlet_management.route_wise_outlet_mapping.model.DivisionRouteModel
import com.impala.rclsfa.activities.outlet_management.route_wise_outlet_mapping.model.ZoneListModel
import com.impala.rclsfa.databinding.ActivityRouteWiseOutletMappingBinding
import com.impala.rclsfa.utils.ApiService
import com.impala.rclsfa.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RouteWiseOutletMappingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRouteWiseOutletMappingBinding

    private lateinit var loadingDialog: Dialog
    private lateinit var sessionManager: SessionManager
    lateinit var adapter: RouteWiseMappingAdapter
    var dsmCode = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRouteWiseOutletMappingBinding.inflate(layoutInflater)
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

        val linearLayoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = linearLayoutManager
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)

        binding.spinnerDiv.prompt = "Select an Option";
        binding.spinnerZone.prompt = "Select an Option";
        binding.spinnerDistributor.prompt = "Select an Option";
        binding.spinnerSO.prompt = "Select an Option";

//        binding.spinnerDiv.onItemClickListener =
//            AdapterView.OnItemClickListener { parent, arg1, pos, id ->
//                val item = parent.getItemAtPosition(pos) as DivisionRouteModel.Result
//                dsmCode = item.dsmCode!!
//
//                showLoadingDialog()
//                zoneListByRoute(dsmCode,"3")
//            }

        binding.spinnerDiv.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val item = parent!!.getItemAtPosition(position) as DivisionRouteModel.Result
                dsmCode = item.dsmCode!!

                showLoadingDialog()
                zoneListByRoute(dsmCode,"3")
            }

        }

        val designationId = sessionManager.designationId
        showLoadingDialog()
        divisionListByRoute("",designationId!!.toString())
    }

    private fun divisionListByRoute(user_id : String,
                                    designation_id : String) {
        val apiService = ApiService.CreateApi2()
        apiService.divisionListByRoute(
              user_id  ,
              designation_id
        ).enqueue(object :
            Callback<DivisionRouteModel> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<DivisionRouteModel>,
                response: Response<DivisionRouteModel>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        if (data.getSuccess()!!) {
                           val dataList = data.getResult()
                            val adapter: ArrayAdapter<DivisionRouteModel.Result> =
                                ArrayAdapter<DivisionRouteModel.Result>(
                                    this@RouteWiseOutletMappingActivity ,
                                    R.layout.spinner_item_text,
                                    dataList as MutableList<DivisionRouteModel.Result>
                                )
                            binding.spinnerDiv.adapter = adapter
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

            override fun onFailure(call: Call<DivisionRouteModel>, t: Throwable) {
                dismissLoadingDialog()
                showDialogBox(SweetAlertDialog.ERROR_TYPE, "Error-NF5801", "Network error")
            }
        })
    }


    private fun zoneListByRoute(user_id : String,
                                    designation_id : String) {
        val apiService = ApiService.CreateApi2()
        apiService.zoneListByRoute(
            user_id  ,
            designation_id
        ).enqueue(object :
            Callback<ZoneListModel> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<ZoneListModel>,
                response: Response<ZoneListModel>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        if (data.getSuccess()!!) {
                            val dataList = data.getResult()
                            val adapter: ArrayAdapter<ZoneListModel.Result> =
                                ArrayAdapter<ZoneListModel.Result>(
                                    this@RouteWiseOutletMappingActivity ,
                                    R.layout.spinner_item_text,
                                    dataList as MutableList<ZoneListModel.Result>
                                )
                            binding.spinnerZone.adapter = adapter
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

            override fun onFailure(call: Call<ZoneListModel>, t: Throwable) {
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