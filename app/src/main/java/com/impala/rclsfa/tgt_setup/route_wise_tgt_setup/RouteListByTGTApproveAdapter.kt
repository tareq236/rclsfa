package com.impala.rclsfa.tgt_setup.route_wise_tgt_setup

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.impala.rclsfa.R
import com.impala.rclsfa.databinding.RouteListByTgtApproveBinding
import com.impala.rclsfa.models.RouteWiseTargetModelResult


class RouteListByTGTApproveAdapter(
    val context: Context,
    val click:MainClickManage
) :
    RecyclerView.Adapter<RouteListByTGTApproveAdapter.ViewHolder>() {

    var list: MutableList<RouteWiseTargetModelResult> = mutableListOf()


    fun addData(allCus: MutableList<RouteWiseTargetModelResult>) {
        list.addAll(allCus)
        notifyDataSetChanged()
    }

//    fun clearData() {
//        list.clear()
//        notifyDataSetChanged()
//    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = RouteListByTgtApproveBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.route_list_by_tgt_approve, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = list[position]

        with(holder) {
            try {
                val nameEn = item.route_name
                val contribution = item.route_target_per

                if (item.first_approval == 0) {
                    binding.approvalId.setTextColor(Color.RED)
                    binding.approvalId.text = "Pending"
                } else if (item.first_approval == 1) {
                    binding.approvalId.text = "Approved"
                }

                binding.nameEn.text = nameEn
                binding.contributionId.text = contribution.toString()
                val dContribution = item.route_target_amount!!.toDouble()
                val targetA = item.target_amount

                val result = targetA * dContribution / 100
                binding.targetAmountId.text = roundTheNumber(result)
            } catch (e: NumberFormatException) {
                e.printStackTrace()
            }

            binding.approveButton.setOnClickListener {

                SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Are you sure?")
                    .setContentText("Approve this")
                    .setCancelText("No,Cancel")
                    .setConfirmText("Yes,delete it!")
                    .showCancelButton(true)
                    .setCancelClickListener { sDialog -> sDialog.cancel() }
                    .setConfirmClickListener {

                    }
                    .show()
            }
            binding.editAmountButton.setOnClickListener {
                click.onEditAmount(binding.targetAmountId.text.toString())
            }


//            binding.itemView.setOnClickListener {
//                var achAmount = 0.0
//                if(item.achAmount!=null){
//                   achAmount = item.achAmount!!
//                }
//
//                click.doClick(
//                    item.routeId.toString(),
//                    item.contribution!!,
//                    achAmount.toString(),
//                    binding.targetAmountId.text.toString()
//                )
//            }


        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    private fun roundTheNumber(numInDouble: Double): String {

        return "%.2f".format(numInDouble)

    }




    interface MainClickManage {
        fun onEditAmount(targetAmount:String)
    }
}
