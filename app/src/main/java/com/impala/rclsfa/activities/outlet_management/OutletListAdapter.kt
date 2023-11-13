package com.impala.rclsfa.activities.outlet_management

import android.annotation.SuppressLint
import android.content.Context
import android.text.TextUtils.isEmpty
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.impala.rclsfa.R
import com.impala.rclsfa.databinding.OutletListItemBinding
import java.text.SimpleDateFormat


class OutletListAdapter(val context: Context) :
    RecyclerView.Adapter<OutletListAdapter.ViewHolder>() {

    var list: MutableList<SearchOutletListModel.Result> = mutableListOf()


    fun addData(allCus: MutableList<SearchOutletListModel.Result>) {
        list.addAll(allCus)
        notifyDataSetChanged()
    }

    fun clearData() {
        list.clear()
        notifyDataSetChanged()
    }

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

        val item = list[position]

        with(holder) {
            binding.nameEn.text = item.retailerName
            if(isEmpty(item.nameBn)){
                binding.nameBn.visibility = View.GONE
            }
            binding.nameBn.text = item.nameBn
            binding.address.text = "Address: " + item.address
            binding.targetAmountId.text = item.targetAmountRe
            binding.achId.text = item.ach.toString()+" %"
            binding.conId.text = item.con.toString()+" %"

        }

    }

    override fun getItemCount(): Int {
        return list.size
    }


}
