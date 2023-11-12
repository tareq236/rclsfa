package com.impala.rclsfa.models

data class MenuResponse(
    val error: String,
    val message: String,
    val result: List<MenuItem>,
    val user_roles: List<UserRoles>,
    val success: Boolean
)

data class MenuItem(
    val id: Int,
    val menu_name: String,
    val access_name: String,
    val image: String,
    val func: String,
    val url: String,
    val status: String,
    val office_user_status: String,
    val order_by: String
)


