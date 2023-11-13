package com.impala.rclsfa.activities.outlet_management

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import cn.pedant.SweetAlert.SweetAlertDialog
import com.impala.rclsfa.databinding.ActivityOutletSearchingBinding
import com.impala.rclsfa.utils.ApiService
import com.impala.rclsfa.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OutletSearchingActivity : AppCompatActivity(),OutletListAdapter.MainClickManage {
    private lateinit var binding: ActivityOutletSearchingBinding
    private lateinit var loadingDialog: Dialog
    private lateinit var sessionManager: SessionManager
    lateinit var adapter: OutletListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOutletSearchingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }

        initView()
    }
    private fun initView() {
        adapter = OutletListAdapter(this,this)
        sessionManager = SessionManager(this)
        val userId = sessionManager.userId
        val designationId = sessionManager.designationId
        // Initialize the loading dialog
        loadingDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
            .setTitleText("Loading")

        val linearLayoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = linearLayoutManager
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)


        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s.toString().isNotEmpty()){
                    searchOutletByName(userId!!,designationId.toString(),s.toString())
                }else{
                    adapter.clearData()
                    adapter.notifyDataSetChanged()
                }

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

    }

    private fun searchOutletByName(
        srId: String,
        designation_id: String,
        retailer_name: String
    ) {
        val apiService = ApiService.CreateApi2()
        apiService.searchOutletByName(
            srId,
            designation_id,
            retailer_name
        ).enqueue(object :
            Callback<SearchOutletListModel> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<SearchOutletListModel>,
                response: Response<SearchOutletListModel>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        if (data.getSuccess()!!) {
                            val dataList = data.getResult()
                            adapter.clearData()
                            adapter.addData(dataList as MutableList<SearchOutletListModel.Result>)
                            dismissLoadingDialog()
                        } else {
                            dismissLoadingDialog()
                            showDialogBox(SweetAlertDialog.WARNING_TYPE, "Problem-SF5801", data.getMessage().toString())
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

            override fun onFailure(call: Call<SearchOutletListModel>, t: Throwable) {
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

    override fun details(
        retailerName: String,
        nameBn: String,
        proprietorName: String,
        mobileNumber: String,
        address: String,
        birthDay: String,
        marriageDate: String,
        nid: String,
        fChildName: String,
        fChildBirthDay: String,
        sChildName: String,
        sChildBirthDay: String
    ) {

        startActivity(Intent(this,OutletDetailsActivity::class.java)
            .putExtra("retailerName",retailerName)
            .putExtra("nameBn",nameBn)
            .putExtra("proprietorName",proprietorName)
            .putExtra("mobileNumber",mobileNumber)
            .putExtra("address",address)
            .putExtra("birthDay",birthDay)
            .putExtra("marriageDate",marriageDate)
            .putExtra("nid",nid)
            .putExtra("fChildName",fChildName)
            .putExtra("fChildBirthDay",fChildBirthDay)
            .putExtra("sChildName",sChildName)
            .putExtra("sChildBirthDay",sChildBirthDay)
        )

    }

}
