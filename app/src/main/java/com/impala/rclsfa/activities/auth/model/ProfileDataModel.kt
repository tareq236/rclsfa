package com.impala.rclsfa.activities.auth.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class ProfileDataModel {
    @SerializedName("error")
    @Expose
    private var error: String? = null

    @SerializedName("message")
    @Expose
    private var message: String? = null

    @SerializedName("result")
    @Expose
    private var result: Result? = null

    @SerializedName("success")
    @Expose
    private var success: Boolean? = null

    fun getError(): String? {
        return error
    }

    fun setError(error: String?) {
        this.error = error
    }

    fun getMessage(): String? {
        return message
    }

    fun setMessage(message: String?) {
        this.message = message
    }

    fun getResult(): Result? {
        return result
    }

    fun setResult(result: Result?) {
        this.result = result
    }

    fun getSuccess(): Boolean? {
        return success
    }

    fun setSuccess(success: Boolean?) {
        this.success = success
    }

    class Result {
        @SerializedName("id")
        @Expose
        var id: String? = null

        @SerializedName("user_id")
        @Expose
        var userId: String? = null

        @SerializedName("user_info")
        @Expose
        var userInfo: UserInfo? = null

        @SerializedName("mail_id")
        @Expose
        var mailId: String? = null

        @SerializedName("blood_group")
        @Expose
        var bloodGroup: String? = null

        @SerializedName("nid")
        @Expose
        var nid: String? = null

        @SerializedName("phone_number")
        @Expose
        var phoneNumber: String? = null

        @SerializedName("alternative_phone_num_one")
        @Expose
        var alternativePhoneNumOne: String? = null

        @SerializedName("alternative_name_one")
        @Expose
        var alternativeNameOne: String? = null

        @SerializedName("alternative_relation_one")
        @Expose
        var alternativeRelationOne: String? = null

        @SerializedName("alternative_phone_num_two")
        @Expose
        var alternativePhoneNumTwo: String? = null

        @SerializedName("alternative_name_two")
        @Expose
        var alternativeNameTwo: String? = null

        @SerializedName("alternative_relation_two")
        @Expose
        var alternativeRelationTwo: String? = null

        @SerializedName("father_name")
        @Expose
        var fatherName: String? = null

        @SerializedName("father_nid")
        @Expose
        var fatherNid: String? = null

        @SerializedName("mother_name")
        @Expose
        var motherName: String? = null

        @SerializedName("mother_nid")
        @Expose
        var motherNid: String? = null

        @SerializedName("cover_image")
        @Expose
        var coverImage: String? = null

        @SerializedName("avatar_image")
        @Expose
        var avatarImage: String? = null
    }

    class UserInfo {
        @SerializedName("id")
        @Expose
        var id: String? = null

        @SerializedName("sr_code")
        @Expose
        var srCode: String? = null

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
        var mobileNumber: String? = null

        @SerializedName("designation_id")
        @Expose
        var designationId: Int? = null

        @SerializedName("designation")
        @Expose
        var designation: String? = null

        @SerializedName("opt")
        @Expose
        var opt: String? = null

        @SerializedName("token")
        @Expose
        var token: String? = null

        @SerializedName("device_type")
        @Expose
        var deviceType: String? = null

        @SerializedName("latitude")
        @Expose
        var latitude: String? = null

        @SerializedName("longitude")
        @Expose
        var longitude: String? = null
    }
}