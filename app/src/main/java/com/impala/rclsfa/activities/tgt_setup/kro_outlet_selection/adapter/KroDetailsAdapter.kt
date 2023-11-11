package com.impala.rclsfa.activities.tgt_setup.kro_outlet_selection.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.impala.rclsfa.R
import com.impala.rclsfa.databinding.RouteDetailsBinding


class KroDetailsAdapter(
    val context: Context
) :
    RecyclerView.Adapter<KroDetailsAdapter.ViewHolder>() {

   // var list: MutableList<TgtRouteDetailsM.Result> = mutableListOf()


//    fun addData(allCus: MutableList<TgtRouteDetailsM.Result>) {
//        list.addAll(allCus)
//        notifyDataSetChanged()
//    }

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

      //  val item = list[position]

        with(holder) {

        }

    }

    override fun getItemCount(): Int {
        return 10
    }

    private fun roundTheNumber(numInDouble: Double): String {

        return "%.2f".format(numInDouble)

    }

}