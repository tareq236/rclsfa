package com.impala.rclsfa.outletManagement.route_wise_outlet_mapping.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class SoListModel {
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

        @SerializedName("sr_code")
        @Expose
        var srCode: Int? = null

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
        var srAddress: String? = null

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


        override fun toString(): String {
            return name.toString() // What to display in the Spinner list.
        }

        override  fun equals(other: Any?): Boolean {
            if (other is Result) {
                val c: Result = other as Result
                if (c.name.equals(name) && c.id === id && c.name === name )
                    return true
            }
            return false
        }
    }
}