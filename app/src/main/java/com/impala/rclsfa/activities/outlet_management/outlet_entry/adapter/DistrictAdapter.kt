package com.impala.rclsfa.activities.outlet_management.outlet_entry.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import androidx.recyclerview.widget.RecyclerView
import com.impala.rclsfa.R
import com.impala.rclsfa.activities.outlet_management.outlet_entry.model.CategoryListModel
import com.impala.rclsfa.activities.outlet_management.outlet_entry.model.DistrictModel
import com.impala.rclsfa.activities.outlet_management.outlet_entry.model.DivisionListModel
import com.impala.rclsfa.activities.outlet_management.outlet_entry.model.RouteListModel
import com.impala.rclsfa.databinding.CategoryListItemBinding
import com.impala.rclsfa.databinding.DistrictListItemBinding
import com.impala.rclsfa.databinding.DivisionListItemBinding
import com.impala.rclsfa.databinding.RouteListItemBinding
import java.util.Locale


class DistrictAdapter(val context: Context, private val iClickManage:IClickManage) :
    RecyclerView.Adapter<DistrictAdapter.ViewHolder>() {

    var list: MutableList<DistrictModel.Result> = mutableListOf()

    fun filterList(filteredList: ArrayList<DistrictModel.Result>) {
        this.list = filteredList;
        notifyDataSetChanged();
    }

    fun addData(allCus: MutableList<DistrictModel.Result>) {
        list.addAll(allCus)
        notifyDataSetChanged()

    }

    fun clearData() {
        list.clear()
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = DistrictListItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.district_list_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = list[position]

        with(holder) {

            binding.titleId.text = item.name

            binding.itemView.setOnClickListener {
                iClickManage.doClick(item.id!!,item.name!!)
            }

        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface IClickManage {
        fun doClick(distId: Int,distName:String)

    }

}