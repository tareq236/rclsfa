package com.impala.rclsfa.order

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.print.PrintAttributes
import android.print.PrintJob
import android.print.PrintManager
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.impala.rclsfa.databinding.ActivityOrderListBinding
import com.impala.rclsfa.order.model.OrderListModel
import com.impala.rclsfa.utils.ApiService
import com.impala.rclsfa.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class OrderListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderListBinding
    var cal = Calendar.getInstance()
    private lateinit var loadingDialog: Dialog
    private lateinit var sessionManager: SessionManager
    var userCode = ""
    lateinit var adapter: OrderListAdapter
    private lateinit var printJob: PrintJob
    private var printBtnPressed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }

        initView()
    }

    fun initView() {
        adapter = OrderListAdapter(this)
        loadingDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
            .setTitleText("Loading")
        sessionManager = SessionManager(this)
        userCode = sessionManager.userId!!

        val linearLayoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = linearLayoutManager
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)


        val dateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val myFormat = "yyyy-MM-dd" // mention the format you need
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                binding.selectDate.text = sdf.format(cal.time)

                // val setOrderJson = setOrderListJson("30031",sdf.format(cal.time),0,50)
                val setOrderJson = setOrderListJson(userCode, sdf.format(cal.time), 0, 50)
                val jsonParser = JsonParser()
                val gsonObject = jsonParser.parse(setOrderJson) as JsonObject
                srOrderList(gsonObject)
            }

        binding.selectDate.setOnClickListener {
            DatePickerDialog(
                this,
                dateSetListener,
                // set DatePickerDialog to point to today's date when it loads up
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()


        }

        binding.printAllId.setOnClickListener {
            startActivity(Intent(this, PrintAllActivity::class.java))
        }
    }

    private fun srOrderList(
        body: JsonObject?
    ) {
        val apiService = ApiService.CreateApi1()
        apiService.srOrderList(
            body
        ).enqueue(object :
            Callback<OrderListModel> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<OrderListModel>,
                response: Response<OrderListModel>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        if (data.getSuccess()!!) {
                            val dataList = data.getResult()
                            adapter.addData(dataList as MutableList<OrderListModel.Result>)
                            dismissLoadingDialog()
                        } else {
                            dismissLoadingDialog()
                            showDialogBox(
                                SweetAlertDialog.WARNING_TYPE, "Problem-SF5801",
                                " Failed"
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

            override fun onFailure(call: Call<OrderListModel>, t: Throwable) {
                dismissLoadingDialog()
                showDialogBox(SweetAlertDialog.ERROR_TYPE, "Error-NF5801", "Network error")
            }
        })
    }

    private fun setOrderListJson(
        userCode: String,
        fromDate: String,
        offset: Int,
        limit: Int
    ): String {
        val model = Model()
        model.user_code = userCode
        model.from_date = fromDate
        model.offset = offset
        model.limit = limit
        val json = Gson().toJson(model)
        return json
    }


    class Model {
        var user_code: String? = null
        var from_date: String? = null
        var offset: Int? = null
        var limit: Int? = null

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

    @SuppressLint("ServiceCast")
    fun printWebPage(webView: WebView) {
        // on below line we are initializing
        // print button pressed variable to true.
        printBtnPressed = true
        // on below line we are initializing
        // our print manager variable.
        val printManager =
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                this.getSystemService(Context.PRINT_SERVICE) as PrintManager
            } else {
                TODO("VERSION.SDK_INT < KITKAT")
            }

        // on below line we are creating a variable for job name
        val jobName = " webpage" + webView.url
        // on below line we are initializing our print adapter.
        val printAdapter =
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                // on below line we are creating
                // our print document adapter.
                webView.createPrintDocumentAdapter(jobName)
            } else {
                TODO("VERSION.SDK_INT < LOLLIPOP")
            }
        // on below line we are checking id
        // print manager is not null
        assert(printManager != null)

        // on below line we are initializing
        // our print job with print manager
        printJob = printManager.print(
            jobName, printAdapter,
            // on below line we are calling
            // build method for print attributes.
            PrintAttributes.Builder().build()
        )
    }
}