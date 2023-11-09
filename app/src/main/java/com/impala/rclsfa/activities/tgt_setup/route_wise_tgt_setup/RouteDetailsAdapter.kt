package com.impala.rclsfa.activities.tgt_setup.route_wise_tgt_setup

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.impala.rclsfa.R
import com.impala.rclsfa.activities.tgt_setup.route_wise_tgt_setup.model.TgtRouteDetailsM
import com.impala.rclsfa.databinding.RouteDetailsBinding
import java.text.SimpleDateFormat


class RouteDetailsAdapter(
    val context: Context,
    val contribution: String,
    val retailerSize: String,
    val achAmount: String
) :
    RecyclerView.Adapter<RouteDetailsAdapter.ViewHolder>() {

    var list: MutableList<TgtRouteDetailsM.Result> = mutableListOf()


    fun addData(allCus: MutableList<TgtRouteDetailsM.Result>) {
        list.addAll(allCus)
        notifyDataSetChanged()
    }

//    fun clearData() {
//        list.clear()
//        notifyDataSetChanged()
//    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = RouteDetailsBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.route_details, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = list[position]

        with(holder) {
            binding.nameEn.text = item.retailerName
            binding.nameBn.text = item.nameBn
            //binding.contribution.text = item.o
            val dContribution = contribution.toDouble()
            val iRetailerS = retailerSize.toInt()
            val result = (dContribution / iRetailerS) / 100
            binding.contribution.text = roundTheNumber(result)+"%"

            val achAmount = achAmount.toDouble()
            val result1 = (achAmount / iRetailerS) / 100
            binding.targetAmountId.text = roundTheNumber(result1)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    private fun roundTheNumber(numInDouble: Double): String {

        return "%.2f".format(numInDouble)

    }

}