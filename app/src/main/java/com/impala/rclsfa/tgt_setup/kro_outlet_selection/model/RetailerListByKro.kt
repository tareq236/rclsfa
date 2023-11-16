package com.impala.rclsfa.tgt_setup.kro_outlet_selection.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class RetailerListByKro {
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
        var birthday: Any? = null

        @SerializedName("marriage_date")
        @Expose
        var marriageDate: Any? = null

        @SerializedName("nid")
        @Expose
        var nid: Any? = null

        @SerializedName("latitude")
        @Expose
        var latitude: Any? = null

        @SerializedName("longitude")
        @Expose
        var longitude: Any? = null

        @SerializedName("first_children_name")
        @Expose
        var firstChildrenName: Any? = null

        @SerializedName("first_children_birthday")
        @Expose
        var firstChildrenBirthday: Any? = null

        @SerializedName("second_children_name")
        @Expose
        var secondChildrenName: Any? = null

        @SerializedName("second_children_birthday")
        @Expose
        var secondChildrenBirthday: Any? = null

        @SerializedName("created_at")
        @Expose
        var createdAt: String? = null

        @SerializedName("updated_at")
        @Expose
        var updatedAt: String? = null

        @SerializedName("division_id")
        @Expose
        var divisionId: Any? = null

        @SerializedName("district_id")
        @Expose
        var districtId: Any? = null

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
    }
}