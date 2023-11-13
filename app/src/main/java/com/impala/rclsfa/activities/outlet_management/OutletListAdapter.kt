package com.impala.rclsfa.activities.outlet_management

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.TextUtils.isEmpty
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.impala.rclsfa.R
import com.impala.rclsfa.databinding.OutletListItemBinding
import java.text.SimpleDateFormat


class OutletListAdapter(val context: Context, val click: MainClickManage) :
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
            if (isEmpty(item.nameBn)) {
                binding.nameBn.visibility = View.GONE
            }
            binding.nameBn.text = item.nameBn
            binding.address.text = "Address: " + item.address
            binding.targetAmountId.text = item.targetAmountRe
            binding.achId.text = item.ach.toString() + " %"
            binding.conId.text = item.con.toString() + " %"

            binding.call.setOnClickListener {

                val mobile = item.mobileNumber
                val dialIntent = Intent(Intent.ACTION_DIAL)
                dialIntent.data = Uri.parse("tel:$mobile")
                context.startActivity(dialIntent)

            }

            binding.itemView.setOnClickListener {
                click.details(
                    item.retailerName!!,
                    item.nameBn!!,
                    item.proprietorName!!,
                    item.mobileNumber!!,
                    item.address!!,
                    item.birthday!!,
                    item.marriageDate!!,
                    item.nid!!,
                    item.firstChildrenName!!,
                    item.firstChildrenBirthday!!,
                    item.secondChildrenName!!,
                    item.secondChildrenBirthday!!
                )
            }

        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface MainClickManage {
        fun details(
            retailerName: String,
            nameBn: String,
            proprietorName: String,
            mobileNumber: String,
            address: String,
            birthDay: String,
            marriageDate: String,
            nid: String,
            fChildName: String,
            fChildBirthDay: String,
            sChildName: String,
            sChildBirthDay: String,
        )
    }

}
