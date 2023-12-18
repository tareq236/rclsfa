package com.impala.rclsfa.tgt_setup.route_wise_tgt_setup

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import cn.pedant.SweetAlert.SweetAlertDialog
import com.impala.rclsfa.databinding.ActivityAssignUserListBinding
import com.impala.rclsfa.models.UsersModel
import com.impala.rclsfa.models.UsersModelResult
import com.impala.rclsfa.utils.ApiService
import com.impala.rclsfa.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AssignUserListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAssignUserListBinding
    private lateinit var loadingDialog: Dialog
    private lateinit var sessionManager: SessionManager
    lateinit var adapter: UserAssignAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAssignUserListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }

        initView()
    }
    private fun initView() {
        sessionManager = SessionManager(this)
        adapter= UserAssignAdapter(this)

        loadingDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE).setTitleText("Loading")
        val srId = sessionManager.userId!!
        val designationId = sessionManager.designationId

        val linearLayoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = linearLayoutManager
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)


        showLoadingDialog()
        getAssignUserList(srId, designationId.toString())


    }
    private fun getAssignUserList(
        user_code: String,
        designation_id: String
    ) {
        val apiService = ApiService.CreateApi2()
        apiService.soList(
            user_code,
            designation_id
        ).enqueue(object :
            Callback<UsersModel> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<UsersModel>,
                response: Response<UsersModel>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        if (data.success!!) {
                            val dataList = data.result
                            adapter.addData(dataList as MutableList<UsersModelResult>)
                            // targetAmount = data.getTargetAmount()!!
                            dismissLoadingDialog()

                        }else{
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

            override fun onFailure(call: Call<UsersModel>, t: Throwable) {
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

}