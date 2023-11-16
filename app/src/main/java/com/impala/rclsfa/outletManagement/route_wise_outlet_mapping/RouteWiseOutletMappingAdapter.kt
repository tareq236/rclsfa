package com.impala.rclsfa.outletManagement.route_wise_outlet_mapping

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.impala.rclsfa.R
import com.impala.rclsfa.databinding.OutletListItemBinding
import java.text.SimpleDateFormat


class RouteWiseOutletMappingAdapter(val context: Context) :
    RecyclerView.Adapter<RouteWiseOutletMappingAdapter.ViewHolder>() {

  //  var list: MutableList<RetailerListModel.Result> = mutableListOf()

//    fun filterList(filteredList: ArrayList<RetailerListModel.Result>) {
//        this.list = filteredList;
//        notifyDataSetChanged();
//    }

//    fun addData(allCus: MutableList<RetailerListModel.Result>) {
//        list.addAll(allCus)
//        notifyDataSetChanged()
//
//    }

//    fun clearData() {
//        list.clear()
//        notifyDataSetChanged()
//    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = OutletListItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.outlet_list_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

      //  val item = list[position]

        with(holder) {

        }

    }

    override fun getItemCount(): Int {
        return 10
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


}