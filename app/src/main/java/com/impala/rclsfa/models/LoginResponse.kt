package com.impala.rclsfa.models

data class LoginResponse(
    val atandences_setting: AtandencesSetting,
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
    val sr_code: String,
    val tsm_code: String,
    val dsm_code: String,
    val hs_code: String,
    val sr_address: String,
    val tsm_address: String,
    val dsm_address: String,
    val hs_address: String,
    val name: String,
    val mobile_number: String,
    val designation_id: Int,
    val designation: String,
    val opt: String,
    val password: String,
    val latitude: Double,
    val longitude: Double
)
