package com.impala.rclsfa.activities.outlet_management.route_wise_outlet_mapping.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class DistributorListModel {
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

        @SerializedName("distributor_name")
        @Expose
        var distributorName: String? = null

        @SerializedName("distributor_area")
        @Expose
        var distributorArea: String? = null

        @SerializedName("distributor_thana")
        @Expose
        var distributorThana: Any? = null

        @SerializedName("distributor_district")
        @Expose
        var distributorDistrict: Any? = null

        @SerializedName("mobile_number")
        @Expose
        var mobileNumber: Any? = null

        @SerializedName("distributor_address")
        @Expose
        var distributorAddress: Any? = null

        @SerializedName("balance")
        @Expose
        var balance: Any? = null

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

        @SerializedName("distributor_permanent_address")
        @Expose
        var distributorPermanentAddress: Any? = null

        @SerializedName("distributor_email")
        @Expose
        var distributorEmail: Any? = null

        @SerializedName("distributor_blood_group")
        @Expose
        var distributorBloodGroup: Any? = null

        @SerializedName("distributor_nid")
        @Expose
        var distributorNid: Any? = null

        @SerializedName("distributor_father_name")
        @Expose
        var distributorFatherName: Any? = null

        @SerializedName("distributor_father_nid")
        @Expose
        var distributorFatherNid: Any? = null

        @SerializedName("distributor_mother_name")
        @Expose
        var distributorMotherName: Any? = null

        @SerializedName("distributor_mother_nid")
        @Expose
        var distributorMotherNid: Any? = null

        @SerializedName("distributor_alternative_phone_number")
        @Expose
        var distributorAlternativePhoneNumber: Any? = null

        @SerializedName("distributor_alternative_name")
        @Expose
        var distributorAlternativeName: Any? = null

        @SerializedName("distributor_alternative_relation")
        @Expose
        var distributorAlternativeRelation: Any? = null

        @SerializedName("distributor_photo")
        @Expose
        var distributorPhoto: Any? = null

        @SerializedName("distributor_photo_father")
        @Expose
        var distributorPhotoFather: Any? = null

        @SerializedName("distributor_photo_mother")
        @Expose
        var distributorPhotoMother: Any? = null


        override fun toString(): String {
            return distributorName.toString() // What to display in the Spinner list.
        }

        override  fun equals(other: Any?): Boolean {
            if (other is DistributorListModel.Result ) {
                val c: DistributorListModel.Result  = other as DistributorListModel.Result
                if (c.distributorName.equals(distributorName) && c.id === id && c.distributorName === distributorName )
                    return true
            }
            return false
        }
    }
}