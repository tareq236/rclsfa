package com.impala.rclsfa.tgt_setup.kro_outlet_selection.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class SaveKroTargetModel {
    @SerializedName("success")
    @Expose
    private var success: Boolean? = null

    @SerializedName("result")
    @Expose
    private var result: List<Result?>? = null

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


    class Result {
        @SerializedName("sr_code")
        @Expose
        var srCode: Any? = null

        @SerializedName("retailer_code")
        @Expose
        var retailerCode: Any? = null

        @SerializedName("target_amount")
        @Expose
        var targetAmount: String? = null

        @SerializedName("default_target_amount")
        @Expose
        var defaultTargetAmount: String? = null

        @SerializedName("kro_target_amount")
        @Expose
        var kroTargetAmount: String? = null

        @SerializedName("status")
        @Expose
        var status: Any? = null

        @SerializedName("created_at")
        @Expose
        var createdAt: Any? = null

        @SerializedName("updated_at")
        @Expose
        var updatedAt: Any? = null

        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("target_amount_re")
        @Expose
        var targetAmountRe: Double? = null

        @SerializedName("target_per_re")
        @Expose
        var targetPerRe: Double? = null

        @SerializedName("target_date")
        @Expose
        var targetDate: String? = null
    }
}