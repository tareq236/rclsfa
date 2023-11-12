package com.impala.rclsfa.utils

import com.google.gson.Gson
import com.impala.rclsfa.models.MenuItem
import com.impala.rclsfa.models.UserRoles

object UserRolesCheck {
    fun checkMenuRole(menuItem: List<MenuItem>, userRoles: String): List<MenuItem> {
        val userRolesList = Gson().fromJson(userRoles, Array<UserRoles>::class.java).toList()
        val filteredMenuItems = menuItem.filter { menuItem ->
            userRolesList.any { userRole ->
                menuItem.access_name == userRole.access_name
            }
        }
        return filteredMenuItems
    }
}
