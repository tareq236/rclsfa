package com.impala.rclsfa.activities.outlet_management

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.impala.rclsfa.R
import com.impala.rclsfa.databinding.OutletListForLocationBinding
import java.text.SimpleDateFormat


class OutletListForLocationAdapter(val context: Context,val click:IClickManage) :
    RecyclerView.Adapter<OutletListForLocationAdapter.ViewHolder>() {

    var list: MutableList<SearchOutletListModel.Result> = mutableListOf()

//    fun filterList(filteredList: ArrayList<RetailerListModel.Result>) {
//        this.list = filteredList;
//        notifyDataSetChanged();
//    }

    fun addData(allCus: MutableList<SearchOutletListModel.Result>) {
        list.addAll(allCus)
        notifyDataSetChanged()

    }

    fun clearData() {
        list.clear()
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = OutletListForLocationBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.outlet_list_for_location, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = list[position]

        with(holder) {
            binding.nameEn.text = item.retailerName
            binding.nameBn.text = item.nameBn

            binding.itemView.setOnClickListener {
                click.doClick(item.id!!,item.nameBn!!,item.retailerName!!,item.address!!)
            }

        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface IClickManage {
        fun doClick(retailerId: Int,nameBn:String,retailerName:String,outletAddress:String)

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


}