package com.impala.rclsfa.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("PREFERENCE_NAME",Context.MODE_PRIVATE)


    var userId: String?
        get() = sharedPreferences.getString("user_id", "")
        set(userId) = sharedPreferences.edit().putString("user_id", userId!!).apply()
    var designationId: Int?
        get() = sharedPreferences.getInt("designation_id", 0)
        set(designationId) = sharedPreferences.edit().putInt("designation_id", designationId!!).apply()


    var routeName: String?
        get() = sharedPreferences.getString("routeName", "")
        set(routeName) = sharedPreferences.edit().putString("routeName", routeName).apply()

    var categoryName: String?
        get() = sharedPreferences.getString("categoryName", "")
        set(categoryName) = sharedPreferences.edit().putString("categoryName", categoryName).apply()

    var routeId: Int?
        get() = sharedPreferences.getInt("routeId", 0)
        set(routeId) = sharedPreferences.edit().putInt("routeId", routeId!!).apply()

    var categoryId: Int?
        get() = sharedPreferences.getInt("categoryId", 0)
        set(categoryId) = sharedPreferences.edit().putInt("categoryId", categoryId!!).apply()

    var divId: Int?
        get() = sharedPreferences.getInt("divId", 0)
        set(divId) = sharedPreferences.edit().putInt("divId", divId!!).apply()

    var divName: String?
        get() = sharedPreferences.getString("divName", "")
        set(divName) = sharedPreferences.edit().putString("divName", divName).apply()

    var districtId: Int?
        get() = sharedPreferences.getInt("districtId", 0)
        set(districtId) = sharedPreferences.edit().putInt("districtId", districtId!!).apply()

    var districtName: String?
        get() = sharedPreferences.getString("districtName", "")
        set(districtName) = sharedPreferences.edit().putString("districtName", districtName).apply()

    var upzId: Int?
        get() = sharedPreferences.getInt("upzId", 0)
        set(upzId) = sharedPreferences.edit().putInt("upzId", upzId!!).apply()

    var upzName: String?
        get() = sharedPreferences.getString("upzName", "")
        set(upzName) = sharedPreferences.edit().putString("upzName", upzName).apply()

    var isSaveLogin: Boolean?
        get() = sharedPreferences.getBoolean("is_save", false)
        set(isSaveLogin) = sharedPreferences.edit().putBoolean("is_save", isSaveLogin!!).apply()

    var email: String?
        get() = sharedPreferences.getString("email", "")
        set(email) = sharedPreferences.edit().putString("email", email).apply()
}