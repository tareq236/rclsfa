package com.impala.rclsfa.activities.tgt_setup.route_wise_tgt_setup

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.impala.rclsfa.R
import com.impala.rclsfa.activities.outlet_management.route_wise_outlet_mapping.model.RouteListBySRModel
import com.impala.rclsfa.activities.tgt_setup.route_wise_tgt_setup.model.RouteListByTgtModel

import com.impala.rclsfa.databinding.RouteListByTgtBinding
import com.impala.rclsfa.utils.SessionManager


class RouteListByTGTAdapter(
    val context: Context,
    val sessionManager: SessionManager,
    val click: MainClickManage
) :
    RecyclerView.Adapter<RouteListByTGTAdapter.ViewHolder>() {

    var list: MutableList<RouteListByTgtModel.Result> = mutableListOf()


    fun addData(allCus: MutableList<RouteListByTgtModel.Result>) {
        list.addAll(allCus)
        notifyDataSetChanged()
    }

//    fun clearData() {
//        list.clear()
//        notifyDataSetChanged()
//    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = RouteListByTgtBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.route_list_by_tgt, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = list[position]

        with(holder) {
            try {
                val nameEn = item.routeName
                val contribution = item.contribution
                binding.nameEn.text = nameEn
                binding.contributionId.text = contribution
                val dContribution = item.contribution!!.toDouble()
                val targetA = sessionManager.targetAmount
                val dTargetA = targetA!!.toInt()
                val result = dTargetA * dContribution / 100
                binding.targetAmountId.text = roundTheNumber(result)
            }catch (e:NumberFormatException){
                e.printStackTrace()
            }

            binding.itemView.setOnClickListener {
                var achAmount = 0.0
                if(item.achAmount!=null){
                   achAmount = item.achAmount!!
                }

                click.doClick(
                    item.routeId.toString(),
                    item.contribution!!,
                    list.size.toString(),
                    achAmount.toString()
                )
            }


        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    private fun roundTheNumber(numInDouble: Double): String {

        return "%.2f".format(numInDouble)

    }


    interface MainClickManage {
        fun doClick(id: String, contribution: String, retailerSize: String, achAmount: String)
    }

}