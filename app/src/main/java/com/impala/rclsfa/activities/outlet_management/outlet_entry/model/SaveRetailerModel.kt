package com.impala.rclsfa.activities.outlet_management.outlet_entry.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import com.impala.rclsfa.activities.auth.model.ProfileUpdateModel.UpdatedAt







class SaveRetailerModel {
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

    class CreatedAt {
        @SerializedName("fn")
        @Expose
        var fn: String? = null

        @SerializedName("args")
        @Expose
        var args: List<Any>? = null
    }

    class Result {
        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("created_at")
        @Expose
        var createdAt: CreatedAt? = null

        @SerializedName("updated_at")
        @Expose
        var updatedAt: UpdatedAt? = null

        @SerializedName("sr_id")
        @Expose
        var srId: String? = null

        @SerializedName("route_id")
        @Expose
        var routeId: String? = null

        @SerializedName("retailer_name")
        @Expose
        var retailerName: String? = null

        @SerializedName("name_bn")
        @Expose
        var nameBn: String? = null

        @SerializedName("proprietor_name")
        @Expose
        var proprietorName: String? = null

        @SerializedName("outlet_category")
        @Expose
        var outletCategory: String? = null

        @SerializedName("mobile_number")
        @Expose
        var mobileNumber: String? = null

        @SerializedName("address")
        @Expose
        var address: String? = null

        @SerializedName("nid")
        @Expose
        var nid: String? = null

        @SerializedName("birthday")
        @Expose
        var birthday: String? = null

        @SerializedName("marriage_date")
        @Expose
        var marriageDate: String? = null

        @SerializedName("first_children_name")
        @Expose
        var firstChildrenName: String? = null

        @SerializedName("first_children_birthday")
        @Expose
        var firstChildrenBirthday: String? = null

        @SerializedName("second_children_name")
        @Expose
        var secondChildrenName: String? = null

        @SerializedName("second_children_birthday")
        @Expose
        var secondChildrenBirthday: String? = null

        @SerializedName("latitude")
        @Expose
        var latitude: String? = null

        @SerializedName("longitude")
        @Expose
        var longitude: String? = null

        @SerializedName("division_id")
        @Expose
        var divisionId: String? = null

        @SerializedName("district_id")
        @Expose
        var districtId: String? = null

        @SerializedName("upazilla_id")
        @Expose
        var upazillaId: String? = null

        @SerializedName("union_id")
        @Expose
        var unionId: String? = null

        @SerializedName("image")
        @Expose
        var image: String? = null
    }

    class UpdatedAt {
        @SerializedName("fn")
        @Expose
        var fn: String? = null

        @SerializedName("args")
        @Expose
        var args: List<Any>? = null
    }
}