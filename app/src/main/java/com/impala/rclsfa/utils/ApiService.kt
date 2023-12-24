package com.impala.rclsfa.utils

import com.google.gson.JsonObject
import com.impala.rclsfa.attendance.model.AllLeaveAttendListM
import com.impala.rclsfa.attendance.model.ApproveLeaveApplicationM
import com.impala.rclsfa.attendance.model.SaveLeaveAttendM
import com.impala.rclsfa.auth.model.ChangePasswordM
import com.impala.rclsfa.auth.model.ImageUploadModel
import com.impala.rclsfa.auth.model.ProfileDataModel
import com.impala.rclsfa.auth.model.ProfileUpdateModel
import com.impala.rclsfa.order.model.OrderListModel
import com.impala.rclsfa.outletManagement.SearchOutletListModel
import com.impala.rclsfa.outletManagement.UpdateLocationModel
import com.impala.rclsfa.outletManagement.outlet_entry.model.CategoryListModel
import com.impala.rclsfa.outletManagement.outlet_entry.model.DistrictModel
import com.impala.rclsfa.outletManagement.outlet_entry.model.DivisionListModel
import com.impala.rclsfa.outletManagement.outlet_entry.model.RouteListModel
import com.impala.rclsfa.outletManagement.outlet_entry.model.SaveRetailerModel
import com.impala.rclsfa.outletManagement.outlet_entry.model.UpazilaListModel
import com.impala.rclsfa.outletManagement.route_wise_outlet_mapping.model.DistributorListModel
import com.impala.rclsfa.outletManagement.route_wise_outlet_mapping.model.DivisionRouteModel
import com.impala.rclsfa.outletManagement.route_wise_outlet_mapping.model.RouteListBySRModel
import com.impala.rclsfa.outletManagement.route_wise_outlet_mapping.model.SoListModel
import com.impala.rclsfa.outletManagement.route_wise_outlet_mapping.model.ZoneListModel
import com.impala.rclsfa.retailer.model.RetailerListModel
import com.impala.rclsfa.tgt_setup.kro_outlet_selection.model.KroDetailsModel
import com.impala.rclsfa.tgt_setup.kro_outlet_selection.model.KroRouteListM
import com.impala.rclsfa.tgt_setup.kro_outlet_selection.model.RetailerListByKro
import com.impala.rclsfa.tgt_setup.kro_outlet_selection.model.SaveKroTargetModel
import com.impala.rclsfa.tgt_setup.route_wise_tgt_setup.model.RouteListByTgtModel
import com.impala.rclsfa.tgt_setup.route_wise_tgt_setup.model.SaveTargetModel
import com.impala.rclsfa.tgt_setup.route_wise_tgt_setup.model.TgtRouteDetailsM
import com.impala.rclsfa.models.AttendanceResponse
import com.impala.rclsfa.models.LoginRequest
import com.impala.rclsfa.models.LoginResponse
import com.impala.rclsfa.models.MenuResponse
import com.impala.rclsfa.models.RouteWiseTargetModel
import com.impala.rclsfa.models.UsersModel
import com.impala.rclsfa.models.UsersModelResult
import com.impala.rclsfa.tgt_setup.kro_outlet_selection.model.UpdateKroTargetM
import com.impala.rclsfa.tgt_setup.route_wise_tgt_setup.model.UpdateResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("api/leave_attendance_approved_by_asm")
    fun leaveAttendanceApprovedByAsm(
        @Query("id") id: String,
        @Query("user_id") user_id: String,
        @Query("designation_id") designation_id: String
    ): Call<ApproveLeaveApplicationM>

    @GET("api/pending_attendance_approved_by_asm")
    fun imoAttendanceApprovedByAsm(
        @Query("id") id: String,
        @Query("user_id") user_id: String,
        @Query("designation_id") designation_id: String
    ): Call<ApproveLeaveApplicationM>

    @GET("api/update_route_wise_first_approval")
    fun updateAmount(
        @Query("id") id: String,
        @Query("user_id") userId: String,
        @Query("route_id") routeId: String,
        @Query("route_amount") route_amount: String
    ): Call<UpdateResponse>

    @GET("api/route_wise_first_approval")
    fun setApprove(
        @Query("id") id: String
    ): Call<UpdateResponse>

    @GET("api/v3/so_list")
    fun soList(
        @Query("user_id") user_id: String,
        @Query("designation_id") designation_id: String
    ): Call<UsersModel>

    @Multipart
    @POST("api/save_end_attendance_sr_with_image")
    fun saveEveningAttendanceWithImage(
        @Part("sr_id") sr_id: RequestBody,
        @Part("latitude") latitude: RequestBody,
        @Part("longitude") longitude: RequestBody,
        @Part image: MultipartBody.Part
    ): Call<AttendanceResponse>

    @Multipart
    @POST("api/save_attendance_sr_with_image")
    fun saveMorningAttendanceWithImage(
        @Part("sr_id") sr_id: RequestBody,
        @Part("latitude") latitude: RequestBody,
        @Part("longitude") longitude: RequestBody,
        @Part image: MultipartBody.Part
    ): Call<AttendanceResponse>

    @GET("api/v1/mobile/get_menu_list")
    fun getMenuItems(
        @Query("user_id") userId: String,
        @Query("designation_id") designationId: String
    ): Call<MenuResponse>

    @POST("api/v1/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    //tad
    @GET("api/v1/user/user_details/{user_id}")
    fun getUserDetails(
        @Path("user_id") userId: String
    ): Call<ProfileDataModel>

    @FormUrlEncoded
    @POST("api/update_profile")
    fun updateProfile(
        @Field("user_id") userId: String,
        @Field("mail_id") mail_id: String,
        @Field("blood_group") blood_group: String,
        @Field("nid") nid: String,
        @Field("phone_number") phone_number: String,
        @Field("alternative_phone_num_one") alternative_phone_num_one: String,
        @Field("alternative_name_one") alternative_name_one: String,
        @Field("alternative_relation_one") alternative_relation_one: String,
        @Field("alternative_phone_num_two") alternative_phone_num_two: String,
        @Field("alternative_name_two") alternative_name_two: String,
        @Field("alternative_relation_two") alternative_relation_two: String,
        @Field("father_name") father_name: String,
        @Field("father_n_id") father_n_id: String,
        @Field("mother_name") mother_name: String,
        @Field("mother_n_id") mother_n_id: String
    ): Call<ProfileUpdateModel>

    @Multipart
    @POST("api/upload_profile_image")
    fun profileImageUpload(
        @Part user_id: MultipartBody.Part?,
        @Part image: MultipartBody.Part?
    ): Call<ImageUploadModel>


    @GET("api/route_list_by_sr/{user_id}/{designation_id}")
    fun routeList(
        @Path("user_id") userId: String,
        @Path("designation_id") designationId: String
    ): Call<RouteListModel>

    @GET("api/category_list")
    fun categoryList(

    ): Call<CategoryListModel>

    @GET("api/division_list")
    fun divisionList(

    ): Call<DivisionListModel>

    @GET("api/district_list/{id}")
    fun districtList(
        @Path("id") divId: String,
    ): Call<DistrictModel>

    @GET("api/upazila_list/{id}")
    fun upazilaList(
        @Path("id") divId: String,
    ): Call<UpazilaListModel>


    @FormUrlEncoded
    @POST("api/save_new_retailer")
    fun saveNewRetailer(
        @Field("sr_id") srId: String,
        @Field("route_id") routeId: String,
        @Field("retailer_name") retailer_name: String,
        @Field("name_bn") nameBn: String,
        @Field("proprietor_name") proprietorName: String,
        @Field("outlet_category") outletCategory: String,
        @Field("mobile_number") mobileNumber: String,
        @Field("address") address: String,
        @Field("nid") nid: String,
        @Field("birthday") birthday: String,
        @Field("marriage_date") marriageDate: String,
        @Field("first_children_name") firstChildrenName: String,
        @Field("first_children_birthday") firstChildrenBirthDay: String,
        @Field("second_children_name") secondChildrenName: String,
        @Field("second_children_birthday") secondChildrenBirthDay: String,
        @Field("latitude") latitude: String,
        @Field("longitude") longitude: String,
        @Field("division_id") divisionId: String,
        @Field("district_id") districtId: String,
        @Field("upazila_id") upazila_id: String,
        @Field("image_base") image_base: String
    ): Call<SaveRetailerModel>


    @FormUrlEncoded
    @POST("api/retailer_list_by_name")
    fun retailerListByName(
        @Field("sr_id") srId: String,
        @Field("designation_id") designation_id: String,
        @Field("retailer_name") retailer_name: String
    ): Call<RetailerListModel>


    @GET("api/group_list/")
    fun divisionListByRoute(
        @Query("user_id") user_id: String,
        @Query("designation_id") designation_id: String
    ): Call<DivisionRouteModel>

    @GET("api/group_list/")
    fun zoneListByRoute(
        @Query("user_id") user_id: String,
        @Query("designation_id") designation_id: String,
    ): Call<ZoneListModel>

    @GET("api/distributor_list/")
    fun distributorListByRoute(
        @Query("tsm_code") tsm_code: String,
    ): Call<DistributorListModel>

    @GET("api/so_list_from_distributor_id/")
    fun soListListByRoute(
        @Query("distributor_id") distributor_id: String,
    ): Call<SoListModel>

    @GET("api/route_list_by_sr/{sr_code}/{designation_id}")
    fun routeListBySr(
        @Path("sr_code") sr_code: String,
        @Path("designation_id") designation_id: String
    ): Call<RouteListBySRModel>

    @GET("api/route_list_by_sr/{sr_code}/{designation_id}")
    fun routeListBySrTgt(
        @Path("sr_code") sr_code: String,
        @Path("designation_id") designation_id: String
    ): Call<RouteListByTgtModel>

    @GET("api/route_wise_target_list")
    fun routeListBySrTgtApprove(
        @Query("user_id") sr_code: String,
        @Query("designation_id") designation_id: String
    ): Call<RouteWiseTargetModel>
    @FormUrlEncoded
    @POST("api/retailer_list_by_name")
    fun searchOutletByName(
        @Field("sr_id") srId: String,
        @Field("designation_id") designation_id: String,
        @Field("retailer_name") retailer_name: String
    ): Call<SearchOutletListModel>


    @FormUrlEncoded
    @POST("api/update_retailer_geo_location")
    fun updateGeoLocation(
        @Field("id") id: String,
        @Field("latitude") latitude: String,
        @Field("longitude") longitude: String
    ): Call<UpdateLocationModel>

    @GET("api/retailer_list_by_sr_route/{sr_id}/{route_id}/{designation_id}")
    fun retailerListBySrRoute(
        @Path("sr_id") sr_id: String,
        @Path("route_id") route_id: String,
        @Path("designation_id") designation_id: String
    ): Call<TgtRouteDetailsM>

    @FormUrlEncoded
    @POST("api/save_leave_attendance_sr")
    fun saveLeaveAttendanceBySr(
        @Field("sr_code") sr_code: String,
        @Field("absent_from_date") absent_from_date: String,
        @Field("absent_to_date") absent_to_date: String,
        @Field("comments") comments: String,
        @Field("status") status: String
    ): Call<SaveLeaveAttendM>

    @GET("api/leave_attendance_sr_list/{sr_id}")
    fun leaveAttendanceSrList(
        @Path("sr_id") sr_id: String,
    ): Call<AllLeaveAttendListM>

    @GET("api/pending_attendance_sr_list/{sr_id}")
    fun iomAttendanceSrList(
        @Path("sr_id") sr_id: String,
    ): Call<AllLeaveAttendListM>

    @FormUrlEncoded
    @POST("api/save_pending_attendance_sr")
    fun saveIomAttendanceBySr(
        @Field("sr_code") sr_code: String,
        @Field("absent_from_date") absent_from_date: String,
        @Field("absent_to_date") absent_to_date: String,
        @Field("comments") comments: String,
        @Field("status") status: String
    ): Call<SaveLeaveAttendM>

    @Headers("Content-Type: application/json")
    @POST("api/v1/order/sr_order_list")
    fun srOrderList(
        @Body body: JsonObject?
    ): Call<OrderListModel>

//    @Headers("Content-Type: application/json")
//    @POST("v1/login")
//    fun srOrderList(@Body body: JsonObject?): Call<OrderListModel>

    @GET("api/save_tgt_by_sr/{sr_id}/{target_amount}")
    fun saveTargetBySr(
        @Path("sr_id") sr_id: String,
        @Path("target_amount") target_amount: String
    ): Call<SaveTargetModel>

    @GET("api/retailer_list_by_sr_route_kro/{sr_id}")
    fun retailerListBySrRouteKro(
        @Path("sr_id") sr_id: String
    ): Call<RetailerListByKro>

    @GET("api/route_list_by_sr/{sr_id}/{designation_id}")
    fun routeListBySrKro(
        @Path("sr_id") sr_id: String,
        @Path("designation_id") designation_id: String
    ): Call<KroRouteListM>

    @GET("api/retailer_list_by_sr_route/{sr_id}/{route_id}/{designation_id}")
    fun retailerListBySrRouteKro(
        @Path("sr_id") sr_id: String,
        @Path("route_id") route_id: String,
        @Path("designation_id") designation_id: String
    ): Call<KroDetailsModel>

    @GET("api/save_kro_tgt_by_sr/{retailer_id}/{tgt}")
    fun saveKroTgtByRetailer(
        @Path("retailer_id") retailer_id: String,
        @Path("tgt") tgt: String
    ): Call<SaveKroTargetModel>

    @FormUrlEncoded
    @POST("api/change_password")
    fun changePassword(
        @Field("user_id") user_id: String,
        @Field("password") password: String
    ): Call<ChangePasswordM>


    @GET("api/update_kro_tgt_by_sr/{id}/{tgt}")
    fun updateKroTgtByRetailer(
        @Path("id") id: String,
        @Path("tgt") tgt: String
    ): Call<UpdateKroTargetM>


    companion object {
        // This function creates an instance of ApiService
        fun CreateApi1(): ApiService {
            // Set up Retrofit and return an instance of ApiService
            val retrofit = Retrofit.Builder()
                .baseUrl("http://157.230.195.60:9011/") // Replace with your API base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(ApiService::class.java)
        }

        fun CreateApi2(): ApiService {
            // Set up Retrofit and return an instance of ApiService
            val retrofit = Retrofit.Builder()
                .baseUrl("http://157.230.195.60:9012/") // Replace with your API base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}

