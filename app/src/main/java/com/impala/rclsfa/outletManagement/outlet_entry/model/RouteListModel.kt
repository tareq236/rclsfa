package com.impala.rclsfa.outletManagement.outlet_entry.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class RouteListModel {
    @SerializedName("success")
    @Expose
    private var success: Boolean? = null

    @SerializedName("result")
    @Expose
    private var result: List<Result?>? = null

    @SerializedName("target_amount")
    @Expose
    private var targetAmount: Int? = null

    fun getSuccess(): Boolean? {
        return success
    }

    fun setSuccess(success: Boolean?) {
        this.success = success
    }

    fun getResult(): List<Result?>? {
        return result
    }

    fun setResult(result: List<Result?>?) {
        this.result = result
    }

    fun getTargetAmount(): Int? {
        return targetAmount
    }

    fun setTargetAmount(targetAmount: Int?) {
        this.targetAmount = targetAmount
    }

    class Result {
        @SerializedName("sr_code")
        @Expose
        var srCode: String? = null

        @SerializedName("route_id")
        @Expose
        var routeId: Int? = null

        @SerializedName("contribution")
        @Expose
        var contribution: String? = null

        @SerializedName("route_name")
        @Expose
        var routeName: String? = null

        @SerializedName("ach_amount")
        @Expose
        var achAmount: Any? = null

        @SerializedName("ach")
        @Expose
        var ach: Any? = null
    }
}