package com.impala.rclsfa.activities.outlet_management

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class SearchOutletListModel {
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

        @SerializedName("retailer_name")
        @Expose
        var retailerName: String? = null

        @SerializedName("sr_id")
        @Expose
        var srId: String? = null

        @SerializedName("route_id")
        @Expose
        var routeId: Int? = null

        @SerializedName("name_bn")
        @Expose
        var nameBn: String? = null

        @SerializedName("proprietor_name")
        @Expose
        var proprietorName: String? = null

        @SerializedName("mobile_number")
        @Expose
        var mobileNumber: String? = null

        @SerializedName("address")
        @Expose
        var address: String? = null

        @SerializedName("outlet_category")
        @Expose
        var outletCategory: String? = null

        @SerializedName("birthday")
        @Expose
        var birthday: String? = null

        @SerializedName("marriage_date")
        @Expose
        var marriageDate: String? = null

        @SerializedName("nid")
        @Expose
        var nid: String? = null

        @SerializedName("latitude")
        @Expose
        var latitude: String? = null

        @SerializedName("longitude")
        @Expose
        var longitude: String? = null

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

        @SerializedName("created_at")
        @Expose
        var createdAt: String? = null

        @SerializedName("updated_at")
        @Expose
        var updatedAt: String? = null

        @SerializedName("division_id")
        @Expose
        var divisionId: String? = null

        @SerializedName("district_id")
        @Expose
        var districtId: String? = null

        @SerializedName("upazilla_id")
        @Expose
        var upazillaId: Any? = null

        @SerializedName("union_id")
        @Expose
        var unionId: Any? = null

        @SerializedName("image")
        @Expose
        var image: Any? = null

        @SerializedName("target_amount_re")
        @Expose
        var targetAmountRe: String? = null

        @SerializedName("target_per_re")
        @Expose
        var targetPerRe: String? = null

        @SerializedName("ach")
        @Expose
        var ach: Int? = null

        @SerializedName("con")
        @Expose
        var con: Int? = null
    }
}