package com.impala.rclsfa.tgt_setup.route_wise_tgt_setup

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.impala.rclsfa.R
import com.impala.rclsfa.tgt_setup.route_wise_tgt_setup.model.TgtRouteDetailsM
import com.impala.rclsfa.databinding.RouteDetailsBinding
import com.impala.rclsfa.databinding.UsersListItemBinding
import com.impala.rclsfa.models.UsersModel
import com.impala.rclsfa.models.UsersModelResult
import java.text.SimpleDateFormat


class UserAssignAdapter(
    val context: Context

) :
    RecyclerView.Adapter<UserAssignAdapter.ViewHolder>() {

    var list: MutableList<UsersModelResult> = mutableListOf()


    fun addData(allCus: MutableList<UsersModelResult>) {
        list.addAll(allCus)
        notifyDataSetChanged()
    }

//    fun clearData() {
//        list.clear()
//        notifyDataSetChanged()
//    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = UsersListItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.users_list_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = list[position]


        with(holder) {
            try {
                binding.titleId.text = item.name+" "+"("+item.id+")"
                binding.designationId.text = item.designation_name

            }catch (e:NumberFormatException){
                e.printStackTrace()
            }

            binding.itemView.setOnClickListener {
                val userId = item.id
                val designationId = item.designation_id
                context.startActivity(Intent(context,RouteListByTGTApproveActivity::class.java)
                    .putExtra("user_id",userId)
                    .putExtra("designation_id",designationId)
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