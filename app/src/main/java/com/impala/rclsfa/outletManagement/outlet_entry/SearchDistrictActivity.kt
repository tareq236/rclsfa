package com.impala.rclsfa.outletManagement.outlet_entry

import android.annotation.SuppressLint
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.recyclerview.widget.LinearLayoutManager
import cn.pedant.SweetAlert.SweetAlertDialog
import com.impala.rclsfa.outletManagement.outlet_entry.adapter.DistrictAdapter
import com.impala.rclsfa.outletManagement.outlet_entry.model.DistrictModel
import com.impala.rclsfa.databinding.ActivitySearchDistrictBinding
import com.impala.rclsfa.utils.ApiService
import com.impala.rclsfa.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale

class SearchDistrictActivity : AppCompatActivity(), DistrictAdapter.IClickManage {
    private lateinit var binding: ActivitySearchDistrictBinding
    private lateinit var loadingDialog: Dialog
    private lateinit var sessionManager: SessionManager
    lateinit var adapter: DistrictAdapter
    lateinit var dataList: MutableList<DistrictModel.Result>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchDistrictBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }

        initView()
    }

    private fun initView() {
        adapter = DistrictAdapter(this, this)
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

        val divisionId = sessionManager.divId
        showLoadingDialog()
        districtListByDivisionId(divisionId.toString())



        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

                if (s.toString().isNotEmpty()) {
                    filter(s.toString())
                }else {
                    showLoadingDialog()
                    adapter.clearData()
                    districtListByDivisionId(divisionId.toString())
                }

            }

            override fun afterTextChanged(s: Editable) {}
        })
    }
    fun filter(text: String) {

        val filteredList: ArrayList<DistrictModel.Result> = ArrayList()

        //val courseAry : ArrayList<Course> = Helper.Companion.getVersionsList()

        for (eachItem in dataList) {
            if (eachItem.name!!.toUpperCase(Locale.getDefault()).contains(text.toUpperCase()) || eachItem.name!!.toUpperCase(
                    Locale.getDefault()).contains(text.toLowerCase())) {
                filteredList.add(eachItem)
            }
        }

        //calling a method of the adapter class and passing the filtered list
        adapter.filterList(filteredList);
    }
    private fun districtListByDivisionId(divId : String) {
        val apiService = ApiService.CreateApi2()
        apiService.districtList(divId).enqueue(object :
            Callback<DistrictModel> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<DistrictModel>,
                response: Response<DistrictModel>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        if (data.getSuccess()!!) {
                            dataList = data.getResult() as MutableList<DistrictModel.Result>
                            adapter.addData(dataList as MutableList<DistrictModel.Result>)
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

            override fun onFailure(call: Call<DistrictModel>, t: Throwable) {
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

    override fun doClick(distId: Int,distName:String) {
        sessionManager.districtId=distId
        sessionManager.districtName=distName
        finish()
    }
}