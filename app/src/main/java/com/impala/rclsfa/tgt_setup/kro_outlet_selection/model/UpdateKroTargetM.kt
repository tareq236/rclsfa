package com.impala.rclsfa.tgt_setup.kro_outlet_selection.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class UpdateKroTargetM {
    @SerializedName("success")
    @Expose
    private var success: Boolean? = null

    @SerializedName("message")
    @Expose
    private var message: String? = null

    @SerializedName("result")
    @Expose
    private var result: List<Int?>? = null

    fun getSuccess(): Boolean? {
        return success
    }

    fun setSuccess(success: Boolean?) {
        this.success = success
    }

    fun getMessage(): String? {
        return message
    }

    fun setMessage(message: String?) {
        this.message = message
    }

    fun getResult(): List<Int?>? {
        return result
    }

    fun setResult(result: List<Int?>?) {
        this.result = result
    }
}