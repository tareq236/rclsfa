package com.impala.rclsfa.models

data class LoginResponse(
    val atandences_setting: AtandencesSetting,
    val user_roles: List<UserRoles>,
    val error: String,
    val message: String,
    val result: Result,
    val server_date_time: String,
    val server_date_time_formate: String,
    val server_time: String,
    val success: Boolean
)

data class AtandencesSetting(
    val id: String,
    val attendances_distance_km: String,
    val atandences_time: String,
    val atandences_late_count_min: String
)

data class Result(
    val id: String,
    val name: String,
    val mobile_number: String,
    val designation_id: Int,
    val designation_details: DesignationDetails,
    val opt: String,
    val password: String,
    val latitude: String,
    val longitude: String
)
data class DesignationDetails(
    val id: String,
    val designation_code: String,
    val designation_name: String,
    val steps: Int,
)
data class UserRoles(
    val user_id: String,
    val role_id: String,
    val role_name: String,
    val role_for: String,
    val access_group_name: String,
    val access_name: String,
    val access_url: String,
    val menu_name: String,
    val add: String,
    val edit: String,
    val view: String,
    val delete: String,
)
