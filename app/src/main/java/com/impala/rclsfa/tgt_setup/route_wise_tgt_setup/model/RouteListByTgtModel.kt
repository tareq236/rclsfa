package com.impala.rclsfa.tgt_setup.route_wise_tgt_setup.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

data class RouteListByTgtModel (
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("result")
    val result: List<RouteListByTgtResult?>? = null,
    @SerializedName("target_amount")
    val targetAmount: Int? = null,
    @SerializedName("steps")
    val steps: Boolean
)

data class RouteListByTgtResult (
    @SerializedName("sr_code")
    var srCode: String? = null,
    @SerializedName("route_id")
    var routeId: Int? = null,
    @SerializedName("contribution")
    var contribution: String? = null,
    @SerializedName("route_name")
    var routeName: String? = null,
    @SerializedName("ach_amount")
    var achAmount: Double? = null,
    @SerializedName("ach")
    var ach: Any? = null,
    @SerializedName("first_approval")
    var firstApproval: Int? = null,
    @SerializedName("second_approval")
    var secondApproval: Int? = null
)

