package com.impala.rclsfa.tgt_setup.kro_outlet_selection.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.impala.rclsfa.R
import com.impala.rclsfa.tgt_setup.kro_outlet_selection.SaveTargetKroActivity
import com.impala.rclsfa.tgt_setup.kro_outlet_selection.model.KroDetailsModel
import com.impala.rclsfa.databinding.RouteDetailsBinding


class KroDetailsAdapter(
    val context: Context
) :
    RecyclerView.Adapter<KroDetailsAdapter.ViewHolder>() {

    var list: MutableList<KroDetailsModel.Result> = mutableListOf()


    fun addData(allCus: MutableList<KroDetailsModel.Result>) {
        list.addAll(allCus)
        notifyDataSetChanged()
    }

    fun clearData() {
        list.clear()
        notifyDataSetChanged()
    }

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
            if(item.targetAmountRe!=null) {
                binding.targetAmountId.text = item.targetAmountRe!!.toString()
            }
            if(item.targetPerRe!=null){
                binding.contribution.text = roundTheNumber(item.targetPerRe!!)
            }


            binding.itemView.setOnClickListener {
                context.startActivity(Intent(context, SaveTargetKroActivity::class.java)
                    .putExtra("retailer_id",item.id)
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

}