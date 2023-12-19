package com.impala.rclsfa.tgt_setup.route_wise_tgt_setup

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.impala.rclsfa.databinding.ActivityRouteListByTgtapproveBinding
import com.impala.rclsfa.models.RouteWiseTargetModel
import com.impala.rclsfa.models.RouteWiseTargetModelResult
import com.impala.rclsfa.tgt_setup.route_wise_tgt_setup.model.UpdateResponse
import com.impala.rclsfa.utils.ApiService
import com.impala.rclsfa.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RouteListByTGTApproveActivity : AppCompatActivity(),
    RouteListByTGTApproveAdapter.MainClickManage {
    private lateinit var binding: ActivityRouteListByTgtapproveBinding
    lateinit var adapter: RouteListByTGTApproveAdapter
    private lateinit var loadingDialog: Dialog
    private lateinit var sessionManager: SessionManager
    var srId = ""
    var targetAmount = 0
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    var id = ""
    var mUserId = ""
    var mRouteId = ""
    var designationId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRouteListByTgtapproveBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }

        initView()
    }

    private fun initView() {
        loadingDialog =
            SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE).setTitleText("Loading")
        srId = this.intent.getStringExtra("user_id")!!
        designationId = this.intent.getIntExtra("designation_id", 0)!!

        bottomSheetBehavior = BottomSheetBehavior.from(binding.incB.bottomSheet)


        sessionManager = SessionManager(this)
        adapter = RouteListByTGTApproveAdapter(
            this,
            this
        )


        val linearLayoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = linearLayoutManager
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)

        showLoadingDialog()
        routeListBySr(srId, designationId.toString())

        //#3 Listening to State Changes of BottomSheet
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
//                buttonBottomSheetPersistent.text = when (newState) {
//                    BottomSheetBehavior.STATE_EXPANDED -> "Close Persistent Bottom Sheet"
//                    BottomSheetBehavior.STATE_COLLAPSED -> "Open Persistent Bottom Sheet"
//                    else -> "Persistent Bottom Sheet"
//                }
            }
        })


        binding.incB.imageClose.setOnClickListener {
            bottomSheetBehavior.isHideable = true
            bottomSheetBehavior.peekHeight = 0
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }

        binding.incB.btnUpdate.setOnClickListener {
            val amount = binding.incB.edtAmount.editText!!.text.toString()
            if (amount.isEmpty()) {
                showDialogBox(
                    SweetAlertDialog.WARNING_TYPE,
                    "Warning",
                    "Enter a amount"
                )
                return@setOnClickListener
            }
            showLoadingDialog()
            updateAmount(
                id,
                mUserId,
                mRouteId,
                amount
            )
        }


    }

    private fun updateAmount(
        id: String,
        userId: String,
        routeId: String,
        route_amount: String
    ) {
        val apiService = ApiService.CreateApi2()
        apiService.updateAmount(
            id,
            userId,
            routeId,
            route_amount
        ).enqueue(object :
            Callback<UpdateResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<UpdateResponse>,
                response: Response<UpdateResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        if (data.success!!) {
                            bottomSheetBehavior.isHideable = true
                            bottomSheetBehavior.peekHeight = 0
                            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                            val message = data.message
                            showDialogForSuccess(
                                SweetAlertDialog.SUCCESS_TYPE,
                                "Success",
                                message
                            )
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

            override fun onFailure(call: Call<UpdateResponse>, t: Throwable) {
                dismissLoadingDialog()
                showDialogBox(SweetAlertDialog.ERROR_TYPE, "Error-NF5801", "Network error")
            }
        })
    }

    private fun giveApprove(
        id: String
    ) {
        val apiService = ApiService.CreateApi2()
        apiService.setApprove(
            id
        ).enqueue(object :
            Callback<UpdateResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<UpdateResponse>,
                response: Response<UpdateResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        if (data.success!!) {
                            bottomSheetBehavior.isHideable = true
                            bottomSheetBehavior.peekHeight = 0
                            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                            val message = data.message
                            showDialogForSuccess(
                                SweetAlertDialog.SUCCESS_TYPE,
                                "Success",
                                message
                            )
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

            override fun onFailure(call: Call<UpdateResponse>, t: Throwable) {
                dismissLoadingDialog()
                showDialogBox(SweetAlertDialog.ERROR_TYPE, "Error-NF5801", "Network error")
            }
        })
    }

    private fun routeListBySr(
        user_code: String,
        designation_id: String
    ) {
        val apiService = ApiService.CreateApi2()
        apiService.routeListBySrTgtApprove(
            user_code,
            designation_id
        ).enqueue(object :
            Callback<RouteWiseTargetModel> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<RouteWiseTargetModel>,
                response: Response<RouteWiseTargetModel>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        if (data.success!!) {
                            val dataList = data.result
                            if (dataList != null) {
                                adapter.clearData()
                                adapter.addData(dataList as MutableList<RouteWiseTargetModelResult>)
                            } else {
                                showDialogBox(
                                    SweetAlertDialog.WARNING_TYPE,
                                    "Error-RN5801",
                                    "Data not found"
                                )
                            }

                            // targetAmount = data.getTargetAmount()!!
                            dismissLoadingDialog()
                            binding.saveTarget.visibility = View.GONE
                        } else {
                            dismissLoadingDialog()
                            showDialogBox(
                                SweetAlertDialog.WARNING_TYPE, "Problem-SF5801",
                                "Failed!!"
                            )
                            binding.saveTarget.visibility = View.GONE
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

            override fun onFailure(call: Call<RouteWiseTargetModel>, t: Throwable) {
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

    private fun showDialogForSuccess(
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
                showLoadingDialog()
                routeListBySr(srId, designationId.toString())
            }
        sweetAlertDialog.show()
    }

    override fun onEditAmount(
        targetAmount: String,
        itemId: String,
        userId: String,
        routeId: String
    ) {
        // bottomSheetBehavior.peekHeight = 400
        id = itemId
        mUserId = userId
        mRouteId = routeId

        binding.incB.edtAmount.editText!!.setText(targetAmount)
        binding.coordinate.visibility = View.VISIBLE
        val state =
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED)
                BottomSheetBehavior.STATE_COLLAPSED
            else
                BottomSheetBehavior.STATE_EXPANDED
        bottomSheetBehavior.state = state
    }

    override fun setApprove(itemId: String) {
        showLoadingDialog()
        giveApprove(itemId)
    }

    override fun doClick(
        id: String,
        contribution: Double,
        achAmount: Double,
        targetAmount: Double,
        userId: String
    ) {
        startActivity(
            Intent(this, RouteListApproveDetailsActivity::class.java)
                .putExtra("route_id", id.toString())
                .putExtra("contribution", contribution.toString())
                .putExtra("route_target", targetAmount.toString())
                .putExtra("user_id", userId)
                .putExtra("designation_id", designationId.toString())
        )
    }


}