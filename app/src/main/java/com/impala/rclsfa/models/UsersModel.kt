package com.impala.rclsfa.models

data class UsersModel(
    val error: String,
    val message: String,
    val result: List<UsersModelResult>,
    val success: Boolean
)

data class UsersModelResult(
    val id: String,
    val name: String,
    val mobile_number: String,
    val designation_id: Int,
    val designation_name: String,
    )


data class RouteWiseTargetModel(
    val error: String,
    val message: String,
    val result:List<RouteWiseTargetModelResult> ,
    val success: Boolean
)

data class RouteWiseTargetModelResult(
    val id: Int,
    val user_id: String,
    val target_month: String,
    val target_amount: Double,
    val route_id: Int,
    val route_name: String,
    val route_target_per: Double,
    val route_target_amount: Double,
    val first_approval: Int,
    val second_approval: Int
)