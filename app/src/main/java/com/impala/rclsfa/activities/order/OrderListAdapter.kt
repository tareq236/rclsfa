package com.impala.rclsfa.activities.order

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.impala.rclsfa.R
import com.impala.rclsfa.activities.order.model.OrderListModel
import com.impala.rclsfa.databinding.OrderListBinding
import java.text.SimpleDateFormat


class OrderListAdapter(val context: Context) :
    RecyclerView.Adapter<OrderListAdapter.ViewHolder>() {

    var list: MutableList<OrderListModel.Result> = mutableListOf()


    fun addData(allCus: MutableList<OrderListModel.Result>) {
        list.addAll(allCus)
        notifyDataSetChanged()
    }

    fun clearData() {
        list.clear()
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = OrderListBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.order_list, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = list[position]

        with(holder) {
            binding.orderId.text = item.id.toString()
            binding.retailerId.text = item.retailerId.toString()
            binding.retName.text = item.retailerDetails!!.retailerName
            binding.orderDate.text = dateFormatter(item.orderDate!!)

        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("SimpleDateFormat")
    fun dateFormatter(dateTime: String): String {
        var thisDate = ""
        try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX")
            val outputFormat = SimpleDateFormat("dd MMMM yyyy")
            val date = inputFormat.parse(dateTime)
            thisDate = outputFormat.format(date!!)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return thisDate
    }

}