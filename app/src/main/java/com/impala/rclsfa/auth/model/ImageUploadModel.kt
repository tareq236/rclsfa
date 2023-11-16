package com.impala.rclsfa.auth.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class ImageUploadModel {
    @SerializedName("success")
    @Expose
    private var success: Boolean? = null

    @SerializedName("result")
    @Expose
    private var result: List<Int?>? = null

    @SerializedName("message")
    @Expose
    private var message: String? = null

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

    fun getMessage(): String? {
        return message
    }

    fun setMessage(message: String?) {
        this.message = message
    }
}