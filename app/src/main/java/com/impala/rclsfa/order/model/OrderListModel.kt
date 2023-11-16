package com.impala.rclsfa.order.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class OrderListModel {
    @SerializedName("error")
    @Expose
    private var error: String? = null

    @SerializedName("message")
    @Expose
    private var message: String? = null

    @SerializedName("pagination")
    @Expose
    private var pagination: Pagination? = null

    @SerializedName("result")
    @Expose
    private var result: List<Result?>? = null

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

    fun getPagination(): Pagination? {
        return pagination
    }

    fun setPagination(pagination: Pagination?) {
        this.pagination = pagination
    }

    fun getResult(): List<Result?>? {
        return result
    }

    fun setResult(result: List<Result?>?) {
        this.result = result
    }

    fun getSuccess(): Boolean? {
        return success
    }

    fun setSuccess(success: Boolean?) {
        this.success = success
    }

    class CategoryDetails {
        @SerializedName("id")
        @Expose
        var id: String? = null

        @SerializedName("category_name")
        @Expose
        var categoryName: String? = null

        @SerializedName("status")
        @Expose
        var status: String? = null
    }
    class DesignationDetails {
        @SerializedName("id")
        @Expose
        var id: String? = null

        @SerializedName("designation_code")
        @Expose
        var designationCode: String? = null

        @SerializedName("designation_name")
        @Expose
        var designationName: String? = null

        @SerializedName("steps")
        @Expose
        var steps: Int? = null
    }

    class Order {
        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("order_id")
        @Expose
        var orderId: Int? = null

        @SerializedName("product_id")
        @Expose
        var productId: String? = null

        @SerializedName("product_details")
        @Expose
        var productDetails: ProductDetails? = null

        @SerializedName("price")
        @Expose
        var price: String? = null

        @SerializedName("discount_percentage")
        @Expose
        var discountPercentage: String? = null

        @SerializedName("discount_amount")
        @Expose
        var discountAmount: String? = null

        @SerializedName("display_sample")
        @Expose
        var displaySample: String? = null

        @SerializedName("qty")
        @Expose
        var qty: String? = null

        @SerializedName("org_total")
        @Expose
        var orgTotal: String? = null

        @SerializedName("total")
        @Expose
        var total: String? = null

        @SerializedName("ctn_factor")
        @Expose
        var ctnFactor: String? = null

        @SerializedName("product_status")
        @Expose
        var productStatus: String? = null

        @SerializedName("is_sample_available")
        @Expose
        var isSampleAvailable: String? = null

        @SerializedName("is_sample")
        @Expose
        var isSample: String? = null

        @SerializedName("for_which_product_code")
        @Expose
        var forWhichProductCode: String? = null

        @SerializedName("quantity_for_sample")
        @Expose
        var quantityForSample: String? = null

        @SerializedName("sample_qty")
        @Expose
        var sampleQty: String? = null

        @SerializedName("sample_product_code")
        @Expose
        var sampleProductCode: String? = null

        @SerializedName("note_type")
        @Expose
        var noteType: String? = null

        @SerializedName("note_details")
        @Expose
        var noteDetails: String? = null

        @SerializedName("send_qty")
        @Expose
        var sendQty: String? = null

        @SerializedName("send_date")
        @Expose
        var sendDate: String? = null

        @SerializedName("return_type")
        @Expose
        var returnType: String? = null

        @SerializedName("send_amount")
        @Expose
        var sendAmount: String? = null

        @SerializedName("received_qty")
        @Expose
        var receivedQty: String? = null

        @SerializedName("received_date")
        @Expose
        var receivedDate: String? = null

        @SerializedName("received_amount")
        @Expose
        var receivedAmount: String? = null

        @SerializedName("return_list")
        @Expose
        var returnList: Any? = null
    }
    class Pagination {
        @SerializedName("limit")
        @Expose
        var limit: Int? = null

        @SerializedName("offset")
        @Expose
        var offset: Int? = null

        @SerializedName("total_rows")
        @Expose
        var totalRows: Int? = null
    }

    class ProductDetails {
        @SerializedName("id")
        @Expose
        var id: String? = null

        @SerializedName("category_id")
        @Expose
        var categoryId: String? = null

        @SerializedName("category_details")
        @Expose
        var categoryDetails: CategoryDetails? = null

        @SerializedName("product_name")
        @Expose
        var productName: String? = null

        @SerializedName("price")
        @Expose
        var price: String? = null

        @SerializedName("discount_percentage")
        @Expose
        var discountPercentage: String? = null

        @SerializedName("discount_amount")
        @Expose
        var discountAmount: String? = null

        @SerializedName("display_sample_for_qty")
        @Expose
        var displaySampleForQty: String? = null

        @SerializedName("display_sample_qty")
        @Expose
        var displaySampleQty: String? = null

        @SerializedName("ctn_discount_percentage")
        @Expose
        var ctnDiscountPercentage: String? = null

        @SerializedName("sku")
        @Expose
        var sku: String? = null

        @SerializedName("ctn_factor")
        @Expose
        var ctnFactor: String? = null

        @SerializedName("product_status")
        @Expose
        var productStatus: String? = null

        @SerializedName("quantity_for_sample")
        @Expose
        var quantityForSample: String? = null

        @SerializedName("sample_quantity")
        @Expose
        var sampleQuantity: String? = null

        @SerializedName("sample_product_code")
        @Expose
        var sampleProductCode: String? = null

        @SerializedName("ctn_quantity_for_sample")
        @Expose
        var ctnQuantityForSample: String? = null

        @SerializedName("ctn_sample_quantity")
        @Expose
        var ctnSampleQuantity: String? = null

        @SerializedName("ctn_sample_product_code")
        @Expose
        var ctnSampleProductCode: String? = null

        @SerializedName("is_sample")
        @Expose
        var isSample: String? = null

        @SerializedName("status")
        @Expose
        var status: String? = null
    }

    class Result {
        @SerializedName("id")
        @Expose
        var id: Int? = null

        @SerializedName("user_id")
        @Expose
        var userId: String? = null

        @SerializedName("user_details")
        @Expose
        var userDetails: UserDetails? = null

        @SerializedName("retailer_id")
        @Expose
        var retailerId: String? = null

        @SerializedName("retailer_details")
        @Expose
        var retailerDetails: RetailerDetails? = null

        @SerializedName("order_date")
        @Expose
        var orderDate: String? = null

        @SerializedName("order_list")
        @Expose
        var orderList: List<Order>? = null

        @SerializedName("device_type")
        @Expose
        var deviceType: String? = null

        @SerializedName("status")
        @Expose
        var status: Int? = null

        @SerializedName("draft")
        @Expose
        var draft: Int? = null

        @SerializedName("visit_type")
        @Expose
        var visitType: String? = null

        @SerializedName("order_list_log")
        @Expose
        var orderListLog: List<Any>? = null

        @SerializedName("latitude")
        @Expose
        var latitude: String? = null

        @SerializedName("longitude")
        @Expose
        var longitude: String? = null

        @SerializedName("created_at")
        @Expose
        var createdAt: String? = null
    }

    class RetailerDetails {
        @SerializedName("id")
        @Expose
        var id: String? = null

        @SerializedName("retailer_name")
        @Expose
        var retailerName: String? = null

        @SerializedName("sr_id")
        @Expose
        var srId: String? = null

        @SerializedName("route_id")
        @Expose
        var routeId: Int? = null

        @SerializedName("outlet_category")
        @Expose
        var outletCategory: String? = null

        @SerializedName("name_bn")
        @Expose
        var nameBn: String? = null

        @SerializedName("mobile_number")
        @Expose
        var mobileNumber: String? = null

        @SerializedName("marriage_date")
        @Expose
        var marriageDate: String? = null

        @SerializedName("wife_name")
        @Expose
        var wifeName: String? = null

        @SerializedName("wife_birthday")
        @Expose
        var wifeBirthday: String? = null

        @SerializedName("children_one_name")
        @Expose
        var childrenOneName: String? = null

        @SerializedName("children_one_birthday")
        @Expose
        var childrenOneBirthday: String? = null

        @SerializedName("children_two_name")
        @Expose
        var childrenTwoName: String? = null

        @SerializedName("children_two_birthday")
        @Expose
        var childrenTwoBirthday: String? = null

        @SerializedName("nid")
        @Expose
        var nid: String? = null

        @SerializedName("latitude")
        @Expose
        var latitude: String? = null

        @SerializedName("longitude")
        @Expose
        var longitude: String? = null

        @SerializedName("present_division_id")
        @Expose
        var presentDivisionId: String? = null

        @SerializedName("present_district_id")
        @Expose
        var presentDistrictId: String? = null

        @SerializedName("present_upozila_id")
        @Expose
        var presentUpozilaId: String? = null

        @SerializedName("present_union_id")
        @Expose
        var presentUnionId: String? = null

        @SerializedName("present_post_office_name")
        @Expose
        var presentPostOfficeName: String? = null

        @SerializedName("present_post_code")
        @Expose
        var presentPostCode: String? = null

        @SerializedName("present_road_area")
        @Expose
        var presentRoadArea: String? = null

        @SerializedName("present_house")
        @Expose
        var presentHouse: String? = null

        @SerializedName("permanent_division_id")
        @Expose
        var permanentDivisionId: String? = null

        @SerializedName("permanent_district_id")
        @Expose
        var permanentDistrictId: String? = null

        @SerializedName("permanent_upozila_id")
        @Expose
        var permanentUpozilaId: String? = null

        @SerializedName("permanent_union_id")
        @Expose
        var permanentUnionId: String? = null

        @SerializedName("permanent_post_office_name")
        @Expose
        var permanentPostOfficeName: String? = null

        @SerializedName("permanent_post_code")
        @Expose
        var permanentPostCode: String? = null

        @SerializedName("permanent_road_area")
        @Expose
        var permanentRoadArea: String? = null

        @SerializedName("permanent_house")
        @Expose
        var permanentHouse: String? = null

        @SerializedName("photo_self")
        @Expose
        var photoSelf: String? = null

        @SerializedName("photo_nid")
        @Expose
        var photoNid: String? = null

        @SerializedName("photo_institution")
        @Expose
        var photoInstitution: String? = null

        @SerializedName("contribution")
        @Expose
        var contribution: Int? = null

        @SerializedName("tgt")
        @Expose
        var tgt: String? = null

        @SerializedName("ach")
        @Expose
        var ach: String? = null
    }

    class UserDetails {
        @SerializedName("id")
        @Expose
        var id: String? = null

        @SerializedName("name")
        @Expose
        var name: String? = null

        @SerializedName("mobile_number")
        @Expose
        var mobileNumber: String? = null

        @SerializedName("designation_id")
        @Expose
        var designationId: Int? = null

        @SerializedName("designation_details")
        @Expose
        var designationDetails: DesignationDetails? = null

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