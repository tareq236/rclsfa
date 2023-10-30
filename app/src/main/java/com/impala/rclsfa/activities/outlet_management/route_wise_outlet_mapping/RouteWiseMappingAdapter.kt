package com.impala.rclsfa.activities.outlet_management.route_wise_outlet_mapping

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.impala.rclsfa.R
import com.impala.rclsfa.activities.outlet_management.route_wise_outlet_mapping.model.RouteListBySRModel
import com.impala.rclsfa.databinding.RouteWiseOutletItemBinding
import java.text.SimpleDateFormat


class RouteWiseMappingAdapter(val context: Context) :
    RecyclerView.Adapter<RouteWiseMappingAdapter.ViewHolder>() {

    var list: MutableList<RouteListBySRModel.Result> = mutableListOf()


    fun addData(allCus: MutableList<RouteListBySRModel.Result>) {
        list.addAll(allCus)
        notifyDataSetChanged()

    }

    fun clearData() {
        list.clear()
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = RouteWiseOutletItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.route_wise_outlet_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

       val item = list[position]

        with(holder) {

            binding.routeName.text = item.routeName
            binding.tgtId.text = item.contribution

        }

    }

    override fun getItemCount(): Int {
        return list.size
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