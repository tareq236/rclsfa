package com.impala.rclsfa.activities.outlet_management.outlet_entry.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class CategoryListModel {
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
        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("category_name")
        @Expose
        var categoryName: String? = null

        @SerializedName("status")
        @Expose
        var status: Int? = null
    }
}