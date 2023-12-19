package com.impala.rclsfa.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.impala.rclsfa.R
import com.impala.rclsfa.activities.DashboardActivity
import com.impala.rclsfa.activities.LeaderBoardActivity
import com.impala.rclsfa.attendance.AttendanceMenuActivity
import com.impala.rclsfa.auth.ProfileActivity
import com.impala.rclsfa.order.OrderMenuActivity
import com.impala.rclsfa.outletManagement.OutletManagementMainMenuActivity
import com.impala.rclsfa.retailer.RetailerSummeryActivity
import com.impala.rclsfa.tgt_setup.TGTSetupMainActivity
import com.impala.rclsfa.models.MenuItem
import com.impala.rclsfa.models.UserRoles
import com.impala.rclsfa.utils.SessionManager
import com.impala.rclsfa.webview.WebViewModelActivity
import com.squareup.picasso.Picasso

class MenuAdapter(private val menuList: List<MenuItem>,private val sessionManager: SessionManager) :
    RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.grid_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val menuItem = menuList[position]
        holder.bind(menuItem, sessionManager)
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(menuItem: MenuItem, sessionManager: SessionManager) {
            val menuNameTextView = itemView.findViewById<TextView>(R.id.menuNameTextView)
            val menuImageView = itemView.findViewById<ImageView>(R.id.menuImageView)
            val llCard = itemView.findViewById<LinearLayout>(R.id.ll_card)

            menuNameTextView.text = menuItem.menu_name
            // Load and set the image using Picasso
            Picasso.get().load("http://157.230.195.60:9011/api/v1/menu_icons/"+menuItem.image).into(menuImageView)

            // Add a click listener to the image view
            llCard.setOnClickListener {
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
                    val userRoles = sessionManager.userRoles
                    val userRolesList = Gson().fromJson(userRoles, Array<UserRoles>::class.java).toList()
                    val buttonDetails = userRolesList.firstOrNull { it.access_name == "Dashboard" }
                    if (buttonDetails != null) {
                        if(buttonDetails.access_url != ""){
                            val intent = Intent(itemView.context, WebViewModelActivity::class.java)
                            intent.putExtra("access_url",buttonDetails.access_url)
                            intent.putExtra("menu_name",buttonDetails.menu_name)
                            itemView.context.startActivity(intent)
                        }
                    }
                }
                if(menuItem.func == "outlet"){
                    val intent = Intent(itemView.context, OutletManagementMainMenuActivity::class.java)
                    itemView.context.startActivity(intent)
                }

                if(menuItem.func == "retailer_summary"){
                    val userRoles = sessionManager.userRoles
                    val userRolesList = Gson().fromJson(userRoles, Array<UserRoles>::class.java).toList()
                    val buttonDetails = userRolesList.firstOrNull { it.access_name == "RetailerSummary" }
                    if (buttonDetails != null) {
                        if(buttonDetails.access_url != ""){
                            val intent = Intent(itemView.context, WebViewModelActivity::class.java)
                            intent.putExtra("access_url",buttonDetails.access_url)
                            intent.putExtra("menu_name",buttonDetails.menu_name)
                            itemView.context.startActivity(intent)
                        }
                    }
                }

                if(menuItem.func == "tgt_setup"){
                    val intent = Intent(itemView.context, TGTSetupMainActivity::class.java)
                    itemView.context.startActivity(intent)
                }

                if(menuItem.func == "laeder_board"){
                    val userRoles = sessionManager.userRoles
                    val userRolesList = Gson().fromJson(userRoles, Array<UserRoles>::class.java).toList()
                    val buttonDetails = userRolesList.firstOrNull { it.access_name == "LaederBoard" }
                    if (buttonDetails != null) {
                        if(buttonDetails.access_url != ""){
                            val intent = Intent(itemView.context, WebViewModelActivity::class.java)
                            intent.putExtra("access_url",buttonDetails.access_url)
                            intent.putExtra("menu_name",buttonDetails.menu_name)
                            itemView.context.startActivity(intent)
                        }
                    }
                }

                if(menuItem.func == "daily_report"){
                    val userRoles = sessionManager.userRoles
                    val userRolesList = Gson().fromJson(userRoles, Array<UserRoles>::class.java).toList()
                    val buttonDetails = userRolesList.firstOrNull { it.access_name == "DailyReport" }
                    if (buttonDetails != null) {
                        if(buttonDetails.access_url != ""){
                            val intent = Intent(itemView.context, WebViewModelActivity::class.java)
                            intent.putExtra("access_url",buttonDetails.access_url)
                            intent.putExtra("menu_name",buttonDetails.menu_name)
                            itemView.context.startActivity(intent)
                        }
                    }
                }

                if(menuItem.func == "position"){
                    val userRoles = sessionManager.userRoles
                    val userRolesList = Gson().fromJson(userRoles, Array<UserRoles>::class.java).toList()
                    val buttonDetails = userRolesList.firstOrNull { it.access_name == "Position" }
                    if (buttonDetails != null) {
                        if(buttonDetails.access_url != ""){
                            val intent = Intent(itemView.context, WebViewModelActivity::class.java)
                            intent.putExtra("access_url",buttonDetails.access_url)
                            intent.putExtra("menu_name",buttonDetails.menu_name)
                            itemView.context.startActivity(intent)
                        }
                    }
                }

                if(menuItem.func == "kpi"){
                    val userRoles = sessionManager.userRoles
                    val userRolesList = Gson().fromJson(userRoles, Array<UserRoles>::class.java).toList()
                    val buttonDetails = userRolesList.firstOrNull { it.access_name == "KPI" }
                    if (buttonDetails != null) {
                        if(buttonDetails.access_url != ""){
                            val intent = Intent(itemView.context, WebViewModelActivity::class.java)
                            intent.putExtra("access_url",buttonDetails.access_url)
                            intent.putExtra("menu_name",buttonDetails.menu_name)
                            itemView.context.startActivity(intent)
                        }
                    }
                }

                if(menuItem.func == "kpi"){
                    val userRoles = sessionManager.userRoles
                    val userRolesList = Gson().fromJson(userRoles, Array<UserRoles>::class.java).toList()
                    val buttonDetails = userRolesList.firstOrNull { it.access_name == "KPI" }
                    if (buttonDetails != null) {
                        if(buttonDetails.access_url != ""){
                            val intent = Intent(itemView.context, WebViewModelActivity::class.java)
                            intent.putExtra("access_url",buttonDetails.access_url)
                            intent.putExtra("menu_name",buttonDetails.menu_name)
                            itemView.context.startActivity(intent)
                        }
                    }
                }

                if(menuItem.func == "stock_challan"){
                    val userRoles = sessionManager.userRoles
                    val userRolesList = Gson().fromJson(userRoles, Array<UserRoles>::class.java).toList()
                    val buttonDetails = userRolesList.firstOrNull { it.access_name == "StockChallan" }
                    if (buttonDetails != null) {
                        if(buttonDetails.access_url != ""){
                            val intent = Intent(itemView.context, WebViewModelActivity::class.java)
                            intent.putExtra("access_url",buttonDetails.access_url)
                            intent.putExtra("menu_name",buttonDetails.menu_name)
                            itemView.context.startActivity(intent)
                        }
                    }
                }

                if(menuItem.func == "products"){
                    val userRoles = sessionManager.userRoles
                    val userRolesList = Gson().fromJson(userRoles, Array<UserRoles>::class.java).toList()
                    val buttonDetails = userRolesList.firstOrNull { it.access_name == "Products" }
                    if (buttonDetails != null) {
                        if(buttonDetails.access_url != ""){
                            val intent = Intent(itemView.context, WebViewModelActivity::class.java)
                            intent.putExtra("access_url",buttonDetails.access_url)
                            intent.putExtra("menu_name",buttonDetails.menu_name)
                            itemView.context.startActivity(intent)
                        }
                    }
                }

                if(menuItem.func == "campaign"){
                    val userRoles = sessionManager.userRoles
                    val userRolesList = Gson().fromJson(userRoles, Array<UserRoles>::class.java).toList()
                    val buttonDetails = userRolesList.firstOrNull { it.access_name == "Campaign" }
                    if (buttonDetails != null) {
                        if(buttonDetails.access_url != ""){
                            val intent = Intent(itemView.context, WebViewModelActivity::class.java)
                            intent.putExtra("access_url",buttonDetails.access_url)
                            intent.putExtra("menu_name",buttonDetails.menu_name)
                            itemView.context.startActivity(intent)
                        }
                    }
                }

                if(menuItem.func == "notice_board"){
                    val userRoles = sessionManager.userRoles
                    val userRolesList = Gson().fromJson(userRoles, Array<UserRoles>::class.java).toList()
                    val buttonDetails = userRolesList.firstOrNull { it.access_name == "NoticeBoard" }
                    if (buttonDetails != null) {
                        if(buttonDetails.access_url != ""){
                            val intent = Intent(itemView.context, WebViewModelActivity::class.java)
                            intent.putExtra("access_url",buttonDetails.access_url)
                            intent.putExtra("menu_name",buttonDetails.menu_name)
                            itemView.context.startActivity(intent)
                        }
                    }
                }

                if(menuItem.func == "about"){
                    val userRoles = sessionManager.userRoles
                    val userRolesList = Gson().fromJson(userRoles, Array<UserRoles>::class.java).toList()
                    val buttonDetails = userRolesList.firstOrNull { it.access_name == "AboutCompany" }
                    if (buttonDetails != null) {
                        if(buttonDetails.access_url != ""){
                            val intent = Intent(itemView.context, WebViewModelActivity::class.java)
                            intent.putExtra("access_url",buttonDetails.access_url)
                            intent.putExtra("menu_name",buttonDetails.menu_name)
                            itemView.context.startActivity(intent)
                        }
                    }
                }

            }

        }
    }
}
