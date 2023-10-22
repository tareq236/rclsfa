package com.impala.rclsfa.activities.auth.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class ProfileUpdateModel {
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

        @SerializedName("cover_image")
        @Expose
        var coverImage: Any? = null

        @SerializedName("avatar_image")
        @Expose
        var avatarImage: Any? = null

        @SerializedName("created_at")
        @Expose
        var createdAt: CreatedAt? = null

        @SerializedName("updated_at")
        @Expose
        var updatedAt: UpdatedAt? = null

        @SerializedName("user_id")
        @Expose
        var userId: String? = null

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

        @SerializedName("father_n_id")
        @Expose
        var fatherNId: String? = null

        @SerializedName("mother_name")
        @Expose
        var motherName: String? = null

        @SerializedName("mother_n_id")
        @Expose
        var motherNId: String? = null
    }

    class UpdatedAt {
        @SerializedName("fn")
        @Expose
        var fn: String? = null

        @SerializedName("args")
        @Expose
        var args: List<Any>? = null
    }

    class CreatedAt {
        @SerializedName("fn")
        @Expose
        var fn: String? = null

        @SerializedName("args")
        @Expose
        var args: List<Any>? = null
    }
}