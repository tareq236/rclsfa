package com.impala.rclsfa.activities.attendance.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class SaveLeaveAttendM {
    @SerializedName("success")
    @Expose
    private var success: Boolean? = null

    @SerializedName("result")
    @Expose
    private var result: Result? = null

    fun getSuccess(): Boolean? {
        return success
    }

    fun setSuccess(success: Boolean?) {
        this.success = success
    }

    fun getResult(): Result? {
        return result
    }

    fun setResult(result: Result?) {
        this.result = result
    }

    class Result {
        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("asm_id")
        @Expose
        var asmId: Any? = null

        @SerializedName("asm_approve")
        @Expose
        var asmApprove: Any? = null

        @SerializedName("asm_approve_date")
        @Expose
        var asmApproveDate: Any? = null

        @SerializedName("sa_id")
        @Expose
        var saId: Any? = null

        @SerializedName("sa_approve")
        @Expose
        var saApprove: Any? = null

        @SerializedName("sa_approve_date")
        @Expose
        var saApproveDate: Any? = null

        @SerializedName("created_at")
        @Expose
        var createdAt: Any? = null

        @SerializedName("updated_at")
        @Expose
        var updatedAt: Any? = null

        @SerializedName("sr_code")
        @Expose
        var srCode: String? = null

        @SerializedName("absent_from_date")
        @Expose
        var absentFromDate: String? = null

        @SerializedName("absent_to_date")
        @Expose
        var absentToDate: String? = null

        @SerializedName("comments")
        @Expose
        var comments: String? = null

        @SerializedName("status")
        @Expose
        var status: String? = null
    }
}