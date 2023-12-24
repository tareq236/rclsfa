package com.impala.rclsfa.attendance.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class ApproveLeaveApplicationM {
    @SerializedName("success")
    @Expose
    private var success: Boolean? = null

    @SerializedName("result")
    @Expose
    private var result: List<Int?>? = null

    fun getSuccess(): Boolean? {
        return success
    }

    fun setSuccess(success: Boolean?) {
        this.success = success
    }

    fun getResult(): List<Int?>? {
        return result
    }

    fun setResult(result: List<Int?>?) {
        this.result = result
    }
}