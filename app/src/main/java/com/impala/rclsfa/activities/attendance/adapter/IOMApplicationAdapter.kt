package com.impala.rclsfa.activities.attendance.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.impala.rclsfa.R
import com.impala.rclsfa.activities.attendance.model.AllLeaveAttendListM
import com.impala.rclsfa.databinding.LeaveApplicationListBinding
import com.impala.rclsfa.databinding.OutletListItemBinding
import java.text.SimpleDateFormat


class IOMApplicationAdapter(val context: Context) :
    RecyclerView.Adapter<IOMApplicationAdapter.ViewHolder>() {

    var list: MutableList<AllLeaveAttendListM.Result> = mutableListOf()


    fun addData(allCus: MutableList<AllLeaveAttendListM.Result>) {
        list.addAll(allCus)
        notifyDataSetChanged()
    }

    fun clearData() {
        list.clear()
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = LeaveApplicationListBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.leave_application_list, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = list[position]

        with(holder) {

             if(item.asmApprove==null){
                 binding.firstApproval.text = "Pending"
             }else if(item.asmApprove==1){
                 binding.firstApproval.text = "Approve"
             }else if(item.asmApprove==2){
                 binding.firstApproval.text = "Cancel"
             }

            if(item.saApprove==null){
                binding.finalApproval.text = "Pending"
            }else if(item.saApprove==1){
                binding.finalApproval.text = "Approve"
            }else if(item.saApprove==2){
                binding.finalApproval.text = "Cancel"
            }

            binding.comments.text = item.comments
            binding.fromDate.text = item.absentFromDate
            binding.toDate.text = item.absentToDate
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