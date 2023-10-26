package com.impala.rclsfa.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.impala.rclsfa.R
import com.impala.rclsfa.activities.DashboardActivity
import com.impala.rclsfa.activities.attendance.AttendanceMenuActivity
import com.impala.rclsfa.activities.auth.ProfileActivity
import com.impala.rclsfa.activities.order.OrderMenuActivity
import com.impala.rclsfa.activities.outlet_management.outlet_entry.OutletManagementMainMenuActivity
import com.impala.rclsfa.activities.retailer.RetailerSummeryActivity
import com.impala.rclsfa.models.MenuItem
import com.squareup.picasso.Picasso

class MenuAdapter(private val menuList: List<MenuItem>) :
    RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.grid_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val menuItem = menuList[position]
        holder.bind(menuItem)
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(menuItem: MenuItem) {
            val menuNameTextView = itemView.findViewById<TextView>(R.id.menuNameTextView)
            val menuImageView = itemView.findViewById<ImageView>(R.id.menuImageView)

            menuNameTextView.text = menuItem.menu_name
            // Load and set the image using Picasso
            Picasso.get().load("http://157.230.195.60:6011/api/v1/menu_icons/"+menuItem.image).into(menuImageView)

            // Add a click listener to the image view
            menuImageView.setOnClickListener {
                // Start the MenuDetailActivity when the image is clicked
                if(menuItem.func == "attendance"){
                    val intent = Intent(itemView.context, AttendanceMenuActivity::class.java)
                    itemView.context.startActivity(intent)
                }
                if(menuItem.func == "order"){
                    val intent = Intent(itemView.context, OrderMenuActivity::class.java)
                    itemView.context.startActivity(intent)
                }

                if(menuItem.func == "profile"){
                    val intent = Intent(itemView.context, ProfileActivity::class.java)
                    itemView.context.startActivity(intent)
                }

                if(menuItem.func == "dashboard"){
                    val intent = Intent(itemView.context, DashboardActivity::class.java)
                    itemView.context.startActivity(intent)
                }
                if(menuItem.func == "outlet"){
                    val intent = Intent(itemView.context, OutletManagementMainMenuActivity::class.java)
                    itemView.context.startActivity(intent)
                }

                if(menuItem.func == "retailer_summary"){
                    val intent = Intent(itemView.context, RetailerSummeryActivity::class.java)
                    itemView.context.startActivity(intent)
                }


            }

        }
    }
}
