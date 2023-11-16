package com.impala.rclsfa.attendance.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class AllLeaveAttendListM {
    @SerializedName("success")
    @Expose
    private var success: Boolean? = null

    @SerializedName("result")
    @Expose
    private var result: List<Result?>? = null

    @SerializedName("first_approval")
    @Expose
    private var firstApproval: FirstApproval? = null

    @SerializedName("final_approval")
    @Expose
    private var finalApproval: FinalApproval? = null

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

    fun getFirstApproval(): FirstApproval? {
        return firstApproval
    }

    fun setFirstApproval(firstApproval: FirstApproval?) {
        this.firstApproval = firstApproval
    }

    fun getFinalApproval(): FinalApproval? {
        return finalApproval
    }

    fun setFinalApproval(finalApproval: FinalApproval?) {
        this.finalApproval = finalApproval
    }

    class FinalApproval {
        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("sr_code")
        @Expose
        var srCode: Any? = null

        @SerializedName("tsm_code")
        @Expose
        var tsmCode: Any? = null

        @SerializedName("dsm_code")
        @Expose
        var dsmCode: String? = null

        @SerializedName("hs_code")
        @Expose
        var hsCode: String? = null

        @SerializedName("sr_address")
        @Expose
        var srAddress: Any? = null

        @SerializedName("tsm_address")
        @Expose
        var tsmAddress: Any? = null

        @SerializedName("dsm_address")
        @Expose
        var dsmAddress: String? = null

        @SerializedName("hs_address")
        @Expose
        var hsAddress: String? = null

        @SerializedName("name")
        @Expose
        var name: String? = null

        @SerializedName("mobile_number")
        @Expose
        var mobileNumber: Any? = null

        @SerializedName("designation_id")
        @Expose
        var designationId: Int? = null

        @SerializedName("designation")
        @Expose
        var designation: String? = null

        @SerializedName("password")
        @Expose
        var password: String? = null

        @SerializedName("active")
        @Expose
        var active: Int? = null

        @SerializedName("opt")
        @Expose
        var opt: Any? = null

        @SerializedName("token")
        @Expose
        var token: Any? = null

        @SerializedName("device_type")
        @Expose
        var deviceType: Any? = null

        @SerializedName("created_at")
        @Expose
        var createdAt: String? = null

        @SerializedName("updated_at")
        @Expose
        var updatedAt: String? = null

        @SerializedName("latitude")
        @Expose
        var latitude: Any? = null

        @SerializedName("longitude")
        @Expose
        var longitude: Any? = null
    }

    class FirstApproval {
        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("sr_code")
        @Expose
        var srCode: Any? = null

        @SerializedName("tsm_code")
        @Expose
        var tsmCode: String? = null

        @SerializedName("dsm_code")
        @Expose
        var dsmCode: String? = null

        @SerializedName("hs_code")
        @Expose
        var hsCode: String? = null

        @SerializedName("sr_address")
        @Expose
        var srAddress: Any? = null

        @SerializedName("tsm_address")
        @Expose
        var tsmAddress: String? = null

        @SerializedName("dsm_address")
        @Expose
        var dsmAddress: String? = null

        @SerializedName("hs_address")
        @Expose
        var hsAddress: String? = null

        @SerializedName("name")
        @Expose
        var name: String? = null

        @SerializedName("mobile_number")
        @Expose
        var mobileNumber: Any? = null

        @SerializedName("designation_id")
        @Expose
        var designationId: Int? = null

        @SerializedName("designation")
        @Expose
        var designation: String? = null

        @SerializedName("password")
        @Expose
        var password: String? = null

        @SerializedName("active")
        @Expose
        var active: Int? = null

        @SerializedName("opt")
        @Expose
        var opt: Any? = null

        @SerializedName("token")
        @Expose
        var token: Any? = null

        @SerializedName("device_type")
        @Expose
        var deviceType: Any? = null

        @SerializedName("created_at")
        @Expose
        var createdAt: String? = null

        @SerializedName("updated_at")
        @Expose
        var updatedAt: String? = null

        @SerializedName("latitude")
        @Expose
        var latitude: Any? = null

        @SerializedName("longitude")
        @Expose
        var longitude: Any? = null
    }

    class Result {
        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("sr_code")
        @Expose
        var srCode: Int? = null

        @SerializedName("absent_from_date")
        @Expose
        var absentFromDate: String? = null

        @SerializedName("absent_to_date")
        @Expose
        var absentToDate: String? = null

        @SerializedName("comments")
        @Expose
        var comments: String? = null

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

        @SerializedName("status")
        @Expose
        var status: Int? = null

        @SerializedName("created_at")
        @Expose
        var createdAt: Any? = null

        @SerializedName("updated_at")
        @Expose
        var updatedAt: Any? = null
    }
}