package com.impala.rclsfa.activities.tgt_setup.kro_outlet_selection.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.impala.rclsfa.R
import com.impala.rclsfa.activities.outlet_management.route_wise_outlet_mapping.model.RouteListBySRModel
import com.impala.rclsfa.activities.tgt_setup.kro_outlet_selection.KroDetailsActivity
import com.impala.rclsfa.activities.tgt_setup.route_wise_tgt_setup.model.RouteListByTgtModel
import com.impala.rclsfa.databinding.RouteListByKroBinding

import com.impala.rclsfa.databinding.RouteListByTgtBinding
import com.impala.rclsfa.utils.SessionManager


class RouteListByKroAdapter(
    val context: Context
) :
    RecyclerView.Adapter<RouteListByKroAdapter.ViewHolder>() {

   // var list: MutableList<RouteListByTgtModel.Result> = mutableListOf()


//    fun addData(allCus: MutableList<RouteListByTgtModel.Result>) {
//        list.addAll(allCus)
//        notifyDataSetChanged()
//    }

//    fun clearData() {
//        list.clear()
//        notifyDataSetChanged()
//    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = RouteListByKroBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.route_list_by_kro, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

       // val item = list[position]

        with(holder) {

                binding.itemView.setOnClickListener {
                    context.startActivity(Intent(context,KroDetailsActivity::class.java))
                }
        }

    }

    override fun getItemCount(): Int {
        return 10
    }

    private fun roundTheNumber(numInDouble: Double): String {

        return "%.2f".format(numInDouble)

    }


    interface MainClickManage {
        fun doClick(id: String, contribution: String, retailerSize: String, achAmount: String)
    }

}