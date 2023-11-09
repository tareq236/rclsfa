package com.impala.rclsfa.activities.attendance.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.impala.rclsfa.R
import com.impala.rclsfa.databinding.LeaveApplicationListBinding
import com.impala.rclsfa.databinding.OutletListItemBinding
import java.text.SimpleDateFormat


class LeaveApplicationAdapter(val context: Context) :
    RecyclerView.Adapter<LeaveApplicationAdapter.ViewHolder>() {

  //  var list: MutableList<SearchOutletListModel.Result> = mutableListOf()


//    fun addData(allCus: MutableList<SearchOutletListModel.Result>) {
//        list.addAll(allCus)
//        notifyDataSetChanged()
//    }

//    fun clearData() {
//        list.clear()
//        notifyDataSetChanged()
//    }

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

      //  val item = list[position]

        with(holder) {


        }

    }

    override fun getItemCount(): Int {
        return 10
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