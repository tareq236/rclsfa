package com.impala.rclsfa.outletManagement.route_wise_outlet_mapping

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import cn.pedant.SweetAlert.SweetAlertDialog
import com.impala.rclsfa.R
import com.impala.rclsfa.outletManagement.route_wise_outlet_mapping.model.DistributorListModel
import com.impala.rclsfa.outletManagement.route_wise_outlet_mapping.model.DivisionRouteModel
import com.impala.rclsfa.outletManagement.route_wise_outlet_mapping.model.SoListModel
import com.impala.rclsfa.outletManagement.route_wise_outlet_mapping.model.ZoneListModel
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

    //  lateinit var adapter: RouteWiseMappingAdapter
    var dsmCode = ""
    var tsmCode = ""
    var distbId = -1
    var srId = -1
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
        //  adapter = RouteWiseMappingAdapter(this)
        sessionManager = SessionManager(this)
        loadingDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
            .setTitleText("Loading")

        binding.spinnerDiv.onItemClickListener =
            AdapterView.OnItemClickListener { parent, arg1, pos, id ->
                val item = parent.getItemAtPosition(pos) as DivisionRouteModel.Result
                dsmCode = item.dsmCode!!
                binding.linZoneId.visibility = View.VISIBLE
                showLoadingDialog()
                zoneListByRoute(dsmCode, "3")
            }

        binding.spinnerZone.onItemClickListener =
            AdapterView.OnItemClickListener { parent, arg1, pos, id ->
                val item = parent.getItemAtPosition(pos) as ZoneListModel.Result
                tsmCode = item.tsmCode!!
                binding.linDistriId.visibility = View.VISIBLE
                showLoadingDialog()
                distributorListByRoute(tsmCode)
            }

        binding.spinnerDistributor.onItemClickListener =
            AdapterView.OnItemClickListener { parent, arg1, pos, id ->
                val item = parent.getItemAtPosition(pos) as DistributorListModel.Result
                distbId = item.id!!
                binding.linSoId.visibility = View.VISIBLE
                showLoadingDialog()
                soListByRoute(distbId.toString())
            }

        binding.spinnerSo.onItemClickListener =
            AdapterView.OnItemClickListener { parent, arg1, pos, id ->
                val item = parent.getItemAtPosition(pos) as SoListModel.Result
                srId = item.id!!

            }


        val designationId = sessionManager.designationId
        showLoadingDialog()
        divisionListByRoute("", designationId!!.toString())

        binding.searchId.setOnClickListener {
            startActivity(Intent(this, ShowSRListActivity::class.java)
                .putExtra("sr_id",srId)
            )
        }
    }

    private fun divisionListByRoute(
        user_id: String,
        designation_id: String
    ) {
        val apiService = ApiService.CreateApi2()
        apiService.divisionListByRoute(
            user_id,
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
                            val adapterD: ArrayAdapter<DivisionRouteModel.Result> =
                                ArrayAdapter<DivisionRouteModel.Result>(
                                    this@RouteWiseOutletMappingActivity,
                                    R.layout.spinner_item_text,
                                    dataList as MutableList<DivisionRouteModel.Result>
                                )
                            binding.spinnerDiv.setAdapter(adapterD)
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


    private fun zoneListByRoute(
        user_id: String,
        designation_id: String
    ) {
        val apiService = ApiService.CreateApi2()
        apiService.zoneListByRoute(
            user_id,
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
                                    this@RouteWiseOutletMappingActivity,
                                    R.layout.spinner_item_text,
                                    dataList as MutableList<ZoneListModel.Result>
                                )
                            binding.spinnerZone.setAdapter(adapter)
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

    private fun distributorListByRoute(tsm_code: String) {
        val apiService = ApiService.CreateApi2()
        apiService.distributorListByRoute(
            tsm_code
        ).enqueue(object :
            Callback<DistributorListModel> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<DistributorListModel>,
                response: Response<DistributorListModel>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        if (data.getSuccess()!!) {
                            val dataList = data.getResult()
                            val adapter: ArrayAdapter<DistributorListModel.Result> =
                                ArrayAdapter<DistributorListModel.Result>(
                                    this@RouteWiseOutletMappingActivity,
                                    R.layout.spinner_item_text,
                                    dataList as MutableList<DistributorListModel.Result>
                                )
                            binding.spinnerDistributor.setAdapter(adapter)
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

            override fun onFailure(call: Call<DistributorListModel>, t: Throwable) {
                dismissLoadingDialog()
                showDialogBox(SweetAlertDialog.ERROR_TYPE, "Error-NF5801", "Network error")
            }
        })
    }

    private fun soListByRoute(distributor_id: String) {
        val apiService = ApiService.CreateApi2()
        apiService.soListListByRoute(
            distributor_id
        ).enqueue(object :
            Callback<SoListModel> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<SoListModel>,
                response: Response<SoListModel>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        if (data.getSuccess()!!) {
                            val dataList = data.getResult()
                            val adapter: ArrayAdapter<SoListModel.Result> =
                                ArrayAdapter<SoListModel.Result>(
                                    this@RouteWiseOutletMappingActivity,
                                    R.layout.spinner_item_text,
                                    dataList as MutableList<SoListModel.Result>
                                )
                            binding.spinnerSo.setAdapter(adapter)
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

            override fun onFailure(call: Call<SoListModel>, t: Throwable) {
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
