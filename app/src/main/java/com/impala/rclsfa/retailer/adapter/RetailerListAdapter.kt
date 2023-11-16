package com.impala.rclsfa.retailer.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.impala.rclsfa.R
import com.impala.rclsfa.retailer.model.RetailerListModel
import com.impala.rclsfa.databinding.ItemRetailerListBinding
import java.text.SimpleDateFormat


class RetailerListAdapter(val context: Context) :
    RecyclerView.Adapter<RetailerListAdapter.ViewHolder>() {

    var list: MutableList<RetailerListModel.Result> = mutableListOf()

    fun filterList(filteredList: ArrayList<RetailerListModel.Result>) {
        this.list = filteredList;
        notifyDataSetChanged();
    }

    fun addData(allCus: MutableList<RetailerListModel.Result>) {
        list.addAll(allCus)
        notifyDataSetChanged()

    }

    fun clearData() {
        list.clear()
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemRetailerListBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_retailer_list, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = list[position]

        with(holder) {
            binding.serialNoId.text = (position+1).toString()
            binding.orderId.text = item.id.toString()
            binding.srName.text = item.nameBn.toString()
            binding.orderDate.text = dateFormatter(item.createdAt.toString())
            binding.mobileNumber.text = item.mobileNumber.toString()

            val generator: ColorGenerator = ColorGenerator.MATERIAL
            val color: Int = generator.getColor(getItem(position))
            binding.linShapeId.setBackgroundColor(color)

        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface IClickManage {
        fun doClick(cateId: Int,cateName:String)

    }


    @SuppressLint("SimpleDateFormat")
    fun dateFormatter(dateTime: String): String {
        var thisDate = ""
        try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            val outputFormat = SimpleDateFormat("dd MMMM yyyy")
            val date = inputFormat.parse(dateTime)
            thisDate = outputFormat.format(date!!)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return thisDate
    }

    private fun getItem(position: Int): String? {
        return list[position].toString()
    }
}