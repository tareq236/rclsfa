package com.impala.rclsfa.tgt_setup.kro_outlet_selection

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.impala.rclsfa.R
import com.impala.rclsfa.tgt_setup.kro_outlet_selection.model.RetailerListByKro
import com.impala.rclsfa.databinding.KroOutletListBinding


class KROOutletListAdapter(val context: Context) :
    RecyclerView.Adapter<KROOutletListAdapter.ViewHolder>() {

    var list: MutableList<RetailerListByKro.Result> = mutableListOf()


    fun addData(allCus: MutableList<RetailerListByKro.Result>) {
        list.addAll(allCus)
        notifyDataSetChanged()
    }

    fun clearData() {
        list.clear()
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = KroOutletListBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.kro_outlet_list, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = list[position]

        with(holder) {

            binding.nameEn.text = item.retailerName
            binding.nameBn.text = item.nameBn
            binding.targetAmountId.text = item.targetAmountRe

            binding.editId.setOnClickListener {
                context.startActivity(Intent(context,EditKroOutletTargetActivity::class.java)
                    .putExtra("name_en",item.retailerName)
                    .putExtra("name_bn",item.nameBn)
                    .putExtra("target_amount",item.targetAmountRe)
                    .putExtra("id",item.target_id)
                    .putExtra("retailer_id",item.id)
                )
            }
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }



}
