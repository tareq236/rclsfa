package com.impala.rclsfa.activities.outlet_management.outlet_entry.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import androidx.recyclerview.widget.RecyclerView
import com.impala.rclsfa.R
import com.impala.rclsfa.activities.outlet_management.outlet_entry.model.RouteListModel
import com.impala.rclsfa.databinding.RouteListItemBinding
import java.util.Locale


class RouteAdapter(val context: Context,val iClickManage:IClickManage) :
    RecyclerView.Adapter<RouteAdapter.ViewHolder>() {

    var list: MutableList<RouteListModel.Result> = mutableListOf()

    fun filterList(filteredList: ArrayList<RouteListModel.Result>) {
        this.list = filteredList;
        notifyDataSetChanged();
    }

    fun addData(allCus: MutableList<RouteListModel.Result>) {
        list.addAll(allCus)
        notifyDataSetChanged()

    }

    fun clearData() {
        list.clear()
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = RouteListItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.route_list_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = list[position]

        with(holder) {

            binding.titleId.text = item.routeName

            binding.itemView.setOnClickListener {
                iClickManage.doClick(item.routeId!!,item.routeName!!)
            }

        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface IClickManage {
        fun doClick(routeId: Int,routeName:String)

    }

}