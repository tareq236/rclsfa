package com.impala.rclsfa.utils

import com.impala.rclsfa.activities.auth.model.ImageUploadModel
import com.impala.rclsfa.activities.auth.model.ProfileDataModel
import com.impala.rclsfa.activities.auth.model.ProfileUpdateModel
import com.impala.rclsfa.models.AttendanceResponse
import com.impala.rclsfa.models.LoginRequest
import com.impala.rclsfa.models.LoginResponse
import com.impala.rclsfa.models.MenuResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

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
        @Query("user_id") userId : String,
        @Query("designation_id") designationId : String
    ): Call<MenuResponse>

    @POST("api/v1/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    //tad
    @GET("api/v1/user/user_details/{user_id}")
    fun getUserDetails(
        @Path("user_id") userId : String
    ): Call<ProfileDataModel>

    @FormUrlEncoded
    @POST("api/update_profile")
    fun updateProfile(
        @Field("user_id") userId : String,
        @Field("mail_id") mail_id : String,
        @Field("blood_group") blood_group : String,
        @Field("nid") nid : String,
        @Field("phone_number") phone_number : String,
        @Field("alternative_phone_num_one") alternative_phone_num_one : String,
        @Field("alternative_name_one") alternative_name_one : String,
        @Field("alternative_relation_one") alternative_relation_one : String,
        @Field("alternative_phone_num_two") alternative_phone_num_two : String,
        @Field("alternative_name_two") alternative_name_two : String,
        @Field("alternative_relation_two") alternative_relation_two : String,
        @Field("father_name") father_name : String,
        @Field("father_n_id") father_n_id : String,
        @Field("mother_name") mother_name : String,
        @Field("mother_n_id") mother_n_id : String
    ): Call<ProfileUpdateModel>

    @Multipart
    @POST("api/upload_profile_image")
    fun profileImageUpload(
        @Part  user_id: MultipartBody.Part?,
        @Part image: MultipartBody.Part?
    ): Call<ImageUploadModel>



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

